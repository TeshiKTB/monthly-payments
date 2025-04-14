CREATE TABLE IF NOT EXISTS users (
	uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	username VARCHAR(50) UNIQUE NOT NULL,
	password TEXT NOT NULL,
	role VARCHAR(50) NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT now(),
	updated_at TIMESTAMP NOT NULL DEFAULT now()
);

MERGE INTO users u
USING (VALUES ('admin', '$2a$10$t/xEnyXknU91UDjNG2OanuqvqnLuxs8yjZEeRo9ghR0gm02vDwHH.', 'ADMIN')) AS merge (username, password, role)
ON merge.username = u.username
WHEN MATCHED THEN
    UPDATE SET password = merge.password,
               role = merge.role
WHEN NOT MATCHED THEN
    INSERT (username, password, role)
	VALUES (merge.username, merge.password, merge.role);