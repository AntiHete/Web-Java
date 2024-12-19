-- Insert testing data into the 'product' table
INSERT INTO product (id, name, description, price, stock_quantity, category_id, wearer)
VALUES
    -- Product 1: Cat Star Toy with details about price, stock, category, and wearer type (0 = adult, 1 = kitten)
    (2, 'Cat Star Toy', 'A fun toy for cats to play with', 57.99, 100, 1, 0),
    -- Product 2: Kitty Star Bed with details about price, stock, category, and wearer type
    (3, 'Kitty Star Bed', 'A cozy bed for kittens to sleep in', 30.99, 50, 2, 1),
    -- Product 3: Cat Star Scratcher with details about price, stock, category, and wearer type
    (4, 'Cat Star Scratcher', 'Durable scratching post for cats', 26.99, 75, 1, 0),
    -- Product 4: Cat Star Demolition with details about price, stock, category, and wearer type
    (5, 'Cat Star Demolition', 'Soft blanket for kittens', 19.99, 60, 2, 1),
    -- Product 5: Cat Star Food with details about price, stock, category, and wearer type
    (6, 'Cat Star Food', 'Healthy food for adult cats', 75.00, 200, 3, 0),
    -- Product 6: Kitty Star Treats with details about price, stock, category, and wearer type
    (7, 'Kitty Star Treats', 'Delicious treats for kittens', 37.99, 150, 3, 1);
