package me.krucii.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.krucii.api.domain.GitHub.Branch;
import me.krucii.api.domain.GitHub.Repo;
import me.krucii.api.domain.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    private final String USER_API = "https://api.github.com/users/";
    private final String REPO_API = "https://api.github.com/repos/";

    HttpHeaders headers = new HttpHeaders();

    public List<Repo> getAllRepositories(String user, RestTemplate restTemplate, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Repo> repositories = new ArrayList<>();
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        //headers.set("Authorization", "Bearer (token)");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        int page = 1;

        // getting repositories of user
        do {
            ResponseEntity<String> repoResponse = restTemplate.exchange(
                    USER_API + user + "/repos?per_page=100&page=" + page,
                    HttpMethod.GET, requestEntity, String.class); // send request

            List<Repo> reposInPage = objectMapper.readValue(repoResponse.getBody(), new TypeReference<>() {});
            repositories.addAll(reposInPage);

            // checking, if there's another page (https://docs.github.com/en/rest/guides/using-pagination-in-the-rest-api?apiVersion=2022-11-28)
            List<String> linkHeader = repoResponse.getHeaders().get("link");
            if (linkHeader == null || linkHeader.stream().noneMatch(n -> n.contains("rel=\"next\""))) {
                break;
            }
            page++;
        } while (true);

        return repositories;
    }


    public List<Response> processRepositories(String user, List<Repo> repositories, RestTemplate restTemplate, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Response> responseList = new ArrayList<>();

        for (Repo repository : repositories) {
            // checking if it's not a fork repo
            if (repository.getFork()) continue;

            String repoName = repository.getName();
            List<Branch> branches = getAllBranches(user, repoName, restTemplate, objectMapper);

            // adding repo to response json
            Response responseItem = new Response();
            responseItem.setName(repository.getName());
            responseItem.setLogin(repository.getOwner().getLogin());

            // adding branches for that repo to response json
            for (Branch branch : branches) {
                Response.Branch responseBranch = new Response.Branch();
                responseBranch.setName(branch.getName());
                responseBranch.setSha(branch.getCommit().getSha());
                responseItem.getBranches().add(responseBranch);
            }

            responseList.add(responseItem);
        }

        return responseList;
    }
    private List<Branch> getAllBranches(String user, String repoName, RestTemplate restTemplate, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Branch> branches = new ArrayList<>();
        int page = 1;

        headers.set("X-GitHub-Api-Version", "2022-11-28");
        //headers.set("Authorization", "Bearer (token)");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // getting branches of repository
        do {
            ResponseEntity<String> branchesResponse = restTemplate.exchange(
                    REPO_API + user + "/" + repoName + "/branches?per_page=100&page=" + page,
                    HttpMethod.GET, requestEntity, String.class); // send request

            List<Branch> branchesInPage = objectMapper.readValue(branchesResponse.getBody(), new TypeReference<>() {});
            branches.addAll(branchesInPage);

            // checking, if there's another page (https://docs.github.com/en/rest/guides/using-pagination-in-the-rest-api?apiVersion=2022-11-28)
            List<String> linkHeader = branchesResponse.getHeaders().get("link");
            boolean hasNextPage = linkHeader != null && linkHeader.stream().anyMatch(n -> n.contains("rel=\"next\""));
            if (!hasNextPage) {
                break;
            }

            page++;
        } while (true);

        return branches;
    }
}
