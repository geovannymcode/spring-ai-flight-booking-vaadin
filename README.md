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

### 5. Acceder a la Aplicación

Abre tu navegador y navega a:

- **Aplicación**: [http://localhost:8080](http://localhost:8080)
- **Health Check**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

## 📁 Estructura del Proyecto

```
springfly/
├── src/main/java/com/geovannycode/springfly/
│   ├── Application.java                 # Aplicación principal
│   ├── agents/                          # Agentes de IA
│   │   ├── SupervisorAgent.java
│   │   ├── BookingAgent.java
│   │   ├── PaymentAgent.java
│   │   └── EscalationAgent.java
│   ├── config/                          # Configuración
│   │   └── PromptConfig.java
│   ├── model/                           # Modelos de dominio
│   │   ├── Booking.java
│   │   ├── Passenger.java
│   │   ├── BookingStatus.java
│   │   ├── BookingClass.java
│   │   ├── BookingDetails.java
│   │   ├── BookingSnapshot.java
│   │   ├── ValidationResult.java
│   │   └── SpringFlyDB.java
│   ├── service/                         # Lógica de negocio
│   │   ├── BookingService.java
│   │   ├── BookingTools.java            # Herramientas de IA (Function Calling)
│   │   ├── ValidationTools.java
│   │   ├── ChatService.java
│   │   ├── DataInitializationService.java
│   │   └── DocumentIngestionService.java
│   └── views/                           # Interfaz Vaadin
│       ├── MainLayout.java
│       ├── DashboardView.java
│       ├── BookingsView.java
│       ├── ChatView.java
│       └── AboutView.java
├── src/main/resources/
│   ├── application.properties           # Configuración de la app
│   ├── META-INF/resources/
│   │   └── styles.css                   # Estilos personalizados
│   ├── prompts/                         # Prompts de agentes
│   │   ├── supervisor-agent-v1.md
│   │   ├── booking-agent-v1.md
│   │   ├── payment-agent-v1.md
│   │   ├── escalation-agent-v1.md
│   │   └── system-prompt-v1.md
│   └── rag/                             # Documentos RAG
│       └── springfly-terms-of-service.md
├── compose.yaml                         # Docker Compose
└── pom.xml                              # Configuración Maven
```

## 🤖 Profundización en las Características de IA

### Arquitectura Multi-Agente

El sistema utiliza un enfoque sofisticado de múltiples agentes:

1. **SupervisorAgent**: Analiza las solicitudes entrantes y las enruta al especialista adecuado
2. **BookingAgent**: Equipado con herramientas de reservas para operaciones de vuelos
3. **PaymentAgent**: Gestiona cálculos de tarifas y políticas de reembolso
4. **EscalationAgent**: Maneja problemas complejos y quejas

### Herramientas de IA Disponibles

- `getBookingDetails` - Obtener información del vuelo
- `changeFlightDate` - Modificar la fecha de salida
- `changeFlightRoute` - Actualizar origen/destino
- `changeBooking` - Cambiar fecha y ruta simultáneamente
- `cancelBooking` - Procesar cancelaciones
- `validateAction` - Verificar que la acción realizada fue exitosa

### Implementación RAG

El sistema utiliza Generación Aumentada por Recuperación para acceder a las políticas de la empresa:
- Términos de servicio almacenados como embeddings en PGVector
- Búsqueda semántica de información relevante sobre políticas
- Respuestas contextuales basadas en las directrices de la empresa

## 📊 Datos de Ejemplo

La aplicación se inicializa con **10 reservas** de ejemplo con rutas dentro de Colombia:

| Reserva # | Pasajero          | Ruta       | Ciudad Origen       | Ciudad Destino    | Estado     |
|-----------|-------------------|------------|---------------------|-------------------|------------|
| 1000      | Geovanny Mendoza  | BAQ → BOG  | Barranquilla        | Bogotá            | Confirmado |
| 1001      | Elena Aguirre     | BOG → CLO  | Bogotá              | Cali              | Confirmado |
| 1002      | Omar Berroteran   | BOG → MDE  | Bogotá              | Medellín          | Confirmado |
| 1003      | Valeria Ahumada   | CLO → CTG  | Cali                | Cartagena         | Confirmado |
| 1004      | Maricela Aguirre  | BAQ → MDE  | Barranquilla        | Medellín          | Confirmado |
| 1005      | Rafael Ramirez    | CTG → BOG  | Cartagena           | Bogotá            | Confirmado |
| 1006      | Maria Gomez       | SMR → BOG  | Santa Marta         | Bogotá            | Confirmado |
| 1007      | Maria Gonzalez    | PEI → BOG  | Pereira             | Bogotá            | Confirmado |
| 1008      | Andres Mendoza    | BGA → BOG  | Bucaramanga         | Bogotá            | Aleatorio  |
| 1009      | Atilio Vega       | MDE → ADZ  | Medellín            | San Andrés        | Aleatorio  |

> **Nota**: Las primeras 8 reservas se crean con estado `CONFIRMED`. Las últimas 2 tienen un estado aleatorio (`CONFIRMED`, `COMPLETED` o `CANCELLED`). La clase de vuelo (Economy, Business, Premium Economy) y los asientos se asignan aleatoriamente.

## 💬 Uso del Asistente de IA

### Conversaciones de Ejemplo

**Consultar reserva:**
```
Usuario: ¿Cuál es el estado de mi reserva 1000? Soy Geovanny Mendoza.
IA: Déjame buscar eso para ti...
```

**Cambiar fecha de vuelo:**
```
Usuario: Necesito cambiar mi vuelo 1001 al 15 de marzo de 2026. Soy Elena Aguirre.
IA: Puedo ayudarte con eso. Tu vuelo actual es de Bogotá a Cali,
    hay una tarifa de cambio según tu clase. ¿Deseas continuar?
```

**Consulta de tarifas:**
```
Usuario: ¿Cuánto cuesta cancelar un boleto de clase Economy?
IA: Para la clase Economy, la tarifa de cancelación es de $200.
    Debes cancelar al menos 48 horas antes de la salida.
```

## 🎨 Componentes de la Interfaz Vaadin

- **MainLayout**: Shell de la aplicación con barra de navegación lateral
- **DashboardView**: Vista principal del dashboard
- **BookingsView**: Grid interactivo con todas las reservas
- **ChatView**: Interfaz de chat en tiempo real con el asistente de IA
- **AboutView**: Información sobre la aplicación

## 🔒 Seguridad y Mejores Prácticas

- Configuración basada en variables de entorno (sin secretos hardcodeados)
- Separación de responsabilidades en la capa de servicios
- Modelos de dominio inmutables usando Java records
- Logging y manejo de errores completo
- Validación de entrada en todas las llamadas a herramientas de IA
- Seguridad transaccional para modificaciones de reservas

## 🛠️ Desarrollo

### Ejecutar en Modo Desarrollo

El modo de desarrollo de Vaadin incluye:
- Hot reload para cambios en Java
- Recarga automática del navegador
- Modo debug habilitado

```bash
./mvnw spring-boot:run
```

### Compilar para Producción

```bash
./mvnw clean package -Pproduction
java -jar target/springfly-1.0-SNAPSHOT.jar
```

## 📝 Configuración

### Propiedades Principales de la Aplicación

```properties
# Configuración del modelo de IA (OpenAI)
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.embedding.options.model=text-embedding-3-small

# Vector Store
spring.ai.vectorstore.pgvector.dimensions=1536
spring.ai.vectorstore.pgvector.distance-type=COSINE_DISTANCE

# Prompts de agentes
app.prompt.supervisor-agent=supervisor-agent-v1.md
app.prompt.booking-agent=booking-agent-v1.md
```

## 🧪 Pruebas

### Pruebas Manuales

1. **Ver reservas**: Navega a la página principal
2. **Chatear con la IA**: Haz clic en "Asistente IA" en la barra lateral
3. **Probar consulta de reserva**: Pregunta por la reserva 1000 de Geovanny Mendoza
4. **Probar cambio de vuelo**: Solicita cambiar una fecha de vuelo
5. **Probar consulta de tarifas**: Pregunta sobre tarifas de cambio o cancelación

## 📈 Mejoras Futuras

- [ ] Tests de integración con TestContainers
- [ ] Autenticación y autorización de usuarios
- [ ] Notificaciones por email para cambios de reservas
- [ ] Dashboard de analíticas avanzado
- [ ] Mejoras de responsive para móviles
- [ ] Soporte multiidioma
- [ ] Integración con datos de vuelos reales

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! No dudes en enviar un Pull Request.

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT.

## 👥 Autor

- **Geovanny Mendoza** - [@geovannymcode](https://github.com/geovannymcode)

## 🙏 Agradecimientos

Un agradecimiento especial a **[Loiane Groner](https://github.com/loiane)** por su excelente proyecto original [spring-ai-flight-booking](https://github.com/loiane/spring-ai-flight-booking), que sirvió como base e inspiración para esta reimplementación. Su trabajo demostrando las capacidades de Spring AI con un sistema multiagente fue fundamental para el desarrollo de SpringFly Vaadin.

- **Proyecto original**: [https://github.com/loiane/spring-ai-flight-booking](https://github.com/loiane/spring-ai-flight-booking)
- Reconstruido desde cero con Java 21, Spring AI y Vaadin Flow
- Impulsado por OpenAI y PGVector
