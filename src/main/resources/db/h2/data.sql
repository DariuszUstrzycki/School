INSERT  INTO school_forms (id, is_deleted, name) VALUES (1, 0, '1A');
INSERT  INTO school_forms (id, is_deleted, name) VALUES (2, 0, '2B');
INSERT  INTO school_forms (id, is_deleted, name) VALUES (3, 0, '3C');
INSERT  INTO school_forms (id, is_deleted, name) VALUES (4, 0, '4D');
INSERT  INTO school_forms (id, is_deleted, name) VALUES (5, 0, '5E');
INSERT  INTO school_forms (id, is_deleted, name) VALUES (6, 0, '6F');

INSERT  INTO subjects (id, is_deleted, name) VALUES (1, 0, 'Biology');
INSERT  INTO subjects (id, is_deleted, name) VALUES (2, 0, 'Physics');
INSERT  INTO subjects (id, is_deleted, name) VALUES (3, 0, 'IT');
INSERT  INTO subjects (id, is_deleted, name) VALUES (4, 0, 'Maths');
INSERT  INTO subjects (id, is_deleted, name) VALUES (5, 0, 'English');
INSERT  INTO subjects (id, is_deleted, name) VALUES (6, 0, 'Geography');

INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('1', 0, 'aaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Alfred', 'Angus', 'aaa', '111111', '1');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('2', 0, 'bbbbbbbbb', '2000-04-01', 'b@wp.pl', 'Ben', 'Benneth', 'bbb', '111111', '1');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('3', 0, 'ccccccccc', '2000-04-01', 'c@wp.pl', 'Claude', 'Camel', 'ccc', '111111', '1');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('4', 0, 'ddddddddd', '2000-04-01', 'd@wp.pl', 'Dick', 'Dickinson', 'ddd', '111111', '2');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('5', 0, 'eeeeeeeee', '2000-04-01', 'e@wp.pl', 'Ed', 'Edmunds', 'eee', '111111', '2');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('6', 0, 'fffffffff', '2000-04-01', 'f@wp.pl', 'Finn', 'Forest', 'fff', '111111', '3');
INSERT  INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, school_form_id) VALUES ('7', 0, 'ggggggggg', '2000-04-01', 'g@wp.pl', 'Greg', 'Green', 'ggg', '111111', '4');

INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('1', 0, 'aaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Alfred', 'ATeacher', 'aaa', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('2', 0, 'bbbbbbbbb', '2000-04-01', 'b@wp.pl', 'Ben', 'BTeacher', 'bbb', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('3', 0, 'ccccccccc', '2000-04-01', 'c@wp.pl', 'Claude', 'CTeacher', 'ccc', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('4', 0, 'ddddddddd', '2000-04-01', 'd@wp.pl', 'Dick', 'DTeacher', 'ddd', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('5', 0, 'eeeeeeeee', '2000-04-01', 'e@wp.pl', 'Ed', 'ETeacher', 'eee', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('6', 0, 'fffffffff', '2000-04-01', 'f@wp.pl', 'Finn', 'FTeacher', 'fff', '111111');
INSERT  INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('7', 0, 'ggggggggg', '2000-04-01', 'g@wp.pl', 'Greg', 'GTeacher', 'ggg', '111111');

INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (1, 1, 1);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (2, 1, 6);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (3, 2, 2);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (4, 2, 3);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (5, 2, 4);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (6, 3, 6);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (7, 4, 5);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (8, 5, 4);
INSERT  INTO teachers_subjects (id, teacher_id, subject_id) VALUES (9, 6, 2);
