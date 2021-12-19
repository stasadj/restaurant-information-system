INSERT INTO category (id, name) VALUES
  (1, 'appetizer'),
  (2, 'main course'),
  (3,'dessert'),
  (4,'drink');

INSERT INTO tag (id, name) VALUES
  (1, 'vegan'),
  (2, 'vegetarian'),
  (3, 'gluten free'),
  (4, 'dairy free');

INSERT INTO item (id, name, category_id, description, image_url, in_menu, type, deleted) VALUES
  (1, 'Spaghetti carbonara', 2, 'Traditional Italian dish served with garlic, olive oil and parsley', NULL, true, 0, false),
  (2, 'Chicken tikka masala', 2, 'Roasted marinated chicken in spiced curry sauce', NULL, true, 0, false),
  (3, 'Mushroom risotto', 2, 'Rice dish cooked with three kinds of mushroom, butter, white wine, onion and cheese', NULL, false, 0, false),
  (4, 'Peach ice tea', 4, 'Cold ice tea sweetened with honey', NULL, true, 1, false),
  (5, 'Deleted', 4, 'Cold ice tea sweetened with honey', NULL, true, 1, true);

  INSERT INTO item_values (from_date, purchase_price, selling_price, item_id) VALUES
  ('2021-11-11', 100.00, 1000.00, 1),
  ('2021-11-11', 120.00, 1200.00, 2),
  ('2021-11-11', 130.00, 1300.00, 3),
  ('2021-11-11', 13.00, 130.00, 4),
  ('2021-11-11', 66.00, 660.00, 5);