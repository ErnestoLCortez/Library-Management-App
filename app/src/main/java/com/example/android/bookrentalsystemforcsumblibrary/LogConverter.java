package com.example.android.bookrentalsystemforcsumblibrary;


import android.content.ContentValues;

import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_DATE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_TYPE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_USER;

public class LogConverter {

    String transactionType, userName, currentDate, bookTitle, pickupDate, returnDate;
    int reservationNumber;
    double transactionTotal;

    public LogConverter(String transactionType, String userName, String currentDate){
        this.transactionType = transactionType;
        this.userName = userName;
        this.currentDate = currentDate;
        this.bookTitle = null;
        this.pickupDate = null;
        this.returnDate = null;
        this.reservationNumber = 0;
        this.transactionTotal = 0;
    }

    public LogConverter(String transactionType, String userName, String currentDate, String bookTitle, String pickupDate, String returnDate){
        this.transactionType = transactionType;
        this.userName = userName;
        this.currentDate = currentDate;
        this.bookTitle = bookTitle;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.reservationNumber = 0;
        this.transactionTotal = 0;
    }

    public LogConverter(String transactionType, String userName, String currentDate, String bookTitle, String pickupDate, String returnDate, int reservationNumber, double transactionTotal){
        this.transactionType = transactionType;
        this.userName = userName;
        this.currentDate = currentDate;
        this.bookTitle = bookTitle;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.reservationNumber = reservationNumber;
        this.transactionTotal = transactionTotal;
    }

    public void putCV(ContentValues cv){
        cv.put(TRANSACTION_TYPE, transactionType);
        cv.put(TRANSACTION_USER, userName);
        cv.put(TRANSACTION_DATE, currentDate);

    }
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public double getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }

    public String toString(){
        return transactionType + "\nUSER: " + userName + " DATE: " + currentDate;
    }
}
