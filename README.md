# ✈️ SpringFly Vaadin - Sistema de Reservas de Vuelos Impulsado por IA

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.1.2-blue)
![Vaadin](https://img.shields.io/badge/Vaadin-24.7.5-blue)
![License](https://img.shields.io/badge/license-MIT-green)

Una aplicación moderna de reservas de vuelos full-stack construida desde cero con **Java 21**, **Spring AI** y **Vaadin Flow**. Incluye un chatbot inteligente de atención al cliente basado en un sistema multiagente impulsado por OpenAI (GPT-4o-mini), implementación RAG con PGVector y una interfaz moderna y responsive.

## 🎯 Descripción del Proyecto

Este proyecto es una reimplementación completa del demo [Spring AI Flight Booking](https://github.com/loiane/spring-ai-flight-booking) de **[Loiane Groner](https://github.com/loiane)**, reemplazando Angular por **Vaadin Flow** para lograr una experiencia full-stack 100% Java.

- **Arquitectura Multi-Agente de IA** con enrutamiento inteligente
- **RAG (Retrieval-Augmented Generation)** usando PGVector para políticas de la empresa
- **Function Calling** para operaciones de reservas en tiempo real
- **Interfaz Vaadin Flow** con diseño moderno y responsive
- **Mejores prácticas** en desarrollo con Spring Boot y Java 21

## 🚀 Características

### 🤖 Soporte al Cliente con IA
- **Chatbot inteligente** impulsado por OpenAI GPT-4o-mini
- **Sistema Multi-Agente**:
    - **SupervisorAgent**: Enruta las solicitudes a agentes especializados
    - **BookingAgent**: Gestiona reservas de vuelos, cambios y cancelaciones
    - **PaymentAgent**: Administra consultas de tarifas y políticas de reembolso
    - **EscalationAgent**: Maneja quejas y problemas complejos
- **Implementación RAG**: Generación Aumentada por Recuperación usando PGVector
- **Function Calling**: Integración directa con herramientas de reservas
- **Gestión de Memoria**: Historial de conversaciones persistente

### ✈️ Gestión de Reservas
- **Visualización en tiempo real**: Grid interactivo con todas las reservas
- **Diseño responsive**: Componentes modernos de Vaadin Flow
- **Operaciones CRUD completas**: A través del asistente de IA o interacción directa

### 🏗️ Arquitectura Técnica
- **Backend**: Spring Boot 3.5.10 con Spring AI 1.1.2
- **Frontend**: Vaadin Flow 24.7.5 (Java puro, sin necesidad de JavaScript)
- **Base de datos**: PostgreSQL con extensión PGVector
- **Modelo de IA**: OpenAI GPT-4o-mini (Chat) + text-embedding-3-small (Embeddings)
- **Vector Store**: PGVector para búsqueda semántica de documentos
- **Contenedores**: Docker Compose para desarrollo

## 📋 Prerrequisitos

- **Java 21** (JDK 21 o superior)
- **Maven 3.8+** (o usar el Maven Wrapper incluido)
- **Docker Desktop** (para PostgreSQL con PGVector)
- **Clave de API de OpenAI**
- **IDE**: IntelliJ IDEA, Eclipse o VS Code con extensiones de Java

## 🔧 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/geovannymcode/spring-ai-flight-booking-vaadin.git
cd springfly
```

### 2. Configurar Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
DB_PASSWORD=springfly123
OPENAI_API_KEY=tu-clave-de-api-de-openai
```

### 3. Iniciar PostgreSQL con PGVector

```bash
docker compose up -d
```

Esto iniciará PostgreSQL 17 con la extensión PGVector en el puerto 5432.

### 4. Compilar y Ejecutar la Aplicación

Usando Maven Wrapper (recomendado):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

O usando tu Maven local:

```bash
mvn clean install
mvn spring-boot:run
```

