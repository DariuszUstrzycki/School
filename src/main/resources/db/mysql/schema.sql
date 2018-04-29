CREATE DATABASE IF NOT EXISTS a_school;

ALTER DATABASE a_school
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON a_school.* TO springuser@localhost IDENTIFIED BY 'springuser';

USE a_school;

CREATE TABLE IF NOT EXISTS teachers (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS subjects (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS teacher_subjects (
  teacher_id INT(4) UNSIGNED NOT NULL,
  subject_id INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (teacher_id) REFERENCES teachers(id),
  FOREIGN KEY (subject_id) REFERENCES subjects(id),
  UNIQUE (teacher_id,subject_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS jobs (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS students (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS persons (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  birth_date DATE,
  job_id INT(4) UNSIGNED NOT NULL,
  student_id INT(4) UNSIGNED NOT NULL,
  INDEX(name),
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (job_id) REFERENCES jobs(id)
) engine=InnoDB;

