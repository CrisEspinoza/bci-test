Hol@, dejo a continuación el desarrollo de mi prueba técnica:

En una primera instancia, debemos tener en cuenta lo siguiente: 

* Se utilizo una base de datos de PosgreSQL con la finalidad de dejar 
un despliegue de la aplicación de manera rapida con docker y docker-compose.
* Por otro lado, se contemplo todo los requerimientos solicitamos y dos de los extras. (JWT y Swagger)
* Además, se dejo dentro del proyecto un archivo de postman en donde se pueden
probar de manera rapida al igual que en swagger los endpoint realizados.

Descripción de ejecución: 

* Para poder ejecutar la aplicación de manera rapida, puede seguir los alguna de las siguientes opciones:
1. MAVEN con H2:
    1. Paso numero 0, es revisar el archivo: application.properties, debe estar de la siguiente manera:
       ![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/h2.png)
    2. Ejecutar dentro de la carpeta principal el siguiente comando:
        1. mvn clean install spring-boot:run
    3. Ya puede empezar a ocupar la aplicación dentro de los siguientes link:
        1. Swagger: http://localhost/swagger-ui.html#/user-controller
        2. API Base:  http://localhost:80/

2. Docker con Postgresql:
   1. Paso numero 0, es revisar el archivo: application.properties, debe estar de la siguiente manera:
        ![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/docker.png)
   2. Primero se posiciona dentro del proyecto en la carpeta raiz
   3. Luego le da permisos al archivo de Docker para poder ejecutar un .sh, con el siguiente 
     comando: sudo chmod 777 Docker.sh 
   4. Luego, de esto puede ejecutar el archivo el cual se va a encargar de levantar la aplicacion completa
     que va a quedar corriendo en el puerto :80. Se debe tener en cuenta que se esta desplegando la aplicacion mediante 
     un docker-compose, el cual engloba a la aplicación dentro de una red privada. El comando para 
     ejecutar es: sudo ./Docker.sh
   5. A continuación, la aplicación ya va a esta a disposición, sin embargo, en caso que necesite validar 
     que todo esta bien puede revisar los contenedores de docker con los siguientes comandos:
     docker ps -a y entrando a la imagen llamada "backend-bci" con el siguiente comando para poder ver la consola
     de ejecución:  docker logs -f {container id}
   6. La aplicación queda disponible en en los siguientes link 
      1. URL: http://localhost:80/
      2. Swagger: http://localhost/swagger-ui.html#/user-controller
      3. Por otr lado se dejo cargado un archivo de postman en la aplicación. Se debe tener en consideración que donde esta con JWT la autenticación tiene que tener en cuenta este paso
     para cuando requiera probar la aplicación. 
      
Descripción de la solución: 

A continuacion, se deja una pequeña explicación de las decisiones tomados dentro del proyecto apra abarcar cada uno de los requerimiento: 

1. En una primera instancia, tenemos que decir que la estructura del proyecto se basa en lo siguiente:
   1. Controller , Servicios, Modelo, DTO, DAO (Repositorio), en donde cada uno de estos tiene una responsabilidad
   particular dentro del ciclo de ejecución de cada uno de las peticiones. 
   2. Por otro lado, se dejo una carpeta de configuración que se encarga de tener la configuración del proyecto
   en donde podemos encontrar la implementación de JWT, Swagger y una clase global de control de dependencias, que nos permite
    poder tener un mayor control de ellos y además, nos da la posibilidad de poder tener optar a crear nuetras propias
   clases de excepciones para manejar dentro del codigo. (Se dejo una creada a modo de ejemplo en la carpeta de 
   excepciones, en donde se utiliza para un par de casos dentro de los servicios del usuario)
   3. Por ultimo, se dejo una clase Utils, en donde se dejan funciones que permiten facilitar la tarea de diversos metodos
   dentor del codigo y son reutilizables en varios oportunidad.
   
2. Se tiene que tener en cuenta que las validaciones solicitadas para la contraseña y para el email
fueron abarcadad con lso decorados de @valid y @pattern, los cuales nos permiten poder tener esto controlado
y además poder mcapturar de manera global las excepciones que entrega, mediante el controlar global de excepciones
que fue prograamado. 

3. Importante destacar que el token se dejo vigente por 30 minutos y se persistio en la DB junto con el usuario, por otro lado, 
tenemos que igual se utilizo un UUID como id para poder tenerlos dentro de las tablas de la DB.

4. Dentro de las librerias utilizadas que son importante destacar son:
   1. Lombok : Lombok es una biblioteca que permite autocompletar codigo fuente. Para utilzarlo es altamente recomendado instalar el plugin de lombok en el IDE a utilizar (por ejemplo IntelliJ o Eclipse). En google pueden encontrar como instalar el plugin para Eclipse, en el caso de IntelliJ es cosa de buscarlo en los el módulo de plugins de los settings de nuestro IDE. Con Lombok podemos autogenerar lo siguiente:
      1. métodos "getter" para atributos que no sean constantes mediante la anotación @Getter 
      2. métodos "setter" para atributos que no sean constantes mediante la anotación @Setter. 
      3. constructores con argumentos válidos para todos los atributos de la clase que no sean constantes mediante la anotación @AllArgsConstructor. 
      4. constructores sin argumentos (constructores vacíos) mediante la anotación @NoArgsConstructor. 
      5. métodos "toString" que permiten generar String con la representación del estado actual del objeto de la clase, mediante la anotación @ToString. 
      6. métodos "equals" y "hashCode" que permiten evaluar y comparar objetos distintos pertenecientes a la misma clase, mediante la anotación @EqualsAndHashCode 
      7. atributo constante de un objeto que permite registrar en el log de nuestra aplicación, mediante las anotaciones @Log y @Slf4j (se recomienda usar @Slf4j)
   2. Jasypt es un artefacto java que permite codificar datos ubicados en el archivo application.properties, esto sirve para ocultar datos sensibles tales como contraseñas o url de conexiones (por ejemplo de conexion a base de datos). 
   Para utilizar esta caracteristica basta con codificar el texto que deseamos ocultar, y colocar el valor codificado en la variable correspondiente dentro de application.properties dentro de ENC(). Por ejemplo:
      1. spring.datasource.username=ENC(PjeMN2dJBd5BryuzypduyA==)
         1. En este caso, el string WdGvUvdCI6cZRR4umOfxUA== corresponde al texto usuario cifrado usando como clave de cifrado el texto bci.
      2. Para poder cifrar/descifrar texto para Jasypt, se puede realizar de forma online en https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
   
PD: Considerar que la estrucutra adquirida en los controladores de Phone y User, son distintas, estas se hicieron a proposito 
con la finalidad de poder mostrar dos formas de poder abordar y estructurar los controladores.

Por ultimo, se deja a continuación una fotografia de la base de datos utilizada para poder solventar los 
requerimientos planteados: 

![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/db.png)

Referencias:

Diagrama de API:
![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/api.png)

Swagger:
![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/swagger.png)

Postman:
![</span><span>BCI Api](https://github.com/CrisEspinoza/bci-test/blob/main/images/postman.png)


*Importante*: La aplicación quedo andando en los siguiente link para que puedan ser probados de manera remota: 

1. Swagger: http://ec2-3-133-145-170.us-east-2.compute.amazonaws.com/swagger-ui.html#/user-controller
2. Url Base: http://ec2-3-133-145-170.us-east-2.compute.amazonaws.com

Sin mas que agregar, se despide Cristian Espinoza. 
