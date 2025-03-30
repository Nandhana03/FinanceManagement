CREATE DATABASE finance_db;
USE finance_db;
drop table Users;
create table Users (
    user_id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    CONSTRAINT Users_userid_pk PRIMARY KEY (user_id),
    CONSTRAINT Users_username_uk UNIQUE(username),
    CONSTRAINT Users_email_uk UNIQUE(email)
);

CREATE TABLE ExpenseCategories (
    category_id INT AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    CONSTRAINT ExpenseCategories_categoryid_pk PRIMARY KEY(category_id),
    CONSTRAINT ExpenseCategories_categoryname_uk UNIQUE(category_name)
);

CREATE TABLE Expenses (
    expense_id INT AUTO_INCREMENT,
    user_id INT,
    category_id INT,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL,
    description MEDIUMTEXT,
    CONSTRAINT Expenses_expenseid_pk PRIMARY KEY(expense_id),
    CONSTRAINT Expenses_userid_fk FOREIGN KEY (user_id) 
          REFERENCES Users(user_id),
    CONSTRAINT Expenses_categoryid_fk FOREIGN KEY (category_id) 
          REFERENCES ExpenseCategories(category_id)
);
