mvn io.quarkus:quarkus-maven-plugin:create \
    -DprojectGroupId=com.example \
    -DprojectArtifactId=my-quarkus-app \
    -DclassName="com.example.MyResource" \
    -Dpath="/hello"


mvn -U io.quarkus:quarkus-maven-plugin:create \
        -DprojectGroupId=org.agoncal.quarkus.microservices \
        -DprojectArtifactId=rest-book \
        -DclassName="org.agoncal.quarkus.microservices.book.BookResource" \
        -Dpath="/api/books" \
        -Dextensions="resteasy-jsonb, smallrye-openapi"

mvn io.quarkus:quarkus-maven-plugin:create \
    -DprojectGroupId=com.example \
    -DprojectArtifactId=my-quarkus-app \
    -DprojectVersion=1.0.0-SNAPSHOT \
    -DclassName="com.example.MyResource" \
    -Dpath="/hello" \
    -Dextensions="resteasy,resteasy-jackson,hibernate-orm" \
    -DpackageName=com.example.myapp \
    -DbuildTool=gradle \
    -DoutputDirectory=C:/Users/mohamed/Documents/quarkus-apps


// rajouter des extensions : 
mvn quarkus:add-extension -Dextensions="resteasy,resteasy-jackson"
