### Installation et configuration de l'environnement de développement (Java, Maven, Quarkus CLI)

Pour démarrer avec Quarkus, il est important d'avoir un environnement de développement bien configuré. Voici les étapes pour installer et configurer Java, Maven et le Quarkus CLI.

#### **1. Installation de Java Development Kit (JDK)**

Quarkus nécessite JDK 11 ou une version ultérieure. Voici comment installer le JDK :

- **Sous Windows, MacOS et Linux :**
  - **Téléchargement :**
    - Rendez-vous sur le site officiel d'Oracle pour télécharger le JDK : [Oracle JDK Downloads](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
    - Alternativement, vous pouvez utiliser des distributions open source comme [AdoptOpenJDK](https://adoptopenjdk.net/) ou [Amazon Corretto](https://aws.amazon.com/corretto/).

  - **Installation :**
    - Suivez les instructions d'installation spécifiques à votre système d'exploitation.

  - **Vérification de l'installation :**
    - Après l'installation, assurez-vous que Java est correctement installé en exécutant la commande suivante dans le terminal ou l'invite de commande :
      ```bash
      java -version
      ```
      Vous devriez voir la version de Java que vous avez installée.

  - **Configuration des variables d'environnement :**
    - **Windows :** Ajoutez le chemin du JDK à la variable d'environnement `JAVA_HOME` et mettez à jour le `PATH`.
    - **MacOS/Linux :** Modifiez le fichier `~/.bashrc`, `~/.zshrc`, ou `~/.bash_profile` pour ajouter les lignes suivantes :
      ```bash
      export JAVA_HOME=/path/to/your/jdk
      export PATH=$JAVA_HOME/bin:$PATH
      ```
    - Remplacez `/path/to/your/jdk` par le chemin d'installation de votre JDK.

#### **2. Installation d'Apache Maven**

Maven est un outil de gestion de projet et d'automatisation de la construction utilisé pour gérer les dépendances et les builds dans les projets Java.

- **Sous Windows, MacOS et Linux :**
  - **Téléchargement :**
    - Rendez-vous sur le site officiel de Maven : [Apache Maven Downloads](https://maven.apache.org/download.cgi).

  - **Installation :**
    - Extrayez l'archive téléchargée dans un répertoire de votre choix.
    - Configurez la variable d'environnement `M2_HOME` pour pointer vers ce répertoire.
    - Ajoutez le répertoire `bin` de Maven à votre `PATH`.

  - **Vérification de l'installation :**
    - Vérifiez que Maven est correctement installé en exécutant la commande suivante :
      ```bash
      mvn -version
      ```
      Cela devrait afficher la version de Maven, la version de Java, et l'OS.

#### **3. Installation de Quarkus CLI**

Le Quarkus CLI (Command Line Interface) facilite la création et la gestion des projets Quarkus.

- **Méthodes d'installation :**
  - **Via SDKMAN! (recommandé) :**
    - **Installation de SDKMAN!** (si vous ne l'avez pas déjà) :
      ```bash
      curl -s "https://get.sdkman.io" | bash
      ```
      Suivez les instructions pour finaliser l'installation.
    - **Installation de Quarkus CLI :**
      ```bash
      sdk install quarkus
      ```

  - **Via Homebrew (pour macOS) :**
    - Si Homebrew est installé :
      ```bash
      brew install quarkusio/tap/quarkus
      ```

  - **Téléchargement manuel :**
    - Vous pouvez aussi télécharger le Quarkus CLI depuis le site officiel et suivre les instructions d'installation : [Quarkus CLI Documentation](https://quarkus.io/guides/cli-tooling).

- **Vérification de l'installation :**
  - Vérifiez que Quarkus CLI est installé en exécutant la commande :
    ```bash
    quarkus --version
    ```
    Cela devrait afficher la version de Quarkus CLI.

#### **4. Création et Exécution d'un Projet Quarkus**

Maintenant que votre environnement est configuré, vous pouvez créer un projet Quarkus simple pour tester l'installation.

- **Création du projet :**
  - Utilisez le Quarkus CLI pour créer un nouveau projet :
    ```bash
    quarkus create app org.acme:quarkus-getting-started:1.0.0-SNAPSHOT
    cd quarkus-getting-started
    ```

- **Exécution en mode développement :**
  - Pour démarrer l'application en mode développement avec hot-reload :
    ```bash
    ./mvnw quarkus:dev
    ```
  - Cela lance le serveur Quarkus et vous pouvez accéder à votre application sur [http://localhost:8080](http://localhost:8080).

- **Structure du projet :**
  - Explorez les dossiers et fichiers créés pour comprendre la structure de base d'un projet Quarkus, notamment `src/main/java` pour le code source et `src/main/resources` pour les configurations.

#### **5. Outils supplémentaires recommandés**

- **IDE (Environnement de Développement Intégré) :**
  - **IntelliJ IDEA :** Un IDE populaire avec un support complet pour Quarkus.
  - **Visual Studio Code :** Léger et flexible, avec des extensions pour Quarkus et Java.
  - **Eclipse :** Un autre IDE populaire avec des plugins pour Quarkus.

- **Docker :**
  - Pour le déploiement de conteneurs et l'intégration avec Kubernetes/OpenShift, il est recommandé d'installer Docker.

- **GraalVM :**
  - Si vous souhaitez compiler vos applications Quarkus en binaires natifs, installez GraalVM et configurez-le avec Quarkus.

Ce guide couvre les étapes nécessaires pour installer et configurer votre environnement de développement pour travailler avec Quarkus. Vous êtes maintenant prêt à développer et déployer des applications Java modernes avec Quarkus.