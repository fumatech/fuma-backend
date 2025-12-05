package com.backend.Entity;


public class DatabaseRequest {
        private String dbName;
        private String dbUsername;
        private String dbPassword;

        // Getters and Setters
        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getDbUsername() {
            return dbUsername;
        }

        public void setDbUsername(String dbUsername) {
            this.dbUsername = dbUsername;
        }

        public String getDbPassword() {
            return dbPassword;
        }

        public void setDbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
        }
 
}
