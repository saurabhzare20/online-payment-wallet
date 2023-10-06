# online-payment-wallet

Welcome to the Online Wallet App repository! This project is an online wallet application that allows users to manage their digital wallet, make transactions, and monitor their transaction history. The application consists of a React-based front-end that communicates with a Node.js Express server for handling REST API calls. The server, in turn, interacts with various backend services implemented using Java Spring Boot.

# Features
User Authentication: Users can create accounts, log in, and securely manage their online wallets.

Transaction Validation: Transactions are validated using Apache Kafka, ensuring secure and reliable fund transfers.

Transaction History: Users can view their transaction history and monitor their balance.

Logging: The application logs various events and transactions for debugging and auditing purposes.

Load Balancing: The backend services are designed to be scalable and provide load balancing to handle high user loads efficiently.


# Technologies Used
Front-end: React
Back-end: Node.js Express, Java Spring Boot
Message Broker: Apache Kafka


# pre -install 
install zookeeper and 

docker run -p 2181:2181 zookeeper

 docker run -p 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka
 
