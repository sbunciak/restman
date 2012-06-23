/* adding user with role ADMIN */
insert into AbstractUser (email, password, role) values ('admin@admin', 'admin', 'ADMIN')
/* adding user with role USER */
insert into AbstractUser (email, password, role) values ('user@user', 'user', 'USER')
insert into User (first_name, phone_number, second_name, id) values ('user', 1234567890, 'user senior', 2)
/* adding restaurant with role MANAGER */
insert into AbstractUser (email, password, role) values ('manager@manager', 'manager', 'MANAGER')
insert into Restaurant (address, information, name, id) values ('restaurace', 'restaurace', 'restaurace', 3)
insert into MenuItem (name, prize, weight, id_restaurant) values ('Kneedl', 150, 150, 3)
insert into MenuItem (name, prize, weight, id_restaurant) values ('Vajco', 20, 15, 3)
/* adding restaurant with role MANAGER */
insert into AbstractUser (email, password, role) values ('manager2@manager2', 'manager2', 'MANAGER')
insert into Restaurant (address, information, name, id) values ('restaurace2', 'restaurace2', 'restaurace2', 4)
insert into MenuItem (name, prize, weight, id_restaurant) values ('Karfiol', 150, 150, 4)
insert into MenuItem (name, prize, weight, id_restaurant) values ('Zeler', 20, 15, 4)

/* adding restaurant with role MANAGER */
insert into AbstractUser (email, password, role) values ('manager3@manager3', 'manager3', 'MANAGER')
insert into Restaurant (address, information, name, id) values ('restaurace3', 'restaurace3', 'restaurace3', 5)


/* adding reservation */
insert into Reservation (id_reservation, id_user, id_restaurant, time, table_number, seats) values (1, 2, 3, '2008-7-04', 15, 5)
