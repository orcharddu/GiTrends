package com.gitrends.api.TrendingAPI;

import java.util.List;

public class TrendingModel<T extends Trending> {
    private String site;
    private List<T> trendingList;

    public String getSite() {
        return site;
    }

    public TrendingModel<T> setSite(String site) {
        this.site = site;
        return this;
    }

    public List<T> getTrendingList() {
        return trendingList;
    }

    public TrendingModel<T> setTrendingList(List<T> trendingList) {
        this.trendingList = trendingList;
        return this;
    }

    public static class GitHubModel implements Trending {
        private String site;
        private String owner;
        private String repo;
        private String description;
        private String url;
        private int stars;
        private int starsToday;
        private int forks;

        @Override
        public String getSite() {
            return site;
        }

        public GitHubModel setSite(String site) {
            this.site = site;
            return this;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        public GitHubModel setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        @Override
        public String getRepo() {
            return repo;
        }

        public GitHubModel setRepo(String repo) {
            this.repo = repo;
            return this;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public GitHubModel setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public String getUrl() {
            return url;
        }

        public GitHubModel setUrl(String url) {
            this.url = url;
            return this;
        }

        public int getStars() {
            return stars;
        }

        public GitHubModel setStars(int stars) {
            this.stars = stars;
            return this;
        }

        public int getStarsToday() {
            return starsToday;
        }

        public GitHubModel setStarsToday(int starsToday) {
            this.starsToday = starsToday;
            return this;
        }

        public int getForks() {
            return forks;
        }

        public GitHubModel setForks(int forks) {
            this.forks = forks;
            return this;
        }
    }


    public static class GitLabModel implements Trending {
        private String site;
        private String owner;
        private String repo;
        private String description;
        private String url;
        private int stars;
        private int forks;

        @Override
        public String getSite() {
            return site;
        }

        public GitLabModel setSite(String site) {
            this.site = site;
            return this;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        public GitLabModel setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        @Override
        public String getRepo() {
            return repo;
        }

        public GitLabModel setRepo(String repo) {
            this.repo = repo;
            return this;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public GitLabModel setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public String getUrl() {
            return url;
        }

        public GitLabModel setUrl(String url) {
            this.url = url;
            return this;
        }

        public int getStars() {
            return stars;
        }

        public GitLabModel setStars(int stars) {
            this.stars = stars;
            return this;
        }

        public int getForks() {
            return forks;
        }

        public GitLabModel setForks(int forks) {
            this.forks = forks;
            return this;
        }
    }
}
