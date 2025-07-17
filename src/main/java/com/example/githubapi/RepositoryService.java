package com.example.githubapi;

import com.example.githubapi.dto.BranchDto;
import com.example.githubapi.dto.RepositoryDto;
import com.example.githubapi.dto.UserRepositoriesResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    private final GitHubClient gitHubClient;

    public RepositoryService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public UserRepositoriesResponse getRepositoriesByUser(@NonNull String username) {
        UserRepositoriesResponse response = new UserRepositoriesResponse(username);
        List<RepositoryDto> repositories = gitHubClient.getRepositories(username);
        repositories.forEach(repo -> {
            List<BranchDto> branches = gitHubClient.getBranches(username, repo.getName());
            repo.setBranches(branches);
        });
        response.setRepositories(repositories);
        return response;
    }
}
