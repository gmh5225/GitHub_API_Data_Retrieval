package me.krucii.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.krucii.api.domain.GitHub.Repo;
import me.krucii.api.domain.Response;
import me.krucii.api.service.ApiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ApiController {
    private final ApiService apiService = new ApiService();

    @GetMapping(path = "/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Response>> getUser(@PathVariable String user) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        // getting repositories of user, which are not forks
        List<Repo> repositories = apiService.getAllRepositories(user, restTemplate, objectMapper);

        // getting branches of repositories and their latest commit sha
        List<Response> response = apiService.processRepositories(user, repositories, restTemplate, objectMapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
