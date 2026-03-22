# 🚀 GitHub Access Report (Spring Boot Project)

## 📌 Overview
This project is a Spring Boot backend application that connects to the GitHub API and generates a report showing **which users have access to which repositories** within a given organization.

It fetches repositories of an organization and maps users (contributors) to the repositories they are associated with.

---

## 🛠 Tech Stack
- Java 17
- Spring Boot
- REST API
- Maven
- GitHub API

---

## ⚙️ Features
- Fetch all repositories of a GitHub organization
- Fetch contributors for each repository
- Map users → repositories
- Parallel API calls using `CompletableFuture` (performance optimized)
- Clean and structured API response

---

## 📡 API Endpoint

### GET Request

/api/github/access-report?org=orgName


### Example:

http://localhost:8080/api/github/access-report?org=github


---

## 📊 Sample Response

```json
[
  {
    "username": "mislav",
    "repositories": ["repo1", "repo2"]
  },
  {
    "username": "user2",
    "repositories": ["repo3"]
  }
]

🔐 Configuration

Before running the project, add your GitHub token in:

src/main/resources/application.properties
github.token=YOUR_TOKEN


▶️ How to Run
1)Clone the repository
2)Open project in VS Code / IntelliJ
3)Add your GitHub token
4)Run the application:
  mvn spring-boot:run
5)Open browser and test API

'''

---

