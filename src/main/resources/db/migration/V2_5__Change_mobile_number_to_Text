ALTER TABLE sales DROP CONSTRAINT foreign_fk_2;
ALTER TABLE CUSTOMERS ALTER COLUMN mobile_number TYPE text;
ALTER TABLE sales ALTER COLUMN customer_mobile_number TYPE text;
ALTER TABLE sales add CONSTRAINT foreign_fk_2 FOREIGN key(customer_mobile_number) REFERENCES customers(mobile_number);
ALTER TABLE suppliers ALTER COLUMN mobile_number TYPE text;
ALTER TABLE EMPLOYEES ALTER COLUMN mobile_number TYPE text;
ALTER TABLE CUSTOMERS ALTER COLUMN uid TYPE text;



ALTER TABLE PROCUREMENT_ITEMS DROP CONSTRAINT foreign_fk_2;
ALTER TABLE inventory DROP CONSTRAINT foreign_fk_1;
ALTER TABLE sales_items DROP CONSTRAINT foreign_fk_2;

ALTER TABLE procurement_items ALTER COLUMN item_id TYPE text;
ALTER TABLE inventory ALTER COLUMN item_id TYPE text;
ALTER TABLE sales_items ALTER COLUMN item_id TYPE text;
ALTER TABLE items ALTER COLUMN uid TYPE text;

ALTER TABLE inventory add  CONSTRAINT foreign_fk_1 FOREIGN key(item_id) REFERENCES items(uid);
ALTER TABLE PROCUREMENT_ITEMS ADD  CONSTRAINT foreign_fk_2 FOREIGN key(item_id) REFERENCES items(uid);







