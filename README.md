# **CCC151 - Development Environment**

## **Made with IntelliJ IDEA’s GUI Designer**  
*Note: Form files created by IntelliJ may not be readable or compatible with other IDEs.*

### **Overview**  
This is a **demo GUI application** for managing a simple **student database** stored in **CSV files**.

> **Note:** The **Login Page** exists but is not yet functional until further development.<br>
>  Uses Java's **Look and Feel.**  
> The project attempts a **modular and maintainable approach** by structuring the codebase into reusable components.  
> However, `adminPanelDashboard.java` currently contains **many reused (copy-pasted) code blocks**, which can make maintenance difficult.  
> - These redundant code sections could be **refactored into reusable functions or separate classes**.  
> - Optimizing this part of the project is still a **work in progress**, and improvements are being explored.  
> - The goal is to reduce redundancy while keeping the code **readable, scalable, and easy to modify** in future updates.

---

### **Main Functionalities**
#### ✅ **CRUDL Operations (Create, Read, Update, Delete, List)**

- **Create**  
  - Enroll a student  
  - Register a college  
  - Propose a course  

- **Read**  
  - Access student, program, and college data from `students.csv`, `programs.csv`, and `college.csv`.  

- **Update**  
  - Modify student details (*excluding ID number*).  
  - Update program and college details.  

- **Delete**  
  - Remove a student.  
  - Remove a program (*only if no students are enrolled under it*).  
  - Remove a college (*only if no students are enrolled under it*).  

- **List**  
  - Display CSV data in a **tabular format**.
