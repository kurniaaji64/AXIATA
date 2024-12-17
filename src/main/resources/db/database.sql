CREATE TABLE USERS 
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	first_name varchar (500) ,
	last_name varchar (500) ,
	email varchar  (100),
	phone varchar (15),
	ceated_by varchar (255)  ,
	updated_by varchar (255) ,
	created_datetime datetime,
	updated_datetime datetime ,
	is_deleted Boolean Default false
);

CREATE  TABLE ITEM 
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name varchar (500),
	description varchar (500),
	price int,
	cost int,
	ceated_by varchar (255)  ,
	updated_by varchar (255) ,
	created_datetime datetime,
	updated_datetime datetime 
	
);

CREATE TABLE PO_H
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	`datetime` datetime ,
	description  varchar (500),
	total_price int,
	total_cost int,
	ceated_by varchar (255)  ,
	updated_by varchar (255) ,
	created_datetime datetime,
	updated_datetime datetime 
		
);

CREATE  TABLE PO_D 
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	poh_id INT NOT NULL,
	item_id INT NOT NULL,
	item_qty int ,
	item_cost int,
	item_price int,
	FOREIGN KEY (poh_id) REFERENCES PO_H (id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (item_id) REFERENCES ITEM  (id) ON DELETE CASCADE ON UPDATE CASCADE
	
);
