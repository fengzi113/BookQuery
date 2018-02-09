package com.example.android.bookquery;

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mPublisher;

    public Book(String title, String publisher, String author) {
        this.mTitle = title;
        this.mPublisher = publisher;
        this.mAuthor = author;
    }

    public String getmAuthor() {
        return this.mAuthor;
    }

    public String getmTitle() {
        return this.mTitle;
    }

    public String getmPublisher() {
        return this.mPublisher;
    }

}
