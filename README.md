# MagoraTest

This project was created as a Test for Job Apply for Magora Systems in Novosibirsk, Russia (https://magora-systems.ru/)

It uses Spring MVC 4.2, Spring Security 4.2 and Hibernate 4.3.6

It's a simple online store, with the following rest methods (methods marked with (*) requires authentication):

# POST - /login
Athenticates the admin

# GET - /api/version/token *
retrieves the Access token

# GET - /api/version/products *
retrieves the list of products

# GET - /api/version/products/{id} *
retrieves information about one specific product

# DELETE - /api/version/products/{id} *
deletes one specific product

# POST - /api/version/products *
saves a new product

# PUT - /api/version/products *
updates a product

# GET - /api/version/orders *
retrieves the list of orders based on a period

# POST - /api/version/basket/{id}
Saves an existing product into the anonymous user basket

# DELETE - /api/version/basket/{id}
Delete a product from anonymous user basket

# GET - /api/version/basket
retrieves the list of existing products from the anonymous user basket

#PUT - /api/version/basket
persists the anonymous user in Session

All the methods listed above were tested in Postman (https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop)
inside src/main/test there is the saved collection containing all the tests.

ps: before start the server, make sure that hibernate.hbm2ddl.auto property in dispatcher-servlet.xml is set to create, in order to create
the tables.

