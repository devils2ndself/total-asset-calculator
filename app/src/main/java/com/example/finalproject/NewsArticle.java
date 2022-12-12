package com.example.finalproject;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NewsArticle {

    String title;
    String description;
    String url;
    Date posted;

    public NewsArticle(String title, String description, String url, String imageUrl, String dateString) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.posted = Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateString)));
    }
}
