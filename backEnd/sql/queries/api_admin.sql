-- Get information about all users. All needed to create an object
(SELECT * 
FROM `unipsdb`.`user` AS u
LEFT JOIN `unipsdb`.`picture` AS p
ON u.user_id = p.user_id
WHERE u.role_id=0)
UNION
(SELECT * 
FROM `unipsdb`.`user` AS u
RIGHT JOIN `unipsdb`.`picture` AS p
ON u.user_id = p.user_id
WHERE u.role_id=0);


-- Get information from one user by username
SELECT * 
FROM `unipsdb`.`user` AS u
LEFT JOIN `unipsdb`.`picture` AS p
ON u.user_id=p.user_id
WHERE u.username='kathy' AND u.role_id=0;

-- Insert a new user
INSERT INTO `unipsdb`.`user` 
(`username`, `password`, `email`,`question1`, `question2`, `status_id`, `role_id`, `token`)
VALUES (?, ?, ?, ?, ?, ?, ?, ?);

-- Get User by token
SELECT username FROM `unipsdb`.`user` u WHERE u.token=?;

-- Update User status by username
UPDATE `unipsdb`.`user` u 
SET u.status_id=? WHERE u.username=?;

-- Upate user by username
UPDATE `unipsdb`.`user` u
SET u.username = ?, 
	u.password = ?,
    u.email = ?,
    u.question1 = ?,
    u.question2 = ?
WHERE u.username = ?;

-- Delete user by username
DELETE FROM `unipsdb`.`user`
WHERE username = '?';

-- Check if user Exists
SELECT u.username FROM `unipsdb`.`user`AS u
WHERE u.username = 'kathy';

-- ----------------------------------------
-- Admin Users and business interacttions
-- ----------------------------------------

-- Get user status
SELECT u.status_id FROM `unipsdb`.`user` AS u WHERE u.username = 'kathy';


-- Update status for user

UPDATE `unipsdb`.`user` AS u
SET u.status_id = 2
WHERE u.username = 'kathy';







