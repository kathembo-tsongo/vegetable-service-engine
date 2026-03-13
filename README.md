#  Vegetable Service Engine 

A professional **3-tier distributed system** for managing vegetable inventory and point-of-sale transactions using **Java RMI, Servlets, and Web Technologies**.

##  Table of Contents

- [Features](#features)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [User Access](#user-access)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Network Deployment](#network-deployment)
- [Troubleshooting](#troubleshooting)
- [Author](#author)

---

##  Features

### Manager Portal
-  Add new vegetables with prices
-  Update existing vegetable prices
-  Delete vegetables from inventory
-  View complete inventory list

### Cashier Portal
- Calculate costs for individual items
- Generate transaction receipts with change calculation
- Process multi-item sales
- View current price list

### Security Features
-  Role-based authentication (Manager/Cashier)
-  Session management
-  Secure error handling (no stack trace exposure)
-  User-specific access control

---

##  System Architecture
```
┌──────────────────────────────────────────────────────────┐
│                  CLIENT LAYER (Web Browser)              │
│  Landing Page → Login → Manager/Cashier Portal          │
└──────────────────────────────────────────────────────────┘
                            ↓ HTTP (Port 8090)
┌──────────────────────────────────────────────────────────┐
│               SERVLET LAYER (Apache Tomcat)              │
│  VegetableServiceServlet.java - HTTP ↔ RMI Bridge      │
└──────────────────────────────────────────────────────────┘
                            ↓ RMI (Port 1099)
┌──────────────────────────────────────────────────────────┐
│                 RMI SERVER LAYER (Backend)               │
│  VegetableComputeEngine.java - Business Logic & Data    │
└──────────────────────────────────────────────────────────┘
```

**Communication Flow:**
1. User interacts with web interface (HTML/JavaScript)
2. Browser sends HTTP POST to servlet
3. Servlet creates RMI task and sends to RMI server
4. RMI server executes task and returns result
5. Servlet sends response back to browser
6. JavaScript displays result to user

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | HTML5, CSS3, JavaScript (ES6+) |
| **Middle Tier** | Java Servlets, Apache Tomcat 9.0.93 |
| **Backend** | Java RMI, UnicastRemoteObject |
| **Data Storage** | In-Memory HashMap |
| **Communication** | HTTP (Fetch API), RMI (Remote Method Invocation) |

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK):** Version 21 or higher
- **Apache Tomcat:** Version 9.0.93 (configured on port 8090)
- **Web Browser:** Chrome, Firefox, Safari, or Edge
- **Operating System:** Linux (Ubuntu 24), macOS, or Windows
- **Network:** Same network for multi-device access

---

##  Installation

### Step 1: Clone/Download Project
```bash
cd ~
# Ensure you have the vegetable-service-engine directory
cd vegetable-service-engine
```

### Step 2: Verify Directory Structure
```bash
ls -la
# Should show: src/, classes/, build/, lib/, WEB-INF/
```

### Step 3: Install Apache Tomcat (if not installed)
```bash
# Download Tomcat 9.0.93
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.93/bin/apache-tomcat-9.0.93.tar.gz

# Extract
tar -xzf apache-tomcat-9.0.93.tar.gz -C ~/

# Make scripts executable
chmod +x ~/apache-tomcat-9.0.93/bin/*.sh
```

### Step 4: Configure Tomcat Ports

Edit `~/apache-tomcat-9.0.93/conf/server.xml`:
```xml
<!-- Change default 8080 to 8090 -->
<Connector port="8090" protocol="HTTP/1.1" ... />

<!-- Change shutdown port to avoid conflicts -->
<Server port="8006" shutdown="SHUTDOWN">
```

### Step 5: Compile Application (if needed)
```bash
cd ~/vegetable-service-engine

# Compile RMI classes
javac -d classes src/rmi/*.java

# Compile Servlet
javac -cp lib/javax.servlet-api-4.0.1.jar:classes -d classes src/servlet/*.java

# Build WAR file
cd build
jar -cvf vegetable-service.war *

# Deploy to Tomcat
cp vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
```

---

##  Running the Application

### Step 1: Start RMI Server (Backend)

**Terminal 1:**
```bash
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine
```

**Expected Output:**
```
VegetableComputeEngine is running on port 1099...
Initial vegetable prices loaded.
```

 **IMPORTANT:** Keep this terminal open! The RMI server must remain running.

**Alternative (if above fails):**
```bash
cd ~/vegetable-service-engine
javac -d build/classes src/rmi/*.java
cd build
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine
```

### Step 2: Start Tomcat Server (Middle Tier)

**Terminal 2:**
```bash
cd ~/apache-tomcat-9.0.93
./bin/startup.sh
```

**Expected Output:**
```
Using CATALINA_BASE:   /home/user/apache-tomcat-9.0.93
...
Tomcat started.
```

**Wait 10-15 seconds** for the application to deploy.

### Step 3: Access Web Application

Open your browser and navigate to:
```
http://localhost:8090/vegetable-service/
```

You should see the landing page with **Manager** and **Cashier** role selection.

---

## 👥 User Access

### Login Credentials

| Role | Username | Password | Permissions |
|------|----------|----------|-------------|
| **Manager** | `admin` | `admin123` | Add, Update, Delete, View Inventory |
| **Cashier** | `cashier` | `cash123` | View Prices, Calculate Cost, Generate Receipt |

### Access URLs

- **Landing Page:** `http://localhost:8090/vegetable-service/`
- **Login Page:** `http://localhost:8090/vegetable-service/login.html?role=manager`
- **Manager Portal:** `http://localhost:8090/vegetable-service/manager.html`
- **Cashier Portal:** `http://localhost:8090/vegetable-service/cashier.html`

---

## 📡 API Documentation

### Base URL
```
http://localhost:8090/vegetable-service/vegetable
```

### Endpoints

#### 1. Add Vegetable
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=add&vegetable=carrot&price=2.50"
```

**Response:**
```
SUCCESS: Added carrot at price $2.5
```

#### 2. Update Price
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=update&vegetable=carrot&price=3.00"
```

**Response:**
```
SUCCESS: Updated carrot to price $3.0
```

#### 3. Delete Vegetable
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=delete&vegetable=carrot"
```

**Response:**
```
SUCCESS: Deleted carrot
```

#### 4. Calculate Cost
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=calculate&vegetable=tomato&quantity=5"
```

**Response:**
```
Vegetable: tomato, Quantity: 5.00 kg, Price: $2.50/kg, Total Cost: $12.50
```

#### 5. List All Vegetables
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=list"
```

**Response:**
```
========== INVENTORY LIST ==========
Vegetable              Price/kg
====================================
potato               $     1.80
onion                $     1.50
tomato               $     2.50
====================================
Total items: 3
```

#### 6. Generate Receipt
```bash
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=receipt&vegetables[]=tomato&quantities[]=2&vegetables[]=onion&quantities[]=1.5&amountGiven=20&cashier=John"
```

**Response:**
```
========== VEGETABLE STORE RECEIPT ========
Date: 2026-02-27 14:30:25
Cashier: John
===========================================

tomato           2.00 kg x $ 2.50 = $  5.00
onion            1.50 kg x $ 1.50 = $  2.25

===========================================
Total Cost:               $  7.25
Amount Given:             $ 20.00
Change Due:               $ 12.75
===========================================
```

---

##  Project Structure
```
vegetable-service-engine/
├── src/
│   ├── rmi/                          # RMI Server & Task Classes
│   │   ├── Task.java                 # Generic task interface
│   │   ├── Compute.java              # Remote interface
│   │   ├── VegetableComputeEngine.java  # Main RMI server
│   │   ├── AddVegetablePrice.java
│   │   ├── UpdateVegetablePrice.java
│   │   ├── DeleteVegetablePrice.java
│   │   ├── CalVegetableCost.java
│   │   ├── ListAllVegetables.java
│   │   ├── CalculateCost.java
│   │   └── VegetableComputeTaskRegistry.java
│   ├── servlet/                      # Servlet Layer
│   │   └── VegetableServiceServlet.java
│   └── client/                       # Test Client
│       └── TestRMIClient.java
├── classes/                          # Compiled .class files
├── build/                            # WAR packaging
│   ├── WEB-INF/
│   │   ├── classes/                  # Compiled classes
│   │   └── web.xml                   # Servlet config
│   ├── index.html                    # Landing page
│   ├── login.html                    # Authentication
│   ├── manager.html                  # Manager portal
│   ├── cashier.html                  # Cashier portal
│   └── vegetable-service.war         # Deployable WAR
├── lib/
│   └── javax.servlet-api-4.0.1.jar   # Servlet dependency
├── WEB-INF/
│   └── web.xml                       # Servlet configuration
└── final-submission/                 # Submission package
    ├── source-code/
    ├── compiled/
    ├── deployment/
    └── documentation/
```

---

## 🌐 Network Deployment (Multi-Device Access)

### On Server Machine (Your PC)

#### Step 1: Find Server IP Address
```bash
hostname -I
# Example output: 192.168.1.100
```

Write down this IP address!

#### Step 2: Stop Current RMI Server

Press `Ctrl+C` in the terminal where RMI is running.

#### Step 3: Restart RMI with Server IP
```bash
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=192.168.1.100 -cp classes rmi.VegetableComputeEngine
```

**Replace `192.168.1.100` with YOUR actual IP address.**

#### Step 4: Configure Firewall
```bash
# Check firewall status
sudo ufw status

# If active, allow ports
sudo ufw allow 8090/tcp
sudo ufw allow 1099/tcp

# Or temporarily disable for testing
sudo ufw disable
```

#### Step 5: Verify Ports
```bash
sudo lsof -i :8090
sudo lsof -i :1099
```

Both should show active connections.

### On Client Machine (Phone/Tablet/Another PC)

#### Step 1: Connect to Same WiFi Network

Ensure both devices are on the **same WiFi network**.

#### Step 2: Access Application

Open browser on client device and navigate to:
```
http://SERVER_IP:8090/vegetable-service/
```

**Example:** `http://192.168.1.100:8090/vegetable-service/`

#### Step 3: Test Real-Time Sync

1. **On Client Device:** Login as Manager, add a vegetable (e.g., "spinach" at $3.00)
2. **On Server Machine:** Login as Cashier, click "View All Prices"
3. **Result:** You should see "spinach" in the list!

This demonstrates the distributed nature of the system — both machines access the same RMI server.

---

## 🔧 Troubleshooting

### Problem 1: "Connection Refused" Error

**Symptoms:** Browser shows "System temporarily unavailable"

**Solutions:**
```bash
# Check if RMI is running
ps aux | grep VegetableComputeEngine

# Check if port 1099 is listening
ss -tuln | grep 1099

# Restart RMI server
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine
```

### Problem 2: 404 Not Found

**Symptoms:** Browser shows "HTTP Status 404 – Not Found"

**Solutions:**
```bash
# Check if WAR is deployed
ls ~/apache-tomcat-9.0.93/webapps/vegetable-service.war

# Check if app extracted
ls ~/apache-tomcat-9.0.93/webapps/vegetable-service/

# Redeploy
cp ~/vegetable-service-engine/build/vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
~/apache-tomcat-9.0.93/bin/shutdown.sh
sleep 3
~/apache-tomcat-9.0.93/bin/startup.sh
```

### Problem 3: Port Already in Use

**Symptoms:** "Port already in use: 1099" or "Port already in use: 8090"

**Solutions:**
```bash
# Kill RMI process
pkill -f VegetableComputeEngine

# Stop Tomcat
~/apache-tomcat-9.0.93/bin/shutdown.sh

# Check what's using port 8090
sudo lsof -i :8090
# Kill process: sudo kill -9 PID
```

### Problem 4: Can't Access from Other Devices

**Symptoms:** Works on localhost but not from phone/tablet

**Solutions:**
1. Ensure both devices on **same WiFi network**
2. Check firewall allows ports 8090 and 1099
3. Verify server IP: `hostname -I`
4. Use server's actual IP in URL, not "localhost"
5. Restart RMI with server IP: `-Djava.rmi.server.hostname=YOUR_IP`

### Problem 5: Session/Login Issues

**Symptoms:** Redirects to login or can't stay logged in

**Solutions:**
- Clear browser cache and cookies
- Use incognito/private browsing mode
- Check browser console for JavaScript errors (F12)
- Verify sessionStorage is enabled in browser settings

---

## 🛑 Stopping the Application

### Stop Tomcat
```bash
cd ~/apache-tomcat-9.0.93
./bin/shutdown.sh
```

### Stop RMI Server
In the terminal where RMI is running, press:
```
Ctrl + C
```

---

## 📚 Additional Resources

- **Implementation Report:** See `final-submission/documentation/IMPLEMENTATION_REPORT.md`
- **Development Diary:** See `final-submission/documentation/DEVELOPMENT_DIARY.md`
- **Java RMI Tutorial:** https://docs.oracle.com/javase/tutorial/rmi/
- **Servlet Tutorial:** https://docs.oracle.com/javaee/7/tutorial/servlets.htm

---

## 👨‍💻 Author

**Dieudonne**  
Educational Project - Distributed Systems Course  
February 2026

---

## 📄 License

This project is an educational assignment and is not licensed for commercial use.

---

## 🎯 Project Highlights

- ✅ **3-Tier Architecture:** Client, Servlet, RMI layers
- ✅ **Distributed Computing:** Remote method invocation across network
- ✅ **Role-Based Access:** Manager and Cashier separation
- ✅ **Security:** Session management, authentication, error masking
- ✅ **Professional UI:** Modern gradient design, responsive layout
- ✅ **Real-Time Sync:** Multi-device inventory updates
- ✅ **RESTful API:** Command-line accessible endpoints
- ✅ **Comprehensive Documentation:** Reports, diagrams, development diary

---

**🌟 Ready to Deploy! 🌟**

For questions or issues, refer to the troubleshooting section or consult the implementation documentation.