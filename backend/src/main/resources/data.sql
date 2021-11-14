INSERT INTO user (first_name, last_name, email, username, password, phone_number, role, monthly_wage, pin) VALUES
  ('Jeff', 'Goldblum', 'jeffgoldbloom@somedomain.blum', 'jeff.goldblum','$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', '123goldblum456', 'admin', NULL, NULL),
  ('Jeff', 'Goldblum', 'jim@somedomain.blum', NULL, NULL, '12345678', 'waiter', 340, 1234);

INSERT INTO category (name) VALUES
  ('appetizer'),
  ('main course'),
  ('dessert'),
  ('drink');

INSERT INTO item (name, category_id, description, image_url, in_menu, type) VALUES
  ('Spaghetti carbonara', 2, 'Traditional Italian dish served with garlic, olive oil and parsley', NULL, true, 0),
  ('Chicken tikka masala', 2, 'Roasted marinated chicken in spiced curry sauce', NULL, true, 0),
  ('Mushroom risotto', 2, 'Rice dish cooked with three kinds of mushroom, butter, white wine, onion and cheese', NULL, false, 0),
  ('Peach ice tea', 4, 'Cold ice tea sweetened with honey', NULL, true, 1);

INSERT INTO restaurant_order (created_at, note, table_id, barman_id, waiter_id) VALUES
  ('2021-11-14', 'note 1', 1, null, 2),
  ('2021-11-14', 'note 2', 2, null, 2);

INSERT INTO order_item (amount, status, cook_id, item_id, order_id) VALUES
  (1, 0, null, 1, 1),
  (2, 0, null, 2, 1),
  (1, 0, null, 3, 1),
  (3, 0, null, 4, 2);



