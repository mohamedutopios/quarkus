### Résumé des Fonctionnalités Demandées pour l'Application de Gestion de Bibliothèque

L'application de gestion de bibliothèque que vous devez développer est basée sur une architecture microservices utilisant Quarkus. Voici un résumé des fonctionnalités demandées et les relations entre les entités.

#### **Microservices à Implémenter**

1. **Book Service (Service des Livres)**
   - **Fonctionnalités** :
     - Ajouter un nouveau livre.
     - Supprimer un livre existant.
     - Mettre à jour les informations d'un livre.
     - Rechercher et lister les livres disponibles.
   - **Entité principale** : `Book`

2. **Author Service (Service des Auteurs)**
   - **Fonctionnalités** :
     - Ajouter un nouvel auteur.
     - Supprimer un auteur existant.
     - Mettre à jour les informations d'un auteur.
     - Rechercher et lister les auteurs.
   - **Entité principale** : `Author`

3. **User Service (Service des Utilisateurs)**
   - **Fonctionnalités** :
     - Enregistrer un nouvel utilisateur.
     - Gérer les informations de profil de l'utilisateur.
     - Supprimer un utilisateur.
     - Lister les utilisateurs.
   - **Entité principale** : `User`


4. **Emprunt Service (Service des Emprunts)**
   - **Fonctionnalités** :
     - Enregistrer un emprunt de livre par un utilisateur.
     - Supprimer un enregistrement d'emprunt.
     - Je dois être capable de lister les emprunts en cours.
     - Je dois être capable de lister les emprunts sur une période.
     - Je dois être capable de lister les emprunts pour un utilisateur.
     - Je dois être capable de savoir si un livre est emprunté.
   - **Entité principale** : `Emprunt`


5. **Catalog Service (Service du Catalogue)**
   - **Fonctionnalités** :
     - Afficher la liste des avis.
     - Afficher les avis pour un utilisateur (par son nom).
     - Afficher tous les avis pour un livre par l'isbn du livre. 
     - Afficher les livres qui ont une note bien précise.
   - **Entité** : `Review` (DTO pour regrouper les informations du livre et de l'auteur).

#### **Relations entre les Entités**

Voici un récapitulatif des relations entre les entités principales :

1. **Book (Livre)** :
   - **Relation** : (un livre est écrit par un auteur).

2. **Author (Auteur)** :
   - **Relation** : (un auteur peut écrire plusieurs livres).

3. **User (Utilisateur)** :
   - **Relation** : un utilisateur peut emprunter plusieurs (disponibles)

4. **Borrowing (Emprunt)** :
   - **Relation** :
     - un emprunt est fait par un utilisateur.
     - un emprunt concerne un livre.

5. **Review (Élément de Avis)** :
   - **Relation** : Un utilisateur peut laisser un avis sur un livre sous forme de commentaire et avec une note sur 5.

- **Book Service** : Gestion des livres avec relation à l'auteur.
- **Author Service** : Gestion des auteurs avec relation aux livres.
- **User Service** : Gestion des utilisateurs.
- **Borrowing Service** : Gestion des emprunts de livres, reliant les utilisateurs et les livres.
- **Review Service** : Regroupe les avis des utilisateurs pour un livre.

**Autres infos** : 

- Un Book a un titre et un isbn.
- Un Author a un nom, une biographie et d'une date de naissance.
- Un User a un nom, un username (unique) et un password.
- Un Emprunt dispose d'une date d'emprunt et une date de retour d'emprunt en lien à un livre et un user.
- Un Avis se compose d'un commentaire, d'une note pour un Book et un User.

