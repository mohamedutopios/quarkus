### Gestion des Formats de Données avec JSON-B et JSON-P dans Quarkus

Quarkus fournit un support intégré pour travailler avec des données JSON via JSON-B (JSON Binding) et JSON-P (JSON Processing). Ces API permettent respectivement de sérialiser/désérialiser des objets Java en JSON et de manipuler des données JSON de manière flexible.

#### **1. JSON-B (JSON Binding)**

JSON-B est une spécification qui permet de convertir automatiquement des objets Java en JSON et inversement, de manière similaire à JAXB pour XML.

##### **Utilisation de JSON-B avec Quarkus :**

Quarkus intègre JSON-B par défaut lorsque vous utilisez JAX-RS pour créer des services RESTful. Voici un exemple d'utilisation de JSON-B dans une application Quarkus.

1. **Création d'une Entité Java :**

```java
package org.acme;

public class Person {

    private String name;
    private int age;

    // Constructeurs, getters, setters

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

2. **Exposition d'un Service REST qui Utilise JSON-B :**

```java
package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/person")
public class PersonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson() {
        return new Person("John Doe", 30);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person createPerson(Person person) {
        // Logique pour traiter l'objet reçu
        return person;
    }
}
```

- **Sérialisation :** La méthode `getPerson` renvoie un objet `Person`, qui est automatiquement converti en JSON par JSON-B.
- **Désérialisation :** La méthode `createPerson` accepte un objet JSON et le désérialise en un objet `Person`.

3. **Personnalisation de JSON-B :**

Vous pouvez personnaliser la sérialisation/désérialisation avec des annotations JSON-B, par exemple :

```java
package org.acme;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Person {

    @JsonbProperty("full_name")
    private String name;
    
    @JsonbTransient
    private int age;

    // Rest of the class
}
```

- **`@JsonbProperty("full_name")` :** Renomme le champ `name` en `full_name` dans le JSON.
- **`@JsonbTransient` :** Exclut le champ `age` de la sérialisation JSON.

#### **2. JSON-P (JSON Processing)**

JSON-P est une API Java pour traiter les objets JSON de manière programmatique. Elle permet de créer, modifier et interroger des objets JSON.

##### **Utilisation de JSON-P avec Quarkus :**

1. **Création et Manipulation d'un Objet JSON :**

```java
package org.acme;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/json")
public class JsonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "John Doe")
                .add("age", 30)
                .build();
        
        return jsonObject;
    }
}
```

- **`Json.createObjectBuilder()` :** Crée un constructeur d'objets JSON.
- **`add("key", "value")` :** Ajoute des paires clé-valeur à l'objet JSON.
- **`build()` :** Construit l'objet JSON final.

2. **Lecture d'un Objet JSON :**

Vous pouvez lire et traiter des données JSON provenant d'une chaîne de caractères, d'un fichier, ou d'un flux d'entrée.

```java
package org.acme;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonParser {

    public void parseJsonString() {
        String jsonString = "{\"name\":\"Jane Doe\",\"age\":25}";
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");

        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}
```

- **`Json.createReader()` :** Crée un lecteur JSON à partir d'un flux d'entrée ou d'une chaîne.
- **`readObject()` :** Lit et retourne l'objet JSON.

3. **Manipulation Avancée avec JSON-P :**

JSON-P permet également des manipulations plus complexes comme la modification des structures JSON existantes.

```java
package org.acme;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class AdvancedJsonProcessing {

    public void modifyJsonObject() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "John Doe")
                .add("age", 30)
                .add("skills", Json.createArrayBuilder()
                        .add("Java")
                        .add("Quarkus")
                )
                .build();

        JsonObject modifiedObject = Json.createObjectBuilder(jsonObject)
                .remove("age")
                .add("location", "New York")
                .build();

        System.out.println("Original: " + jsonObject);
        System.out.println("Modified: " + modifiedObject);
    }
}
```

- **`Json.createArrayBuilder()` :** Permet de construire des tableaux JSON.
- **`Json.createObjectBuilder(existingJsonObject)` :** Permet de créer une copie modifiable d'un objet JSON existant.
- **`remove("key")` :** Supprime une clé de l'objet JSON.
- **`add("key", value)` :** Ajoute ou modifie une clé dans l'objet JSON.

### **Conclusion**

Quarkus, avec JSON-B et JSON-P, offre une gestion complète et flexible des données JSON dans les applications Java. JSON-B facilite la sérialisation et la désérialisation des objets Java, tandis que JSON-P permet une manipulation fine et programmée des structures JSON. Ces outils sont essentiels pour développer des services RESTful efficaces et modulaires.