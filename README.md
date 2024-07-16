# API de Gestión de Tours

Esta API REST permite gestionar tours, tickets, reservas, hoteles, clientes y vuelos. Proporciona endpoints para crear, leer, actualizar y eliminar información de cada entidad.

## Endpoints

### Tours (Tour)
- `GET /tours` - Obtener todos los tours
- `POST /tours` - Crear un nuevo tour
- `GET /tours/{id}` - Obtener un tour por ID
- `PUT /tours/{id}` - Actualizar un tour por ID
- `DELETE /tours/{id}` - Eliminar un tour por ID

### Tickets (Ticket)
- `GET /tickets` - Obtener todos los tickets
- `POST /tickets` - Crear un nuevo ticket
- `GET /tickets/{id}` - Obtener un ticket por ID
- `PUT /tickets/{id}` - Actualizar un ticket por ID
- `DELETE /tickets/{id}` - Eliminar un ticket por ID

### Reservas (Reservation)
- `GET /reservations` - Obtener todas las reservas
- `POST /reservations` - Crear una nueva reserva
- `GET /reservations/{id}` - Obtener una reserva por ID
- `PUT /reservations/{id}` - Actualizar una reserva por ID
- `DELETE /reservations/{id}` - Eliminar una reserva por ID

### Hoteles (Hotel)
- `GET /hotels` - Obtener todos los hoteles
- `POST /hotels` - Crear un nuevo hotel
- `GET /hotels/{id}` - Obtener un hotel por ID
- `PUT /hotels/{id}` - Actualizar un hotel por ID
- `DELETE /hotels/{id}` - Eliminar un hotel por ID

### Clientes (Customer)
- `GET /customers` - Obtener todos los clientes
- `POST /customers` - Crear un nuevo cliente
- `GET /customers/{id}` - Obtener un cliente por ID
- `PUT /customers/{id}` - Actualizar un cliente por ID
- `DELETE /customers/{id}` - Eliminar un cliente por ID

### Vuelos (Fly)
- `GET /flights` - Obtener todos los vuelos
- `POST /flights` - Crear un nuevo vuelo
- `GET /flights/{id}` - Obtener un vuelo por ID
- `PUT /flights/{id}` - Actualizar un vuelo por ID
- `DELETE /flights/{id}` - Eliminar un vuelo por ID

## Instalación

1. Clona el repositorio: `https://github.com/ale94/best-travel-api.git`
2. Navega al directorio del proyecto: `cd best-travel-api`
3. Instala las dependencias: `mvn install`
4. Ejecuta la aplicación: `mvn spring-boot:run`

## Uso

Utiliza herramientas como Postman o cURL para interactuar con los endpoints de la API.

## Contribuciones

¡Las contribuciones son bienvenidas! Por favor, abre un issue o envía un pull request.

## Licencia

Esta API está bajo la licencia MIT.
