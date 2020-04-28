package com.example.boxofficeapplication;

import java.io.Serializable;

public class movieRankList implements Serializable {
    //"audiCnt":"106491","audiInten":"-11865","audiChange":"-10","audiAcc":"1275180","scrnCnt":"1103","showCnt":"5203"
    String rnum;
    String rank;
    String movieName;
    String rankInten;
    String rankOldAndNew;
    String movieCd;
    String movieNm;
    String openDt;
    String salesAmt;
    String salesShare;
    String salesInten;
    String salesChange;
    String salesAcc;
    String audiCnt;
    String audiInten;
    String audiChange;
    String audiAcc;
    String scrnCnt;
    String showCnt;



    public String getRnum() {
        return rnum;
    }

    public String getRank() {
        return rank;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRankInten() {
        return rankInten;
    }

    public String getRankOldAndNew() {
        return rankOldAndNew;
    }

    public String getMovieCd() {
        return movieCd;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public String getSalesAmt() {
        return salesAmt;
    }

    public String getSalesShare() {
        return salesShare;
    }

    public String getSalesInten() {
        return salesInten;
    }

    public String getSalesChange() {
        return salesChange;
    }

    public String getSalesAcc() {
        return salesAcc;
    }

    public String getAudiCnt() {
        return audiCnt;
    }

    public String getAudiInten() {
        return audiInten;
    }

    public String getAudiChange() {
        return audiChange;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public String getScrnCnt() {
        return scrnCnt;
    }

    public String getShowCnt() {
        return showCnt;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setRankInten(String rankInten) {
        this.rankInten = rankInten;
    }

    public void setRankOldAndNew(String rankOldAndNew) {
        this.rankOldAndNew = rankOldAndNew;
    }

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public void setSalesAmt(String salesAmt) {
        this.salesAmt = salesAmt;
    }

    public void setSalesShare(String salesShare) {
        this.salesShare = salesShare;
    }

    public void setSalesInten(String salesInten) {
        this.salesInten = salesInten;
    }

    public void setSalesChange(String salesChange) {
        this.salesChange = salesChange;
    }

    public void setSalesAcc(String salesAcc) {
        this.salesAcc = salesAcc;
    }

    public void setAudiCnt(String audiCnt) {
        this.audiCnt = audiCnt;
    }

    public void setAudiInten(String audiInten) {
        this.audiInten = audiInten;
    }

    public void setAudiChange(String audiChange) {
        this.audiChange = audiChange;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public void setScrnCnt(String scrnCnt) {
        this.scrnCnt = scrnCnt;
    }

    public void setShowCnt(String showCnt) {
        this.showCnt = showCnt;
    }
}
