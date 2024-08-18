Voici une liste plus détaillée des options et commandes pour Maven et Quarkus, avec des exemples concrets pour chaque cas.

### **Maven**

#### **Commandes Maven Avancées :**

1. **`mvn validate`** :
   - Valide le projet, s’assure que toutes les informations nécessaires sont disponibles.
   - Exemple : `mvn validate` - Vérifie que la structure du projet est correcte avant de continuer.

2. **`mvn process-resources`** :
   - Traite les ressources du projet (copie des fichiers `.properties`, etc.).
   - Exemple : `mvn process-resources` - Copie les ressources dans le répertoire `target/classes`.

3. **`mvn verify`** :
   - Vérifie le projet, en exécutant toutes les vérifications de qualité.
   - Exemple : `mvn verify` - Exécute des tests d'intégration et d'autres vérifications spécifiques.

4. **`mvn dependency:analyze`** :
   - Analyse les dépendances pour identifier les dépendances déclarées non utilisées et les dépendances non déclarées.
   - Exemple : `mvn dependency:analyze` - Identifie les dépendances inutiles ou manquantes dans le projet.

5. **`mvn versions:display-dependency-updates`** :
   - Affiche les mises à jour possibles des dépendances du projet.
   - Exemple : `mvn versions:display-dependency-updates` - Montre quelles dépendances peuvent être mises à jour.

6. **`mvn versions:display-plugin-updates`** :
   - Affiche les mises à jour possibles des plugins Maven utilisés dans le projet.
   - Exemple : `mvn versions:display-plugin-updates` - Montre quelles versions de plugins peuvent être mises à jour.

7. **`mvn enforcer:enforce`** :
   - Applique des règles pour s’assurer que le projet respecte certaines contraintes (versions JDK, dépendances spécifiques, etc.).
   - Exemple : `mvn enforcer:enforce -Drules=RequireJavaVersion` - Vérifie que le projet utilise une version spécifique de Java.

8. **`mvn site:stage`** :
   - Prépare la génération du site Maven à une autre étape du build.
   - Exemple : `mvn site:stage -DstagingDirectory=/path/to/stage` - Génère le site dans un répertoire de staging.

#### **Options Maven Avancées :**

1. **`-Dmaven.test.skip=true`** :
   - Ignore complètement la compilation des tests et leur exécution.
   - Exemple : `mvn install -Dmaven.test.skip=true` - Installe le projet sans compiler ni exécuter les tests.

2. **`-DskipITs`** :
   - Ignore les tests d'intégration.
   - Exemple : `mvn verify -DskipITs` - Exécute le cycle de vie jusqu'à la phase `verify` mais ignore les tests d'intégration.

3. **`-T 4`** :
   - Utilise le multithreading pour paralléliser les phases du build.
   - Exemple : `mvn clean install -T 4` - Exécute les phases `clean` et `install` en utilisant 4 threads.

4. **`-Dlog.level=DEBUG`** :
   - Change le niveau de journalisation du build Maven.
   - Exemple : `mvn install -Dlog.level=DEBUG` - Exécute le build avec une sortie de log au niveau DEBUG.

5. **`-DgroupId=org.example -DartifactId=my-app -Dversion=1.0`** :
   - Spécifie les coordonnées du projet lors de la création d'un artefact.
   - Exemple : `mvn archetype:generate -DgroupId=com.example -DartifactId=my-app -Dversion=1.0` - Crée un nouveau projet Maven avec les coordonnées spécifiées.

6. **`-Dcheckstyle.skip=true`** :
   - Ignore la vérification de code via Checkstyle.
   - Exemple : `mvn verify -Dcheckstyle.skip=true` - Exécute la vérification sans exécuter Checkstyle.

### **Quarkus**

#### **Commandes Quarkus Avancées :**

1. **`mvn quarkus:add-extension`** :
   - Ajoute des extensions Quarkus au projet.
   - Exemple : `mvn quarkus:add-extension -Dextensions="resteasy,hibernate-orm"` - Ajoute les extensions RESTEasy et Hibernate ORM.

2. **`mvn quarkus:create-app`** :
   - Crée un nouveau projet Quarkus.
   - Exemple : `mvn quarkus:create-app -DprojectGroupId=com.example -DprojectArtifactId=my-quarkus-app` - Crée une nouvelle application Quarkus avec les coordonnées spécifiées.

3. **`mvn quarkus:build -Dquarkus.package.type=uber-jar`** :
   - Construit un JAR "fat" ou "uber-JAR" contenant toutes les dépendances.
   - Exemple : `mvn quarkus:build -Dquarkus.package.type=uber-jar` - Construit l'application en un seul JAR incluant toutes les dépendances.

4. **`mvn quarkus:test`** :
   - Exécute les tests dans un environnement Quarkus.
   - Exemple : `mvn quarkus:test` - Lance les tests unitaires et d'intégration dans un contexte Quarkus.

5. **`mvn quarkus:dev -Dquarkus.args="--help"`** :
   - Démarre l'application en mode dev avec des arguments spécifiques.
   - Exemple : `mvn quarkus:dev -Dquarkus.args="--help"` - Démarre l'application avec l'argument `--help` pour afficher l'aide.

6. **`mvn quarkus:remote-dev -Dquarkus.live-reload.password=my-secret`** :
   - Configure un mot de passe pour le rechargement à chaud à distance.
   - Exemple : `mvn quarkus:remote-dev -Dquarkus.live-reload.password=my-secret` - Active le rechargement à chaud à distance avec un mot de passe.

7. **`mvn quarkus:info`** :
   - Affiche les informations sur l’environnement Quarkus et la configuration.
   - Exemple : `mvn quarkus:info` - Montre la configuration actuelle de l'environnement Quarkus.

#### **Options Quarkus Avancées :**

1. **`-Dquarkus.profile=test`** :
   - Utilise un profil spécifique pour le build ou l'exécution.
   - Exemple : `mvn quarkus:dev -Dquarkus.profile=test` - Démarre l'application en mode développement avec le profil `test`.

2. **`-Dquarkus.container-image.build=true`** :
   - Construit une image Docker directement depuis le projet Quarkus.
   - Exemple : `mvn package -Dquarkus.container-image.build=true` - Construit l'application et génère une image Docker.

3. **`-Dquarkus.package.main-class=com.example.Main`** :
   - Spécifie la classe principale pour l'application.
   - Exemple : `mvn quarkus:build -Dquarkus.package.main-class=com.example.Main` - Construit l'application avec `Main` comme classe principale.

4. **`-Dquarkus.native.additional-build-args="-H:+TraceClassInitialization"`** :
   - Ajoute des arguments supplémentaires lors de la compilation native.
   - Exemple : `mvn package -Dquarkus.package.type=native -Dquarkus.native.additional-build-args="-H:+TraceClassInitialization"` - Construit un binaire natif avec des arguments de trace spécifiques.

5. **`-Dquarkus.log.console.level=INFO`** :
   - Change le niveau de journalisation pour la console.
   - Exemple : `mvn quarkus:dev -Dquarkus.log.console.level=DEBUG` - Démarre l'application en mode développement avec les logs en niveau DEBUG.

6. **`-Dquarkus.http.port=8081`** :
   - Change le port HTTP par défaut sur lequel l'application écoute.
   - Exemple : `mvn quarkus:dev -Dquarkus.http.port=8081` - Démarre l'application sur le port 8081 au lieu du port par défaut 8080.

Ces commandes et options supplémentaires vous donnent un contrôle fin sur les processus de développement et de construction avec Maven et Quarkus. Elles sont particulièrement utiles pour personnaliser votre environnement et adapter les outils à des cas d'utilisation spécifiques.