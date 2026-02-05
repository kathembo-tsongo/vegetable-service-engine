# Vegetable Service Engine - Setup Guide

## Prerequisites
- Java JDK 11 or higher
- Apache Tomcat 9.x

## Installation Steps

### 1. Extract Files
```bash
unzip vegetable-service-project.zip
cd vegetable-service-engine
```

### 2. Compile Source Code (if needed)
```bash
javac -d classes src/rmi/*.java
javac -cp lib/javax.servlet-api-4.0.1.jar:classes -d classes src/servlet/*.java
javac -cp classes -d classes src/client/*.java
```

### 3. Deploy to Tomcat
```bash
cp vegetable-service.war $CATALINA_HOME/webapps/
```

### 4. Start RMI Server
```bash
cd vegetable-service-engine
java -cp classes rmi.VegetableComputeEngine
```

### 5. Start Tomcat
```bash
$CATALINA_HOME/bin/startup.sh
```

### 6. Access Application
- Web Interface: http://localhost:8080/vegetable-service/
- API Endpoint: http://localhost:8080/vegetable-service/vegetable

## Testing

### Test RMI Standalone
```bash
java -cp classes client.TestRMIClient
```

### Test via curl
```bash
# Add vegetable
curl -X POST "http://localhost:8080/vegetable-service/vegetable" \
  -d "action=add&vegetable=carrot&price=2.50"

# Calculate cost
curl -X POST "http://localhost:8080/vegetable-service/vegetable" \
  -d "action=calculate&vegetable=carrot&quantity=5"
```

## Troubleshooting

### Port Already in Use
If port 1099 or 8080 is in use:
```bash
# Check and kill process on port 1099
lsof -ti:1099 | xargs kill -9

# Or change Tomcat port in conf/server.xml
```

### RMI Connection Failed
Ensure RMI server is running before starting Tomcat.

## Contact
For issues, refer to DEVELOPMENT_DIARY.md for detailed setup logs.
