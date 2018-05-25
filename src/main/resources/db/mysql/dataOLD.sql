INSERT IGNORE INTO teachers VALUES (1, 'James', 'Carter');

INSERT IGNORE INTO students ('id', 'is_deleted', 'address', 'birth_date', 'email', 'first_name', 'last_name', 'password', 'telephone', 'school_form_id') 
VALUES ('1', 'false', 'aaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Alfred', 'Angus', 'aaa', '111111', '1');



INSERT IGNORE INTO teachers VALUES (2, 'Helen', 'Leary');
INSERT IGNORE INTO teachers VALUES (3, 'Linda', 'Douglas');
INSERT IGNORE INTO teachers VALUES (4, 'Rafael', 'Ortega');
INSERT IGNORE INTO teachers VALUES (5, 'Henry', 'Stevens');
INSERT IGNORE INTO teachers VALUES (6, 'Sharon', 'Jenkins');

INSERT IGNORE INTO subjects VALUES (1, 'Maths');
INSERT IGNORE INTO subjects VALUES (2, 'Biology');
INSERT IGNORE INTO subjects VALUES (3, 'Geography');

INSERT IGNORE INTO teacher_subjects VALUES (2, 1);
INSERT IGNORE INTO teacher_subjects VALUES (3, 2);
INSERT IGNORE INTO teacher_subjects VALUES (3, 3);
INSERT IGNORE INTO teacher_subjects VALUES (4, 2);
INSERT IGNORE INTO teacher_subjects VALUES (5, 1);

INSERT IGNORE INTO jobs VALUES (1, 'doctor');
INSERT IGNORE INTO jobs VALUES (2, 'painter');
INSERT IGNORE INTO jobs VALUES (3, 'asles assistant');
INSERT IGNORE INTO jobs VALUES (4, 'manager');
INSERT IGNORE INTO jobs VALUES (5, 'sailor');
INSERT IGNORE INTO jobs VALUES (6, 'cook');

INSERT IGNORE INTO students VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT IGNORE INTO students VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT IGNORE INTO students VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT IGNORE INTO students VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT IGNORE INTO students VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT IGNORE INTO students VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT IGNORE INTO students VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT IGNORE INTO students VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT IGNORE INTO students VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT IGNORE INTO students VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT IGNORE INTO persons VALUES (1, 'Leo', '2000-09-07', 1, 1);
INSERT IGNORE INTO persons VALUES (2, 'Basil', '2002-08-06', 6, 2);
INSERT IGNORE INTO persons VALUES (3, 'Rosy', '2001-04-17', 2, 3);
INSERT IGNORE INTO persons VALUES (4, 'Jewel', '2000-03-07', 2, 3);
INSERT IGNORE INTO persons VALUES (5, 'Iggy', '2000-11-30', 3, 4);
INSERT IGNORE INTO persons VALUES (6, 'George', '2000-01-20', 4, 5);
INSERT IGNORE INTO persons VALUES (7, 'Samantha', '1995-09-04', 1, 6);
INSERT IGNORE INTO persons VALUES (8, 'Max', '1995-09-04', 1, 6);


/* 
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (1, 1, 1, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (2, 1, 6, 4);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (3, 2, 2, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (4, 2, 3, 2);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (5, 2, 4, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (6, 3, 6, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (7, 4, 5, 4);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (8, 5, 4, 5);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id) VALUES (9, 6, 2);
*/
INSERT IGNORE INTO persons VALUES (9, 'Lucky', '1999-08-06', 5, 7);
INSERT IGNORE INTO persons VALUES (10, 'Mulligan', '1997-02-24', 2, 8);
INSERT IGNORE INTO persons VALUES (11, 'Freddy', '2000-03-09', 5, 9);
INSERT IGNORE INTO persons VALUES (12, 'Lucky', '2000-06-24', 2, 10);
INSERT IGNORE INTO persons VALUES (13, 'Sly', '2002-06-08', 1, 10);
