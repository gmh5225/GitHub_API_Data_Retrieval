package me.krucii.api.domain.GitHub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// mapped json from https://api.github.com/repos/{user}/{repo}/branches
@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    @JsonProperty("commit")
    private Commit commit;

    public Commit getCommit() {
        return commit;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Commit {
        @JsonProperty("sha")
        private String sha;

        public String getSha() {
            return sha;
        }
    }

}
