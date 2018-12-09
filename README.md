# ReadFilesApp-Sockets

To run this project: 

1- Install maven on your local machine

2- open cmd and navigate to project `$path/ReadFilesApp-Sockets`

3- Run this command: mvn clean install

4- Run the following commands: 

`mvn exec:java -Dexec.mainClass="com.polystar.app.FirstServer` to run the first server

`mvn exec:java -Dexec.mainClass="com.polystar.app.SecondServer` to run the second server

`mvn exec:java -Dexec.mainClass="com.polystar.app.Client` to run the client
  
5- You should see the result which is "the 5 most common words in the two texts"
