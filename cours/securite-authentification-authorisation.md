La sécurité est un aspect crucial dans le développement d'applications modernes, et Quarkus propose un ensemble de fonctionnalités pour gérer efficacement l'authentification et l'autorisation. Voici une introduction à la sécurité dans Quarkus, en se concentrant sur l'authentification et l'autorisation.

### 1. Concepts de Base

- **Authentification** : Il s'agit de vérifier l'identité d'un utilisateur ou d'un système. Dans Quarkus, cela se fait généralement via des mécanismes tels que les identifiants utilisateur/mot de passe, OAuth2, OpenID Connect (OIDC), ou encore les certificats.
  
- **Autorisation** : Une fois l'utilisateur authentifié, l'autorisation détermine ce que cet utilisateur est autorisé à faire. Cela inclut les vérifications de rôles, de permissions, et l'accès aux ressources.

### 2. Authentification

Quarkus supporte plusieurs méthodes d'authentification, parmi lesquelles :

#### 2.1. Basic Authentication
C'est une méthode simple où l'utilisateur envoie son nom d'utilisateur et son mot de passe encodés en Base64 dans l'en-tête HTTP `Authorization`.

Pour configurer la Basic Authentication dans Quarkus :

1. Ajouter les extensions nécessaires :
   ```bash
   ./mvnw quarkus:add-extension -Dextensions="security, security-jdbc"
   ```

2. Configurer les propriétés de sécurité dans `application.properties` :
   ```properties
   quarkus.security.users.file.enabled=true
   quarkus.security.users.file.path=users.properties
   quarkus.security.roles.file.path=roles.properties
   ```

3. Créer les fichiers `users.properties` et `roles.properties` avec les informations d'authentification.

#### 2.2. OAuth2 et OpenID Connect (OIDC)

Pour une authentification plus moderne et sécurisée, Quarkus supporte OAuth2 et OIDC. OIDC est une couche d'identité sur OAuth2 qui permet d'obtenir les informations de l'utilisateur authentifié.

1. Ajouter les extensions OAuth2 ou OIDC :
   ```bash
   ./mvnw quarkus:add-extension -Dextensions="oidc"
   ```

2. Configurer l'authentification OIDC dans `application.properties` :
   ```properties
   quarkus.oidc.auth-server-url=https://your-oidc-server.com/auth/realms/your-realm
   quarkus.oidc.client-id=your-client-id
   quarkus.oidc.credentials.secret=your-client-secret
   quarkus.oidc.application-type=web-app
   ```

3. Protéger les endpoints REST avec des annotations comme `@RolesAllowed` pour restreindre l'accès :

   ```java
   @GET
   @Path("/secure-endpoint")
   @RolesAllowed({"user", "admin"})
   public Response secureEndpoint() {
       return Response.ok("This is a secure endpoint").build();
   }
   ```

### 3. Autorisation

L'autorisation dans Quarkus peut être gérée via des annotations et des règles définies dans le code ou via des configurations externes.

#### 3.1. Basée sur les Rôles

- **`@RolesAllowed`** : Cette annotation est utilisée pour spécifier les rôles autorisés à accéder à une méthode ou un endpoint particulier.

  Exemple :
  ```java
  @GET
  @Path("/admin")
  @RolesAllowed("admin")
  public Response adminEndpoint() {
      return Response.ok("Admin access only").build();
  }
  ```

- **`@DenyAll` et `@PermitAll`** : `@DenyAll` empêche l'accès à tous les utilisateurs, tandis que `@PermitAll` permet l'accès à tous.

  Exemple :
  ```java
  @GET
  @Path("/public")
  @PermitAll
  public Response publicEndpoint() {
      return Response.ok("This is a public endpoint").build();
  }
  
  @GET
  @Path("/restricted")
  @DenyAll
  public Response restrictedEndpoint() {
      return Response.status(Response.Status.FORBIDDEN).build();
  }
  ```

#### 3.2. Basée sur des Permissions

Quarkus permet également de gérer l'autorisation de manière plus granulaire via des permissions spécifiques. Par exemple, vous pouvez créer des méthodes de validation personnalisées pour vérifier si un utilisateur a la permission d'accéder à une ressource.

### 4. Sécurisation des Services

Quarkus facilite la sécurisation des services en offrant des extensions pour intégrer facilement la sécurité dans votre application. Vous pouvez sécuriser les services REST, les pages web (avec des extensions comme `quarkus-oidc` pour sécuriser les pages via Keycloak par exemple), et même les applications réactives avec Vert.x.

### 5. Gestion des Sessions et des Jetons

- **Sessions** : Pour les applications classiques (web), Quarkus peut gérer des sessions utilisateur qui stockent l'état de l'utilisateur entre les requêtes.
  
- **JWT (JSON Web Token)** : Utilisé dans les applications sans état (stateless), où chaque requête contient un JWT signé qui porte les informations d'identification de l'utilisateur. Quarkus offre une extension `quarkus-smallrye-jwt` pour gérer JWT.

### 6. Conclusion

La sécurité dans Quarkus est robuste et flexible, permettant de gérer à la fois l'authentification et l'autorisation de manière simple et efficace. Les intégrations natives avec OAuth2, OIDC, JWT, et d'autres protocoles modernes facilitent la création d'applications sécurisées prêtes pour la production.

En combinant ces mécanismes avec les annotations et les extensions Quarkus, vous pouvez construire des applications sécurisées adaptées à différents scénarios d'utilisation, que ce soit pour des API REST, des microservices ou des applications web complètes.
