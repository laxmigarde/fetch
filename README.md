# Fetch Receipt Processor Evaluation

As part of the problem statement a webservice has been developed using Java and Spring Boot that implements the APIs based on the provided api.yml file and follows all implementation rules specified in this Github Repo. The APIs created are:
- POST request at `/receipts/process`
- GET request at `/receipts/{id}/points`

#### Based on the requirements:
- POST call (`/receipts/process`) 
    - accepts a JSON request body (required) of the format:
        ```bash
        # sample request
        {"retailer": "Walgreens", "purchaseDate": "2022-01-02", "purchaseTime": "08:13", "total": "2.65","items": [{"shortDescription": "Pepsi - 12-oz", "price": "1.25"},{"shortDescription": "Dasani", "price": "1.40"}]}
        ```
    - returns 200 or 400 response as follows
        - 200 OK when the receipt request is valid and creates a new receipt id and stores id and receipt in-memory
        ```bash
        # sample response
        {"id": "9d066b23-ebc4-4c11-b200-46accf520674"}
        ```
        - 400 BAD REQUEST when receipt request is invalid
        ```bash
        {"message": "The receipt is invalid."}
        ```

- GET call (`/receipts/{id}/points`) 
    - accepts `id` from the users as parameter and url becomes /receipts/52da5c3d-a54e-4675-a784-da3abef5354b/points
    - returns 200 or 404 responses
        - 200 OK when `id` is already present in-memory and calculates the points based on the given rules
        ```bash
        # sample response
        {"points": 15}
        ```
        - 404 NOT FOUND when `id` is not present
        ```bash
        # error response
        {"message": "No receipt found for that ID."}
        ``` 


## 🔎 Overview:

- Development is done using a layered architecture pattern and programmed using Java, Spring Boot framework and the application accepts requests at the controller layer (i.e. the API calls - POST, GET).
- Request is handled by the service layer and after processing and exceptions handling suitable response is given back to the user.
- Data is not retained on application restart and in-memory solution - HashMap is used for implementation. Other solutions considered were H2 database, caching.
- Good unit testing coverage for the project.
- Documentation done using comments, OpenAPI and README.md
- Dependencies available in pom.xml file.
- DockerFile added to build and run application on any system.


## 🛠️ Execution Instructions:

- Requirements: [Docker Desktop](https://www.docker.com/products/docker-desktop/) for Mac or Windows
- Suitable IDE like Intellij IDEA or VSCode
- Clone the repository from [fetch - GitHub Repo](https://github.com/laxmigarde/fetch)
- Open the project and find the [Dockerfile](https://github.com/laxmigarde/fetch/blob/main/Dockerfile) in the project
- If you are in the project, goto the Dockerfile's directory level and build the docker by copying the following command:
```
docker build -t receiptprocessor-app .
```
- The above command clean builds the entire project and creates a JAR file named `fetch-receipt-processor-1.0-SNAPSHOT.jar` (it can be checked in the target folder of the project, which will be generated after the build)
- Next we run the JAR / our project. Please copy the following command to CLI:
```
 docker run -p 8080:8080 receiptprocessor-app
```
- Once the Spring boot application starts, it can be seen as started on Docker desktop Images and Containers tabs as well.
- Next please open the following [Swagger Link](http://localhost:8080/swagger-ui/index.html#/) to test the endpoints:
```
http://localhost:8080/swagger-ui/index.html#/
```
- The above link opens the SwaggerUI / OpenAPI page which is similar to a rest client for API testing.
- Click on the dropdown for each endpoint and click on `Try it out` button to test.


## 🗂️ Project Structure:

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.fetch.receipt.processor
│   │   │   │   ├── config
│   │   │   │   │   ├── SwaggerConfig.java
│   │   │   │   ├── controller
│   │   │   │   │   ├── ReceiptProcessorController.java
│   │   │   │   ├── data
│   │   │   │   │   ├── model
│   │   │   │   │   │   ├── Item.java
│   │   │   │   │   │   ├── Receipt.java
│   │   │   │   │   ├── response
│   │   │   │   │   │   ├── BadRequestResponse.java
│   │   │   │   │   │   ├── NotFoundResponse.java
│   │   │   │   │   │   ├── Response.java
│   │   │   │   ├── exception
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   ├── UUIDNotFoundException.java
│   │   │   │   ├── service
│   │   │   │   │   ├── ReceiptProcessorService.java
│   │   │   ├── ReceiptProcessorApp.java (main)
│   │   ├── resources
│   │   │   ├── application.yaml
│   ├── test
│   │   ├── java
│   │   │   ├── com.fetch.receipt.processor
│   │   │   │   ├── controller
│   │   │   │   │   ├── ReceiptProcessorControllerTest.java
│   │   │   │   ├── service
│   │   │   │   │   ├── ReceiptProcessorServiceTest.java
│   │   │   ├── ReceiptProcessorAppTest.java
├── Dockerfile
├── pom.xml
├── README.md ( this file )
└── .gitignore
```

## 🖥️ Execute project on your local machine:
- Requirements: Java 17.6.0 temurin, Maven 3.6.3, suitable IDE like Intellij IDEA, VSCode etc.
- Clone GitHub repo and open the project
- Build and clean the Maven project to download all the dependencies using IDE or execute command from terminal:
```bash
mvn clean package -DskipTests
```
-  Run the application by right click on `ReceiptProcessorApp.java` and select `Run ReceiptProcessorApp` (Goto project folder: /src/main/java/com/fetch/receipt/processor)
- Test the APIs using this [Swagger Link:](http://localhost:8080/swagger-ui/index.html#/)
```bash
http://localhost:8080/swagger-ui/index.html#/
```

#### Screenshots of few outputs are available in 'screenshots' folder for reference.