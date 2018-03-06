
ALTER TABLE items add COLUMN deleted BOOLEAN DEFAULT false;
ALTER TABLE items add COLUMN quantity DECIMAL DEFAULT 0;
--units will hold values like kgs / grams / litres  etc - if units  is null then it means that item is sold as unit packets
ALTER TABLE items add COLUMN units text DEFAULT null;

DROP TABLE IF EXISTS item_purchase_history;
CREATE TABLE item_purchase_history (
id bigserial,
item_id text ,
price DECIMAL DEFAULT '0',
quantity DECIMAL DEFAULT 0,
units text DEFAULT null,
time_stamp TIMESTAMP WITHOUT TIME zone DEFAULT now() NOT NULL,
CONSTRAINT foreign_fk_1 FOREIGN key(item_id) REFERENCES items(uid)
);


DROP TABLE IF EXISTS item_sale_history;
CREATE TABLE item_sale_history (
id bigserial,
item_id text ,
price DECIMAL DEFAULT '0',
quantity DECIMAL DEFAULT 0,
units text DEFAULT null,
time_stamp TIMESTAMP WITHOUT TIME zone DEFAULT now() NOT NULL,
CONSTRAINT foreign_fk_1 FOREIGN key(item_id) REFERENCES items(uid)
);


DROP TABLE IF EXISTS item_sale_history_daily;
CREATE TABLE item_sale_history_daily (
id bigserial,
item_id text ,
price DECIMAL DEFAULT '0',
quantity DECIMAL DEFAULT 0,
units text DEFAULT null,
time_stamp TIMESTAMP WITHOUT TIME zone DEFAULT now() NOT NULL,
CONSTRAINT foreign_fk_1 FOREIGN key(item_id) REFERENCES items(uid)
);

DROP TABLE IF EXISTS item_updates;
CREATE TABLE item_updates (
id bigserial,
item_id text ,
field text,
VALUE text,
time_stamp TIMESTAMP WITHOUT TIME zone DEFAULT now() NOT NULL,
CONSTRAINT foreign_fk_1 FOREIGN key(item_id) REFERENCES items(uid)
);

