package com.example.android.bookrentalsystemforcsumblibrary.helperobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atomi on 12/10/2016.
 */

public class LibraryBook implements Parcelable {
    String title, author, isbn;
    double fee;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(author);
        out.writeString(isbn);
        out.writeDouble(fee);
    }

    public static final Parcelable.Creator<LibraryBook> CREATOR = new Parcelable.Creator<LibraryBook>() {
        public LibraryBook createFromParcel(Parcel in) {
            return new LibraryBook(in);
        }

        public LibraryBook[] newArray(int size) {
            return new LibraryBook[size];
        }
    };

    private LibraryBook(Parcel in) {
        title = in.readString();
        author = in.readString();
        isbn = in.readString();
        fee = in.readDouble();
    }

    public LibraryBook(String title, String author, String isbn, double fee ) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.fee = fee;
    }

    public LibraryBook(String bookInfo){
        String[] array = bookInfo.split("\\n");
        for(int i = 0; i < 4; i++){
            String[] tempArray = array[i].split("\\t");
            array[i] = tempArray[1];
        }
        title = array[0];
        author = array[1];
        isbn = array[2];
        fee = Double.parseDouble(array[3]);
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

    public String toString(){
        return "Book Title: \t" + title +
                "\nAuthor: \t" + author +
                "\nISBN: \t" + isbn +
                "\nFee/hr: \t" + fee;
    }
}
