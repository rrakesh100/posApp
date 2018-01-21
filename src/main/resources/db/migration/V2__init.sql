
-- Table to store all generic configuration information
DROP TABLE IF EXISTS global_config;
CREATE TABLE global_config (
    key text NOT NULL primary key,
    value text NOT NULL
);

-- entity specific config entity_type could be customer/employee/item/sales etc
-- entity_id will be a unique key to identify in a entity type
-- key_1, key_2 can be used to store name value pairs
-- If you need more use key_3 and store value_3 as JSON string

DROP TABLE IF EXISTS entity_config;
CREATE TABLE entity_config (
    entity_id bigint not null,
    entity_type text not null,
    key_1 text default NULL ,
    value_1 text default NULL,
    key_2 text default NULL ,
    value_2 text default NULL,
    key_3 text default NULL ,
    value_3 text default NULL,
    primary key (entity_id, entity_type)
);



-- Items in a store
DROP TABLE IF EXISTS items cascade;
CREATE TABLE items (
  id bigserial,
  uid bigint PRIMARY KEY,
  sku text default null,
  name TEXT NOT NULL,
  description TEXT default NULL,
  created_date timestamp without time zone DEFAULT now() NOT NULL
);





-- table to register and identify customers. Mobile number is the key
-- mobile number will be used to join other table to get reward points/ user specific coupon code / discounts etc
DROP TABLE IF EXISTS customers cascade;
CREATE TABLE customers (
  mobile_number bigint primary key,
  id serial,
  name text DEFAULT NULL,
  email text default null,
  address text default null,
  company_name text default null,
  date_of_birth date default null,
  deleted boolean default false
);


-- table to hold information about employees working in a shop/store
-- Mobile number is the key, employee_id is auto incrementing sequence
-- username is unique to identify users like ramraju1/ admin/ rrakesh100
-- that will be used to login to the app
-- A single user can have multiple roles, so mobile number is not a good choice to maintain uniqueness
DROP TABLE IF EXISTS employees cascade;
create table employees (
  id serial primary key,
  username text NOT NULL UNIQUE,
  mobile_number bigint,
  password text NOT NULL,
  name text not null,
  email text not null,
  address text not null,
  date_of_birth date default null,
  deleted boolean NOT NULL DEFAULT false
);



-- All suppliers information. Shopkeeper will add all the suppliers that supply to his shop
DROP TABLE IF EXISTS suppliers cascade;
CREATE TABLE suppliers (
  id serial primary key,
  mobile_number bigint,
  company_name text NOT NULL,
  agency_name text NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  constraint unique_uk_1 unique(company_name , agency_name)
);


-- Table to hold information about all the incoming supplies
DROP TABLE IF EXISTS incoming_supplies cascade;
CREATE TABLE incoming_supplies (
   receiving_id serial primary key,
   supplier_id serial  NOT NULL,
   time timestamp without time zone DEFAULT now() NOT NULL,
   employee_id integer ,
   comment text ,
   payment_type text NOT NULL,
   payment_amount decimal not null,
   constraint foreign_fk_1 foreign key(supplier_id) references suppliers(id),
   constraint foreign_fk_2 foreign key(employee_id) references employees(id)
);


-- Table to hold information about all the  items in a single supply

DROP TABLE IF EXISTS incoming_supply_items;
CREATE TABLE incoming_supply_items (
  receiving_id serial ,
  item_id bigint NOT NULL DEFAULT '0',
  description text DEFAULT NULL,
  serial_number serial,
  quantity_purchased decimal(15,3) NOT NULL DEFAULT '0',
  number_of_units integer not null,
  item_cost_price decimal(15,2) NOT NULL,
  item_unit_price decimal(15,2) NOT NULL,
  unit_discount_percent decimal(15,2) NOT NULL DEFAULT '0',
  constraint unique_uk_2 unique(receiving_id,item_id),
  constraint foreign_fk_1 foreign key(receiving_id) references incoming_supplies(receiving_id),
  constraint foreign_fk_2 foreign key(item_id) references items(uid)
);


-- Inventory table. Detailed information about the current stock
-- 1 carton box of rice could contain 20 units
-- 1 carton box of maggi could contain 50 units
drop table if exists inventory;
create table inventory (
    item_id bigint,
    item_description text default null,
    quantity decimal default '1',
    unit_weight decimal default '0',
    number_of_units integer default '1',
    constraint foreign_fk_1 foreign key(item_id) references items(uid)
);

-- sales information.
DROP TABLE IF EXISTS sales cascade;
CREATE TABLE sales (
  sale_id serial,
  invoice_number bigint primary key,
  sale_number text default null, -- extra place holder for any other info
  sale_time  timestamp NOT NULL default current_timestamp,
  employee_id integer not null,
  customer_mobile_number integer default '0',
  gross_amount decimal not null,
  tax_percent decimal default '0',
  discount decimal not null,
  payment_amount decimal not null,
  payment_type text not null,
  constraint foreign_fk_1 foreign key(employee_id) references employees(id),
  constraint foreign_fk_2 foreign key(customer_mobile_number) references customers(mobile_number)
  );


employee_id
customer_mobile_number
sale_time
gross_amount (this will show as “SUB TOTAL” when bill is printed)
add three more columns cgst, sgst, igst (tax columns)

-- items contained in a sale
DROP TABLE IF EXISTS sales_items;
CREATE TABLE sales_items (
  invoice_number bigint ,
  item_id integer ,
  serial_number integer,
  quantity_purchased decimal not null default '0',
  item_cost_price decimal default '0',
  item_unit_price decimal default '0',
  discount_percent decimal default '0',
  tax_percent decimal default '0',
  total_price decimal default '0',
  constraint unique_uk_3 unique (invoice_number, item_id),
  constraint foreign_fk_1 foreign key(invoice_number) references sales(invoice_number),
  constraint foreign_fk_2 foreign key(item_id) references items(uid)
);
