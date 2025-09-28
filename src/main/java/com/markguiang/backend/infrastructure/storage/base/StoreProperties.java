package com.markguiang.backend.infrastructure.storage.base;

public class StoreProperties {
    private String projectId;
    private String bucket;
    private String sa;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    // Manual Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String bucket;
        private String sa;

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        public Builder sa(String sa) {
            this.sa = sa;
            return this;
        }

        public StoreProperties build() {
            StoreProperties props = new StoreProperties();
            props.setProjectId(projectId);
            props.setBucket(bucket);
            props.setSa(sa);
            return props;
        }
    }
}
