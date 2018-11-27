# simple-wiki

## flyway

```
mvn flyway:baseline -Dflyway.baselineVersion=0.0.1 -Dflyway.user=wiki -Dflyway.password=hoge00 -Dflyway.url=jdbc:mysql://localhost:3306/simple_wiki?serverTimezone=JST -Dflyway.driver=com.mysql.cj.jdbc.Driver
```

```
mvn flyway:clean -Dflyway.user=wiki -Dflyway.password=hoge00 -Dflyway.url=jdbc:mysql://localhost:3306/simple_wiki?serverTimezone=JST -Dflyway.driver=com.mysql.cj.jdbc.Driver -Dflyway.placeholderReplacement=false
```

```
mvn flyway:migrate -Dflyway.user=wiki -Dflyway.password=hoge00 -Dflyway.url=jdbc:mysql://localhost:3306/simple_wiki?serverTimezone=JST -Dflyway.driver=com.mysql.cj.jdbc.Driver -Dflyway.placeholderReplacement=false
```