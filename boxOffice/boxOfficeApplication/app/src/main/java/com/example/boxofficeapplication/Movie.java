package com.example.boxofficeapplication;


import java.io.Serializable;

public class Movie implements Serializable {
    String title;  //제목
    String pubDate; //제작년도
    String director; //검색 결과 영화의 감독이다.
    String actor;   //검색 결과 영화의 출연 배우이다.
    String userRating; //검색 결과 영화에 대한 유저들의 평점이다.
    String link; //검색 결과 영화의 하이퍼텍스트 link를 나타낸다.

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public Movie(){

    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
