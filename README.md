# test_java_gradle_unit
Rest API done in java, gradle and with test unit

Tecnologias utilizadas:

SpringBoot 2.2.1.RELEASE.
Gradle.

Descripcion API BackEnd:

Al momento de desarrollar se tenian las siguientes versiones:
Java 1.8
SpringBoot 2.2.1.RELEASE

Ruta base /users
Puerto: 8080

GET:
/{status}; regresa todos los usuarios con su informacion, los tipos de estatus son actives y inactives.
Algunos codigos que retorna: BAD_REQUEST (codigo 400), INTERNAL_SERVER_ERROR (codigo 500) y OK (codigo 200).

POST:
/; guarda un usuario en la DB, retorna un Json con los datos del usuario.
Se realiaron validacion las cuales son retornadas en el Json de la respuesta de cada endpoint con el tag "mensaje", de haber algun error retornara: BAD_REQUEST (codigo 400) o CREATED (codigo 201).

PUT:
/activate/{id}; verifica que exista un usuario desactivado con ese id y lo activa.
Algunos codigos que retorna:  NOT_FOUND (codigo 404) o OK (codigo 200).

/desactivate/{id}; verifica que exista un usuario activado con ese id y lo desactiva.
Algunos codigos que retorna:  NOT_FOUND (codigo 404) o OK (codigo 200).

Se implementaron test para los endpoint GET(/{status}), POST(/) y PUT(/activate/{id}, /desactivate/{id}), manejan su propia DB(db_test).
Se manejo la DB H2, la cual es una base de datos en memoria, la cual se pobla al iniciar la aplicacion con el script en la carpeta resource.
