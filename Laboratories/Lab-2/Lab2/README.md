# Laboratory 2

<!--
**Lab assignment**

A.	Write a Java class with at least three instance attributes of different types. For example, Car with the following attributes: a manufacturer, a model, maximum speed, a price and a manufacturing year. This is only an example, please choose your own type. Implement the constructors, setters and getters, and other required methods (e.g. toString(), equals()).

B.	Create a repository that contains objects having the type defined at A and provide an iterator for the repository. Write a program that displays the list of all objects and then computes different kinds of information. For example, for a collection of cars, the program prints the following information:
-	the cheapest car;
-	the fastest car;
-	the models manufactured by a given manufacturer.

For testing, you will create some objects in the main function and add them to the repository.


---

**Home assignment**

-->

For your chosen problem implement the requirements below:
-	All entities should be identifiable (use a superclass/interface Identifiable) and unique.
-	Provide a generic interface for the repository and an implementation for a generic memory repository which stores identifiable objects in a Map (HashMap, TreeMap), where the objects identifiers are the keys and the values are the actual objects.
-	Add basic data validation and use the exception mechanism in Java for exceptional situations. Show messages in case of such situations.
-	The UI must allow CRUD operations for both entities.
