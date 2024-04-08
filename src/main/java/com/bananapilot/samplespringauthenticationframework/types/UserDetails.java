package com.bananapilot.samplespringauthenticationframework.types;

import java.util.List;

public class UserDetails {

    private int id;
    private String username;
    private String password;
    private List<String> roles;

    public UserDetails(int id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDetails() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }


    public static final class UserDetailsBuilder {
        private int id;
        private String username;
        private String password;
        private List<String> roles;

        private UserDetailsBuilder() {
        }

        public static UserDetailsBuilder anUserDetails() {
            return new UserDetailsBuilder();
        }

        public UserDetailsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public UserDetailsBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDetailsBuilder withRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(id, username, password, roles);
        }
    }
}
