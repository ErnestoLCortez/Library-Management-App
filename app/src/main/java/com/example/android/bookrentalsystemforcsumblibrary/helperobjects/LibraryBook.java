package com.example.android.bookrentalsystemforcsumblibrary.helperobjects;

/**
 * Created by atomi on 12/10/2016.
 */

public class LibraryBook {
    String title, author, isbn;
    double fee;

    public LibraryBook(String title, String author, String isbn, double fee ) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.fee = fee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
