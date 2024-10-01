-- Users
INSERT INTO users (user_id, password) VALUES ('user1', 'password1');
INSERT INTO users (user_id, password) VALUES ('user2', 'password2');

-- Lectures
INSERT INTO lecture (title, lecture_name, capacity, lecture_at, lecture_status) VALUES ('lecturer1', '강사 1', 15, NOW(), 'OPENED');
INSERT INTO lecture (title, lecture_name, capacity, lecture_at, lecture_status) VALUES ('lecturer2', '강사 2', 15, NOW(), 'OPENED');

-- Lecture_history
INSERT INTO lecture_history (user_id, lecture_id, applied_at, history_status)
VALUES (1, 1, '2024-09-29 12:00:00', 'SUCCESS');