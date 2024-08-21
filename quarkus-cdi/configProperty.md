2. **Testez les endpoints :**
  - **`/config/message`**
    - URL : `http://localhost:8080/config/message`
    - Réponse attendue :
      ```
      Message: Hello, Quarkus!
      Version: 1.0.0
      ```
  - **`/config/feature`**
    - URL : `http://localhost:8080/config/feature`
    - Réponse attendue :
      ```
      Max Items: 100
      Feature Enabled: true
      ```
  - **`/config/greeting`**
    - URL : `http://localhost:8080/config/greeting`
    - Réponse attendue :
      ```
      Hello, default user!
      ```
  - **`/config/server`**
    - URL : `http://localhost:8080/config/server`
    - Réponse attendue :
      ```
      Server is running on localhost:8080
      ```
