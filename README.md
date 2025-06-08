# Cryptsafe – Password Manager

**Cryptsafe** is an application developed as an academic project for the course **Information Security Techniques**. This password manager allows users to securely store credentials for various websites or services, integrating modern practices such as authentication, encryption, and controlled access to sensitive data.

## Project Overview

The main objective of Cryptsafe is to implement a secure and functional system that allows:
- Secure storage of user credentials.
- User registration with **email verification** as a form of two-factor authentication.
- Temporary access to passwords via **unique session token generation**.

This project was developed using **Spring Boot** for the backend and **Angular** for the frontend. **PostgreSQL** is used as the primary database system.

---

## Key Features

- User registration with **email-based verification** as 2FA.
- Secure password storage per site (site, username, password, URL, and notes).
- Passwords are only viewable through the **generation of a unique access token**.
- Intuitive interface with table display and password actions.
- Backend and frontend are fully decoupled via **RESTful API**.
- Encrypted storage and access control for maximum security.

---

## Prerequisites

Make sure to install the following tools on your local machine before running the application:

### Required Tools
- [Node.js](https://nodejs.org/en/download/package-manager/)
- [Angular CLI](https://angular.dev/installation)
- [npm](https://docs.npmjs.com/cli/v6/commands/npm-install)
- [Java JDK](https://www.oracle.com/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)

---

## Clone the Repository

```bash
git clone https://github.com/Santi-Ortiz/Password-Manager
cd Password-Manager
```
## Run Project

```bash
cd Password-Manager/src/main/java/com/example/demo/
```
- Click on Run Project



