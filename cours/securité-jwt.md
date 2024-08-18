L'implémentation de la sécurité dans Quarkus en utilisant des annotations et des extensions comme JWT (JSON Web Token) est un moyen efficace de sécuriser les applications. Voici un guide pas-à-pas pour mettre en place cette sécurité.

### 1. Présentation de JWT

Le JWT est un standard ouvert (RFC 7519) pour représenter des revendications de manière sécurisée entre deux parties. Il est souvent utilisé pour l'authentification stateless dans les API REST, où un jeton est envoyé par le client à chaque requête pour prouver son identité.

### 2. Configuration de Quarkus pour JWT

#### 2.1. Ajouter les extensions nécessaires

Commencez par ajouter les extensions nécessaires à votre projet Quarkus :

```bash
./mvnw quarkus:add-extension -Dextensions="smallrye-jwt, security, resteasy"
```

- **`smallrye-jwt`** : Permet de gérer les JWT dans Quarkus.
- **`security`** : Fournit les API de sécurité de base pour gérer l'authentification et l'autorisation.
- **`resteasy`** : Pour gérer les endpoints REST.

#### 2.2. Configurer JWT dans `application.properties`

Ajoutez les configurations de base pour JWT dans votre fichier `application.properties` :

```properties
mp.jwt.verify.publickey.location=META-INF/resources/publicKey.pem
mp.jwt.verify.issuer=https://your-issuer.com
quarkus.smallrye-jwt.always-check-authorization=true
quarkus.smallrye-jwt.token.header=Authorization
quarkus.smallrye-jwt.token.cookie=JWT-Cookie
```

- **`mp.jwt.verify.publickey.location`** : Chemin vers la clé publique utilisée pour vérifier les JWT.
- **`mp.jwt.verify.issuer`** : L'URL de l'émetteur des JWT (fournisseur d'authentification).
- **`quarkus.smallrye-jwt.always-check-authorization`** : Vérifie automatiquement la présence du jeton dans chaque requête.
- **`quarkus.smallrye-jwt.token.header` et `quarkus.smallrye-jwt.token.cookie`** : Définit où le jeton doit être lu (dans les en-têtes HTTP ou les cookies).

#### 2.3. Générer une clé publique et privée pour signer les JWT

Vous pouvez générer une paire de clés RSA pour signer et vérifier vos JWT. Utilisez `openssl` :

```bash
openssl genpkey -algorithm RSA -out privateKey.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in privateKey.pem -out publicKey.pem
```

- **`privateKey.pem`** : Utilisé pour signer les JWT.
- **`publicKey.pem`** : Utilisé par Quarkus pour vérifier les JWT.

Placez le fichier `publicKey.pem` dans le répertoire `src/main/resources/META-INF/resources`.

### 3. Création d'Endpoints Sécurisés avec JWT

#### 3.1. Protéger les Endpoints avec des Annotations

Vous pouvez utiliser les annotations `@RolesAllowed`, `@PermitAll`, et `@DenyAll` pour gérer l'accès aux différents endpoints.

Exemple :

```java
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class SecureResource {

    @GET
    @Path("/public")
    @PermitAll
    public Response publicEndpoint() {
        return Response.ok("This is a public endpoint").build();
    }

    @GET
    @Path("/user")
    @RolesAllowed("user")
    public Response userEndpoint() {
        return Response.ok("This is a user endpoint").build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    public Response adminEndpoint() {
        return Response.ok("This is an admin endpoint").build();
    }
}
```

- **`@PermitAll`** : Accès ouvert à tout le monde.
- **`@RolesAllowed`** : Restreint l'accès aux utilisateurs ayant le rôle spécifié.

#### 3.2. Vérification des Rôles dans le JWT

Lorsqu'un utilisateur envoie un JWT avec une requête, Quarkus extraira automatiquement les rôles (claims `groups`) du JWT pour vérifier l'accès aux ressources.

Un exemple de JWT pourrait ressembler à ceci :

```json
{
  "iss": "https://your-issuer.com",
  "sub": "1234567890",
  "preferred_username": "user123",
  "groups": ["user", "admin"],
  "exp": 1716239022
}
```

Dans cet exemple, les rôles de l'utilisateur sont définis dans le champ `groups`.

### 4. Génération et Utilisation des JWT

#### 4.1. Génération d'un JWT

Voici un exemple de méthode pour générer un JWT en Java (utilisez une bibliothèque comme `jjwt` pour cela) :

```java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Set;

public class JwtGenerator {

    private String privateKey; // Chargez votre clé privée

    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("groups", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 heure
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }
}
```

#### 4.2. Utilisation du JWT

Une fois le JWT généré et distribué au client (via une requête de login, par exemple), le client doit l'inclure dans l'en-tête `Authorization` de chaque requête suivante :

```http
GET /api/user HTTP/1.1
Host: your-api.com
Authorization: Bearer <your-jwt-token>
```

Quarkus s'occupera alors de vérifier le JWT, d'extraire les informations pertinentes (comme les rôles), et de sécuriser l'accès aux endpoints en fonction de ces informations.

### 5. Conclusion

En suivant ces étapes, vous pouvez implémenter une sécurité robuste dans une application Quarkus en utilisant JWT pour l'authentification et les annotations pour l'autorisation. Cette approche permet de créer des applications stateless et sécurisées, parfaitement adaptées aux architectures modernes comme les microservices.