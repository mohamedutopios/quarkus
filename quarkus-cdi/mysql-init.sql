CREATE TABLE products (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        price DECIMAL(10, 2)
);

INSERT INTO products (name, price) VALUES
                                     ('Product A (MySQL)', 10.50),
                                     ('Product B (MySQL)', 20.00),
                                     ('Product C (MySQL)', 15.75);
