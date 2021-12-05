INSERT INTO user (id, first_name, last_name, email, username, password, phone_number, role, monthly_wage, pin, deleted) VALUES
  (1, 'Will', 'Smith', 'will@somedomain.smith', NULL, NULL, '12345678', 'waiter', 340, 1234, false),
  (2, 'Will', 'Smith', 'will1@somedomain.smith', NULL, NULL, '12345678', 'cook', 340, 1234, false);

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
('2021-11-11', 13.00, 130.00, 4);


INSERT INTO restaurant_order (id, created_at, note, table_id, waiter_id) VALUES
  (1, '2021-11-14', 'note 1', 1, 1),
  (2, '2021-11-14', 'note 2', 2, 1),
  (3, '2021-11-14', 'note 3', 3, 1);

INSERT INTO order_item (id, amount, status, cook_id, barman_id, item_id, order_id) VALUES
  (1, 1, 0, null, null, 1, 1),
  (2, 2, 0, null, null, 2, 1),
  (3, 1, 0, null, null, 3, 1),
  (4, 3, 0, null, null, 4, 2),
  (5, 1, 2, 2, null, 1, 3);


