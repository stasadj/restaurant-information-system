INSERT INTO category (id, name) VALUES
  (1, 'appetizer'),
  (2, 'main course'),
  (3, 'dessert'),
  (4, 'drink');

INSERT INTO item (id, name, category_id, description, image_url, in_menu, type, deleted) VALUES
  (1, 'Spaghetti carbonara', 1, 'Traditional Italian dish served with garlic, olive oil and parsley', NULL, true, 0, false),
  (2, 'Chicken tikka masala', 2, 'Roasted marinated chicken in spiced curry sauce', NULL, true, 0, false),
  (3, 'Mushroom risotto', 3, 'Rice dish cooked with three kinds of mushroom, butter, white wine, onion and cheese', NULL, false, 0, false);

INSERT INTO item_values (id, from_date, purchase_price, selling_price, item_id) VALUES
  (1, '2021-01-11', 100.00, 1000.00, 1),
  (2, '2021-01-11', 120.00, 1200.00, 2),
  (3, '2021-01-11', 130.00, 1300.00, 3);

INSERT INTO order_record (id, amount, item_value_id, created_at) VALUES
  (1, 1, 1, '2021-01-21'),
  (2, 1, 1, '2021-01-16'),
  (3, 2, 2, '2021-01-17'),
  (4, 2, 3, '2021-01-18');