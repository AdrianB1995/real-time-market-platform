# Kafka Docker Container Setup

This directory contains the Docker Compose configuration for running Apache Kafka with Kafka UI.

## Overview

- **Kafka**: Apache Kafka 3.7.0
- **Kafka UI**: Provectus Labs Kafka UI for managing topics and monitoring
- **Network**: Isolated Docker network for service communication
- **Broker Port**: 9092 (PLAINTEXT)
- **UI Port**: 8080

## Prerequisites

- Docker and Docker Compose installed on your system

## Starting Kafka

Navigate to the containers directory and start the services:

```bash
cd containers
docker-compose up -d
```

This will start:
- **kafka**: Apache Kafka broker
- **kafka-ui**: Web UI for Kafka management

Verify services are running:

```bash
docker-compose ps
```

## Stopping Kafka

```bash
docker-compose down
```

To also remove data volumes:

```bash
docker-compose down -v
```

## Accessing Kafka UI

Open your browser and navigate to:

```
http://localhost:8080
```

The UI displays:
- Topics and their partitions
- Consumer groups and lag
- Message browsing
- Cluster metrics

## Basic Kafka Commands

Connect to the Kafka container to run CLI commands:

```bash
docker exec -it kafka bash
```

### Create a Topic

```bash
kafka-topics.sh --create \
  --bootstrap-server localhost:9092 \
  --topic test-topic \
  --partitions 3 \
  --replication-factor 1
```

### List Topics

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```

### Describe a Topic

```bash
kafka-topics.sh --describe \
  --bootstrap-server localhost:9092 \
  --topic test-topic
```

### Produce Messages

```bash
kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic test-topic
```

Then type messages (one per line) and press Ctrl+D to exit.

### Consume Messages

```bash
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic test-topic \
  --from-beginning
```

### List Consumer Groups

```bash
kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --list
```

### Describe Consumer Group

```bash
kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group <group-name>
```

### Delete a Topic

```bash
kafka-topics.sh --delete \
  --bootstrap-server localhost:9092 \
  --topic test-topic
```

## Connecting to Kafka from Applications

When connecting from services in Docker or locally:

- **From Docker container**: `bootstrap-server: kafka:9092`
- **From local machine**: `bootstrap-server: localhost:9092`

## Troubleshooting

### Kafka container won't start

Check logs:

```bash
docker-compose logs kafka
```

### Can't connect to Kafka from application

- Ensure the application is on the same Docker network or using `localhost:9092`
- Verify the container is running: `docker-compose ps`

### Data persistence

Kafka data is stored in a Docker volume `kafka-data`. To inspect:

```bash
docker volume ls | grep kafka
docker volume inspect containers_kafka-data
```

## Configuration Details

See `kafka-docker-compose.yml` for:
- Environment variables and broker settings
- Network configuration
- Volume mounting

The setup uses KRaft (Kafka Raft) mode with a single broker serving as both broker and controller.
