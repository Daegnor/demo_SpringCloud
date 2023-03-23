# Demo Spring Cloud

Ceci est un petit projet pour montrer l'utilisation du framework Spring Boot, notamment Spring Boot Cloud pour les micro-services

## Lancer la démo

```
cd demo
mvn install
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

L'API est exécutée sur le port 9090, il utilise une db H2, disponible a l'url localhost:9090/h2-console/ (lorsque la démo est exécutée)  
Spring Boot est compatible avec d'autres DB comme Postgres ou Mongo, il faut ajouter les dépendances dans le `pom.xml` et l'url / les identifiants dans `src/main/resources/applicaton.properties`