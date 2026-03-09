CREATE DATABASE car_rental_db;

USE car_rental_db;

-- Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    role VARCHAR(20)
);

-- Cars table
CREATE TABLE cars (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50),
    car_number VARCHAR(20),
    rent_price INT,
    status VARCHAR(20)
);

-- Bookings table
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    car_id INT,
    booking_date DATE,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);

-- Amount table
CREATE TABLE amount (
    user_id INT,
    amount INT
);

-- Default admin account
INSERT INTO users(username,password,role)
VALUES('admin','admin123','admin');
