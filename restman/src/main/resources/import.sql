-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212')
insert into AbstractUser (email, password, role) values ('admin@admin', 'admin', 'ADMIN')
insert into AbstractUser (email, password, role) values ('user@user', 'user', 'USER')
insert into AbstractUser (email, password, role) values ('manager@manager', 'manager', 'MANAGER')
insert into User (first_name, phoneNumber, second_name, id) values ('user', 1234567890, 'user senior', 1)
insert into Restaurant (address, information, name, id) values ('restaurace', 'restaurace', 'restaurace', 2)