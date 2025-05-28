![AI Order Banner](banner.png)

# ☕ AI Order Recommendation Service

This is a cloud-native Java Spring Boot project that turns **natural language input into structured order recommendations**, powered by OpenAI, Kafka, and MongoDB.

---

## 🚀 Features

- 🧠 Input free-form sentence, receive smart food & drink recommendations
- 💬 Uses OpenAI API (gpt-3.5-turbo) to analyze user inputs
- 🔒 Session-based data isolation using client-generated UUIDs
- 📦 Sends AI response into Kafka topic `recommendations`
- 🔄 Kafka UI for visualization
- 🧾 MongoDB: stores orders with TTL (auto-delete after 24h)
- 📊 Prometheus + Grafana observability
- 🐳 Docker Compose for Kafka stack (Kafka, Zookeeper, Kafka UI)

---

## 🧱 Tech Stack

| Layer            | Tech                             |
|------------------|----------------------------------|
| Backend          | Java 17, Spring Boot 3           |
| Messaging        | Apache Kafka, Spring Kafka       |
| AI Integration   | OpenAI GPT API                   |
| Database         | MongoDB, Spring Data MongoDB     |
| Monitoring       | Prometheus, Grafana              |
| DevOps           | Docker Compose                   |
| Frontend         | Angular, Typescript, Tailwind    |

---

## 📚 Learning Purpose
This project is designed for learning and building skills in:

- Spring Boot microservices & REST APIs

- Kafka event-driven architecture

- Cloud-native deployments

- OpenAI integration into real services

- Scalable observability (Prometheus, Grafana)

---

## 🔧 How to Run

### 1. Start Kafka & UI

```bash
docker compose up -d
```

### 2. Set OpenAI API Key
In your environment:
```bash
export OPENAI_API_KEY=xxxxxxxxxxx
```

### 3. Run
Run AiServiceApplication.java

### 4. API Testing
```bash
POST http://localhost:8081/api/ai/recommend
Content-Type: application/json

{
  "prompt": "I want something warm and spicy",
  "username": "Jane",
  "uuid": "some-uuid"
}
```

### 👩‍💻 Author
Built with ❤️ by JaneLee8693


