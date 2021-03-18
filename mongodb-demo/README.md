# MongoDb Sample Spring boot app for CRUD and search operations 

### Steps to test application doing CRUD operations using MongoDB-Template
1. Comment or delete configuration class ```DatabaseSeederRunnerUsingRepositories``` to perform CRUD operations using MongoDb-Template.
2. Start the application, ```DatabaseSeederRunnerUsingMongoTemplate``` class will run after the application starts and will insert few records in MongoDb database using MongoDb-Template.
3. Test crud operations on records added in 2nd step using endpoints available in ```BasicController``` class.

### Steps to test application doing CRUD operations using Spring-mongo-repositories
1. Comment or delete configuration class ```DatabaseSeederRunnerUsingMongoTemplate``` to perform CRUD operations using Spring-mongo-repositories.
2. Start the application, ```DatabaseSeederRunnerUsingRepositories``` class will run after the application starts and will insert few records in MongoDb database using Spring-mongo-repositories.
3. Test crud operations on records added in 2nd step using endpoints available in ```RepositoryController``` class.
