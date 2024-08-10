# Assessment final del Career Booster de Metaphorce - Gestor de tareas.

Este es el assessment final del career booster en la que se se implementará un gestor de tareas
para uno o más usuarios (equipos) utilizando tecnologías como java, spring boot, mysql entre
otras que se especificarán más adelante.

---

## Tecnologías y herramientas utilizadas.
- Java 17.0.11
- Maven 3.9.6
- Spring boot 3.3.2
- Spring Security
- Json web token JWT 0.11.5
- Spring Data JPA (para la persistencia de datos)
- Spring Validation (para la validación de los datos)
- MySQL
- Lombok 
- SpringDoc - OpenAPI (para la documentación con Swagger)
- JUnit
- Mockito
- JaCoCo 0.8.12
- Postman

---

## Seguridad y autenticación.
El acceso a los endpoints de la apliación estan protegidos mediante autenticación con JWT. Para poder hacer uso de los servicios 
proporcionados de la aplicación, será necesario hacer su registro del usuario y posteriormente realizar el login de la apliación.

> [!IMPORTANT]
> Al realizar el login de la aplicación se devolverá un token el cual deberá enviarse en cada petición
> del servicio para poder acceder a ellos, exceptuando claro el registro y el login de la aplicación.

###### Registro de nuevos usuarios.

Ruta: **http://localhost:8080/auth/register**
Método http: POST.

Se deberá de enviar en el body de la petición la información siguiente información como se muestra en el ejemplo.

````
{
  "first_name": "juan",
  "last_name": "perez",
  "maternal_surname": "meza",
  "phone_number": "4772352465",
  "email": "example@example.com",
  "password": "secret password"
}
````
*Respuestas*

200 si fue exitosa la solicitud:
````
{
"token": "token secret"
}
````

409 si el teléfono o email ya estan registrados:
````
{
  "message": "Already exists user whit email: example@example.com",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "CONFLICT"
}
````

###### Login de la aplicación.

Ruta: **http://localhost:8080/auth/login**
Método http: POST.

Se deberá de enviar en el body de la petición la información como se muestra en el ejemplo.

````
{
  "email": "example@example.com",
  "password": "secretpassword"
}
````
*Respuestas*

200 si fue exitosa la solicitud:
````
{
"token": "token secret"
}
````

> [!IMPORTANT]
> Este token es el que se deberá enviar en las peticiones a los servicios.

404 Si no se encontro una cuenta con esas credenciales:
````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````


## Funcionalidades

### Usuarios

La aplicación cuenta con un usuario predeterminado con un rol de ADMIN que puede acceder al endpoint que
modifica el status del usuario como se muestra en el punto *Actualizar el status de algún usuario.
Las credenciales del usuario son:
email: "admin@admin.com"
password: "admin1234"

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
  "status": "BLOCKED",
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
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimate_delivery": "2024-01-11",
  "priority": "HIGH",
  "create_date": "2024-01-02",
  "runtime": 0
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
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimate_delivery": "2024-01-11",
  "priority": "HIGH",
  "create_date": "2024-01-02",
  "runtime": 3
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
[
 {
   "id": 12,
   "title": "any title",
   "description": "any description",
   "status": "COMPLETE",
   "estimate_delivery": "2024-01-11",
   "priority": "HIGH",
   "create_date": "2024-01-02",
   "runtime": 3
 }
]
````

404 si no se encontraron tareas,

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
  "id": 12,
  "title": "any title",
  "description": "any description",
  "status": "COMPLETE",
  "estimate_delivery": "2024-01-11",
  "priority": "HIGH",
  "create_date": "2024-01-02",
  "runtime": 3
}
````

404 si no se encontraron tareas,

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

---
###### Obtener todas las tareas relacionadas a un usuario en un proyecto

Ruta: http://localhost:8080/api/v1/tasks/user/idUser/project/idProject
Método http: GET

> [!NOTE]
> No olvides remplazar el "idUser" de la ruta por el id del usuario
> y el "idProject" por el id del proyecto
> a eliminar: http://localhost:8080/api/v1/tasks/user/2/project/3

*Respuestas*
````
[
  {
    "id": 12,
    "title": "any title",
    "description": "any description",
    "status": "COMPLETE",
    "estimate_delivery": "2024-01-11",
    "priority": "HIGH",
    "create_date": "2024-01-02",
    "runtime": 3
  }
]
````

404 si no se encontraron tareas,

````
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NO_CONTENT"
}
````

## Tests

En esta aplicación se realizarón pruebas unitarias para garantizar la calidad
de la aplicación. Estas pruebas se realizarón en los servicios de ProjectServiceImpl, 
TaskServiceImpl y UserServiceImpl, cubriendo varios casos de prueba esenciales. 

Las herramientas utilizadas fuerón:
- JUnit para la ejecución de pruebas unitarias.
- Mockito para la creación de mocks y simulaciones.
- JaCoCo para la generación de reportes de los tests

###### Ejemplo de pruebas
````
@Test
    void whenGetProjectIsSuccessful() {

        Project project = Project.builder().id(1L)
                .title("first title")
                .description("first description")
                .status(Status.IN_PROGRESS)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        ProjectResponse result = underTest.getProject(1L);

        assertEquals(project.getId(), result.id());
        assertEquals(project.getTitle(), result.title());
    }
````

Para poder acceder al reporte:
1. Se deben de ejecutar los tests, en la consla se deberá ejecutar el comando: **mvn clean test**
2. Después ejecutar el siguiente comando: mvn jacoco:report
3. Al ejecutar el comando anterior se generara el reporte en la ruta: target/site/jacoco/index.html el cual prodrás abrir con el navegador y visualizar la cobertura.