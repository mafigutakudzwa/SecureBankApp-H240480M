# Secure Banking Application

## Description  
The **Secure Banking Application** is a console-based Java program that allows users to manage their banking activities in a safe and efficient way.  
It offers secure user registration, authentication, and basic account operations such as deposits, withdrawals, and balance checks — all with encrypted password handling and reliable data storage.

---

##  Student Details  
- **Name:** Chamboko Tashinga  
- **Registration Number:** H240648Q  
- **Date:** 14 October 2025  

---

##  Features  
-  **User Registration** with username, email, and strong password validation.  
-  **Encrypted Password Storage** using SHA-256 hashing and unique salt for each user.  
-  **User Login** with password verification against stored encrypted credentials.  
-  **Automatic Account Number Generation** to ensure uniqueness and prevent duplication.  
-  **Deposit and Withdrawal** with real-time balance updates and transaction logging.  
-  **Balance Inquiry** and **Transaction History** display in a formatted table view.  
-  **Persistent Data Storage** using `users.txt` and `accounts.txt` files.  
-  **Error Handling** for invalid input, insufficient funds, and authentication errors.  

---

##  Technologies Used  
- **Language:** Java  
- **Libraries:**  
  - `java.io` – File input/output for data persistence  
  - `java.security` – Secure password hashing  
  - `java.time` – Timestamps for user and transaction logs  
  - `java.util` – Data structures, scanner input, and random generation  

---



##  Security Highlights  
- Uses **SHA-256 hashing** with **unique per-user salt** for password protection.  
- Enforces **strong password policy**:  
  - Minimum 8 characters  
  - At least one uppercase letter  
  - At least one number  
  - At least one special character  
- Prevents unauthorized access through **secure login validation**.  

---

##  Example Operations  
- Register a new user  
- Login securely  
- Create a new account  
- Deposit funds  
- Withdraw funds  
- View balance and transaction history  
- Logout or exit  

---

##  Author  
**Name:** Chamboko Tashinga  
**Registration Number:** H240648Q  
**Date:** 14 October 2025  
