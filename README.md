# Bank Application - Java GUI with JDBC

## Overview
This is a simple bank application built using Java based in MVC pattern that allows users to perform various banking operations such as deposits, 
withdrawals, and transfers. The application features a graphical user interface (GUI) built using Java Swing and stores user 
data and transactions in a relational database using JDBC.

## Features
- User Registration and Login: Users can create an account and log in securely.
- Deposit and Withdrawal: Users can deposit and withdraw funds from their accounts.
- Transfer Funds: Users can transfer money between accounts.
- Transaction History: All user transactions (deposits, withdrawals, transfers) are stored in a database and can be viewed.
- GUI: A user-friendly interface built using Java Swing for ease of use.
- JDBC Database Integration: The application communicates with a relational database to store user data and transactions.

## Technologies Used
- Java: Core programming language.
- Java Swing: For building the graphical user interface (GUI).
- JDBC: For connecting and interacting with the database.
- MySQL: The relational database used for storing data.

## Prerequisites
- Before running the application, make sure you have the following installed:
- Java JDK 8 or higher
- MySQL (or any other relational database that supports JDBC)


## Database Setup
To use the application, you will need to set up a database with the following schema:


```sql
CREATE DATABASE bank_app;

USE bank_app;

CREATE TABLE users (
    id INT  PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    current_balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00
);

CREATE TABLE transactions (
    id VARCHAR(255) PRIMARY KEY,
    transaction_amount VARCHAR(45) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(45) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```









