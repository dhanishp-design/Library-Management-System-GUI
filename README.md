# 📚 Library Management System

A **Java Swing-based desktop application** designed to manage library operations such as books, students, and issue/return transactions. The system provides a user-friendly interface to automate and simplify traditional library processes.

---

## 🚀 Features

### 📖 Book Management
- Add new books  
- View all books (displayed in table format)  
- Update book details  
- Delete books  
- Search books by ID or Title  

### 👨‍🎓 Student Management
- Add student details  
- View student records  
- Delete student records  

### 🔄 Issue & Return System
- Issue books to students  
- Return books  
- Track issue and return dates  
- Maintain issue history  

### 🔐 Authentication
- Librarian login system  
- Username and password validation  

### 💰 Fine Calculation
- Automatic fine calculation (₹5 per day for late return)  

---

## 🛠️ Tech Stack

- **Frontend:** Java Swing (GUI)  
- **Database:** MySQL  
- **Connectivity:** JDBC  
- **IDE:** Eclipse / VS Code

---


## 🏗️ Project Structure

```bash
LibraryManagementSystem/
│── out/
│
│── src/
│   ├── config/
│   │   └── DatabaseConfig.java
│   │
│   ├── db/
│   │   └── DBConnection.java
│   │
│   ├── models/
│   │   ├── Book.java
│   │   ├── Issue.java
│   │   └── Student.java
│   │
│   ├── ui/
│   │   ├── LoginGUI.java
│   │   └── MainGUI.java
│   │
│   └── utils/
│       └── SecurityUtils.java
│
│── database.sql
│── mysql-connector.jar
│── README.md

```
---

## 🖥️ GUI Overview
- Login Screen
- Dashboard
- Forms for adding books and students
- Table view for displaying records
- Buttons for all operations (Add, Update, Delete, Search)
- Popup messages for success/error notifications

--- 

## 🔄 System Workflow
```
User → Java Swing GUI → JDBC → MySQL Database
```
---
## ⚠️ Requirements
- Java JDK 8 or above
- MySQL Server
- JDBC Driver (MySQL Connector)
- IDE (Eclipse Or VS)
---
## Output - 

<img width="1919" height="1019" alt="Screenshot 2026-04-19 234040" src="https://github.com/user-attachments/assets/cfbe0cee-a8b9-450c-89bf-74871e5ee6c8" />

  
---
## 🚀 Future Enhancements
- Web-based version
- Barcode scanning integration
- Email notifications
- Multi-user roles and permissions
- Cloud database integration

---
## 🏁 Conclusion

The Library Management System provides an Efficient and Structured way to Manage Library Resources. It reduces manual work, improves Accuracy, and ensures better organization of data through a simple and intuitive interface.

---
## 👨‍💻 Author

**DHANISH H POOJARY** & **ASHWINI POOJARI**
