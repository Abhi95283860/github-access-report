package com.example.demo.controller;

import com.example.demo.service.GitHubService;
import com.example.demo.model.UserRepoResponse;  // ✅ ADD THIS
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    // GET API
    @GetMapping("/access-report")
    public List<UserRepoResponse> getAccessReport(@RequestParam String org) {
        return gitHubService.getUserRepoAccess(org);
    }
}