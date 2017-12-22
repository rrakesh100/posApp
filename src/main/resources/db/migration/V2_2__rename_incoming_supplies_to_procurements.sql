ALTER TABLE incoming_supplies RENAME to procurements;

ALTER TABLE procurements RENAME column receiving_id to procurement_id;

ALTER TABLE incoming_supply_items RENAME to procurement_items;

ALTER TABLE procurement_items RENAME column receiving_id to procurement_id;

ALTER TABLE procurement_items drop constraint foreign_fk_1;

ALTER TABLE procurement_items add constraint foreign_fk_1 foreign key(procurement_id) references procurements(procurement_id);






