CREATE TABLE purchase (
    id int AUTO_INCREMENT PRIMARY KEY,
    customer_id int not null,
    price decimal(15, 2) not null,
    nfe varchar(255),
    created_at DATETIME not null,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE purchase_books (
    purchase_id int not null,
    book_id int not null,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    PRIMARY KEY(book_id, purchase_id)
);
