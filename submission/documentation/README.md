# Vegetable Service Engine
# Vegetable Service Engine

A distributed mobile application for managing vegetable prices and generating transaction receipts using Java RMI and Servlets.

## Features

- ✅ Add new vegetable prices
- ✅ Update existing vegetable prices
- ✅ Delete vegetable entries
- ✅ Calculate vegetable costs based on quantity
- ✅ Generate detailed transaction receipts
- ✅ Web-based user interface
- ✅ RESTful API via servlets

## Technology Stack

- **Java RMI** - Remote Method Invocation for distributed computing
- **Java Servlets** - HTTP request handling
- **Apache Tomcat 9** - Web server and servlet container
- **HTML/CSS/JavaScript** - Frontend interface

## Quick Start

### 1. Start RMI Server
```bash
cd ~/vegetable-service-engine
java -cp classes rmi.VegetableComputeEngine
```

### 2. Start Tomcat
```bash
cd ~/apache-tomcat-9.0.93
./bin/startup.sh
```

### 3. Access Application
Open browser: http://localhost:8090/vegetable-service/

## API Usage
```bash
# Add vegetable
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=add&vegetable=carrot&price=2.50"

# Update price
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=update&vegetable=carrot&price=3.00"

# Calculate cost
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=calculate&vegetable=carrot&quantity=5"

# Delete vegetable
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=delete&vegetable=carrot"

# Generate receipt
curl -X POST "http://localhost:8090/vegetable-service/vegetable" \
  -d "action=receipt&vegetables[]=tomato&quantities[]=2&vegetables[]=onion&quantities[]=1.5&amountGiven=20&cashier=John"
```

## Project Structure
```
vegetable-service-engine/
├── src/rmi/              # RMI server and task classes
├── src/servlet/          # Servlet implementation
├── src/client/           # Test client
├── classes/              # Compiled classes
├── lib/                  # Dependencies
└── build/                # WAR file
```

========================
========================
Users with rolebased access
----------------------------
this is the defaust code with the index in the build folder.
-----------------------------------------------------------
If your need a single page for users where the admin and cashier are not separated, then copy and past the index from submition/documentation/ folders to the build/index.html. then redeploy the code and run. you will seed a single page index with all manager and cashier activities confused together.
----------------------------------------------------------------
after updating the code, remember to redeploy the code: 

cd ~/vegetable-service-engine/build
jar -cvf vegetable-service.war *
~/apache-tomcat-9.0.93/bin/shutdown.sh
sleep 3
rm -rf ~/apache-tomcat-9.0.93/webapps/vegetable-service*
cp vegetable-service.war ~/apache-tomcat-9.0.93/webapps/
~/apache-tomcat-9.0.93/bin/startup.sh
sleep 5

## Author

Dieudonne

## License

Educational Project - 2026
