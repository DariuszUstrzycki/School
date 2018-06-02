INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (1, 0, '1A');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (2, 0, '2B');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (3, 0, '3C');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (4, 0, '4D');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (5, 0, '5E');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (6, 0, '6F');

INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (1, 0, 'Biology');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (2, 0, 'Physics');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (3, 0, 'IT');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (4, 0, 'Maths');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (5, 0, 'English');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (6, 0, 'Geography');

INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('1', 0, 'aaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Anna', 'Angus', 'aaa', '111111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('2', 0, 'bbbbbbbbb', '2000-04-01', 'b@wp.pl', 'Betty', 'Benneth', 'bbb', '111111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('3', 0, 'ccccccccc', '2000-04-01', 'c@wp.pl', 'Camille', 'Camel', 'ccc', '111111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('4', 0, 'ddddddddd', '2000-04-01', 'd@wp.pl', 'Daria', 'Dickinson', 'ddd', '111111', '2');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('5', 0, 'eeeeeeeee', '2000-04-01', 'e@wp.pl', 'Edith', 'Edmunds', 'eee', '111111', '2');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('6', 0, 'fffffffff', '2000-04-01', 'f@wp.pl', 'Fuscia', 'Forest', 'fff', '111111', '3');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('7', 0, 'ggggggggg', '2000-04-01', 'g@wp.pl', 'Grace', 'Grog', 'ggg', '111111', '4');

INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('1', 0, 'aaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Alfred', 'Albinoss', 'aaa', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('2', 0, 'bbbbbbbbb', '2000-04-01', 'b@wp.pl', 'Ben', 'Brown', 'bbb', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('3', 0, 'ccccccccc', '2000-04-01', 'c@wp.pl', 'Claude', 'Cherryred', 'ccc', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('4', 0, 'ddddddddd', '2000-04-01', 'd@wp.pl', 'Dick', 'Dark', 'ddd', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('5', 0, 'eeeeeeeee', '2000-04-01', 'e@wp.pl', 'Ed', 'Emerald', 'eee', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('6', 0, 'fffffffff', '2000-04-01', 'f@wp.pl', 'Finn', 'Flowery', 'fff', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('7', 0, 'ggggggggg', '2000-04-01', 'g@wp.pl', 'Greg', 'Green', 'ggg', '111111');

INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (1, 1, 1, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (2, 1, 6, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (3, 2, 2, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (4, 2, 3, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (5, 2, 4, 2);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (6, 3, 6, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (7, 4, 5, 4);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (8, 5, 4, 4);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (9, 6, 2, 3);

INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (1, 1, 1, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (2, 1, 1, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (3, 1, 1, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (4, 1, 2, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (5, 1, 2, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (6, 1, 6, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (7, 3, 1, '5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (8, 3, 2, '2');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (9, 3, 3, '2.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(10, 3, 4, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(11, 2, 1, '4.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(12, 2, 3, '2');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(13, 4, 3, '3.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(14, 4, 3, '5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(15, 5, 2, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(16, 6, 2, '6');



