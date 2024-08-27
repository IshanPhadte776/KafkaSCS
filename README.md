# Restaurant Reservation System

## Purpose
Work with various Java and Spring style technologies to build a robust and scalable event-driven application.

## Project Story
Solve the ad-hoc restaurant reservation problem by creating a system that efficiently handles reservations and provides real-time feedback to users.

## Program Overview
This project is an event-driven reservation system where users can make reservations that are processed through a message broker. The system then sends confirmation messages to a different Kafka topic, ensuring that reservations are managed asynchronously and confirmations are delivered reliably.

## Tech Stack
- **Java**
- **Spring Framework**
- **Spring Boot**
- **Spring Cloud**
- **Spring Cloud Stream**
- **Apache Kafka**


## Sample Output 

Received Reservation: Reservation [ID=1, Name='Ishan', Party Size=5, Placed Order Time='19:19:45']
Reservation for Ishan has been confirmed.
Received Reservation: Reservation [ID=2, Name='Ishan', Party Size=5, Placed Order Time='19:19:50']
Reservation for Ishan has been confirmed.
Received Reservation: Reservation [ID=3, Name='Ishan', Party Size=5, Placed Order Time='19:19:55']
Reservation for Ishan has been confirmed.
Received Reservation: Reservation [ID=4, Name='Ishan', Party Size=5, Placed Order Time='19:20:00']
Reservation for Ishan has been confirmed.


