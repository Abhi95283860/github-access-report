package com.example.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class GitHubClient {

    @Value("${github.token}")
    private String token;

    private final String BASE_URL = "https://api.github.com";

    private RestTemplate restTemplate = new RestTemplate();

    // 🔹 Get repositories of organization
    public String getRepositories(String org) {
        String url = BASE_URL + "/orgs/" + org + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    // 🔹 UPDATED: Get contributors instead of collaborators
    public String getCollaborators(String owner, String repo) {

        // ✅ CHANGE IS HERE
        String url = BASE_URL + "/repos/" + owner + "/" + repo + "/contributors";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}