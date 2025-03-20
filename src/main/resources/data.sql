TRUNCATE TABLE bulletins RESTART IDENTITY CASCADE;

INSERT INTO bulletins (title, author, content, password, views, created_at, updated_at, deleted) 
VALUES 
    ('Welcome to Bulletin Board', 'Admin', 'This is the first post in our bulletin board.', 'pass123', 10, '2024-03-19 10:00:00', NULL, FALSE),
    ('Spring Boot Tips', 'JohnDoe', 'Here are some useful Spring Boot tips.', 'secure123', 5, '2024-03-18 09:30:00', '2024-03-18 12:45:00', FALSE),
    ('Database Migration', 'JaneDoe', 'How to manage database migrations efficiently.', 'dbadmin', 3, '2024-03-17 14:20:00', NULL, FALSE),
    ('Deleted Post Example', 'GhostUser', 'This post was deleted.', 'ghost123', 0, '2024-03-16 08:00:00', NULL, TRUE),
    ('Frontend Best Practices', 'Alice', 'Best practices for writing clean and maintainable frontend code.', 'frontend123', 7, '2024-03-15 11:15:00', '2024-03-15 15:00:00', FALSE),
    ('Effective REST API Design', 'Bob', 'Designing scalable and efficient REST APIs.', 'restapi456', 12, '2024-03-14 13:45:00', NULL, FALSE),
    ('Security in Web Applications', 'Charlie', 'Understanding web security vulnerabilities and how to prevent them.', 'secure789', 6, '2024-03-13 10:10:00', NULL, FALSE),
    ('CSS Tricks You Should Know', 'Daisy', 'Useful CSS tricks to enhance your web development skills.', 'cssmagic', 8, '2024-03-12 09:00:00', NULL, FALSE),
    ('Scaling Your Backend Services', 'Eve', 'Techniques for scaling backend services for high traffic applications.', 'scaleit', 4, '2024-03-11 16:30:00', NULL, FALSE),
    ('Best JavaScript Libraries', 'Frank', 'Top JavaScript libraries to boost productivity.', 'jsbest', 9, '2024-03-10 14:20:00', NULL, FALSE);
