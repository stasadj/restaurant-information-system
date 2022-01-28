INSERT INTO user (first_name, last_name, email, username, password, phone_number, role, monthly_wage, pin, deleted) VALUES
  ('Jeff', 'Goldblum', 'jeffgoldbloom@somedomain.blum', 'jeff.goldblum','$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', '123goldblum456', 'admin', NULL, NULL, false),
  ('Will', 'Waiter', 'will@somedomain.smith', NULL, NULL, '12345678', 'waiter', 340, 1234, false),
  ('Bob', 'Waiter', 'will@somedomain.smith', NULL, NULL, '12345678', 'waiter', 340, 5678, false),
  ('Will', 'Cook', 'will@somedomain.smith', NULL, NULL, '12345678', 'cook', 340, 1111, false),
  ('Will', 'Barman', 'will@somedomain.smith', NULL, NULL, '12345678', 'barman', 340, 2222, false),
  ('Morgan', 'Freeman', 'morgan@somedomain.freeman', 'morgan', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', '12345678', 'manager', 1340, NULL, false);

INSERT INTO category (name) VALUES
  ('appetizer'),
  ('main course'),
  ('dessert'),
  ('drink');

INSERT INTO tag (name) VALUES
  ('vegan'),
  ('vegetarian'),
  ('gluten free'),
  ('dairy free');

INSERT INTO item (name, category_id, description, in_menu, type, deleted, image_url) VALUES
  ('Spaghetti carbonara', 2, 'Traditional Italian dish served with garlic, olive oil and parsley', true, 0, false, 'image1.png'),
  ('Chicken tikka masala', 2, 'Roasted marinated chicken in spiced curry sauce', true, 0, false, 'image2.png'),
  ('Mushroom risotto', 2, 'Rice dish cooked with three kinds of mushroom, butter, white wine, onion and cheese', false, 0, false, 'image3.png'),
  ('Peach ice tea', 4, 'Cold ice tea sweetened with honey', true, 1, false, 'image4.png'),
  ('DeletedItem', 4, 'Deleted item description', true, 1, true, 'image5.png');

INSERT INTO item_tags (item_id, tags_id) VALUES 
    (1, 2),
    (2, 4),
    (2, 3),
    (3, 2),
    (4, 1),
    (4, 2);

INSERT INTO item_values (from_date, purchase_price, selling_price, item_id) VALUES
  ('2021-11-11', 600.00, 1000.00, 1),
  ('2021-11-11', 700.00, 1200.00, 2),
  ('2021-11-11', 600.00, 1300.00, 3),
  ('2021-11-11', 150.00, 660.00, 4),
  ('2021-11-11', 1000.00, 2500.00, 5);


-- Had to comment this part, because table organization can only be changed if there are no orders

INSERT INTO restaurant_order (created_at, note, table_id, waiter_id) VALUES
  ('2021-11-14', 'note 1', 1, 2),
  ('2021-11-14', 'note 2', 2, 2);

INSERT INTO order_item (amount, status, cook_id, barman_id, item_id, order_id) VALUES
  (1, 0, null, null, 1, 1),
  (2, 0, null, null, 2, 1),
  (1, 0, null, null, 3, 1),
  (3, 0, null, null, 4, 2);

INSERT INTO order_record (amount, item_value_id, created_at) VALUES
  (1, 1, '2021-11-21'),
  (1, 1, '2021-11-16'),
  (2, 1, '2021-11-17'),
  (2, 4, '2021-11-18');
