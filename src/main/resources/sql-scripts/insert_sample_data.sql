-- Chèn dữ liệu mẫu vào bảng persons
INSERT INTO persons (full_name, gender, birth_of_date, citizen_identity_id, citizen_identity_date, citizen_identity_where, citizen_identity_out_date, pass_port_id, pass_port_date, pass_port_where, pass_port_out_date, permanent_address, current_address, phone_number, email) VALUES
('John Doe', 'Male', '1990-01-01', '012345678901', '2010-01-01', 'Hanoi', '2025-01-01', '01234567', '2010-01-01', 'Hanoi', '2025-01-01', '123 Main St', '123 Main St', '0123456789', 'johndoe@example.com'),
('Jane Smith', 'Female', '1992-02-02', '012345678902', '2011-02-02', 'Saigon', '2026-02-02', '01234568', '2011-02-02', 'Saigon', '2026-02-02', '456 Elm St', '456 Elm St', '0123456790', 'janesmith@example.com'),
('Alice Johnson', 'Female', '1985-03-03', '012345678903', '2012-03-03', 'Danang', '2027-03-03', '01234569', '2012-03-03', 'Danang', '2027-03-03', '789 Oak St', '789 Oak St', '0123456791', 'alicejohnson@example.com'),
('Bob Brown', 'Male', '1988-04-04', '012345678904', '2013-04-04', 'Hue', '2028-04-04', '01234570', '2013-04-04', 'Hue', '2028-04-04', '101 Pine St', '101 Pine St', '0123456792', 'bobbrown@example.com'),
('Charlie Davis', 'Male', '1995-05-05', '012345678905', '2014-05-05', 'Hanoi', '2029-05-05', '01234571', '2014-05-05', 'Hanoi', '2029-05-05', '202 Birch St', '202 Birch St', '0123456793', 'charliedavis@example.com');

-- Chèn dữ liệu mẫu vào bảng personnels
INSERT INTO personnels (level, education, graduation_major, graduation_school, graduation_year, internal_phone_number, internal_email, department, position, direct_management_staff, status) VALUES
('Senior', 'Bachelor', 'Computer Science', 'Hanoi University', 2010, '0123456789', 'john.doe@company.com', 'IT', 'Developer', 'Jane Smith', 'DANG_LAM_VIEC'),
('Junior', 'Master', 'Information Technology', 'Saigon University', 2012, '0123456790', 'jane.doe@company.com', 'IT', 'Tester', 'John Doe', 'DANG_LAM_VIEC'),
('Mid-level', 'Bachelor', 'Software Engineering', 'Danang University', 2011, '0123456791', 'alice.johnson@company.com', 'IT', 'Project Manager', 'Bob Brown', 'NGHI_CHE_DO'),
('Lead', 'PhD', 'Data Science', 'Hue University', 2008, '0123456792', 'bob.brown@company.com', 'Data', 'Data Scientist', 'Charlie Davis', 'NGHI_THAI_SAN'),
('Entry', 'Bachelor', 'Information Systems', 'Hanoi University', 2015, '0123456793', 'charlie.davis@company.com', 'IT', 'System Administrator', 'Alice Johnson', 'DANG_LAM_VIEC');

-- Chèn dữ liệu mẫu vào bảng contracts
INSERT INTO contracts (contract_type, salary, start_date, end_date, version) VALUES
('Full-time', 1000000, '2023-01-01', '2024-01-01', 1),
('Part-time', 2000000, '2023-02-01', '2023-08-01', 1),
('Internship', 3000000, '2023-03-01', '2023-06-01', 1),
('Temporary', 4000000, '2023-04-01', '2023-09-01', 1),
('Contractor', 5000000, '2023-05-01', '2023-12-01', 1);

-- Chèn dữ liệu mẫu vào bảng employees
INSERT INTO employees (personnel_id, person_id, contract_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 0);

-- Chèn dữ liệu mẫu vào bảng attendances
INSERT INTO attendances (employee_id, date, work_hours, check_in_time, check_out_time, note, used) VALUES
(1, '2023-07-30', '08:00:00', '08:00:00', '16:00:00', 'Normal workday', false),
(2, '2023-07-1', '07:30:00', '08:30:00', '16:00:00', 'Late check-in', false),
(3, '2023-07-2', '09:00:00', '08:00:00', '17:00:00', 'Overtime', false),
(4, '2023-07-3', '08:00:00', '09:00:00', '17:00:00', 'Meeting in the morning', false),
(5, '2023-07-4', '06:30:00', '08:00:00', '14:30:00', 'Left early for appointment', false);

-- Chèn dữ liệu mẫu vào bảng roles
INSERT INTO roles (name, description) VALUES
('HE_THONG', ''),
('ADMIN', ''),
('GUEST', ''),
('NHAN_VIEN', ''),
('BAN_GIAM_DOC', ''),
('TRUONG_PHONG_HC', ''),
('PHO_PHONG_HC', ''),
('NHAN_VIEN_HC', ''),
('TRUONG_PHONG_NS', ''),
('PHO_PHONG_NS', ''),
('NHAN_VIEN_NS', ''),
('TRUONG_PHONG_TB', ''),
('PHO_PHONG_TB', ''),
('NHAN_VIEN_TB', ''),
('TRUONG_PHONG_TT', ''),
('PHO_PHONG_TT', ''),
('NHAN_VIEN_TT', ''),
('TO_TRUONG_MEDIA', ''),
('TO_PHO_MEDIA', ''),
('NHAN_VIEN_MEDIA', ''),
('TO_TRUONG_SK', ''),
('TO_PHO_SK', ''),
('NHAN_VIEN_SK', ''),
('TRUONG_PHONG_CSKH', ''),
('PHO_PHONG_CSKH', ''),
('NHAN_VIEN_CSKH', '');

-- Chèn dữ liệu mẫu vào bảng work_status
INSERT INTO work_status (status_id, name, description) VALUES
('TV', 'THU_VIEC', ''),
('DNV', 'DA_NGHI_VIEC', ''),
('DLV', 'DANG_LAM_VIEC', ''),
('NCD', 'NGHI_CHE_DO', ''),
('NTS', 'NGHI_THAI_SAN', '');

INSERT INTO users (employee_id, user_name, pass_word, role, avatar) VALUES
(1, 'duc180701', 'Mrp0np0n!', "HE_THONG", '');