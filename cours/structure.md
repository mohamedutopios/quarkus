La structure d'un projet Quarkus suit une organisation classique des projets Java basés sur Maven, mais avec quelques spécificités propres à Quarkus. Voici un aperçu de la structure de base d'un projet Quarkus :

### Structure de base d'un projet Quarkus

```
quarkus-getting-started/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── acme/
│   │   │           ├── GreetingResource.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── META-INF/
│   │   │   │   └── resources/
│   │   │   │       └── index.html
│   ├── test/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── acme/
│   │   │           ├── GreetingResourceTest.java
│   │   │           ├── NativeGreetingResourceIT.java
├── target/
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

### Détails de la structure

#### 1. `src/`

- **`main/`** : Contient le code source de l'application.
  - **`java/`** : C'est ici que réside le code Java principal de l'application.
    - **`org/acme/`** : Représente le package Java de base. Ce dossier suit la convention de nommage des packages Java en fonction du domaine de l'organisation. Dans cet exemple, `org.acme` est utilisé, mais il peut être remplacé par le nom de domaine réel de votre organisation.
      - **`GreetingResource.java`** : Un exemple de classe qui définit un service REST en utilisant JAX-RS. Les ressources REST sont généralement placées ici.
      
  - **`resources/`** : Contient les fichiers de configuration et les ressources statiques.
    - **`application.properties`** : Le fichier de configuration principal pour Quarkus. C'est ici que vous définissez les paramètres de configuration tels que les ports, les chemins de base, les configurations de la base de données, etc.
    - **`META-INF/resources/`** : Dossier pour les ressources statiques accessibles via le web. Par exemple, `index.html` sera accessible à l'adresse racine de votre application (ex: `http://localhost:8080/`).

- **`test/`** : Contient les tests unitaires et d'intégration.
  - **`java/`** : Similaire au dossier `main/java`, mais pour les tests.
    - **`GreetingResourceTest.java`** : Test unitaire pour la ressource `GreetingResource`.
    - **`NativeGreetingResourceIT.java`** : Test d'intégration pour les tests en mode natif. Ce fichier est utilisé pour tester l'application lorsqu'elle est compilée en binaire natif via GraalVM.

#### 2. Fichiers et dossiers à la racine

- **`target/`** : Dossier généré par Maven qui contient les artefacts compilés de l'application, y compris les fichiers `.class` et le fichier `.jar` final.
- **`.mvn/`, `mvnw`, `mvnw.cmd`** : Fichiers Maven Wrapper, qui permettent d'exécuter Maven sans nécessiter une installation préalable de Maven sur la machine de développement.
- **`pom.xml`** : Fichier de configuration Maven. Il contient les dépendances, les plugins, et les configurations spécifiques au projet Quarkus. C'est un élément clé du projet qui gère les dépendances et les tâches de build.
- **`README.md`** : Fichier de documentation qui peut être utilisé pour décrire le projet, ses objectifs, et fournir des instructions de configuration ou d'utilisation.

### Exemple de Contenu de `GreetingResource.java`

```java
package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, Quarkus!";
    }
}
```

### Exemple de Contenu de `application.properties`

```properties
quarkus.http.port=8080
quarkus.application.name=quarkus-getting-started
```

### Fonctionnement Global

- **Ressources REST :** Les ressources REST comme `GreetingResource` sont définies avec des annotations JAX-RS (`@Path`, `@GET`, etc.) et répondent aux requêtes HTTP.
- **Configuration :** Le fichier `application.properties` est utilisé pour configurer divers aspects de l'application, comme les paramètres de serveur, les profils de développement, et les intégrations avec des bases de données ou des services tiers.
- **Tests :** Les tests unitaires et d'intégration vérifient que les composants de l'application fonctionnent comme prévu.

Cette structure modulaire et bien organisée permet de gérer efficacement le développement, les tests et le déploiement d'applications avec Quarkus.