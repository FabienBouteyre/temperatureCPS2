# temperatureCPS2



## Installation

Clone the repository.


### Angular App : 

#### Install Angular
Go to temperatureAPP folder using console and enter the following commands:
```npm install -g npm@latest```
```npm install -g @angular/cli```
```npm install --save-dev @angular-devkit/build-angular```
```npm update```

If you face the error ```npm ERR! Unexpected end of JSON input while parsing near '...0","grunt-contrib-wat'```
Then enter the command : ```npm cache clean --force```


### MySQL server: 

#### Create the database
Create the database entering these following commands:
```create database tempBDD;```
```use tempBDD; ```

#### Import the database
Import ```tempbdd.sql``` you can download here: ```https://drive.google.com/open?id=1UsklC0ZOlvnHnz-ILr-BgYahumjnKmmD```


### RabbitMQ:

#### Installation
RabbitMQ is the most widely deployed open source message broker. It supports multiple messaging protocols. This message broker have supported a lot of protocols. We will use MQTT plugin for accepting and transferring data from devices (Arduino MKR WiFI 1010).
Default port is ```1883```;
For this project we need to install RabbitMQ server by follow this link http://www.rabbitmq.com/download.html.

After the installation, go to folder C:\Users\User\AppData\Roaming\RabbitMQ and edit or create a file named ```rabbitmq.config``` and put this value ```[{rabbit, [{loopback_users, []}]}].``` and save the file.

Enter the next command in the console (add to your PATH the folder ```RabbitMQ\rabbitmq_server-3.7.9\sbin``` if you are on Windows): ```rabbitmq-plugins list```

With this command, you can take plugins list supported by RabbitMQ server. You need to enable rabbitmq_mqtt with follow command: ```rabbitmq-plugins enable rabbitmq_mqtt```

Next plugin will give oportunity to use convenient http user interface: ```rabbitmq-plugins enable rabbitmq_management```

Start the Rabbitmq service using the following command: ```rabbitmq-server start```

Go on the web-interface ```http://localhost:15672/```. The credentials are: login: ```guest```, password: `guest`, by default.

#### Create a queue
Go to the ```Queue``` tab and Add a new queue named ```queue1```
#### Create a binding
Go to the ```Èxchanges``` tab.
Click on ```amq.topic```.
Open the ```Bindings``` dropdown. Enter ```queue1``` in the 'To queue' input and ```#``` in 'Routing key' input then Bind.


### IoT `Arduino MKR WiFi 1010`:
In this project we are using Arduino MKR WiFi 1010 with different sensors to get the information of the room.

Code you can find the Arduino code in the following folder: ```temperatureCPS2\IoT\Arduino\Arduino MKR WiFi 1010 .ino```


### Spring Boot App : 
Import the maven project : (with STS IDE) File>import>Existing maven project>Link of your folder

To connect the app to the database, change the user and password in the ```application.properties``` file.

Run as Spring Boot Application. 


## Start

### Start the Angular app
Enter ```ng serve``` to start the server available on ```localhost:4200```

### Start the MySQL server
Start the service on Windows using the Services windows or by command line ```mysql-server start```

### Start the RabbitMQ service
Start the Rabbitmq service using the following command: ```rabbitmq-server start```

### Start the Spring Boot App
Use your IDE to lauch the Spring Boot App.



## Usage

### Login
Be sure to use the proper credentials to login (check the database, table user)

### API
Get the documentation about the API going on ```localhost:8080/swagger-ui.html#/```
