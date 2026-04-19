# Java Swing Library Management System

A beginner-friendly, secure, full-stack library management system featuring a Java Swing UI and a MySQL database backend.

## Features
* **Security:** Passwords are hashed using SHA-256 and all SQL queries use `PreparedStatement` to prevent SQL injection.
* **Authentication:** Secure librarian login.
* **Book Management:** Add, View, and Delete books. Live search functionality.
* **Student Management:** Manage library members/students easily.
* **Issue & Return Logic:** Issues books automatically checking availability, records the return date, and automatically assesses late fines (₹5 per day after 14 days).
* **Modern Architecture:** Developed using MVC strategy and separated configuration files.

## Project Structure
```text
c:\Users\DHANISH\Desktop\DBMS MINI PROJECT
├── database.sql                        -- SQL Script to import tables and sample data
├── README.md                           -- Instructions
└── src/
    ├── config/DatabaseConfig.java      -- Setup MySQL URL, root username, and password here
    ├── db/DBConnection.java            -- Singleton Database Connectivity Manager
    ├── models/Book.java                -- Database Entity for Books
    ├── models/Student.java             -- Database Entity for Students
    ├── models/Issue.java               -- Database Entity for Issued Records
    ├── ui/LoginGUI.java                -- The starting point (contains main method)
    ├── ui/MainGUI.java                 -- The dashboard containing Tabbed UI
    └── utils/SecurityUtils.java        -- SHA-256 Hashing Utility
```

## How to Run in Eclipse / IntelliJ

### Step 1: Database Setup
1. Download, install, and start MySQL.
2. Open a tool like MySQL Workbench.
3. Open `database.sql` and run all lines. This creates the `LibraryDB` schema, tables, and inserts the default admin login:
   * **Username:** admin
   * **Password:** admin123

### Step 2: Code Setup
1. Create a new Java project in your IDE without using a Build System (or create a standard Java project).
2. Point the source directory to the `src` folder.
3. Download the `mysql-connector-java.jar` (e.g., version 8.0.33) and add it to your project's **Build Path** / **Dependencies**.
4. Open `src/config/DatabaseConfig.java` and make sure the `PASSWORD` constant is set to your local MySQL root password.

### Step 3: Run
1. Navigate to `src/ui/LoginGUI.java`.
2. Right-click and select **Run 'LoginGUI.main()'**.
3. Login using `admin` / `admin123`.
