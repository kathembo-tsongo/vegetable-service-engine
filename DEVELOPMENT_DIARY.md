# Vegetable Service Engine - Development Diary

## Project Overview
A distributed mobile application using Java RMI and Servlets to manage vegetable prices and transactions.

## Architecture
1. **Client Layer** - HTML/JavaScript web interface and curl commands
2. **Servlet Layer** - HTTP request handler (VegetableServiceServlet.java)
3. **RMI Layer** - Business logic processor (VegetableComputeEngine.java)

---

## Development Steps

### Date: February 5, 2026

#### Step 1: Project Setup
- Created project directory structure: `src/{rmi,servlet,client}`, `classes`, `lib`
- Verified Java installation (Java 11+)

#### Step 2: RMI Implementation
**Files Created:**
1. `Task.java` - Interface for executable tasks
2. `Compute.java` - Remote interface for task execution
3. `VegetableComputeEngine.java` - RMI server with vegetable price table
4. `VegetableComputeTaskRegistry.java` - Client-side RMI registry lookup
5. `AddVegetablePrice.java` - Task to add vegetables
6. `UpdateVegetablePrice.java` - Task to update prices
7. `DeleteVegetablePrice.java` - Task to delete vegetables
8. `CalVegetableCost.java` - Task to calculate costs
9. `CalculateCost.java` - Task to generate receipts

**Compilation:**
```bash
javac -d classes src/rmi/*.java
```

**Testing:**
- Created `TestRMIClient.java` to test all RMI operations
- Started RMI server on port 1099
- Successfully tested all CRUD operations

**Challenges:**
- Initial compilation error due to missing `AddVegetablePrice.java`
- Port 1099 conflict from previous RMI instance
- Solution: Used `pkill -f VegetableComputeEngine` to kill old process

#### Step 3: Servlet Layer
**Files Created:**
1. `VegetableServiceServlet.java` - HTTP to RMI bridge
2. `WEB-INF/web.xml` - Servlet configuration

**Dependencies:**
- Downloaded `javax.servlet-api-4.0.1.jar`

**Compilation:**
```bash
javac -cp lib/javax.servlet-api-4.0.1.jar:classes -d classes src/servlet/*.java
```

#### Step 4: Tomcat Setup
**Installation:**
- Downloaded Apache Tomcat 9.0.93
- Extracted to `~/apache-tomcat-9.0.93`
- Made scripts executable: `chmod +x ~/apache-tomcat-9.0.93/bin/*.sh`

**Configuration:**
- Changed HTTP port from 8080 to 8090 (system Tomcat conflict)
- Changed shutdown port from 8005 to 8006 (port conflict)

**Deployment:**
```bash
# Created WAR structure
mkdir -p build/WEB-INF/classes
cp -r classes/* build/WEB-INF/classes/
cp WEB-INF/web.xml build/WEB-INF/

# Built and deployed WAR
cd build
jar -cvf vegetable-service.war *
cp vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
```

**Challenges:**
- Port conflicts with system Tomcat on Ubuntu
- Solution: Used custom ports (8090, 8006)

#### Step 5: Integration Testing
**Terminal 1 - RMI Server:**
```bash
java -cp classes rmi.VegetableComputeEngine
```

**Terminal 2 - Tomcat Server:**
```bash
cd ~/apache-tomcat-9.0.93
./bin/startup.sh
```

**Terminal 3 - Testing:**
```bash
# All CRUD operations tested successfully
curl -X POST "http://localhost:8090/vegetable-service/vegetable" -d "action=add&vegetable=cucumber&price=1.75"
curl -X POST "http://localhost:8090/vegetable-service/vegetable" -d "action=update&vegetable=cucumber&price=2.00"
curl -X POST "http://localhost:8090/vegetable-service/vegetable" -d "action=calculate&vegetable=cucumber&quantity=5"
curl -X POST "http://localhost:8090/vegetable-service/vegetable" -d "action=delete&vegetable=cucumber"
curl -X POST "http://localhost:8090/vegetable-service/vegetable" -d "action=receipt&vegetables[]=tomato&quantities[]=2&amountGiven=20&cashier=Alice"
```

**Results:**
✅ Add operation - SUCCESS
✅ Update operation - SUCCESS  
✅ Calculate operation - SUCCESS (Total: $10.00)
✅ Delete operation - SUCCESS
✅ Receipt generation - SUCCESS

#### Step 6: Web Interface
**Created:** `index.html` - User-friendly web interface
**Features:**
- Add vegetable form
- Update price form
- Delete vegetable form
- Calculate cost calculator
- Receipt generator with multiple items

**Access:** http://localhost:8090/vegetable-service/

---

## Final System Architecture
```
[Web Browser / Mobile Client]
           ↓
    [HTTP POST Request]
           ↓
[VegetableServiceServlet] (Port 8090)
           ↓
    [RMI Call via Registry]
           ↓
[VegetableComputeEngine] (Port 1099)
           ↓
    [Task Execution]
           ↓
[In-Memory Vegetable Price Table]
```

---

## File Structure
```
vegetable-service-engine/
├── src/
│   ├── rmi/
│   │   ├── Task.java
│   │   ├── Compute.java
│   │   ├── VegetableComputeEngine.java
│   │   ├── VegetableComputeTaskRegistry.java
│   │   ├── AddVegetablePrice.java
│   │   ├── UpdateVegetablePrice.java
│   │   ├── DeleteVegetablePrice.java
│   │   ├── CalVegetableCost.java
│   │   └── CalculateCost.java
│   ├── servlet/
│   │   └── VegetableServiceServlet.java
│   └── client/
│       └── TestRMIClient.java
├── classes/
│   ├── rmi/
│   └── servlet/
├── lib/
│   └── javax.servlet-api-4.0.1.jar
├── WEB-INF/
│   └── web.xml
├── build/
│   └── vegetable-service.war
└── DEVELOPMENT_DIARY.md
```

---

## How to Run

### Prerequisites
- Java JDK 11 or higher
- Apache Tomcat 9.x

### Starting the System

1. **Start RMI Server:**
```bash
   cd ~/vegetable-service-engine
   java -cp classes rmi.VegetableComputeEngine
```

2. **Start Tomcat:**
```bash
   cd ~/apache-tomcat-9.0.93
   ./bin/startup.sh
```

3. **Access the Application:**
   - Web Interface: http://localhost:8090/vegetable-service/
   - API Endpoint: http://localhost:8090/vegetable-service/vegetable

### Stopping the System

1. **Stop Tomcat:**
```bash
   cd ~/apache-tomcat-9.0.93
   ./bin/shutdown.sh
```

2. **Stop RMI Server:**
   Press `Ctrl+C` in the RMI terminal

---
### Redeploy
cd ~/vegetable-service-engine/build
jar -cvf vegetable-service.war *
~/apache-tomcat-9.0.93/bin/shutdown.sh
sleep 3
rm -rf ~/apache-tomcat-9.0.93/webapps/vegetable-service*
cp vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
~/apache-tomcat-9.0.93/bin/startup.sh
sleep 15
================================

## Testing Checklist

- [x] RMI server starts successfully on port 1099
- [x] Tomcat deploys WAR file without errors
- [x] Servlet connects to RMI server
- [x] Add vegetable operation works
- [x] Update vegetable operation works
- [x] Delete vegetable operation works
- [x] Calculate cost operation works
- [x] Generate receipt operation works
- [x] Web interface displays correctly
- [x] All forms submit successfully

---

## Lessons Learned

1. **Port Management:** Always check for port conflicts before starting servers
2. **RMI Registry:** Must be running before clients can connect
3. **Servlet Deployment:** WAR files auto-extract in Tomcat's webapps directory
4. **Error Handling:** Important to have clear error messages for debugging
5. **Testing:** Test RMI layer independently before integrating with servlets

---

## Future Enhancements

1. Add database persistence (MySQL/PostgreSQL)
2. Implement user authentication
3. Add transaction history logging
4. Create mobile Android app
5. Add inventory management features
6. Implement barcode scanning
7. Add report generation (PDF/Excel)
8. Multi-cashier support with sessions

---

## Conclusion

Successfully implemented a three-tier distributed vegetable service engine using Java RMI and Servlets. All CRUD operations work correctly, and the system handles HTTP requests, delegates to RMI server, and returns formatted responses. The web interface provides user-friendly access to all features.

**Project Status:** ✅ COMPLETE AND FUNCTIONAL
