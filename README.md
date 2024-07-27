# Assessment final del Career Booster de Metaphorce - Gestor de tareas.

Este es el assessment final de la carrera en la que se se implementará un gestor de tareas
para uno o más usuarios (equipos) utilizando tecnologías como java, spring boot, mysql entre
otras que se especificarán más adelante.

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

* Obetener información de un usuario en especifico, se deberá enviar
ruta el id del usuario que se requiera solicitar su información.

Ruta: **http://localhost:8080/api/user/id**
Metodo http: GET.

> [!NOTE]
> No olvides remplazar el "id" de la ruta por el id del usuario
> a inspeccionar: http://localhost:8080/api/v1/user/1

````
Respuesta 200:
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

````
Respuesta 404 si no se encontro al usuario:
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

* Actualizar la información general de algun usuario

Ruta: **http://localhost:8080/api/user**
Metodo http: PUT.

> [!NOTE]
> Se deberá enviar en el body de la petición lo siguiente:
> {
>   "id": 1,
>   "first_name": "jh",
>   "last_name": "jhbvg",
>   "maternal_surname": "Apellido materno (opcional)",
>   "phone_number": "47712345432"
> }

````
Respuesta 200:
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

````
Respuesta 404 si no se encontro al usuario:
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````

* Actualizar el status de algún usuario.

Ruta: **http://localhost:8080/api/user/status**
Metodo http: PUT.

> [!NOTE]
> Se deberá enviar en el body de la petición lo siguiente:
> { 
>   "id": 1,
>   "status": "BLOCKED"
> }

````
Respuesta 200:
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

````
Respuesta 404 si no se encontro al usuario:
{
  "message": "User not found",
  "timestamp": "2024-07-26T23:48:13.0317694-06:00",
  "status": "NOT_FOUND"
}
````