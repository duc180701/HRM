package com.training.hrm.services;

import com.training.hrm.dto.MachineAttendanceRequest;
import com.training.hrm.dto.ManuallyAttendanceRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Attendance;
import com.training.hrm.repositories.AttendanceRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance createByMachineAttendance (MachineAttendanceRequest machineAttendanceRequest) throws InvalidException, ServiceRuntimeException {
        try {
            Attendance attendance = attendanceRepository.findAttendanceByDate(machineAttendanceRequest.getTimeStamp().toLocalDate());
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setDate(machineAttendanceRequest.getTimeStamp().toLocalDate());
            }

            attendance.setEmployeeID(machineAttendanceRequest.getEmployeeID());
            attendance.setNote("");

            LocalTime localTime = machineAttendanceRequest.getTimeStamp().toLocalTime().withNano(0).withSecond(0);
            if (localTime.getHour() < 12 && attendance.getCheckInTime() == null) {
                attendance.setCheckInTime(localTime);
            } else if (localTime.getHour() >= 12 && attendance.getCheckOutTime() == null) {
                attendance.setCheckOutTime(localTime);
            }

            if (attendance.getCheckInTime() == null || attendance.getCheckOutTime() == null) {
                attendance.setWorkHour(LocalTime.of(0, 0));
            } else {
                Duration duration = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
                long hour = duration.toHours();
                long minute = duration.toMinutes() % 60;

                attendance.setWorkHour(LocalTime.of((int) hour, (int) minute));
            }

            return attendanceRepository.save(attendance);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e ) {
            throw new ServiceRuntimeException("An error occurred while creating this attendance by machine: " + e.getMessage());
        }
    }

    public Attendance createByManually (ManuallyAttendanceRequest manuallyAttendanceRequest) throws InvalidException, ServiceRuntimeException {
        try {
            Attendance attendance = attendanceRepository.findAttendanceByDate(manuallyAttendanceRequest.getDate());
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setDate(manuallyAttendanceRequest.getDate());
            }

            attendance.setEmployeeID(manuallyAttendanceRequest.getEmployeeID());
            attendance.setNote("");

            if (manuallyAttendanceRequest.getCheckInTime().isBlank() == false && attendance.getCheckInTime() == null) {
                String checkInTime = manuallyAttendanceRequest.getCheckInTime();
                LocalTime time;
                try {
                    time = LocalTime.parse(checkInTime, DateTimeFormatter.ofPattern("HH:mm"));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check in time format. Please use HH:mm.");
                }
                try {
                    int hour1 = Integer.parseInt(checkInTime.substring(0, 1));
                    int hour2 = Integer.parseInt(checkInTime.substring(1, 2));
                    if ((hour1 * 10 + hour2) < 12) {
                        attendance.setCheckInTime(time);
                    } else {
                        throw new InvalidException("Check in time must be less than 12 noon");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please enter a valid time");
                }
            }

            if (manuallyAttendanceRequest.getCheckOutTime().isBlank() == false && attendance.getCheckOutTime() == null) {
                String checkOutTime = manuallyAttendanceRequest.getCheckOutTime();
                LocalTime time;
                try {
                    time = LocalTime.parse(checkOutTime, DateTimeFormatter.ofPattern("HH:mm"));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check out time format. Please use HH:mm.");
                }
                try {
                    int hour1 = Integer.parseInt(checkOutTime.substring(0, 1));
                    int hour2 = Integer.parseInt(checkOutTime.substring(1, 2));
                    if ((hour1 * 10 + hour2) >= 12) {
                        attendance.setCheckOutTime(time);
                    } else {
                        throw new InvalidException("Check out time must be equal or greater than 12 noon");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please enter a valid time");
                }
            }

            if (attendance.getCheckInTime() == null || attendance.getCheckOutTime() == null) {
                attendance.setWorkHour(LocalTime.of(0, 0));
            } else {
                Duration duration = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
                long hour = duration.toHours();
                long minute = duration.toMinutes() % 60;

                attendance.setWorkHour(LocalTime.of((int) hour, (int) minute));
            }

            return attendanceRepository.save(attendance);
        } catch (NumberFormatException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating this attendance by manually: " + e.getMessage());
        }
    }

    public void importExcelFile(MultipartFile file) throws IOException, ServiceRuntimeException {
        try {
            Attendance attendance = new Attendance();
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String mnv = row.getCell(0).getStringCellValue();
                if (mnv.isBlank()) {
                    throw new InvalidException("Please enter a valid employee id");
                } else {
                    try {
                        Long employeeID = Long.parseLong(mnv);
                        attendance.setEmployeeID(employeeID);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Error converting employee id from string to long");
                    }
                }

                String date = row.getCell(2).getStringCellValue();
                if (date.isBlank()) {
                    throw new InvalidException("Please enter a valid date");
                }
                try {
                    LocalDate formatDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    attendance.setDate(formatDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Please use dd-MM-YYYY.");
                }

                String checkInTime = row.getCell(4).getStringCellValue();
                if (checkInTime.isBlank()) {
                    throw new InvalidException("Please enter a valid check in time");
                }
                try {
                    LocalTime formatTime = LocalTime.parse(checkInTime, DateTimeFormatter.ofPattern("HH:mm"));
                    if (formatTime.getHour() < 12) {
                        attendance.setCheckInTime(formatTime);
                    } else {
                        throw new InvalidException("Check in time must be less than 12 noon");
                    }
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check in time format. Please use HH:mm.");
                }

                String checkOutTime = row.getCell(5).getStringCellValue();
                if (checkOutTime.isBlank()) {
                    throw new InvalidException("Please enter a valid check out time");
                }
                try {
                    LocalTime formatTime = LocalTime.parse(checkOutTime, DateTimeFormatter.ofPattern("HH:mm"));
                    if (formatTime.getHour() >= 12) {
                        attendance.setCheckOutTime(formatTime);
                    } else {
                        throw new InvalidException("Check out time must be equal or greater than 12 noon");
                    }
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check out time format. Please use HH:mm.");
                }

                String workHour = row.getCell(6).getStringCellValue();
                if (workHour.isBlank()) {
                    throw new InvalidException("Please enter a valid work hour");
                }
                try {
                    LocalTime formatWorkHour = LocalTime.parse(workHour, DateTimeFormatter.ofPattern("HH:mm"));
                    attendance.setWorkHour(formatWorkHour);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check out time format. Please use HH:mm.");
                }

                String note = row.getCell(10).getStringCellValue();
                if (workHour.isBlank()) {
                    attendance.setNote("");
                } else {
                    attendance.setNote(note);
                }

                attendanceRepository.save(attendance);
            }
        } catch (IOException e) {
            throw new IOException("An error occurred while system reading this file: " + e.getMessage());
        } catch (InvalidException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating this attendance by file: " + e.getMessage());
        }
    }
}
