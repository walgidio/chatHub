# ChatHub

ChatHub is a distributed group chat application that supports real-time communication using WebSockets, with backend services managed by Spring Boot, RabbitMQ for messaging, MongoDB for data persistence, and Apache ZooKeeper for load balancing. The application enables users to send and receive messages instantly while connecting to the optimal server in a distributed setup.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Usage](#usage)
- [Endpoints](#endpoints)

## Features

- **Real-time Messaging**: Send and receive messages instantly using WebSockets.
- **Distributed Architecture**: Connects users to the server with the lowest load for optimal performance.
- **Centralized Configuration Management**: Uses a config server to manage and centralize application settings.
- **Load Balancing**: ZooKeeper monitors server load and redirects users to the least loaded server.
- **Persistence**: Stores user information and chat history in MongoDB.
- **Secure User Authentication**: Supports user registration and login.

## Technologies Used

- **Spring Boot** - Backend framework
- **WebSocket with STOMP** - Real-time communication
- **RabbitMQ** - Message broker for distributed messaging
- **MongoDB** - NoSQL database for data storage
- **Apache ZooKeeper** - Load balancing and server monitoring
- **SockJS** - WebSocket fallback for older browsers

## Architecture

The ChatHub application has a distributed architecture with the following components:

1. **Config Server** - Centralized server to manage configuration properties for all services.
2. **WebSocket Server** - Manages real-time communication between users and distributes messages.
3. **RabbitMQ** - Routes messages between different servers.
4. **ZooKeeper** - Monitors each server's load and redirects users to the least busy server.
5. **MongoDB** - Stores user data and chat history.

## Usage
WebSocket Configuration
Connecting to the WebSocket:
Frontend clients connect via the /chat-websocket endpoint using SockJS and STOMP protocols.

Sending Messages:
Messages are sent to the /app/send endpoint.

Receiving Messages:
Subscribe to /topic/messages to receive chat messages in real-time.

Client-Side Setup
The frontend JavaScript uses SockJS and STOMP to manage WebSocket connections. Users connect to the optimal server endpoint based on ZooKeeper's load balancing.

## Endpoints
User Management
POST /api/users/register - Register a new user
POST /api/users/login - User login
GET /api/users/{username} - Get user by username
WebSocket Endpoints
/chat-websocket - Main WebSocket endpoint for connections
/app/send - Endpoint for sending messages
/topic/messages - Subscription endpoint for receiving messages
