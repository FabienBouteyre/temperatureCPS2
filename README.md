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