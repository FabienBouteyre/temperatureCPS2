# temperatureCPS2

Angular : 

aller dans le fichier temperatureAPP modifier bar et taper cmd pour ouvrir console :
```npm install -g npm@latest```
```npm install -g @angular/cli```
```npm install --save-dev @angular-devkit/build-angular```
```npm update```
Si : npm ERR! Unexpected end of JSON input while parsing near '...0","grunt-contrib-wat'
Alors : ```npm cache clean --force```
si erreur  
```ng serve```

Spring : 

File>import>Existing maven project>Link of your folder
Puis finish 
Dans ressources attion modifier le mot de passe de notre bdd
Run as spring boot application 

MySQL: 

Donwload MySQL
```create database tempBDD;```
```use tempBDD; ```
```create table data (id int unsigned not null auto_increment primary key, room varchar(255), value int);```
```insert into data (room, value) values ('J007', 24);```

RabbitMQ:

RabbitMQ is the most widely deployed open source message broker. It supports multiple messaging protocols. This message broker have supported a lot of protocols. We will use MQTT plugin for accepting and transferring data from devices (Arduino MKR WiFI 1010).
Default port is 1883;
For this project we need to install RabbitMQ server by follow this link http://www.rabbitmq.com/download.html.
After installing, go to folder C:\Users\User\AppData\Roaming\RabbitMQ and open file with name rabbitmq.config.
Put this value `[{rabbit, [{loopback_users, []}]}].` and save the file.
After we need to do next command in command line:
            ```rabbitmq-plugins list```
With this command, you can take plugins list supported by RabbitMQ server. we need to enable `rabbitmq_mqtt` with follow command
            ```rabbitmq-plugins enable rabbitmq_mqtt```
Next plugin will give oportunity to use convenient http user interface
            ```rabbitmq-plugins enable rabbitmq_management```
For detailed sitting, you can use the web-interface `http://localhost:15672/`, login: `guest`, pass: `guest`, by default;

IoT `Arduino MKR WiFi 1010`
In this project we are using Arduino MKR WiFi 1010 with Sensor `Temperature and Humidity Sensor DHT22` for to get temperature value of room. 
Code you can found in folder with name `\temperatureCPS2\IoT\Arduino\Arduino MKR WiFi 1010 .ino`;

