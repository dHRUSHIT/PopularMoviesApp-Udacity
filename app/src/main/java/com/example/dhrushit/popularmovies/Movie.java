package com.example.dhrushit.popularmovies;

/**
 * Created by dHRUSHIT on 10/7/2016.
 */
public class Movie {

    private String poster_path = null;
    private boolean adult = false;
    private String overview = null;
    private String releaseDate = null;
    private int[] genere_ids = null;
    private int id = 0;
    private String original_title = null;
    private String original_language = null;
    private String title = null;
    private String backdrop_path = null;
    private Double popularity = 0.0;
    private int vote_count = 0;
    private boolean video = false;
    private Double vote_avg = 0.0;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenere_ids() {
        return genere_ids;
    }

    public void setGenere_ids(int[] genere_ids) {
        this.genere_ids = genere_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Double getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(Double vote_avg) {
        this.vote_avg = vote_avg;
    }



}
