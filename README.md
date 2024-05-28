<h1 align="center">Weather viewer</h1>

![Java](https://img.shields.io/badge/java-black.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Jakarta Servlets](https://img.shields.io/badge/Jakarta%20Servlets-black?style=for-the-badge&logo=java&logoColor=white)
![Cookie](https://img.shields.io/badge/Cookies-black?style=for-the-badge&logo=cookie&logoColor=white)
![Sessions](https://img.shields.io/badge/sessions-black?style=for-the-badge&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-black?style=for-the-badge&logo=mysql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-black?style=for-the-badge&logo=hibernate&logoColor=white)
![Integration Testing](https://img.shields.io/badge/Integration%20Testing-black?style=for-the-badge&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-black?style=for-the-badge&logo=mockito&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-black?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-black?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

___

## About project

A web application for viewing the current weather. The user can register and add one or more locations 
(cities, villages, and others) to the list, after which the main page of the application begins 
to display a list of locations with their current weather. User also can see hourly forecast for each location.


## Interface
![img_2.png](src/main/webapp/static/img/img_2.png)
![img_1.png](src/main/webapp/static/img/img_1.png)
___

## Functionality

### You can:
1. <h3>User related</h3>

   1.1. `Sign up`

   1.2. `Sign in`

   1.3. `Logout`

2. <h3>Locations related</h3>

   2.1. `search` a location

   2.2. `add` a location in your list

   2.3. `remove` a location from the list

   2.4. `view` a list of locations in your list
<br></br>

---

### Design pattern

Application uses [MVC](https://en.wikipedia.org/wiki/Model–view–controller) pattern. 

The `model` here is the **entity** classes that are needed to represent tables from the database. They are using [DAO](https://en.wikipedia.org/wiki/Data_access_object) to communicate with the database.

The `view` layer made by HTML, CSS & Thymeleaf.

The **servlets** act as `controllers`. They handle requests from user and sends HTTP responses.

---

### Sessions & cookies

The application uses cookies and sessions. Cookie is storing in user's browser and session storing in the application database.

Using what was described above, you can identify the user and give him access to the site without signing in again.

However, sessions in the database are deleted if the user has not visited the site for a long time. Cookies are deleted from his browser, and sessions in the database are periodically cleared.







