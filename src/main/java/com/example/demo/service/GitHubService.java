package com.example.demo.service;

import com.example.demo.client.GitHubClient;
import com.example.demo.model.UserRepoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Autowired
    private GitHubClient gitHubClient;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public List<UserRepoResponse> getUserRepoAccess(String org) {

        Map<String, List<String>> userRepoMap = new ConcurrentHashMap<>();

        // Step 1: Get all repositories
        String repoResponse = gitHubClient.getRepositories(org);
        JSONArray repos = new JSONArray(repoResponse);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Step 2: Parallel processing
        for (int i = 0; i < repos.length(); i++) {

            JSONObject repo = repos.getJSONObject(i);
            String repoName = repo.getString("name");

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                try {
                    String collabResponse = gitHubClient.getCollaborators(org, repoName);
                    JSONArray collaborators = new JSONArray(collabResponse);

                    for (int j = 0; j < collaborators.length(); j++) {
                        JSONObject user = collaborators.getJSONObject(j);
                        String username = user.getString("login");

                        userRepoMap.computeIfAbsent(username, k -> new ArrayList<>())
                                   .add(repoName);
                    }

                } catch (Exception e) {
                    System.out.println("Error processing repo: " + repoName);
                }

            }, executor);

            futures.add(future);
        }

        // Wait for all tasks
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Convert to response model
        return userRepoMap.entrySet()
                .stream()
                .map(entry -> new UserRepoResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}