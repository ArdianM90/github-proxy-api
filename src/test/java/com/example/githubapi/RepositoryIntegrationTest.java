package com.example.githubapi;

import com.example.githubapi.dto.RepositoryDto;
import com.example.githubapi.dto.UserRepositoriesResponse;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFetchRepositoriesAndBranchesFromGitHub() {
        // given
        String username = "ArdianM90";

        // when
        ResponseEntity<UserRepositoriesResponse> response = restTemplate
                .getForEntity("/repositories/" + username, UserRepositoriesResponse.class);
        Assumptions.assumeTrue(
                !response.getStatusCode().equals(HttpStatus.FORBIDDEN),
                "Test skipped due to GitHub API rate limit"
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(username, response.getBody().getOwnerLogin());
        assertNotNull(response.getBody().getRepositories());
        assertFalse(response.getBody().getRepositories().isEmpty());

        List<RepositoryDto> repositories = response.getBody().getRepositories();
        assertFalse(repositories.getFirst().getBranches().isEmpty());
    }
}