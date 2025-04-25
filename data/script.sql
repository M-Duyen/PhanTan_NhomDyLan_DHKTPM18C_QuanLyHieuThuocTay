SELECT * FROM administration_routes;
SELECT * FROM categories;
SELECT * FROM prescriptions;
SELECT * FROM vendors;
SELECT * FROM accounts;
SELECT * FROM managers;
SELECT * FROM employees;
SELECT * FROM customers;



SELECT * FROM products;

DROP DATABASE pharmacy_management;

select *
from products p
left join pharmacy_management.functional_foods ff on p.product_id = ff.product_id
left join pharmacy_management.medical_supplies ms on p.product_id = ms.product_id
left join pharmacy_management.medicines m on p.product_id = m.product_id;

SELECT *
FROM products p
WHERE p.category_id = 'CA019';