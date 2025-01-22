# E-Banking Transactions Monitor
An e-banking system that tracks users monthly transactions under different currencies.

This project aimed for simulating real-world e-banking system where the number of requests could be astronomical. To prevent the app from crashing, efforts need to be made to maintain the system's scalability and reliability.


## Key Features
- **Highly Scalable & Reliable:** Able to handle 10,000+ transactions within seconds.
- **Transaction Dashboard:** Cards overview of a user's transaction records.
- **Monthly Credit & Debit Calculation:** Calculate user's monthly credit and debit value, under specific currency.


## Tech Stack
- **Frontend:** Vue, JavaScript, HTML, CSS
- **Backend:** Spring Boot, Java, Kafka
- **Infra:** Docker, Kubernetes


## Motivation
The project was an add-on to an assignment of one of my classes, which only required me to build the prototype of a simple e-banking systems. However, I was curious about how it could be adjusted to the real-world architecture, which apparently, should be able to **handle the load of thousands of users without crashing down**. It is when I got introduced to Apache Kafka and Kubernetes and decided to incorporate into this project. 

To ensure each transaction is safely stored. I incorporated **Apache Kafka**, which is a famous tool for adding message queue to a system. I specifically incorporated **Kafka Connect**, successfully decoupled data production and consumption processes from database. This enhanced 2x MongoDB write throughput.

To scale the system and prevent request overload, I learned **Kubernetes** and applied to this project. I fine-tuned resources allocation, with many tries on multiple sets of CPU and RAM using both HPA and VPA. As a result, the system achieved 12,000 requests with 0% error rate.


## Challenges and Learnings
- **Challenge:** Spring Boot wasn't able to connect to Kafka when I run the system on Windows. 
  - **Solution:** I was running Kafka binary files on WSL, which has a different file system to Windows.
- **Challenge:** Kafka Connnect couldn't link to MongoDB, when both executed on containers. 
  - **Solution:** When tested in local environment, I found an additional step of moving a jar file from `MongoSinkConnector` to `kafka` directory. This is not included in kafka-connect section of `docker-compose.yaml`. Therefore, I went to official doc and found another Dockerfile that did the exact instruction.
- **Challenge:** minikube cluster often encountered http timeout.
  - **Cause:** The running containers exhausted the RAM allocated to my Docker.


## Getting Started / Installation
### Run with Docker
_Prerequisite: [Docker](https://www.docker.com/get-started/)_

- **1. Project setup**
  - Clone the project
  - Open **Docker** and then `docker compose up`
- **2. Configure Kafka Connect**
  - Wait until ```INFO [Worker clientId=connect-1, groupId=connect-cluster-group] (Re-)joining group``` appears on the log file
  - Then, register the Kafka Topic with ```curl -X POST -H "Content-Type: application/json" -d @./connector-config/transactions-sink-connector.json http://localhost:8083/connectors/```
- **3. Start using the project**
  - Go to [transaction-frontend](https://github.com/genewang0929/transaction-frontend/tree/main#run-with-docker)


### Run with Kubernetes (Pod Autoscaling)
_Prerequisite: [Docker](https://www.docker.com/get-started/), [minikube](https://minikube.sigs.k8s.io/docs/start/), [metrics-server](https://github.com/kubernetes-sigs/metrics-server#installation), [VPA](https://github.com/kubernetes/autoscaler/tree/master/vertical-pod-autoscaler#installation)_

- **1. Project setup**
  - Clone the project
  - Open **Docker** and then start the minikube cluster with `minikube start`
  - `cd k8s` and then apply all files under the folder by `kubectl apply -f [FILENAME1] -f [FILENAME2]`
- **2. Configure Kafka Connect**
  - Wait until ```INFO [Worker clientId=connect-1, groupId=connect-cluster-group] (Re-)joining group``` appears on the log file
  - Then, register the Kafka Topic with ```curl -X POST -H "Content-Type: application/json" -d @./connector-config/transactions-sink-connector.json http://192.168.49.2:30162/connectors/```
- **3. Start using the project**
  - Go to [transaction-frontend](https://github.com/genewang0929/transaction-frontend/tree/main#run-with-kubernetes-pod-autoscaling)


## Contact / Connect
- **Chun-Han, Wang**
- genewang7@gmail.com
- [GitHub](https://github.com/genewang0929)
- [LinkedIn](https://www.linkedin.com/in/chun-han-wang/)
