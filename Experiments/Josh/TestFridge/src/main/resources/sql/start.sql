/*
 * NOTE: You will have to create a schema for these tables to be placed in, and you will
 * have to configure the application.properties to properly connect to the database!
 */ 
 CREATE SCHEMA IF NOT EXISTS fridgesjf2419;
CREATE TABLE IF NOT EXISTS fridgesjf2419.fridges (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fridge_name VARCHAR(30),
  INDEX(id)
) engine=InnoDB;
INSERT IGNORE INTO fridgesjf2419.fridges VALUES ('13','Kelly\'s fridge');
INSERT IGNORE INTO fridgesjf2419.fridges VALUES ('8484212','Dorm');
INSERT IGNORE INTO fridgesjf2419.fridges VALUES ('351','tylers frige');
CREATE TABLE IF NOT EXISTS fridgesjf2419.foods (
	id INT(4) UNSIGNED NOT NULL,
    food_name VARCHAR(30),
    amount INT(2)
) engine=InnoDB;
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('13','White Wine','2');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('13','Shrimp','34');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('8484212','Soda','48');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('8484212','Bad Pizza Slice','3');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('351','Oluvs','984');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('351','Malk','3');
INSERT IGNORE INTO fridgesjf2419.foods VALUES ('351','Other mispelled food item','6');