# TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN, DOCKER Y AWS
Se construye una aplicación web pequeña usando el micro-framework de Spark java. Una vez tengamos esta aplicación procederemos a construir un container para docker para la aplicación y los desplegaremos y configuraremos en nuestra máquina local. Luego, cerremos un repositorio en DockerHub y subiremos la imagen al repositorio. Finalmente, crearemos una máquina virtual de en AWS, instalaremos Docker , y desplegaremos el contenedor que acabamos de crear.

### Entendimiento 
La aplicación está compuesta de 3 componentes: un balanceador de carga cajo el algoritmo de Round Robin, 3 instancias lógicas web de LogService y una instancia del servicio MongoDB, modularizadas in contenedores de Docker.

### Diseño de la arquitectura de la aplicación 
Diseño General
![image](https://user-images.githubusercontent.com/59893804/136866148-96008fd9-c70a-4201-bb84-b0cf41fb71eb.png)



## Generación de imágenes para el despliegue 

#### **Prerequisitos**
+ Maven, Java
+ Docker instalado en la máquina
+ Repositorio clonado

##### Parte I Crear imagen e instancias

1. **Para crear las imágenes, ejecute el siguiente comando.** 
    ```
    docker build --tag dockerssparkprimer
    ```
    > En este caso ```dockerssparkprimer```
2. **Verifique que se creo la imagen**
    ```
    docker images
    ```
    ![image](https://user-images.githubusercontent.com/59893804/136999433-2d7bf10d-7f63-4a06-afce-a37c9db92ef8.png)
    
    > Puede ingresar a la aplicación de Docker Desktop y revisar que los contenedores, imágenes y/o volúmenes creados, están corriendo.
    
    ![image](https://user-images.githubusercontent.com/59893804/136999511-e6665611-8533-4dd1-9efc-1bef305e35d0.png)
    
3. **Apartir de la imagen creada creamos instancias de un contenedor de docker independiente con el siguiente comando**
    ```
    docker run -d -p 34000:6000 --name firstdockercontainer dockersparkprimer
    ```
    > Podemos crear cuantas se necesiten.
    
4. **Nos aseguramos que el contenedor este corriendo con el comando**
     ```
     docker ps
    ```
    ![image](https://user-images.githubusercontent.com/59893804/136999643-93bdadd2-11e8-4b70-84a5-586796b527bf.png)
    
5. **Por ultimo con el siguiente comando generamos automaticamente una configuración docker habiendo creado en el directorio raiz el archivo ``` docker-compose.yml```**
    ``` 
    docker-compose up -d
    ```
6. **El Docker Desktop tiene este resultado**
![image](https://user-images.githubusercontent.com/59893804/136999007-9e92f8a5-f9a6-4d05-a57e-fc88c94b6fbb.png)

##### Parte II Subir imagenes creadas a Docker Hub 
1. Cree una referencia a su imagen con el nombre del repositorio a donde desea subirla:
    ```
    docker tag dockersparkprimer lina6020/firstwebappspark
    ```
    ![image](https://user-images.githubusercontent.com/59893804/136999822-858c78f2-7235-4098-b933-2a629b19330e.png)

2. Ahora se sube las imágenes  a Docker Hub, usando el siguiente bloque de comandos, por cada imagen. Previamente, debe haber creado una cuenta y un repositorio por c/a imagen.
    
    ```
    docker login  Realizamos la autenticación
    docker push lina6020/firstwebappspark:latest Empujar imagen al repositorio en Docker Hub
    ```


#### Parte III

1. **Para el despliegue en aws, debemos tener instala una máquina virtual EC2. Ejecutamos el siguiente bloque de código para instalar docker y actualizarla.**
    ```sh
    sudo yum update -y
    sudo yum install docker
    ```

2. **Posteriormente, iniciamos el servicio de docker y configuramos nuestro usuario en el grupo de docker, usando los siguientes comandos.**
    ```sh
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    ```

3. **Ahora clonamos el presente repositorio en Github en nuestra máquina virtual en AWS y ejecutamos `docker-compose up -d` para instalar a partir de las imágenes creadas en Dockerhub las instancias de los contenedores docker.**
    ```sh
    sudo curl -L "https://github.com/docker/compose/releases/download/1.26.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose   --Descarga la versión y guarda el ejecutable, que lo hará accesible globalmente por docker-compose
    sudo chmod +x /usr/local/bin/docker-compose     --Establecemos los permisos del ejecutable
    docker-compose --version        --Verficamos la correcta instalación

    ```
    
4. **Finalmente, ejecutamos el siguiente comando, para tener las imágenes en la máquina virtual. Debemos previamente haber abierto los puertos de entrada del security group de la máxima virtual para acceder al servicio. En este caso, el puerto 42000, en la plataforma de AWS.**
    ```sh
    sudo cp docker-compose.yml /  --Crear la copia que ejecutaremos en la raiz de la máquina virtual
    docker-compose up -d    --Generar automáticamente la configuración docker
    ```
    > Ahora podremos acceder a los servicios a través de aws en el navegador con el **DNS público** de nuestra máquina virtual y el puerto **42000**. Por ejemplo, http://ec2-52-91-24-157.compute-1.amazonaws.com:42000
    
![image](https://user-images.githubusercontent.com/59893804/137001948-b6bdc15a-105f-4ae9-9ac9-5b8dda64d370.png)




## Herramientas utilizadas

| Nombre | Uso |
| ------ | ------ |
| **Maven**  | Gestión y construcción del proyecto |
| **Eclipse IDE**  | Plataforma de desarrollo |
| **Git**  | Sistema de control de versiones |
| **Github** | Respositorio del código fuente |
| **Docker & DockerHub** | Contrucción de los contenedores |
| **Amazon Web Services** | Plataforma de producción en la nube |

## Autor 
Lina Maria Buitrago Espindola 
