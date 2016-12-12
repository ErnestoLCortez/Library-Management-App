package com.example.android.bookrentalsystemforcsumblibrary.helperobjects;


import android.content.ContentValues;
import android.icu.text.SimpleDateFormat;

import java.text.NumberFormat;
import java.util.Date;

import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.CANCEL_HOLD_ID;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.CANCEL_PICKUP;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.CANCEL_RETURN;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.CANCEL_TABLE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.CANCEL_TITLE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.HOLD_PICKUP;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.HOLD_RESERVATION;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.HOLD_RETURN;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.HOLD_TITLE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.HOLD_TOTAL;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_DATE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_TYPE;
import static com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase.TRANSACTION_USER;

public class LogConverter {

    String transactionType, userName, currentDate, bookTitle, pickupDate, returnDate;
    int reservationNumber;

    public int getHoldID() {
        return holdID;
    }

    public void setHoldID(int holdID) {
        this.holdID = holdID;
    }

    int holdID;
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

    public LogConverter(LogConverter mLog, int holdID){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.transactionType = "Cancel Hold";
        this.userName = mLog.userName;
        this.currentDate = sdfDate.format(new Date());
        this.bookTitle = mLog.bookTitle;
        this.pickupDate = mLog.pickupDate;
        this.returnDate = mLog.returnDate;
        this.reservationNumber = mLog.reservationNumber;
        this.transactionTotal = mLog.transactionTotal;
        this.holdID = holdID;
    }

    public void putCV(ContentValues cv){
        cv.put(TRANSACTION_TYPE, transactionType);
        cv.put(TRANSACTION_USER, userName);
        cv.put(TRANSACTION_DATE, currentDate);
    }

    public void putCV(ContentValues cv, ContentValues cv2){
        cv.put(TRANSACTION_TYPE, transactionType);
        cv.put(TRANSACTION_USER, userName);
        cv.put(TRANSACTION_DATE, currentDate);

        if(transactionType.equals("Place Hold")){
            cv2.put(HOLD_TITLE, bookTitle);
            cv2.put(HOLD_PICKUP, pickupDate);
            cv2.put(HOLD_RETURN, returnDate);
            cv2.put(HOLD_RESERVATION, reservationNumber);
            cv2.put(HOLD_TOTAL, transactionTotal);
        }
        if(transactionType.equals("Cancel Hold")) {
            cv2.put(CANCEL_HOLD_ID, holdID);
            cv2.put(CANCEL_TITLE, bookTitle);
            cv2.put(CANCEL_PICKUP, pickupDate);
            cv2.put(CANCEL_RETURN, returnDate);
        }
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
        String text = transactionType + "\nUSER: " + userName + " DATE: " + currentDate;
        if(transactionType.equalsIgnoreCase("Place Hold")){
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            text += "\nPICKUP: " + pickupDate + " RETURN: " + returnDate +
                    "\nBOOK: " + bookTitle + " RESERV#: " + reservationNumber +
            "\nTOTAL: " + nf.format(transactionTotal);
        }
        if(transactionType.equalsIgnoreCase("Cancel Hold")){
            text += "\nPICKUP: " + pickupDate + " RETURN: " + returnDate +
                    "\nBOOK: " + bookTitle;
        }

        return text;
    }
}
