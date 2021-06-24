package com.gitrends.api.AnalysisAPI;

import java.util.List;

public class AnalysisModel<T extends Analysis> {
    private String site;
    private String repo;
    private Label sentiment;
    private Emotion emotion;
    private List<Label> categories;
    private List<T> commentList;

    public String getSite() {
        return site;
    }

    public AnalysisModel<T> setSite(String site) {
        this.site = site;
        return this;
    }

    public String getRepo() {
        return repo;
    }

    public AnalysisModel<T> setRepo(String repo) {
        this.repo = repo;
        return this;
    }

    public Label getSentiment() {
        return sentiment;
    }

    public AnalysisModel<T> setSentiment(Label sentiment) {
        this.sentiment = sentiment;
        return this;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public AnalysisModel<T> setEmotion(Emotion emotion) {
        this.emotion = emotion;
        return this;
    }

    public List<Label> getCategories() {
        return categories;
    }

    public AnalysisModel<T> setCategories(List<Label> categories) {
        this.categories = categories;
        return this;
    }

    public List<T> getCommentList() {
        return commentList;
    }

    public AnalysisModel<T> setCommentList(List<T> commentList) {
        this.commentList = commentList;
        return this;
    }

    public static class Label {
        private String label;
        private double score;
        private List<String> labelSegments;

        public String getLabel() {
            return label;
        }

        public Label setLabel(String label) {
            this.label = label;
            return this;
        }

        public double getScore() {
            return score;
        }

        public Label setScore(double score) {
            this.score = score;
            return this;
        }

        public List<String> getLabelSegments() {
            return labelSegments;
        }

        public Label setLabelSegments(List<String> labelSegments) {
            this.labelSegments = labelSegments;
            return this;
        }
    }

    public static class Emotion {
        private double sadness;
        private double joy;
        private double fear;
        private double disgust;
        private double anger;

        public double getSadness() {
            return sadness;
        }

        public Emotion setSadness(double sadness) {
            this.sadness = sadness;
            return this;
        }

        public double getJoy() {
            return joy;
        }

        public Emotion setJoy(double joy) {
            this.joy = joy;
            return this;
        }

        public double getFear() {
            return fear;
        }

        public Emotion setFear(double fear) {
            this.fear = fear;
            return this;
        }

        public double getDisgust() {
            return disgust;
        }

        public Emotion setDisgust(double disgust) {
            this.disgust = disgust;
            return this;
        }

        public double getAnger() {
            return anger;
        }

        public Emotion setAnger(double anger) {
            this.anger = anger;
            return this;
        }
    }


    public static class Tweet implements Analysis {
        private String site;
        private String repo;
        private String text;
        private String createTime;
//        private double positive;
//        private double negative;

        @Override
        public String getSite() {
            return site;
        }

        public Tweet setSite(String site) {
            this.site = site;
            return this;
        }

        @Override
        public String getRepo() {
            return repo;
        }

        public Tweet setRepo(String repo) {
            this.repo = repo;
            return this;
        }

        @Override
        public String getText() {
            return text;
        }

        public Tweet setText(String text) {
            this.text = text;
            return this;
        }

        public String getCreateTime() {
            return createTime;
        }

        public Tweet setCreateTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

//        public double getPositive() {
//            return positive;
//        }
//
//        public Tweet setPositive(double positive) {
//            this.positive = positive;
//            return this;
//        }
//
//        public double getNegative() {
//            return negative;
//        }
//
//        public Tweet setNegative(double negative) {
//            this.negative = negative;
//            return this;
//        }
    }

}
