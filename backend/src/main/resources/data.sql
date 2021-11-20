INSERT INTO user (first_name, last_name, email, username, password, phone_number, role, monthly_wage, pin) VALUES
  ('Jeff', 'Goldblum', 'jeffgoldbloom@somedomain.blum', 'jeff.goldblum','$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', '123goldblum456', 'admin', NULL, NULL),
  ('Will', 'Smith', 'will@somedomain.smith', NULL, NULL, '12345678', 'waiter', 340, 1234),
  ('Morgan', 'Freeman', 'morgan@somedomain.freeman', 'morgan', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', '12345678', 'manager', 1340, NULL);

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

INSERT INTO item (name, category_id, description, image_url, in_menu, type, deleted) VALUES
  ('Spaghetti carbonara', 2, 'Traditional Italian dish served with garlic, olive oil and parsley', NULL, true, 0, false),
  ('Chicken tikka masala', 2, 'Roasted marinated chicken in spiced curry sauce', NULL, true, 0, false),
  ('Mushroom risotto', 2, 'Rice dish cooked with three kinds of mushroom, butter, white wine, onion and cheese', NULL, false, 0, false),
  ('Peach ice tea', 4, 'Cold ice tea sweetened with honey', NULL, true, 1, false);

INSERT INTO item_values (from_date, purchase_price, selling_price, item_id) VALUES
  ('2021-11-15', 100.00, 1000.00, 1),
  ('2021-11-15', 120.00, 1200.00, 2),
  ('2021-11-15', 130.00, 1300.00, 3),
  ('2021-11-15', 13.00, 130.00, 4);

INSERT INTO restaurant_order (created_at, note, table_id, waiter_id) VALUES
  ('2021-11-14', 'note 1', 1, 2),
  ('2021-11-14', 'note 2', 2, 2);

INSERT INTO order_item (amount, status, cook_id, barman_id, item_id, order_id) VALUES
  (1, 0, null, null, 1, 1),
  (2, 0, null, null, 2, 1),
  (1, 0, null, null, 3, 1),
  (3, 0, null, null, 4, 2);



