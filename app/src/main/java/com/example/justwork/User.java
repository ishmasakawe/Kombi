package com.example.justwork;


    public class User {

        String homeAddress;
        String homePostCode;
        String name;
        String password;
        String studentNumber;
        String phoneNumber;
        String userDob;
        String university;
        Integer carbonBalance;
        String emailAddress;
        Integer carbonRank;

        Integer pRank;

        public User(String homeAddress, String homePostCode, String name, String password, String studentNumber, String phoneNumber, String userDob, String university, Integer carbonBalance, String emailAddress, Integer carbonRank, Integer pRank) {
            this.homeAddress = homeAddress;
            this.homePostCode = homePostCode;
            this.name = name;
            this.password = password;
            this.studentNumber = studentNumber;
            this.phoneNumber = phoneNumber;
            this.userDob = userDob;
            this.university = university;
            this.carbonBalance = carbonBalance;
            this.emailAddress = emailAddress;
            this.carbonRank = carbonRank;
            this.pRank = pRank;
        }

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }

        public String getHomePostCode() {
            return homePostCode;
        }

        public void setHomePostCode(String homePostCode) {
            this.homePostCode = homePostCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserDob() {
            return userDob;
        }

        public void setUserDob(String userDob) {
            this.userDob = userDob;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public Integer getCarbonBalance() {
            return carbonBalance;
        }

        public void setCarbonBalance(Integer carbonBalance) {
            this.carbonBalance = carbonBalance;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public Integer getCarbonRank() {
            return carbonRank;
        }

        public void setCarbonRank(Integer carbonRank) {
            this.carbonRank = carbonRank;
        }

        public Integer getpRank() {
            return pRank;
        }

        public void setpRank(Integer pRank) {
            this.pRank = pRank;
        }
    }
