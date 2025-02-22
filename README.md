# **CCC151 - Development Environment** 
### **Made with IntelliJ IDEA’s GUI Designer**  
*Note: Form files created by IntelliJ may not be readable or compatible with other IDEs.*

#### **Overview**  
This is a **demo GUI application** for managing a simple **student database** stored in **CSV files**.

>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Note:** The **Login Page** exists but is not yet functional until further development.  <br>  <br>  
> The project attempts a **modular and maintainable approach** by structuring the codebase into reusable components. 
> However, `adminPanelDashboard.java` currently contains **many reused (copy-pasted) code blocks**, which can make maintenance difficult. <br><br> 
> - These redundant code sections could be **refactored into reusable functions or separate classes**.  <br><br>
> - Optimizing this part of the project is still a **work in progress**, and improvements are being explored.  <br><br>
> - The goal is to reduce redundancy while keeping the code **readable, scalable, and easy to modify** in future updates.<br><br>

---

## <br><br> **Main Functionalities**
### ✅ **CRUDL Operations (Create, Read, Update, Delete, List)**<br><br>

- **Create**  
  - Enroll a student  
  - Register a college  
  - Propose a course  <br><br>

- **Read**  
  - Access student, program, and college data from `students.csv`, `programs.csv`, and `college.csv`.  <br><br>

- **Update**  
  - Modify student details (*excluding ID number*).  
  - Update program and college details.  <br><br>

- **Delete**  
  - Remove a student.  
  - Remove a program (*only if no students are enrolled under it*).  
  - Remove a college (*only if no students are enrolled under it*).  <br><br>

- **List**  
  - Display CSV data in a **tabular format**.  <br><br>
