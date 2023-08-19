# Transactions Monitor (Back-end)
The application is built for an e-banking system, where users can check their transactions information by any given date.


## Run with Docker
_Prerequisite: [Docker](https://www.docker.com/get-started/)_

### 1. Project setup
- Clone the project
- Open **Docker** and then `docker compose up`
### 2. Configure Kafka Connect
- Wait until ```INFO [Worker clientId=connect-1, groupId=connect-cluster-group] (Re-)joining group``` appears on the log file
- Then, register the Kafka Topic with ```curl -X POST -H "Content-Type: application/json" -d @./connector-config/transactions-sink-connector.json http://localhost:8083/connectors/```
### 3. Start using the project
- Go to [transaction-frontend](https://github.com/genewang0929/transaction-frontend/edit/main/README.md#run-with-docker)


## Run with Kubernetes (Pod Autoscaling)
_Prerequisite: [Docker](https://www.docker.com/get-started/), [minikube](https://minikube.sigs.k8s.io/docs/start/), [metrics-server](https://github.com/kubernetes-sigs/metrics-server#installation), [VPA](https://github.com/kubernetes/autoscaler/tree/master/vertical-pod-autoscaler#installation)_

### 1. Project setup
- Clone the project
- Open **Docker** and then start the minikube cluster with `minikube start`
- `cd k8s` and then apply all files under the folder by `kubectl apply -f [FILENAME1] -f [FILENAME2]`
### 2. Configure Kafka Connect
- Wait until ```INFO [Worker clientId=connect-1, groupId=connect-cluster-group] (Re-)joining group``` appears on the log file
- Then, register the Kafka Topic with ```curl -X POST -H "Content-Type: application/json" -d @./connector-config/transactions-sink-connector.json http://192.168.49.2:30162/connectors/```
### 3. Start using the project
- Go to [transaction-frontend](https://github.com/genewang0929/transaction-frontend/edit/main/README.md#run-with-kubernetes-pod-autoscaling)

