# Vegetable Service Engine

**A Three-Tier Distributed System for Inventory and Point-of-Sale Management**

---

## 1. Overview

The **Vegetable Service Engine** is a professionally designed three-tier distributed application developed to manage vegetable inventory and point-of-sale transactions. The system integrates **Java RMI**, **Java Servlets**, and modern web technologies to demonstrate robust distributed computing principles, clear separation of concerns, and role-based access control.

The architecture follows a classical client–server model with a dedicated computation engine, ensuring modularity, scalability, and maintainability.

---

## 2. System Features

### 2.1 Manager Portal

The Manager interface provides full administrative control over inventory:

* Add new vegetables with associated pricing
* Update existing vegetable prices
* Remove vegetables from the inventory
* View the complete inventory listing

### 2.2 Cashier Portal

The Cashier interface is designed for transaction processing:

* Calculate costs for individual vegetable quantities
* Process multi-item transactions
* Generate detailed receipts with change computation
* View current inventory pricing

### 2.3 Security and Access Control

The system incorporates structured security mechanisms:

* Role-based authentication (Manager and Cashier)
* Session-based access management
* Controlled error handling (no stack trace exposure)
* User-specific authorization constraints

---

## 3. System Architecture

The application implements a **three-tier distributed architecture**:

### Client Layer (Presentation Tier)

* Web-based user interface (HTML, CSS, JavaScript)
* Executes in a standard web browser
* Communicates via HTTP over Port 8090

### Servlet Layer (Application Tier)

* Hosted on Apache Tomcat
* Acts as an HTTP–RMI bridge
* Handles request parsing, session validation, and task delegation

### RMI Server Layer (Business Logic Tier)

* Implements core business logic
* Executes remote computation tasks
* Maintains in-memory data storage
* Communicates via Java RMI on Port 1099

### Communication Flow

1. User interaction occurs through the browser interface.
2. The browser submits an HTTP request to the servlet.
3. The servlet constructs and dispatches an RMI task.
4. The RMI server executes the task and returns the result.
5. The servlet forwards the response to the client.
6. The browser dynamically renders the result.

This structure ensures strict separation between presentation, application control, and business logic layers.

---

## 4. Technology Stack

| Layer         | Technology                          |
| ------------- | ----------------------------------- |
| Frontend      | HTML5, CSS3, JavaScript (ES6+)      |
| Middle Tier   | Java Servlets, Apache Tomcat 9.0.93 |
| Backend       | Java RMI (UnicastRemoteObject)      |
| Data Storage  | In-Memory HashMap                   |
| Communication | HTTP (Fetch API), Java RMI          |

---

## 5. Prerequisites

* Java Development Kit (JDK) 21 or higher
* Apache Tomcat 9.0.93 (configured to run on Port 8090)
* Modern web browser (Chrome, Firefox, Safari, or Edge)
* Linux (Ubuntu 24), macOS, or Windows
* Shared network (for multi-device deployment)

---

## 6. Installation and Deployment

### 6.1 Project Setup

```bash
cd ~
cd vegetable-service-engine
```

Verify directory structure:

```bash
ls -la
```

Expected directories include: `src/`, `classes/`, `build/`, `lib/`, `WEB-INF/`.

---

### 6.2 Apache Tomcat Installation (If Required)

```bash
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.93/bin/apache-tomcat-9.0.93.tar.gz
tar -xzf apache-tomcat-9.0.93.tar.gz -C ~/
chmod +x ~/apache-tomcat-9.0.93/bin/*.sh
```

Modify `server.xml` to configure:

* HTTP Connector Port: 8090
* Shutdown Port: 8006

---

### 6.3 Compilation and WAR Deployment

Compile RMI components:

```bash
javac -d classes src/rmi/*.java
```

Compile Servlet components:

```bash
javac -cp lib/javax.servlet-api-4.0.1.jar:classes -d classes src/servlet/*.java
```

Package WAR file:

```bash
cd build
jar -cvf vegetable-service.war *
cp vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
```

---

## 7. Running the Application

### Step 1: Start the RMI Server

```bash
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine
```

The RMI server must remain active during system operation.

---

### Step 2: Start Apache Tomcat

```bash
cd ~/apache-tomcat-9.0.93
./bin/startup.sh
```

Allow 10–15 seconds for deployment.

---

### Step 3: Access the Application

Navigate to:

```
http://localhost:8090/vegetable-service/
```

You will be presented with role selection for Manager or Cashier access.

---

## 8. User Access Credentials

| Role    | Username | Password | Permissions                  |
| ------- | -------- | -------- | ---------------------------- |
| Manager | admin    | admin123 | Full inventory control       |
| Cashier | cashier  | cash123  | Sales and receipt processing |

---

## 9. API Endpoints

Base URL:

```
http://localhost:8090/vegetable-service/vegetable
```

Supported operations include:

* Add vegetable
* Update price
* Delete vegetable
* Calculate cost
* List inventory
* Generate receipt

Each endpoint accepts HTTP POST parameters specifying the intended action and required data.

---

## 10. Project Structure

```
vegetable-service-engine/
├── src/
│   ├── rmi/
│   ├── servlet/
│   └── client/
├── classes/
├── build/
├── lib/
├── WEB-INF/
└── final-submission/
```

The structure clearly separates source code, compiled classes, deployment artifacts, and submission documentation.

---

## 11. Network Deployment (Multi-Device Access)

To enable distributed access across devices:

1. Identify the server machine’s IP address:

   ```bash
   hostname -I
   ```

2. Restart the RMI server using:

   ```bash
   java -Djava.rmi.server.hostname=SERVER_IP -cp classes rmi.VegetableComputeEngine
   ```

3. Ensure firewall rules permit traffic on ports 8090 and 1099.

4. Access the system from another device via:

   ```
   http://SERVER_IP:8090/vegetable-service/
   ```

All devices must be connected to the same network.

---

## 12. Troubleshooting Overview

Common issues include:

* Connection refused (RMI not running)
* HTTP 404 (WAR not deployed correctly)
* Port conflicts (1099 or 8090 already in use)
* Firewall restrictions
* Session or browser caching issues

Each issue can typically be resolved by verifying active services, port availability, and correct deployment configuration.

---

## 13. Terminating the Application

Stop Tomcat:

```bash
./bin/shutdown.sh
```

Terminate the RMI server using:

```
Ctrl + C
```

---

## 14. Author

Dieudonne
Distributed Systems Course
February 2026

---

## 15. Project Significance

This project demonstrates:

* Structured three-tier architecture
* Practical implementation of distributed computing via Java RMI
* Role-based access control and session management
* Clear separation of concerns across presentation, application, and business layers
* Professional deployment using Apache Tomcat and WAR packaging

The Vegetable Service Engine serves as a comprehensive academic demonstration of enterprise-level distributed system design principles implemented within a controlled educational context.
