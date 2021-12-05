INSERT INTO user (id, first_name, last_name, email, username, password, phone_number, role, monthly_wage, pin, deleted) VALUES
  (1, 'Will', 'Cook', 'will@somedomain.cook', NULL, NULL, '12345678', 'cook', 340, 1234, false),
  (2, 'Will', 'Barman', 'will@somedomain.barman', NULL, NULL, '12345678', 'barman', 340, 5678, false),
  (3, 'Will', 'Smith', 'will@somedomain.smith', NULL, NULL, '12345678', 'waiter', 340, 9000, false);

INSERT INTO category (id, name) VALUES
  (1, 'main course'),
  (2, 'tea');

INSERT INTO item (id, name, category_id, description, image_url, in_menu, type, deleted) VALUES
  (1, 'Spaghetti carbonara', 1, 'Traditional Italian dish served with garlic, olive oil and parsley', NULL, true, 0, false),
  (2, 'Peach ice tea', 2, 'Cold ice tea sweetened with honey', NULL, true, 1, false);

INSERT INTO restaurant_order (id, created_at, note, table_id, waiter_id) VALUES
  (1, '2021-12-05', 'some note', 1, 3);

INSERT INTO order_item (id, amount, status, cook_id, barman_id, item_id, order_id) VALUES
                       ( 1,      1,      0,    null,      null,       1,        1),
                       ( 2,      1,      0,    null,      null,       2,        1),
                       ( 3,      1,      3,    null,      null,       1,        1),
                       ( 4,      1,      3,    null,      null,       2,        1),
                       ( 5,      1,      1,       1,      null,       1,        1),
                       ( 6,      1,      1,    null,         2,       2,        1),
                       ( 7,      1,      2,       1,      null,       1,        1),
                       ( 8,      1,      2,    null,         2,       2,        1);
