DROP DATABASE IF EXISTS smsdb;

CREATE DATABASE smsdb
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE smsdb;


create table student (
    stu_id VARCHAR(20) PRIMARY KEY,  -- Changed to INT for Auto_Increment
    stu_name VARCHAR(100) NOT NULL,
    stu_dob DATE,
    gender ENUM('Male', 'Female') DEFAULT 'Male',
    contact VARCHAR(30) NOT NULL,
    email VARCHAR(100)UNIQUE,  -- Increased length for email
    address TEXT,
    photo_path VARCHAR(255)
);


create table course(
	course_id int  Auto_Increment PRIMARY KEY ,
	course_name VARCHAR(100)not null unique ,
	description TEXT,
	duration DOUBLE,
	fee int
	
);

create table teacher(

	teacher_id int Auto_Increment PRIMARY KEY,
	name VARCHAR (100)not null unique ,
	qualification VARCHAR (100),
	contact VARCHAR (20),
	email VARCHAR (100)UNIQUE,
    address VARCHAR (255),
    photo VARCHAR(255)

	

);

create table teacher_subjects(

	tsid int Auto_Increment PRIMARY key,
	teacher_id int not null,
    course_id int not null,
	FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
	
);

create table classes(

	class_id int Auto_Increment PRIMARY KEY,
    date Date not null default current_date,
	class_name VARCHAR (100) not null,
	course_id int not null,
	scedule int not null,
	limit_stu int ,
    status tinyint,
	FOREIGN Key (course_id) REFERENCES course(course_id)


);


create table teacher_class(

    teacher_classid int PRIMARY KEY Auto_Increment,
    teacher_id int,
    class_id int,
    FOREIGN Key (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN Key (class_id) REFERENCES classes(class_id)


);

create table register(

	re_id VARCHAR (20) PRIMARY Key,
	re_date DATE not null,
	class_id int not null,
	stu_id VARCHAR(20)not null,
	FOREIGN Key (class_id) REFERENCES classes(class_id),
	FOREIGN Key (stu_id) REFERENCES student(stu_id)

);
create table attendance(

	attendance_id VARCHAR(20) PRIMARY Key,
	date DATE Not null,
	stu_id VARCHAR(20)not null,
	class_id int not null,
	status ENUM('Present','Absent')DEFAULT 'Absent',
	FOREIGN Key (stu_id)REFERENCES student (stu_id),
	FOREIGN Key (class_id) REFERENCES classes(class_id)	
);

create table certificate_templates(

	ctid int AUTO_INCREMENT PRIMARY key,
	course_id int not null,
	template VARCHAR(100),
	FOREIGN KEY (course_id) REFERENCES course(course_id)

);


create table certificate(

	cer_id VARCHAR (20)PRIMARY Key,
	stu_id VARCHAR(20)not null,
	issue_date DATE not null,
   	ctid int  not null,
	FOREIGN Key (stu_id) REFERENCES student(stu_id),
	FOREIGN KEY (ctid) REFERENCES certificate_templates(ctid)

);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Store hashed passwords
    role ENUM('Admin', 'Teacher', 'Student'),
    email VARCHAR(100) UNIQUE
);








