# Valven Candidate Task Readme

This Readme provides an overview of the Valven Candidate Task project, explaining its components, functionality, and how to run it.

## Project Description

The Valven Candidate Task is a Java-based web application that retrieves commits from both GitHub and GitLab repositories within the last month and stores them in a database. Users can access the application via a web interface to view the list of commits, commit details, and developer information.

## Project Structure

The project consists of several components:

- **Controller Classes**: These classes handle HTTP requests and control the flow of the application.
  - `CommitController`: Manages the interaction with the web interface and provides endpoints for displaying developer information and commit lists.

- **Service Classes**: These classes contain the business logic of the application.
  - `CommitService`: Retrieves commits from the database, GitHub, and GitLab, and performs DTO (Data Transfer Object) conversions.
  - `GitHubService`: Connects to the GitHub API, fetches commits, and stores them in the database.
  - `GitLabService`: Connects to the GitLab API, retrieves commits, and saves them to the database.

- **Entity Classes**: These classes define the structure of the database tables.
  - `Commit`: Represents a commit entity with fields like ID, hash, timestamp, message, and author.

- **Repository Interfaces**: These interfaces define the database operations for each entity.
  - `CommitRepository`: Provides methods for CRUD (Create, Read, Update, Delete) operations for the `Commit` entity.

- **DTO (Data Transfer Object) Classes**: These classes represent data that is transferred between the web interface and the services.
  - `CommitDTO`: Represents a commit's data in a more user-friendly format.

- **Utility Classes**:
  - `MapperUtil`: Provides methods for converting between entity and DTO objects using the ModelMapper library.

- **`ApiResponse` Class**: Offers utility methods for generating HTTP responses with success or error messages.

## Running the Application

To run the Valven Candidate Task application, follow these steps:

1. Clone the project from the repository to your local machine.

2. Configure the application properties:
   - In the `application.properties` file, specify your GitHub and GitLab credentials, such as `github.username`, `github.oauthAccessToken`, `gitlab.username`, and `gitlab.oauthAccessToken`.

3. Set up a PostgreSQL database. You can use Docker to run a PostgreSQL container with the following command:
```bash
docker run -d --name postgres-container -e POSTGRES_PASSWORD=password -e POSTGRES_USER=username -e POSTGRES_DB=commit_tracker -p 5432:5432 postgres:latest
```
or

```bash
docker-compose up
```
4. Build and run the application:

```bash
./mvnw spring-boot:run
```

5. Access the application by navigating to `http://localhost:8080/developer` in your web browser.

## Using the Application

Once the application is running, you can interact with it through a web interface. The following functionalities are available:

- **Developer Information**: Access `http://localhost:8080/developer` to view developer information, including GitHub and GitLab usernames and email addresses.

- **Commit List**: Navigate to `http://localhost:8080/commitList` to see a list of commits from both GitHub and GitLab repositories.

- **Commit Detail**: Click on a specific commit from the commit list to view detailed information about that commit.

## Error Handling

The application includes error handling that returns appropriate HTTP response status codes and error messages when errors occur.

## Dependencies

The project utilizes the following libraries and frameworks:

- Spring Boot: Provides a robust and scalable platform for building Java applications.
- ModelMapper: Simplifies object-to-object mapping between entities and DTOs.
- GitHub API: Used to retrieve commits from GitHub repositories.
- GitLab API: Utilized to fetch commits from GitLab repositories.
- PostgreSQL: The chosen database for storing commit information.

## Conclusion

The Valven Candidate Task is a Java-based web application designed to fetch commits from GitHub and GitLab repositories, store them in a PostgreSQL database, and provide a user-friendly web interface to access and view this data. The project includes a well-structured codebase with distinct components for separation of concerns, error handling, and utility functions.

If you have any further questions or need assistance with the project, please don't hesitate to reach out for support.