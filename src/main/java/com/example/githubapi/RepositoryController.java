package com.example.githubapi;

import com.example.githubapi.dto.ErrorResponse;
import com.example.githubapi.dto.UserRepositoriesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Username must not be blank."));
        }
        try {
            UserRepositoriesResponse response = repositoryService.getRepositoriesByUser(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpClientErrorException.Unauthorized ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized – bad GitHub credentials."));
        } catch (HttpClientErrorException.Forbidden ex) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), "GitHub API rate limit exceeded."));
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found."));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error."));
        }
    }
}
