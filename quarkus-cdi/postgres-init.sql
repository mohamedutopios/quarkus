CREATE TABLE products (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100),
                        price DECIMAL(10, 2)
);

INSERT INTO products (name, price) VALUES
                                     ('Product A (PostgreSQL)', 12.00),
                                     ('Product B (PostgreSQL)', 18.50),
                                     ('Product C (PostgreSQL)', 22.30);
