-- @block
SHOW TABLES  


  
   -- @block
CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL
);


   -- @block
CREATE TABLE volunteer (
id INT AUTO_INCREMENT PRIMARY KEY,
firstName VARCHAR(100) NOT NULL,
lastName VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
pass_word TEXT NOT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
points BIGINT,
city_id INT NOT NULL, 
FOREIGN KEY (city_id) REFERENCES city(id)
);


   -- @block
CREATE TABLE association (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description VARCHAR(100) NOT NULL
);

-- @block
ALTER TABLE association
ADD point BIGINT NOT NULL,
ADD image VARCHAR(100) NOT NULL;
   -- @block
CREATE TABLE donation (
id INT AUTO_INCREMENT PRIMARY KEY,
association_id INT NOT NULL, 
FOREIGN KEY (association_id) REFERENCES association(id),
volunteer_id INT NOT NULL, 
FOREIGN KEY (volunteer_id) REFERENCES volunteer(id)
);

-- @block
ALTER TABLE donation
ADD amount BIGINT NOT NULL;

   -- @block
CREATE TABLE volunteer_donation (
id INT AUTO_INCREMENT PRIMARY KEY,
donation_id INT NOT NULL, 
FOREIGN KEY (donation_id) REFERENCES donation(id),
volunteer_id INT NOT NULL, 
FOREIGN KEY (volunteer_id) REFERENCES volunteer(id)
);


   -- @block
CREATE TABLE city_collect (
id INT AUTO_INCREMENT PRIMARY KEY,
city_id INT NOT NULL, 
FOREIGN KEY (city_id) REFERENCES city(id),
collect_id INT NOT NULL, 
FOREIGN KEY (collect_id) REFERENCES collect(id)
);
   -- @block
CREATE TABLE collect (
id INT AUTO_INCREMENT PRIMARY KEY,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
volunteer_id INT NOT NULL, 
FOREIGN KEY (volunteer_id) REFERENCES volunteer(id)
);

   -- @block
CREATE TABLE waste (
id INT AUTO_INCREMENT PRIMARY KEY,
type VARCHAR(100) NOT NULL,
name  VARCHAR(100) NOT NULL,
point BIGINT NOT NULL
);

   -- @block
CREATE TABLE waste_collect (
id INT AUTO_INCREMENT PRIMARY KEY,
quantity_waste BIGINT  NOT  NULL,
waste_id INT NOT NULL, 
FOREIGN KEY (waste_id) REFERENCES waste(id),
collect_id INT NOT NULL, 
FOREIGN KEY (collect_id) REFERENCES collect(id)
);

inner join
-- @block
INSERT INTO city (city, latitude, longitude)
VALUES ('Paris', '48.8566', '2.3522'),
       ('Marseille', '43.2965', '5.3698'),
       ('Lyon', '45.7578', '4.832'),
       ('Toulouse', '43.6047', '1.4442'),
       ('Nice', '43.7102', '7.262'),
       ('Nantes', '47.2184', '-1.5536'),
       ('Strasbourg', '48.5734', '7.7521'),
       ('Montpellier', '43.6108', '3.8767'),
       ('Bordeaux', '44.8378', '0.5792'),
       ('Lille', '50.6292', '3.0573');

-- @block
INSERT INTO volunteer (firstName, lastName, email, pass_word, created_at, updated_at, points, city_id)
VALUES ('Monica', 'Geller', 'monica.geller@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 1),
       ('Rachel', 'Green', 'rachel.green@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 1),
       ('Phoebe', 'Buffay', 'phoebe.buffay@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 6),
       ('Joey', 'Tribbiani', 'joey.tribbiani@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 6),
       ('Chandler', 'Bing', 'chandler.bing@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 1),
       ('Ross', 'Geller', 'ross.geller@test.fr', 'test1234', '2025-05-25', '2025-05-25', 0, 3);

-- @block
SELECT * FROM donation;

-- @block
ALTER TABLE donation
DROP COLUMN image;

-- @block
INSERT INTO association (name, description, point, image)
VALUES ('Ocean Cleanup', 'Association dédiée au nettoyage des océans et à la protection de la vie marine.', '100', '🌊'),
      ('Forest Guardians', 'Protection des forêts et reforestation à travers le monde.', '150', '🌳'),
      ('Clean Air Initiative', 'Lutte contre la pollution de l"air et promotion des énergies propres.', '120', '🌬️'),
      ('Wildlife Protectors', 'Protection des espèces menacées et préservation de leur habitat.', '200', '🦁')   ;

-- @block
INSERT INTO waste (name, type, point)
VALUES ('Mégots de cigarette', 'cigarette', 0),
      ('Plastique', 'plastic', 0),
      ('Verre', 'glass', 0),
      ('Électronique', 'electronic', 0),
      ('Métal', 'metal', 0),
      ('Autre', 'other', 0);

