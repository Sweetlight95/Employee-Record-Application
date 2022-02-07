create database if not exists employee_db;

create user if not exists 'employee_user'@'localhost' identified by 'palm5';
grant all privileges on employee_db.* to 'employee_user'@'localhost';
flush privileges;