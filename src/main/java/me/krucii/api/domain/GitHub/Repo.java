package me.krucii.api.domain.GitHub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// mapped json from https://api.github.com/users/{user}/repos
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

        @JsonProperty("name")
        private String name;

        @JsonProperty("owner")
        private Owner owner;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Owner {
                @JsonProperty("login")
                private String login;
                public String getLogin() {
                        return login;
                }
        }

        @JsonProperty("fork")
        private Boolean fork;

        public String getName() {
                return name;
        }

        public Owner getOwner() {
                return owner;
        }

        public Boolean getFork() {
                return fork;
        }
}
