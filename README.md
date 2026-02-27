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

## Author

Dieudonne

## License

Educational Project - 2026

==============================

IMPORTANT NOTES 

### Running the app

cd ~/apache-tomcat-9.0.93 && ./bin/startup.sh

### connect rmi
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine

### if it refuse try this:
cd ~/vegetable-service-engine
javac -d build/classes src/rmi/*.java
**Then**
cd build
java -Djava.rmi.server.hostname=localhost -cp classes rmi.VegetableComputeEngine

**You should see:**

VegetableComputeEngine is running on port 1099...
Initial vegetable prices loaded.

#### Keep this terminal open and running!** The RMI server must stay running for the web application to work.

Once the RMI server is running, go back to your browser and refresh the page:

http://localhost:8090/vegetable-service/


2.Step-by-Step Setup:
On Your PC (Server Machine): -cp
Step 1: Find your PC's IP address
hostname -I
You should see something like 192.168.1.100 or 10.0.0.5. Write this down!
Step 2: Stop the current RMI server
Go to the terminal where RMI is running and press Ctrl+C
Step 3: Restart RMI with your PC's actual IP
Replace YOUR_PC_IP with the IP you found:
cd ~/vegetable-service-engine
java -Djava.rmi.server.hostname=YOUR_PC_IP -cp classes rmi.VegetableComputeEngine

Example:
java -Djava.rmi.server.hostname=192.168.1.100 -cp classes rmi.VegetableComputeEngine
Step 4: Check if firewall is blocking
sudo ufw status
If it says "active", temporarily disable it for testing:
sudo ufw disable
sudo lsof -i :8090


# On Your Client Machine:

Step 1: Make sure the two machines (Client and Server) are on the SAME WiFi network.

Step 2: Open the browser on your Client Machine (Chrome, Safari, Firefox, etc.)

Step 3: Type this URL** (replace with your PC's IP):

http://YOUR_PC_IP:8090/vegetable-service/

Example: http://192.168.1.100:8090/vegetable-service/ 

Test real-time sync: 
Add a vegetable from your Client Machine browser
Click "View All Vegetables" on your Your Server Machine - you should see it!
Add a vegetable from your Server Machine and View it on your Client Machine


