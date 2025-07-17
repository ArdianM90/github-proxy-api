package com.example.githubapi.dto;

import java.util.ArrayList;
import java.util.List;

public class RepositoryDto {
    private String name;
    private List<BranchDto> branches;

    public RepositoryDto(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchDto> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDto> branches) {
        this.branches = branches;
    }
}
