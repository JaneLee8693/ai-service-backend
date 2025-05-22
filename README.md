# ☕ AI Order Recommendation Service

This is a Spring Boot side project that turns **natural language input into structured order recommendations**, powered by OpenAI and Kafka.

---

## 🚀 Features

- 🧠 Input free-form sentence, receive smart product recommendations
- 💬 Uses OpenAI API (gpt-3.5-turbo) to analyze user intent
- 📦 Sends AI response into Kafka topic `recommendations`
- 🔄 Kafka UI for visualization
- 🧾 MongoDB: persist order items from Kafka
- 📊 Prometheus + Grafana monitoring
- 🐳 Docker Compose setup for Kafka, Zookeeper, Kafka UI, MongoDB

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
| Frontend         | Angular                          |

---

## 📚 Learning Purpose
This project is designed for learning and building skills in:

- Spring Boot microservices

- Kafka event-driven architecture

- Cloud-native deployments

- AI x backend integration

- Real-world observability

---

## 🔧 How to Run (Locally)

### 1. Start Kafka & UI

```bash
docker compose up -d
```

### 2. Start MongoDB
```bash
docker compose -f docker-compose.mongodb.yml up -d
```

### 3. Set OpenAI API Key
In your environment:
```bash
export OPENAI_API_KEY=xxxxxxxxxxx
```

### 4. Run
Run AiServiceApplication.java

### 5. API Testing
```bash
POST http://localhost:8081/api/ai/recommend
Content-Type: application/json

{
  "text": "I'm in a bad mood today and want to eat something sweet"
}
```

The response will be saved to Kafka topic recommendations, and displayed in Kafka UI:

```bash
http://localhost:8080
```

👩‍💻 Author
Built with ❤️ by JaneLee8693


