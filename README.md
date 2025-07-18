# GitHub Proxy API Project

A simple Spring Boot application that proxies GitHub's public REST API to fetch repositories (which are not forks) and their branches for a given user.

## Technologies

- Java 21
- Spring Boot 3.5.3
- RestTemplate
- JUnit 5
- Maven

## Installation and Launch

Requirements:
- Java 21+
- Maven

Before launching the application, set a personal GitHub token as an environment variable named `GITHUB_TOKEN`.
[How to generate token?](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)

> ℹ️ Note: If you don't want to use authentication, you can remove or comment the `app.github.token=${GITHUB_TOKEN}` line from `application.properties` file.

Build and run the project:
```bash
mvn clean package
java -jar target/githubapi-0.0.1-SNAPSHOT.jar
```

The application will start at: http://localhost:8080

## Example request

```
GET http://localhost:8080/repositories/example-user
```

## Example response

```json
{
    "ownerLogin": "ArdianM90",
    "repositories": [
        {
            "name": "battleships",
            "branches": [
                {
                    "name": "game-summary",
                    "lastCommitSha": "192a101b4270c745027da1a3bbc482dfcf46ac92"
                },
                {
                    "name": "main",
                    "lastCommitSha": "05370dac12dd94cf8643d62b0093ebd78289070d"
                }
            ]
        },
        {
            "name": "github-proxy-api",
            "branches": [
                {
                    "name": "master",
                    "lastCommitSha": "b213efe1644a06bb6c9b5a8f2f9e58db8ce26ccb"
                }
            ]
        }
    ]
}
```

## Error responses
| Status code | Description                                                                         | 
|-------------|-------------------------------------------------------------------------------------|
| 400         | Username must not be blank.                                                         | 
| 401         | Unauthorized – bad GitHub credentials.                                              |
| 403         | GitHub API rate limit exceeded (60 requests per hour for unauthenticated requests). | 
| 404         | User not found.                                                                     |
| 500         | Unexpected error.                                                                   |

## Integration Test

The project includes an integration test for the happy path, using JUnit 5 and Spring Boot Test to verify the full request flow, including communication with the real GitHub's API.
To run the integration test:

```bash
mvn verify
```

## Project structure

```bash
\githubapi
 |   AppConfig.java
 |   GithubApiApplication.java
 |   GitHubClient.java
 |   RepositoryController.java
 |   RepositoryService.java
 |
 \---dto
      BranchDto.java
      ErrorResponse.java
      RepositoryDto.java
      UserRepositoriesResponse.java
```

## Notes

- The app communicates with GitHub's public REST API (with or without authentication). 
- GitHub limits unauthenticated requests up to 60 requests per hour ([GitHub documentation](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28)).
- In case GitHub rate limits (403), a proper message is returned.
- The app follows clean code principles and keeps a flat structure to maintain readabilty.
