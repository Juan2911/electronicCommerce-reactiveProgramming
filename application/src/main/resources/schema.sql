CREATE TABLE IF NOT EXISTS brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    brand_id INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INT,
    product_id INT,
    priority INT,
    price DECIMAL(10, 2),
    currency VARCHAR(255),
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

INSERT INTO brand (name)
SELECT 'ZARA'
WHERE NOT EXISTS (
    SELECT 1 FROM brand WHERE name = 'ZARA'
);
 INSERT INTO product (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
 SELECT 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'
 WHERE NOT EXISTS (
     SELECT 1 FROM product
     WHERE brand_id = 1 AND start_date = '2020-06-14 00:00:00'
 );

 INSERT INTO product (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
 SELECT 1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'
 WHERE NOT EXISTS (
     SELECT 1 FROM product
     WHERE brand_id = 1 AND start_date = '2020-06-14 15:00:00'
 );

 INSERT INTO product (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
 SELECT 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'
 WHERE NOT EXISTS (
     SELECT 1 FROM product
     WHERE brand_id = 1 AND start_date = '2020-06-15 00:00:00'
 );

 INSERT INTO product (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
 SELECT 1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR'
 WHERE NOT EXISTS (
     SELECT 1 FROM product
     WHERE brand_id = 1 AND start_date = '2020-06-15 16:00:00'
 );
