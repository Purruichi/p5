# P5
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método   | Ruta                  | Descripción                                        | Respuestas principales                                |
|----------|-----------------------|----------------------------------------------------|--------------------------------------------------------|
| POST     | /api/users            | Registra un nuevo usuario                          | 201 Created, 400 Bad Request, 409 Conflict             |
| POST     | /api/users/me/session | Inicia sesión y devuelve cookie de sesión          | 201 Created, 401 Unauthorized                          |
| DELETE   | /api/users/me/session | Cierra la sesión (elimina la cookie de sesión)     | 204 No Content, 401 Unauthorized                       |
| GET      | /api/users/me         | Obtiene el perfil del usuario autenticado          | 200 OK, 401 Unauthorized                               |
| PUT      | /api/users/me         | Actualiza el perfil del usuario autenticado        | 200 OK, 400 Bad Request, 401 Unauthorized, 409 Conflict|
| DELETE   | /api/users/me         | Elimina el usuario autenticado y sus tokens        | 204 No Content, 401 Unauthorized                       |


## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
