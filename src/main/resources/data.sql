insert into USERS(id, login, email, password)
values (3001, 'user1', 'user1@gmail.com', '$2a$10$L8BTPKYxARZdsv8DRTR7KeH.Q2CwqkpFd0FVKmAY4xwKzrBJ3ILUu'),
       (3002, 'user2', 'user2@gmail.com', '$2a$10$L8BTPKYxARZdsv8DRTR7KeH.Q2CwqkpFd0FVKmAY4xwKzrBJ3ILUu'),
       (3003, 'admin', 'admin@gmail.com', '$2a$10$L8BTPKYxARZdsv8DRTR7KeH.Q2CwqkpFd0FVKmAY4xwKzrBJ3ILUu');

insert into USER_ROLES(user_id, roles)
values (3001, 'ROLE_USER'),
       (3002, 'ROLE_USER'),
       (3002, 'ROLE_USER'),
       (3003, 'ROLE_ADMIN');

