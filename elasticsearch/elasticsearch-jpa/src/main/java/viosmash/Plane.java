package viosmash;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Plane {

    private String authors;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String title;
    private String url;
    private String image_url;
    private String explanation;

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image_url='" + image_url + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
