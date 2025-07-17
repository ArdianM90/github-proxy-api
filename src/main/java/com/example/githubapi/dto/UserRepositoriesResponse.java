package com.example.githubapi.dto;

import java.util.List;

public class UserRepositoriesResponse {
    private String ownerLogin;
    private List<RepositoryDto> repositories;

    public UserRepositoriesResponse(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<RepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        this.repositories = repositories;
    }
}
