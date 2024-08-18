# Étude de cas - Mise en place d'une solution Cloud Native pour "FoodieFast"

**Contexte :**
Vous travaillez pour "FoodieFast", une startup qui souhaite lancer une application mobile pour la commande et la livraison de repas cuisinés par des chefs locaux indépendants. L'application doit permettre aux utilisateurs de parcourir les menus, de commander, de payer en ligne, et de suivre leur livraison en temps réel. La startup s'attend à une croissance rapide, nécessitant des déploiements fréquents, et prévoit des pics d'utilisation importants pendant les heures de repas.

**Partie 1 : Analyse des besoins**

1.1. Pourquoi serait-il judicieux pour "FoodieFast" d'adopter des runtimes éphémères pour sa plateforme 

- *Ils minimisent le gaspillage des ressources et une meilleure gestion des coût*

1.2. Comment l'utilisation des runtimes éphémères peut-elle assurer une haute disponibilité lors des pics de commande pendant les heures de repas ?

- *Pendant les pics de demandes, les runtimes peuvent être augmenter dynamiquement, et également les réduire quand la demande baisse*

1.3. Discutez des préoccupations de sécurité potentielles concernant les transactions de paiement et les données des utilisateurs. Comment les runtimes éphémères peuvent-ils aider ou compliquer la situation ?

-*Pour aider*
    - Réduire la surface d'attaque.

-*Compliquer la situation*
    - Le controle sur la consomation des ressources
    - Gestion des secrets.
    - Gestion des volumes.
    - Concurrence d'accès aux données.

**Partie 2 : Proposition d'architecture**

2.1. Esquissez une architecture Cloud Native pour l'application "FoodieFast".

    - Conteneurisation et orchestration par kubernetes.
    - Microservices Metiers:
        - Menu
        - Commandes
        - Paiement
        - Livraison
        - Administration
        - Utilisateur
        - Authentifications
    
    - Microservices d'api Gateway
    - Microservices de composition

    - Une gestion de bases de données polyglottes
        

2.2. Expliquez comment chaque microservice contribue à l'efficacité globale et à l'évolutivité de l'application.
    - Menu : *Permet la mise à jour indépendante des menus sans affecter les autres microservices* 
    - Commandes: *Facilite la scalabilité lors des pics de demande*
    - Paiement: *Service isolé des autres pour assurer une sécurité et fiabilité des transaction*
    - Livraison: *Suivre les livraison en temps réel sans surcharger les autres services*
    - Une resilience globale entre les microservices *!!!!*

2.3. Comment cette architecture supporte-t-elle les déploiements fréquents et la gestion des erreurs ?

    - Déploiements fréquents: Les microservices permettent des déploiements indépendants.
    - Gestion des erreurs: Les microservices permettent une gestion plus facile des erreurs et des pannes. Les services defaillants peuvent être redémarrés ou un retour en arrière sans affecter le reste de l'application.

**Partie 3 : Le concept des Twelve-Factor App**

3.1. Identifiez cinq principes des Twelve-Factor App que "FoodieFast" devrait adopter en priorité pour assurer la scalabilité et la résilience de son application.


3.2. Comment ces principes pourraient-ils faciliter le déploiement, le scaling, et la maintenance de l'application, notamment lors de l'ajout de nouveaux chefs ou de nouvelles fonctionnalités ?


**Partie 4 : Stratégies de déploiement**

4.1. Comparez les avantages et inconvénients des déploiements en "rolling update" et "blue-green" pour "FoodieFast".

4.2. Comment la startup pourrait-elle utiliser les "rollbacks automatiques" pour minimiser les interruptions de service ?

4.3. Proposez un scénario dans lequel un déploiement progressif serait bénéfique pour "FoodieFast".