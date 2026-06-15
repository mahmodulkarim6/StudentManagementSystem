#Student Management System (Java Swing + MySQL)

A desktop-based Student Management System built using **Java Swing**, **JDBC**, and **MySQL**.  
This project allows admin to manage students efficiently with CRUD operations and a clean UI.

---

## 🚀 Features

- 🔐 Admin Login System
- ➕ Add New Student
- 📋 View Student Records (Table UI)
- ✏️ Update Student Information (including ID update)
- ❌ Delete Student
- 🔍 Live Search (by ID, Name, Department, Email, Phone)
- 📊 Export Reports (CSV file)
- ⚙️ Admin Settings (Username & Password update)
- 🖥️ Responsive Dashboard UI with Sidebar Navigation

---

## 🛠️ Technologies Used

- Java (Swing GUI)
- JDBC (Database Connection)
- MySQL (Database)
- MySQ Server
- Launch4j (Convert JAR to EXE)

---

## 📁 Project Structure

StudentManagementSystem/
│
├── src/ # Java source files
├── lib/ # MySQL Connector JAR
├── Database.sql # Database schema
├── manifest.txt # JAR manifest file
├── StudentManagementSystem.jar
├── .gitignore
└── README.md


---

## 🗄️ Database Setup

1. Open MySQL server and start **MySQL**
2. Go to `MySQL Workbench`
3. Create database:

```sql
CREATE DATABASE student_db;

Import Database.sql or run tables manually:

CREATE TABLE admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

▶️ How to Run Project
👉 Using IDE (NetBeans / IntelliJ / Eclipse)
Open project in IDE
Add mysql-connector-j in library
Run Login.java

Using JAR file
java -jar StudentManagementSystem.jar

👉 Using EXE (Launch4j)
    Double click StudentManagementSystem.exe
    Make sure MySQL/XAMPP is running

⚠️ Requirements
    Java JDK 8 or higher
    MySQL Server / XAMPP
    Internet not required (local DB)

📌 Important Notes
Database must be running before launching app
student_db must exist in MySQL
MySQL credentials must match in DBConnection.java

👨‍💻 Developer
Developed by: Mahmoduk karim Akash
Purpose: Academic Project (CSE),City University

⭐ Future Improvements
Cloud database integration
Role-based login system
Advanced UI (JavaFX / Web version)
Mobile version (Android)


