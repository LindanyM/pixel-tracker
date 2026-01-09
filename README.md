# Pixel Tracker

[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

**Pixel Tracker** is a simple **Java Spring Boot** project that provides a REST endpoint to serve telemetry as a PNG image (pixel tracking). This can be used for server metrics tracking or basic user interaction tracking.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Endpoint](#endpoint)
- [Prerequisites](#prerequisites)
- [Build & Run](#build--run)
- [Testing](#testing)
- [Future Development](#future-development)
- [Author](#author)

---

## Project Structure


```
pixel-tracker/
├─ src/
│ ├─ main/
│ │ ├─ java/
│ │ │ └─ com/example/pixeltracker/
│ │ │ ├─ PixelTrackerApplication.java # Main Spring Boot app
│ │ │ └─ controllers/
│ │ │ └─ TrackerController.java # REST endpoint
│ │ └─ resources/
│ │ ├─ application.properties # Spring configuration
│ │ └─ static/ # Optional static files
├─ pom.xml # Maven descriptor
└─ README.md # Project documentation
```

---

## Endpoint

**GET** `/api/tracker`

| Parameter | Required | Description |
|-----------|----------|-------------|
| `name`    | No       | Name of the server or client sending telemetry (default: `Unknown`) |
| `cpuC`    | No       | CPU count (default: `0`) |
| `cpuL`    | No       | CPU load percentage (default: `0`) |
| `tRam`    | No       | Total RAM in MB (default: `0`) |
| `uRam`    | No       | Used RAM in MB (default: `0`) |
| `disk`    | No       | Free disk space in MB (default: `0`) |

**Example Request:**

```
curl "http://localhost:8080/api/tracker?name=TestServer&cpuC=4&cpuL=20&tRam=8192&uRam=2048&disk=50000"
```

Response: A PNG image representing telemetry.

Logs the telemetry data in the console.

Prerequisites

Java 17+ or 21

Maven 3.8+

Internet connection to download dependencies

Verify installation:
```
java -version
javac -version
mvn -v
```

Build & Run
1️⃣ Clone the project
```git clone <your-repo-url>
cd pixel-tracker
```

2️⃣ Build and run
```mvn clean spring-boot:run```


⚠️ If port 8080 is in use:

- Kill the existing process:

```sudo lsof -i :8080
sudo kill -9 <PID>
```

- Or change the port in src/main/resources/application.properties:
```
server.port=8081
```

3️⃣ Access the endpoint
```
http://localhost:8080/api/tracker?name=TestServer
```

Testing

Use curl, Postman, or any HTTP client:

```
curl "http://localhost:8080/api/tracker?name=Server1&cpuC=2&cpuL=15&tRam=4096&uRam=1024&disk=25000" --output tracker.png
```


This will download a PNG image called tracker.png.


Future Development

1×1 Transparent Pixel Tracker

Replace telemetry image with a 1×1 pixel for web tracking.

Structured Logging

Save logs in JSON format for easy integration with logging platforms (ELK, GCP Logging, etc.).

Dockerization

Add a Dockerfile to containerize the service:

```
FROM eclipse-temurin:21-jdk
COPY target/pixel-tracker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]`
```


Cloud Deployment

Deploy on GCP Cloud Run, AWS ECS, or Azure Container Apps.

Use environment variable for port assignment:
```
server.port=${PORT:8080}
```

CI/CD

Automate builds, Docker image creation, and deployments using GitHub Actions or GitLab CI.

Author

Lindani R Mabaso – Software Engineer | AI, Data, Cloud

