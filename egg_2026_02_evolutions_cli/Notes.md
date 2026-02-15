
### sbt

```
sbt "runMain com.myapp.ApplyEvolutions"
```

### main class

```
package com.myapp;

import play.api.db.evolutions.Evolutions;
import play.api.db.evolutions.EnvironmentEvolutionsReader;
import play.api.db.evolutions.DatabaseEvolutions;
import play.api.Environment;
import play.api.Mode;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ApplyEvolutions {
    public static void main(String[] args) throws Exception {
        // Minimal Play Environment (just for reading conf/evolutions/ files)
        Environment env = Environment.simple();

        // Read evolution scripts from conf/evolutions/default/
        EnvironmentEvolutionsReader reader = new EnvironmentEvolutionsReader(env);

        // Connect to your DB directly
        String url = "jdbc:postgresql://localhost/mydb";
        String user = "myuser";
        String pass = "mypass";

        // Use play.api.db.evolutions classes to apply
        // The exact API depends on your Play version (2.6+, 2.8, 3.x)
        Database db = /* your database reference */;
        DatabaseEvolutions dbEvolutions = new DatabaseEvolutions(db);
        dbEvolutions.evolve(
            dbEvolutions.scripts(reader),
            true  // autocommit
        );

        System.out.println("Evolutions applied successfully.");
    }
}
```

### Database option A

```
import play.api.db.Databases;

// Scala API, but callable from Java:
play.api.db.Database db = Databases.withDatabase(
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/mydb",
    scala.collection.immutable.Map$.MODULE$.empty()
);
```

### Database option B

```
val app = new GuiceApplicationBuilder().in(Mode.Test).build()         
```
