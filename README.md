🚀 Investo – Real-Time Stock Market Simulation Platform
📌 Overview 
Investo is a real-time stock market simulation backend system built using Spring Boot.
It allows users to experience stock market behavior using live (free-tier) market data APIs without risking real money.
The system focuses on secure authentication, real-time data simulation, and scalable backend architecture.
--------------------------------------------------------------------------------------------------------------------------------
✨ Features 
📊 Real-time stock market data simulation using free APIs
🔐 Secure authentication using Google OAuth2 + JWT
📱 Mobile number verification using OTP system (backend-driven)
🗄️ MySQL database integration for persistent storage
⚡ RESTful APIs for frontend integration
👤 User profile management system
📈 Modular and scalable backend architecture
--------------------------------------------------------------------------------------------------------------------------------
🧠 Tech Stack
Backend: Spring Boot
Security: Spring Security, JWT, OAuth2
Database: MySQL
API Testing: Postman
Build Tool: Maven
Language: Java
------------------------------------------------------------------------------------------------------------------------------------
🔄 System Flow
User Login (Google OAuth2)
        ↓
JWT Token Generated
        ↓
User Enters Mobile Number
        ↓
OTP Generated (Backend)
        ↓
OTP Verified
        ↓
Mobile Marked as Verified
        ↓
Access to Dashboard (Stock Simulation)
------------------------------------------------------------------------------------------------------------------------
📡 API Features
/auth/login → Google OAuth login
/auth/token → JWT generation
/user/add-mobile → Add mobile number
/otp/send → Generate OTP
/otp/verify → Verify OTP
/stocks/live → Fetch simulated live stock data
-----------------------------------------------------------------------------------------------------------------------------
🗄️ Database Structure
User Table
id
name
email
google_id
mobile_number
mobile_verified
role
OTP Table
id
mobile_number
otp
expiry_time
--------------------------------------------------------------------------------------------------------------------------
🎯 Project Goal
To build a real-world backend system that simulates stock market behavior while implementing industry-level authentication and verification systems.
🚀 Future Improvements
📊 Live trading simulation engine
💼 Portfolio management system
🤖 AI-based stock prediction module
📱 React/Android frontend dashboard
🔔 Real-time notifications
🏁 How to Run
git clone https://github.com/your-username/investo.git
cd investo
mvn spring-boot:run
-----------------------------------------------------------------------------------------------------------------------------
💡 Highlights
Clean backend architecture
Production-style authentication flow
Real-world OTP verification system
Scalable design for fintech applications
---------------------------------------------------------------------------------------------------------------------------------
👨‍💻 Author
Sachin Sahu
Backend Developer | Spring Boot Enthusiast
⭐ If you like this project
Give a ⭐ on GitHub and feel free to contribute!
