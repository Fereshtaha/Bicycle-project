# Bicycle-project

## Full stack bicycle project

- This is a project in the course Application Development at NTNU Ålesund. 
- Our goal is to make a functional fullstack application for renting bikes in ålesund. 
- This project taught us how to make both frontend and backend for a website. 

---
## Table of Contents

Here is the content overview: 

- [Preparation](#preparation)
- [Installation and running the project](#installation-and-running-the-project)
- [User accounts](#user-accounts)
- [Credits](#credits)
- [License](#license)
---

## Preparation
1. Install PostgreSQL (optional)


2. Edit the `application.properties` file if you are planning on using another database than PostgreSQL (would recommend to go through either ways) :


3. change the `server port` to whatever you want. (recommended: 8443 for https)
```
server.port=
```
4. Change the `url` to the desired database.
```
spring.datasource.url=jdbc:postgresql://localhost:5432/bikeshop
```
5. Change the `username` and `password` for the selcted database(may not be required for some databases).
```
spring.datasource.username=
spring.datasource.password=
```
6. Change the dialect to the selected database type.
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
---

## Installation and running the project

1. To install the project, you can either use `git clone https://github.com/Sebastiannilsen/Backend.git` or download zip and open it on your local computer.
2. Open the project in your selected text editor.
3. Execute `mvn spring-boot:run` in the root of this project, or run the main method in the BikeApplication.
4. If you see the project running and "Importing test data..." in the console, congrats!
5. open https://localhost:8443/ or change host/port depending on settings in application.properties file. 
6. Enjoy our website!
---

## User accounts
To log into the website you can create a new user at "url/create-user" or login with the user credentials below. And to access the admin pages please login with the admin credentials below

Admin details:
- Email: admin@sensor.no
- Password: Passord1

User details:
- Email: user@sensor.no
- Password: Passord1

To access the admin pages, please open https://localhost:8443/admin or change host/port depending on settings in application.properties file.





## Credits

We would like to give credits to NTNU Ålesund for this assignment. 

Collaborators: 
- https://github.com/Sebastiannilsen
- https://github.com/Fereshtaha

We followed the tutorials given by our lecturer Girts Strazdins.

- https://github.com/strazdinsg/app-dev
- https://github.com/strazdinsg/web-examples

---
## License

This project is licenced by the © group 16 in the course [Application Development IDATA2306](https://www.ntnu.edu/studies/courses/IDATA2306#tab=omEmnet) and [Web Technologies IDATA2301](https://www.ntnu.edu/studies/courses/IDATA2301#tab=omEmnet) at the Norwegian University of Science and Technology (NTNU).

---


