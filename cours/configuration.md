### Configuration de base dans Quarkus

La configuration dans Quarkus est principalement gérée via le fichier `application.properties`, qui se trouve dans le dossier `src/main/resources`. Ce fichier permet de définir les propriétés globales de l'application ainsi que des configurations spécifiques pour différents environnements (profils).

#### **1. Fichier `application.properties`**

Le fichier `application.properties` est utilisé pour configurer divers aspects de l'application, y compris les paramètres du serveur HTTP, les bases de données, la sécurité, et les configurations spécifiques aux extensions Quarkus.

##### **Exemples de configurations courantes :**

- **Configuration du port HTTP :**
  ```properties
  quarkus.http.port=8080
  ```

- **Nom de l'application :**
  ```properties
  quarkus.application.name=quarkus-getting-started
  ```

- **Activation des logs SQL (pour Hibernate/JPA) :**
  ```properties
  quarkus.hibernate-orm.log.sql=true
  ```

- **Configuration de la datasource (JDBC) :**
  ```properties
  quarkus.datasource.db-kind=postgresql
  quarkus.datasource.username=postgres
  quarkus.datasource.password=secret
  quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/mydb
  ```

- **Activation de l'interface graphique OpenAPI (Swagger UI) :**
  ```properties
  quarkus.swagger-ui.always-include=true
  ```

#### **2. Profils de Configuration**

Quarkus permet de définir des configurations spécifiques à des environnements via l'utilisation de profils. Les profils sont particulièrement utiles pour adapter la configuration en fonction de l'environnement de déploiement (développement, test, production).

##### **Définition des profils :**

- **Profil par défaut :** Les configurations définies sans spécifier de profil s'appliquent à tous les environnements. Par exemple :
  ```properties
  quarkus.http.port=8080
  ```

- **Profil spécifique :** Pour des configurations spécifiques à un profil, utilisez le format suivant : `quarkus.profile-nompropriete`. Par exemple :
  - **Profil de développement (`dev`) :**
    ```properties
    %dev.quarkus.http.port=8081
    %dev.quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/devdb
    ```
  - **Profil de production (`prod`) :**
    ```properties
    %prod.quarkus.http.port=80
    %prod.quarkus.datasource.jdbc.url=jdbc:postgresql://dbserver:5432/proddb
    ```

- **Activation d'un profil spécifique :**
  - Par défaut, Quarkus active le profil `dev` lorsque l'application est lancée en mode développement. Pour activer un autre profil, utilisez le paramètre `-Dquarkus.profile=<profil>` lors du démarrage de l'application :
    ```bash
    ./mvnw quarkus:dev -Dquarkus.profile=prod
    ```

#### **3. Variables d'environnement et substitution**

Les propriétés peuvent également être définies via des variables d'environnement ou des options de ligne de commande, ce qui permet une plus grande flexibilité dans la configuration.

- **Utilisation de variables d'environnement :**
  - Dans `application.properties`, vous pouvez utiliser la syntaxe `${VARIABLE}` pour substituer les valeurs provenant des variables d'environnement.
  - Exemple :
    ```properties
    quarkus.datasource.username=${DB_USER:postgres}
    quarkus.datasource.password=${DB_PASSWORD:secret}
    ```
    Ici, `DB_USER` et `DB_PASSWORD` sont des variables d'environnement. Si elles ne sont pas définies, les valeurs par défaut `postgres` et `secret` sont utilisées.

- **Substitution via la ligne de commande :**
  - Vous pouvez également passer des propriétés directement via la ligne de commande lorsque vous lancez l'application :
    ```bash
    ./mvnw quarkus:dev -Dquarkus.datasource.username=customuser -Dquarkus.datasource.password=custompassword
    ```

#### **4. Configuration des extensions Quarkus**

Chaque extension Quarkus que vous ajoutez à votre projet peut avoir des configurations spécifiques qui sont généralement définies dans `application.properties`. Par exemple :

- **Configuration de RESTEasy (pour les services REST) :**
  ```properties
  quarkus.resteasy.path=/api
  ```

- **Configuration de Hibernate ORM avec Panache :**
  ```properties
  quarkus.hibernate-orm.database.generation=drop-and-create
  quarkus.hibernate-orm.log.sql=true
  ```

- **Configuration de la sécurité (avec OAuth2) :**
  ```properties
  quarkus.oidc.auth-server-url=https://auth.example.com
  quarkus.oidc.client-id=my-client-id
  quarkus.oidc.credentials.secret=my-secret
  ```

### Conclusion

La configuration de base dans Quarkus repose principalement sur le fichier `application.properties`, qui centralise les paramètres de configuration pour l'application. Grâce aux profils et à la flexibilité de la substitution de variables, vous pouvez facilement adapter votre application aux différents environnements de développement, test, et production.