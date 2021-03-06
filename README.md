<h1 align="center">
    Football-Database ⚽️
</h1>

_Also available in: [Polski](README.pl.md)_

## About

Football-Database project for Database Systems at Poznan University of Technology. The application allows managing a database of various football leagues whose entity relationship diagram is shown below:

![Entity relationship diagram](./src/main/resources/META-INF/resources/sql/entity_relationship_diagram.png)

The main goal of this project was to implement a [Java](https://www.java.com/) [Spring](https://spring.io/) web application that connects to database using [JPA](https://spring.io/projects/spring-data-jpa) and allows the user to add, modify or delete data from database. Additionally, GUI was implemented using [Vaadin](https://vaadin.com/) template. All [SQL scripts](src/main/resources/META-INF/resources/sql/schema.sql) required to create tables can be found in the resources folder.

## Folder Structure

```bash
PROJECT_FOLDER
│  README.md
│  README.pl.md
│  pom.xml
└──[frontend]
│  └──[themes] # Contains the custom CSS styles.
└──[src]
   └──[main]
      └──[java]
      └──[resources]
         │  application.properties # Contains springboot configurations and database connection
         └──[META-INF]
            └──[resources]
               └──[sql]
                  │  schema.sql    # Contains DB Script to create tables
                  └──data.sql      # Contains DB Script to insert data (after schema.sql)
```

## Running the application

The application has to be connected to database, therefore you first need to configure connection parameters to your database in the [application.properties](src/main/resources/application.properties) file and run [SQL schema script](src/main/resources/META-INF/resources/sql/schema.sql) in this database to create required tables. You can also insert some prepared sample data using [SQL data script](src/main/resources/META-INF/resources/sql/schema.sql).

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), and then open
http://localhost:8080 in your browser to see the application.

You can also import the project to your IDE of choice as you would with any
Maven project.

## Contributors

<a href="https://github.com/filipciesielski7/Football-Database/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=filipciesielski7/Football-Database" />
</a>
