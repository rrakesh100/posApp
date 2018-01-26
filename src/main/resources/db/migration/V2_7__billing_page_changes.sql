ALTER TABLE sales_items add COLUMN  id bigserial PRIMARY key;

ALTER TABLE sales_items DROP CONSTRAINT foreign_fk_1;

ALTER TABLE sales ALTER COLUMN invoice_number type text;
ALTER TABLE sales_items ALTER COLUMN invoice_number type text;

ALTER TABLE sales_items ADD CONSTRAINT foreign_fk_1 FOREIGN key(invoice_number) REFERENCES sales(invoice_number);

alter table sales_items add constraint foreign_fk_2 foreign key(item_id) references items(uid);

ALTER TABLE sales_items rename COLUMN invoice_number TO sale_invoice_number;

ALTER TABLE sales_items add COLUMN item_name text;



INSERT INTO employees (username,mobile_number,password, name,email,address,date_of_birth,deleted)
VALUES ('rrakesh100','9901250919','123456', 'Rakesh Rampalli', 'rrakesh100@gmail.com','bangalore','2001-12-21',false);

INSERT INTO customers (mobile_number, name) VALUES ('7981008285' , 'Rakesh R');
INSERT INTO customers (mobile_number, name) VALUES ('XXXXX' , 'Guest');

ALTER TABLE inventory ALTER COLUMN item_id TYPE text;



ALTER TABLE sales add COLUMN net_amount DECIMAL DEFAULT 0;
ALTER TABLE sales add COLUMN tax_amount DECIMAL DEFAULT 0;
ALTER TABLE sales rename COLUMN gross_amount TO sub_total;
ALTER TABLE sales rename COLUMN payment_amount TO total;


