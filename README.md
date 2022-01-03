<h1 align="center">
    Football-Database ⚽️
</h1>

_Also available in: [Polski](README.pl.md)_

## About

Football-Database project for Database Systems at Poznan University of Technology

![Entity relationship diagram](./src/main/resources/META-INF/resources/sql/entity_relationship_diagram.png)

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
         │  application.properties # Contains springboot configurations and database connection parameters
         └──[META-INF]
            └──[resources] 
               └──[sql]
                  │  schema.sql    # Contains DB Script to create tables         
                  └──data.sql      # Contains DB Script to insert data (after schema.sql)
```

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project.