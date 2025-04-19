create database omsdb;

-- USERS TABLE
CREATE TABLE users (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) CHECK (role IN ('Admin', 'User'))
);

-- PRODUCTS BASE TABLE
CREATE TABLE products (
    productId INT PRIMARY KEY AUTO_INCREMENT,
    productName VARCHAR(100),
    description TEXT,
    price DOUBLE,
    quantityInStock INT,
    type VARCHAR(20) CHECK (type IN ('Electronics', 'Clothing'))
);

-- ELECTRONICS SUBCLASS TABLE
CREATE TABLE electronics (
    productId INT PRIMARY KEY,
    brand VARCHAR(100),
    warrantyPeriod INT,
    FOREIGN KEY (productId) REFERENCES products(productId)
);

-- CLOTHING SUBCLASS TABLE
CREATE TABLE clothing (
    productId INT PRIMARY KEY,
    size VARCHAR(10),
    color VARCHAR(20),
    FOREIGN KEY (productId) REFERENCES products(productId)
);

-- ORDERS TABLE
CREATE TABLE orders (
    orderId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

-- ORDER_ITEMS TABLE (for multiple products per order)
CREATE TABLE order_items (
    orderItemId INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    productId INT,
    quantity INT,
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (productId) REFERENCES products(productId)
);
