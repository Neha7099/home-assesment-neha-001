--------1. product Table:---------
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    added_by VARCHAR(255) NOT NULL
);

----------2. product_price Table:----------
CREATE TABLE product_price (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    discount_percent DECIMAL(5, 2) DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-----------3. product_price_change_log Table:---------
CREATE TABLE product_price_change_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    old_price DECIMAL(10, 2) NOT NULL,
    new_price DECIMAL(10, 2) NOT NULL,
    change_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

------------Query to Join "product" and "product_price" Tables:--------------
SELECT
    p.name AS product_name,
    p.category,
    pp.price,
    pp.updated_by,
    pp.updated_at
FROM
    product p
JOIN
    product_price pp ON p.id = pp.product_id;