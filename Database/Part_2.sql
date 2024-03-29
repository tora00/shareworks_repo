--Part 1 - Database
-- QUERIES ARE BASED ON MYSQL FORMAT

-- Question 1
SELECT
	make, 
	model
FROM
	SC_CAR
GROUP BY
    make, model
ORDER BY
	make, 
	model DESC;


-- Question 2
SELECT
	a.make, 
	COUNT(a.model) as count
FROM
    (SELECT make,model
     FROM SC_CAR
     GROUP BY make, model) as a
GROUP BY
	make
ORDER BY
	make;

-- QUESTION 3
SELECT
	m.first_name, 
	m.last_name, 
	COUNT(t.transaction_id) as jobcount
FROM
	SC_MECHANIC AS m,
	SC_TRANSACTION AS t, 
	SC_CUSTOMER_CAR AS cc, 
	SC_CAR AS c
WHERE 
	m.mechanic_id=t.mechanic_id 
	AND t.customer_car_id=cc.customer_car_id 
	AND cc.car_id=c.car_id
	AND c.make='BMW' 
	AND t.car_odo_km>=150000
GROUP BY
	m.first_name,
	m.last_name
ORDER BY
    jobcount DESC;

-- QUESTION 4
SELECT
	m.first_name,
	m.last_name, 
	SUM(ti.cost) AS total
FROM
	SC_MECHANIC AS m, 
	SC_TRANSACTION AS t, 
	SC_TRANSACTION_ITEM AS ti
WHERE
	m.mechanic_id=t.mechanic_id
	AND t.transaction_id=ti.transaction_id
	AND YEAR(t.service_date)=2011
GROUP BY
  m.first_name, 
  m.last_name
ORDER BY
  total DESC;

-- QUESTION 5
SELECT
	m.first_name,
	m.last_name, 
	ti.cost, 
	ti.description,
    t.service_date
FROM
	SC_MECHANIC AS m,
	SC_TRANSACTION AS t, 
	SC_TRANSACTION_ITEM AS ti
WHERE
	m.mechanic_id=t.mechanic_id
	AND t.transaction_id=ti.transaction_id
	AND ((m.first_name='Tim' AND m.last_name='Ivers') OR (m.first_name='Moe' AND m.last_name='Unkle'))
ORDER BY
	m.first_name ASC,
    m.last_name ASC,
    t.service_date DESC;

