package com.training.hrm.services;

import com.training.hrm.customservices.CustomUserDetails;
import com.training.hrm.dto.MachineAttendanceRequest;
import com.training.hrm.dto.ManuallyAttendanceRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.ApproveAttendance;
import com.training.hrm.models.Attendance;
import com.training.hrm.models.User;
import com.training.hrm.repositories.ApproveAttendanceRepository;
import com.training.hrm.repositories.AttendanceRepository;
import com.training.hrm.repositories.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApproveAttendanceRepository approveAttendanceRepository;

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
            attendance.setUsed(false);

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
            List<Attendance> listAttendance = attendanceRepository.findAttendanceByEmployeeID(manuallyAttendanceRequest.getEmployeeID());
            if (!listAttendance.isEmpty()) {
                for (Attendance attendance1 : listAttendance) {
                    if (attendance1.getEmployeeID().equals(manuallyAttendanceRequest.getEmployeeID()) && attendance1.getDate().equals(manuallyAttendanceRequest.getDate())) {
                        throw new InvalidException("Employee attendance already exits");
                    }
                }
            }
            Attendance attendance = new Attendance();
            attendance.setDate(manuallyAttendanceRequest.getDate());
            attendance.setEmployeeID(manuallyAttendanceRequest.getEmployeeID());
            attendance.setNote("");
            attendance.setUsed(false);

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
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                    continue;
                }
                Cell check = row.getCell(1);
                if (check == null) {
                    continue;
                }
                Attendance attendance = new Attendance();
                double mnv = row.getCell(1).getNumericCellValue();
                System.out.print(mnv + " ");
                if (String.valueOf(mnv).isBlank()) {
                    throw new InvalidException("Please enter a valid employee id");
                } else {
                    try {
                        long employeeID = (long) mnv;
                        attendance.setEmployeeID(employeeID);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Error converting employee id from string to long");
                    }
                }

                Date date = row.getCell(5).getDateCellValue();
                System.out.print(date + " ");
                if (String.valueOf(date).isBlank()) {
                    throw new InvalidException("Please enter a valid date");
                }
                try {
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    attendance.setDate(localDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Please use dd-MM-YYYY.");
                }

//                String checkInTime = row.getCell(7).getNumericCellValue();
                Cell checkInTimeCell = row.getCell(7);
                System.out.print(checkInTimeCell + " ");
                if (checkInTimeCell == null) {
                    throw new InvalidException("Please enter a valid check in time");
                }
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    if(checkInTimeCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(checkInTimeCell)) {
                        Date checkInTime = checkInTimeCell.getDateCellValue();
                        String formattedTime = timeFormat.format(checkInTime);
                        LocalTime localTime = LocalTime.parse(formattedTime, DateTimeFormatter.ofPattern("HH:mm"));
                        attendance.setCheckInTime(localTime);
                    }

                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check in time format. Please use HH:mm.");
                }

//                String checkOutTime = row.getCell(5).getStringCellValue();
                Cell checkOutTimeCell = row.getCell(8);
                System.out.print(checkOutTimeCell + " ");
                if (checkOutTimeCell == null) {
                    throw new InvalidException("Please enter a valid check out time");
                }
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    if(checkOutTimeCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(checkOutTimeCell)) {
                        Date checkOutTime = checkOutTimeCell.getDateCellValue();
                        String formattedTime = timeFormat.format(checkOutTime);
                        LocalTime localTime = LocalTime.parse(formattedTime, DateTimeFormatter.ofPattern("HH:mm"));
                        attendance.setCheckOutTime(localTime);
                    }
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid check out time format. Please use HH:mm.");
                }

//                String workHour = row.getCell(6).getStringCellValue();
                Cell workHourCell = row.getCell(9);
                System.out.print(workHourCell + " ");
                String note = row.getCell(13).getStringCellValue();
                System.out.println(note + " ");
                if (workHourCell == null) {
                    attendance.setNote("");
                    throw new InvalidException("Please enter a valid work hour");
                }
                attendance.setNote(note);
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    if(workHourCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(workHourCell)) {
                        Date workHour = workHourCell.getDateCellValue();
                        String formattedTime = timeFormat.format(workHour);
                        LocalTime localTime = LocalTime.parse(formattedTime, DateTimeFormatter.ofPattern("HH:mm"));
                        attendance.setWorkHour(localTime);
                    }
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid work hour time format. Please use HH:mm.");
                }

                attendance.setUsed(false);

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

    public List<Attendance> getAttendanceByDate(LocalDate startDate, LocalDate endDate) throws InvalidException, ServiceRuntimeException {
        try {
            if (!startDate.isBefore(endDate)) {
                throw new InvalidException("Start date must before end date");
            }
            List<Attendance> listAttendanceTable = new ArrayList<>();
            List<Attendance> exitsListAttendance = attendanceRepository.findAll();
            if (exitsListAttendance.isEmpty()) {
                throw new InvalidException("No attendance during this period");
            }
            for (Attendance attendance : exitsListAttendance) {
                if ((!attendance.getDate().isBefore(startDate) && !attendance.getDate().isAfter(endDate))) {
                         listAttendanceTable.add(attendance);
                }
            }
            if(listAttendanceTable.isEmpty()) {
                throw new InvalidException("No attendance during this period");
            }

            return listAttendanceTable;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting this attendance: " + e.getMessage());
        }
    }

    public List<ApproveAttendance> createAttendanceTable(LocalDate startDate, LocalDate endDate) throws ServiceRuntimeException, InvalidException {
        try {
            if (!startDate.isBefore(endDate)) {
                throw new InvalidException("Start date must before end date");
            }
            List<Attendance> exitsListAttendance = attendanceRepository.findAll();
            if (exitsListAttendance.isEmpty()) {
                throw new InvalidException("No attendance during this period");
            }
            List<ApproveAttendance> listApproveAttendance = new ArrayList<>();
            for (Attendance attendance : exitsListAttendance) {
                if ((!attendance.getDate().isBefore(startDate) && !attendance.getDate().isAfter(endDate))) {
                    if (attendance.isUsed() == false) {
                        ApproveAttendance approveAttendance = new ApproveAttendance();
                        approveAttendance.setAttendanceID(attendance.getAttendanceID());
                        approveAttendance.setEmployeeID(attendance.getEmployeeID());
                        approveAttendance.setCheckInTime(attendance.getCheckInTime());
                        approveAttendance.setCheckOutTime(attendance.getCheckOutTime());
                        approveAttendance.setDate(attendance.getDate());
                        approveAttendance.setWorkHour(attendance.getWorkHour());
                        approveAttendance.setNote(attendance.getNote());
                        approveAttendance.setApproveFirstBy("");
                        approveAttendance.setApproveFinalBy("");
                        approveAttendance.setApproveFirstDate(null);
                        approveAttendance.setApproveSecondDate(null);
                        approveAttendance.setApproveStatus("PENDING");

                        attendance.setUsed(true);
                        approveAttendanceRepository.save(approveAttendance);
                        listApproveAttendance.add(approveAttendance);

                    }
                }
            }
            if(listApproveAttendance.isEmpty()) {
                throw new InvalidException("No attendance during this period");
            }

            return listApproveAttendance;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting this attendance: " + e.getMessage());
        }
    }

    public Attendance updateAttendance(Long attendanceID, ManuallyAttendanceRequest manuallyAttendanceRequest) throws ServiceRuntimeException, InvalidException {
        try {
            Attendance exitsAttendance = attendanceRepository.findAttendanceByAttendanceID(attendanceID);
            if (exitsAttendance == null) {
                throw new InvalidException("Attendance not found");
            }
            List<Attendance> listCheckAttendance = attendanceRepository.findAttendanceByEmployeeID(manuallyAttendanceRequest.getEmployeeID());
            for (Attendance checkAttendance : listCheckAttendance) {
                if (checkAttendance.getEmployeeID().equals(manuallyAttendanceRequest.getEmployeeID()) && checkAttendance.getDate().equals(manuallyAttendanceRequest.getDate())) {
                    throw new InvalidException("Employee attendance already exits");
                }
            }

            exitsAttendance.setEmployeeID(manuallyAttendanceRequest.getEmployeeID());
            exitsAttendance.setDate(manuallyAttendanceRequest.getDate());

            if (manuallyAttendanceRequest.getCheckInTime().isBlank() == false && exitsAttendance.getCheckInTime() == null) {
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
                        exitsAttendance.setCheckInTime(time);
                    } else {
                        throw new InvalidException("Check in time must be less than 12 noon");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please enter a valid time");
                }
            }

            if (manuallyAttendanceRequest.getCheckOutTime().isBlank() == false && exitsAttendance.getCheckOutTime() == null) {
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
                        exitsAttendance.setCheckOutTime(time);
                    } else {
                        throw new InvalidException("Check out time must be equal or greater than 12 noon");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please enter a valid time");
                }
            }

            if (exitsAttendance.getCheckInTime() == null || exitsAttendance.getCheckOutTime() == null) {
                exitsAttendance.setWorkHour(LocalTime.of(0, 0));
            } else {
                Duration duration = Duration.between(exitsAttendance.getCheckInTime(), exitsAttendance.getCheckOutTime());
                long hour = duration.toHours();
                long minute = duration.toMinutes() % 60;

                exitsAttendance.setWorkHour(LocalTime.of((int) hour, (int) minute));
            }

            attendanceRepository.save(exitsAttendance);
            return exitsAttendance;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting this attendance: " + e.getMessage());
        }
    }

    public void approveAttendanceByMonth(String month, String username) throws ServiceRuntimeException, InvalidException, IllegalStateException{
        try {
            User user = userRepository.findUserByUsername(username);
            String role = user.getRole();

            int monthNumber = Integer.parseInt(month);
            Month monthParse = Month.of(monthNumber);
            List<ApproveAttendance> listApproveAttendance = approveAttendanceRepository.findAll();
            for (ApproveAttendance approveAttendance : listApproveAttendance) {
                if (approveAttendance.getDate().getMonth().equals(monthParse)) {
                    if ((role.equals("PHO_PHONG_NS") || role.equals("TRUONG_PHONG_NS")) && approveAttendance.getApproveFirstBy().isEmpty() && approveAttendance.getApproveStatus().equals("PENDING")) {
                        approveAttendance.setApproveFirstBy(username);
                        approveAttendance.setApproveStatus("Human resources department signed");
                        approveAttendance.setApproveFirstDate(LocalDate.now());
                        approveAttendanceRepository.save(approveAttendance);
                    }
                    if (role.equals("BAN_GIAM_DOC") && !approveAttendance.getApproveFirstBy().isEmpty() && approveAttendance.getApproveStatus().equals("Human resources department signed") && approveAttendance.getApproveFinalBy().isEmpty()) {
                        approveAttendance.setApproveFinalBy(username);
                        approveAttendance.setApproveStatus("Board of directors signed");
                        approveAttendance.setApproveSecondDate(LocalDate.now());
                        approveAttendanceRepository.save(approveAttendance);
                    }
                }
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Can not get information authentication");
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting this attendance: " + e.getMessage());
        }
    }
}
