# Assessment final del Career Booster de Metaphorce - Gestor de tareas.

Este es el assessment final de la carrera en la que se se implementará un gestor de tareas
para uno o más usuarios (equipos) utilizando tecnologías como java, spring boot, mysql entre
otras que se especificarán más adelante.

---

## Tecnologías y herramientas utilizadas.
- Java 17.0.11
- Maven 3.9.6
- Spring boot 3.3.2
- Spring Data JPA (para la persistencia de datos)
- Spring Validation (para la validación de los datos)
- MySQL
- Lombok 
- SpringDoc - OpenAPI (para la documentación con Swagger)
- JUnit
- Mockito
- Postman

---

## Funcionalidades


### Usuarios

###### Obetener información de un usuario en específico, se deberá enviar en la ruta el id del usuario que se requiera solicitar su información.

Ruta: **http://localhost:8080/api/v1/user/id**
Método http: GET.

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id del usuario
> a inspeccionar: http://localhost:8080/api/v1/user/1

*Respuestas*

200 si fue exitosa la solicitud:
````
{
  "id": 21,
  "first_name": "Juan",
  "last_name": "Ramírez",
  "maternal_surname": "Mata",
  "phone_number": "2344221243",
  "status": "ACTIVE",
  "email": "juan@example.com"
}
````

404 si no se encontro al usuario:
````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

###### Actualizar la información general de algún usuario

Ruta: **http://localhost:8080/api/v1/user**
Metodo http: PUT.

Se deberá enviar en el body de la petición lo siguiente:
````
{
  "id": 1,
  "first_name": "jh",
  "last_name": "jhbvg",
  "maternal_surname": "Apellido materno (opcional)",
  "phone_number": "47712345432"
}
````
*Respuestas*

200 si fue exitosa la solicitud:
````
{
  "id": 21,
  "first_name": "Juan",
  "last_name": "Ramírez",
  "maternal_surname": "Mata",
  "phone_number": "2344221243",
  "status": "ACTIVE",
  "email": "juan@example.com"
}
````

404 si no se encontro al usuario:
````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

###### Actualizar el status de algún usuario.

Ruta: **http://localhost:8080/api/v1/user/status**
Metodo http: PUT.

Se deberá enviar en el body de la petición lo siguiente:

````
{ 
  "id": 1,
  "status": "BLOCKED"
}
````

*Respuesta*

200 si fue exitosa la solicitud:
````
{
  "id": 21,
  "first_name": "Juan",
  "last_name": "Ramírez",
  "maternal_surname": "Mata",
  "phone_number": "2344221243",
  "status": "ACTIVE",
  "email": "juan@example.com"
}
````

404 si no se encontro al usuario:
````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

### Proyectos

###### Crear un nuevo proyecto.

Ruta: **http://localhost:8080/api/v1/projects**
Método http: POST

Se deberá enviar en el body de la petición como se muestra en el siguiente ejemplo:
````
{
  "title": "Any title",
  "description": "Any description",
  "leader": 12,
  "estimate_completion": "2007-12-03"
}
````

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimated_completion": "2025-12-03"
}
Además te devolvera la ruta para consultar el recurso creado en el header: http://localhost:8080/api/v1/projects/1 
````

404 si no se encontro al usuario,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

409 si el usuario no esta activo
````
{
  "message": "The user is blocked or deleted",
  "timestamp": "2024-07-27T15:16:03.434046-06:00",
  "status": "CONFLICT"
}
````

###### Obtener información de un proyecto en específico

Ruta: http://localhost:8080/api/v1/projects/id
Método http: GET

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id del proyecto
> a inspeccionar: http://localhost:8080/api/v1/projects/1

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimated_completion": "2025-12-03"
}
````

404 si no se encontraron proyectos,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````

###### Obtener todos los proyectos relacionados a un usuario
Ruta: http://localhost:8080/api/v1/projects/all/id
Método http: GET

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id de usuario
> a inspeccionar: http://localhost:8080/api/v1/projects/all/1

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimated_completion": "2025-12-03"
}
````

404 si no se encontraron proyectos,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````

###### Cambiar el status del proyecto
  Ruta: http://localhost:8080/api/v1/projects
  Método http: PUT

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimated_completion": "2025-12-03"
}
````

404 si no se encontraron proyectos,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

###### Eliminar un proyecto y todas sus tareas asociadas a el.

Ruta: http://localhost:8080/api/v1/projects/id
Método http: DELETE

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id del proyecto
> a eliminar: http://localhost:8080/api/v1/projects/1

*Respuestas*

404 sin contenido.

###### Obtener un reporte del progreso del proyecto (status de las tareas)
Ruta: http://localhost:8080/api/v1/projects/report/id
Método http: GET

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id de usuario
> a inspeccionar: http://localhost:8080/api/v1/projects/report/1

*Respuestas*

200 si fue exitosa la solicitud:

> [!NOTE]
> La lista del reporte puede estar vacía si no se han asignado tareas.
````
{
  "project": {
    "id": 12,
    "title": "any title",
    "description": "any description",
    "status": "COMPLETE",
    "estimated_completion": "2025-12-03"
  },
  "report": [
    {
      "first_name": "Juan",
      "last_name": "Ramirez",
      "email": "juan@example.com",
      "phone_number": "4332121544",
      "assigned": 10,
      "pending": 3,
      "in_progress": 2,
      "complete": 5
    }
  ]
}
````

404 si no se encontro el proyecto solicitado para el reporte.

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````

### Tareas

###### Crear una nueva tarea.

Ruta: **http://localhost:8080/api/v1/tasks**
Método http: POST

Se deberá enviar en el body de la petición la siguiente información:
````
{
  "title": "any title",
  "description": "any description",
  "email": "jorge@example.com",
  "project": 1,
  "estimate_delivery": "2024-01-11",
  "priority": "MEDIUM"
}
````

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 1,
  "title": "any title",
  "description": "any description",
  "status": "PENDING",
  "project": {
    "id": 1,
    "title": "Any title",
    "description": "Any description",
    "status": "PENDING",
    "estimated_completion": "2007-12-03"
  },
  "estimate_delivery": "2024-01-11",
  "priority": "MEDIUM"
}
Además te devolvera la ruta para consultar el recurso creado en el header: http://localhost:8080/api/v1/projects/1 
````

404 si no se encontro al usuario o no se encontro el proyecto asignado,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

###### Obtener una tarea en específico.

Ruta: **http://localhost:8080/api/v1/tasks/id**
Método http: GET

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id de la tarea
> a inspeccionar: http://localhost:8080/api/v1/tasks/1

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 1,
  "title": "any title",
  "description": "any description",
  "status": "PENDING",
  "project": {
    "id": 1,
    "title": "Any title",
    "description": "Any description",
    "status": "PENDING",
    "estimated_completion": "2007-12-03"
  },
  "estimate_delivery": "2024-01-11",
  "priority": "MEDIUM"
}
Además te devolvera la ruta para consultar el recurso creado en el header: http://localhost:8080/api/v1/projects/1 
````

404 si no se encontró la tarea,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````
###### Obtener todas las tareas relacionadas a un usuario
Ruta: http://localhost:8080/api/v1/tasks/all/id
Método http: GET

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id del usuario
> a inspeccionar: http://localhost:8080/api/v1/tasks/all/1

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 1,
  "title": "any title",
  "description": "any description",
  "status": "PENDING",
  "project": {
    "id": 1,
    "title": "Any title",
    "description": "Any description",
    "status": "PENDING",
    "estimated_completion": "2007-12-03"
  },
  "estimate_delivery": "2024-01-11",
  "priority": "MEDIUM"
}
````

404 si no se encontraron proyectos,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````

###### Cambiar el status de la tarea
Ruta: http://localhost:8080/api/v1/tasks
Método http: PUT

*Respuestas*

200 si fue exitosa la solicitud:

````
{
  "id": 1,
  "title": "any title",
  "description": "any description",
  "status": "PENDING",
  "project": {
    "id": 1,
    "title": "Any title",
    "description": "Any description",
    "status": "PENDING",
    "estimated_completion": "2007-12-03"
  },
  "estimate_delivery": "2024-01-11",
  "priority": "MEDIUM"
}
````

404 si no se encontraron proyectos,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

###### Eliminar una tarea

Ruta: http://localhost:8080/api/v1/tasks/id
Método http: DELETE

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id de la tarea
> a eliminar: http://localhost:8080/api/v1/tasks/1

*Respuestas*

404 sin contenido.
