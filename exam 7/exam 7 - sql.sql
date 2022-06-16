-- exam 7: SQL

/*
-- q1: Display all the Products that have a lower price than the price of the Product with id number 4.
select *
from Products
where UnitPrice < (select UnitPrice
					from Products
					where ProductID = 4)
*/


/*
-- q2: Display the name and hire date of all the employees that got hired after the hire date of employee with id 6.
select FirstName+' '+LastName as FullName, HireDate
from Employees
where HireDate > (select HireDate
					from Employees
					where EmployeeID = 6)
*/


/*
-- q3: Display all product names and prices for products that have a lower price than all other prices of products from category number 7.

select ProductName, UnitPrice
from Products
where UnitPrice < (select min(UnitPrice)
					from Products
					where CategoryID = 7)
*/


/*
-- q4: Display the details of the supplier that have the order with highest quantity (of all the items in the order) number of units on order.
SELECT *
FROM Suppliers
WHERE SupplierID = (SELECT DISTINCT SupplierID
						FROM Products
						WHERE UnitsOnOrder = (SELECT MAX(UnitsOnOrder)
												FROM Products))
*/


/*
-- q5: Display the City, Company name, contact name for all customers and all suppliers but without any duplicated rows.
(select City, CompanyName, ContactName 
from Customers
union all   -- adding all togther
select City, CompanyName, ContactName 
from Suppliers) except (select City, CompanyName, ContactName -- delete duplicated rows
						from Customers 
						intersect      -- find duplicated rows
						select s.City, s.CompanyName, s.ContactName
						from Suppliers s)
*/