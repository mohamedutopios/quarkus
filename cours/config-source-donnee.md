La configuration des sources de données dans Quarkus est essentielle pour connecter votre application à une base de données. Quarkus offre une configuration simple et efficace pour gérer les connexions aux bases de données via JDBC. Vous pouvez configurer une ou plusieurs sources de données en fonction des besoins de votre application.

### 1. Configuration d'une Source de Données Unique

#### 1.1. Ajouter les Extensions Nécessaires

Pour commencer, vous devez ajouter les extensions pour gérer les bases de données et JPA. Par exemple, si vous utilisez PostgreSQL, vous ajouteriez les extensions suivantes :

```bash
./mvnw quarkus:add-extension -Dextensions="jdbc-postgresql, hibernate-orm"
```

#### 1.2. Configurer les Propriétés de la Source de Données

La configuration des propriétés de la source de données se fait dans le fichier `application.properties`. Voici un exemple de configuration pour une base de données PostgreSQL :

```properties
# Type de la base de données
quarkus.datasource.db-kind=postgresql

# URL de connexion JDBC
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/your_database

# Identifiants de connexion
quarkus.datasource.username=your_username
quarkus.datasource.password=your_password

# Configuration de la taille du pool de connexions
quarkus.datasource.jdbc.max-size=20
quarkus.datasource.jdbc.min-size=5

# Configuration Hibernate (JPA)
quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.log.sql=true
```

- **`quarkus.datasource.db-kind`** : Indique le type de la base de données (`postgresql`, `mysql`, `mariadb`, `h2`, etc.).
- **`quarkus.datasource.jdbc.url`** : URL de connexion JDBC pour accéder à la base de données.
- **`quarkus.datasource.username`** et **`quarkus.datasource.password`** : Identifiants de connexion à la base de données.
- **`quarkus.datasource.jdbc.max-size`** : Taille maximale du pool de connexions.
- **`quarkus.datasource.jdbc.min-size`** : Taille minimale du pool de connexions.

#### 1.3. Configuration de Hibernate

Pour gérer les entités JPA avec Hibernate, vous pouvez spécifier des propriétés supplémentaires :

- **`quarkus.hibernate-orm.database.generation`** : Contrôle la stratégie de génération du schéma de la base de données (`none`, `update`, `drop-and-create`, etc.).
- **`quarkus.hibernate-orm.log.sql`** : Active la journalisation des requêtes SQL générées par Hibernate.

### 2. Configuration de Sources de Données Multiples

Dans certaines applications, vous pourriez avoir besoin de vous connecter à plusieurs bases de données. Quarkus permet de configurer plusieurs sources de données avec des noms spécifiques.

#### 2.1. Définir des Sources de Données Nommées

Vous pouvez configurer plusieurs sources de données en leur donnant des noms distincts. Voici un exemple avec deux bases de données, `mydb1` et `mydb2` :

```properties
# Première source de données
quarkus.datasource.mydb1.db-kind=postgresql
quarkus.datasource.mydb1.jdbc.url=jdbc:postgresql://localhost:5432/your_database1
quarkus.datasource.mydb1.username=user1
quarkus.datasource.mydb1.password=pass1

# Deuxième source de données
quarkus.datasource.mydb2.db-kind=mysql
quarkus.datasource.mydb2.jdbc.url=jdbc:mysql://localhost:3306/your_database2
quarkus.datasource.mydb2.username=user2
quarkus.datasource.mydb2.password=pass2
```

#### 2.2. Utilisation de Sources de Données Nommées dans le Code

Lorsque vous avez plusieurs sources de données, vous pouvez les injecter spécifiquement dans vos services ou répertoires via l'annotation `@DataSource` :

```java
import io.agroal.api.AgroalDataSource;
import javax.inject.Inject;
import javax.sql.DataSource;

public class MyService {

    @Inject
    @DataSource("mydb1")
    AgroalDataSource dataSource1;

    @Inject
    @DataSource("mydb2")
    AgroalDataSource dataSource2;

    public void useDataSources() {
        // Utilisation de dataSource1 et dataSource2
    }
}
```

### 3. Configuration Avancée

Quarkus offre des options de configuration avancées pour gérer des aspects spécifiques des sources de données, tels que le pool de connexions, les transactions, et l'isolation des transactions.

#### 3.1. Pool de Connexions

Le pool de connexions est géré par Agroal, et vous pouvez ajuster ses paramètres via les propriétés suivantes :

```properties
# Taille du pool de connexions
quarkus.datasource.jdbc.min-size=5
quarkus.datasource.jdbc.max-size=20

# Temps maximum pour emprunter une connexion (en millisecondes)
quarkus.datasource.jdbc.max-lifetime=1800000

# Temps d'inactivité maximum avant fermeture de la connexion (en millisecondes)
quarkus.datasource.jdbc.idle-removal-interval=10000
```

#### 3.2. Transactions et Isolation

Vous pouvez configurer l'isolation des transactions et d'autres aspects transactionnels comme suit :

```properties
# Isolation des transactions (READ_COMMITTED, READ_UNCOMMITTED, REPEATABLE_READ, SERIALIZABLE)
quarkus.datasource.jdbc.transaction-isolation=READ_COMMITTED

# Activer ou désactiver les transactions XA (pour les sources de données JDBC)
quarkus.datasource.jdbc.enable-xa=false
```

### 4. Conclusion

La configuration des sources de données dans Quarkus est flexible et simple à mettre en place. Que vous ayez besoin de configurer une seule base de données ou plusieurs, Quarkus offre des propriétés claires pour définir tous les aspects de la connexion à la base de données, y compris la gestion du pool de connexions, les transactions, et l'isolation.

En utilisant ces configurations, vous pouvez assurer une connectivité robuste et performante à vos bases de données, tout en profitant des fonctionnalités avancées offertes par Quarkus et Hibernate.