CREATE TABLE book (
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    price decimal(13, 2) not null,
    customer_id int not null,
    status varchar(255) not null,
    sale_date DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);