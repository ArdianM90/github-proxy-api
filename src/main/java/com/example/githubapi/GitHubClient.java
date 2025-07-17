package com.example.githubapi;

import com.example.githubapi.dto.BranchDto;
import com.example.githubapi.dto.RepositoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubClient {

    private static final String USER_REPOS_URL_PATTERN = "https://api.github.com/users/%s/repos";
    private static final String BRANCHES_URL_PATTERN = "https://api.github.com/repos/%s/%s/branches";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GitHubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryDto> getRepositories(String username) {
        String url = String.format(USER_REPOS_URL_PATTERN, username);
        String json = restTemplate.getForObject(url, String.class);
        List<RepositoryDto> repositories = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(json);
            for (JsonNode repoNode : root) {
                repositories.add(new RepositoryDto(repoNode.get("name").asText()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing repositories response from GitHub", e);
        }
        return repositories;
    }

    public List<BranchDto> getBranches(String username, String repository) {
        String url = String.format(BRANCHES_URL_PATTERN, username, repository);
        String json = restTemplate.getForObject(url, String.class);
        List<BranchDto> branches = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(json);
            for (JsonNode branchNode : root) {
                String name = branchNode.get("name").asText();
                String sha = branchNode.get("commit").get("sha").asText();
                branches.add(new BranchDto(name, sha));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing branch response from GitHub", e);
        }
        return branches;
    }
}
