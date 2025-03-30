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
INSERT INTO Users (username, password, email) VALUES
('john_doe', 'password123', 'john.doe@example.com'),
('jane_smith', 'securepass456', 'jane.smith@example.com'),
('mike_jones', 'mikepass789', 'mike.jones@example.com'),
('emily_davis', 'emilypass321', 'emily.davis@example.com'),
('chris_wilson', 'chrispass654', 'chris.wilson@example.com'),
('laura_brown', 'laurapass987', 'laura.brown@example.com'),
('kevin_miller', 'kevinpass111', 'kevin.miller@example.com');
Select * from users;

INSERT INTO ExpenseCategories (category_name) VALUES
('Food'),
('Transportation'),
('Utilities'),
('Entertainment'),
('Healthcare'),
('Education'),
('Shopping');
select * from ExpenseCategories;

INSERT INTO Expenses (user_id, category_id, amount, date, description) VALUES
(1, 1, 50.00, '2024-03-01', 'Grocery shopping'),
(2, 2, 20.00, '2024-03-02', 'Bus fare'),
(3, 3, 100.00, '2024-03-03', 'Electricity bill'),
(4, 4, 30.00, '2024-03-04', 'Movie tickets'),
(5, 5, 200.00, '2024-03-05', 'Doctor consultation'),
(6, 6, 500.00, '2024-03-06', 'Course fee'),
(7, 7, 150.00, '2024-03-07', 'Clothing shopping');
Select * from Expenses;

