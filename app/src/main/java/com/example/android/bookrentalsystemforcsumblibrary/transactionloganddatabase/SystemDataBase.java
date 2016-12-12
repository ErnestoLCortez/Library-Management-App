package com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.database.SQLException;

import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryBook;
import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryUser;
import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LogConverter;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.id.list;

/**
 * Created by atomi on 12/1/2016.
 */

public class SystemDataBase {

    /************************
     * Database Constants
     ************************/
    public static final String DB_NAME = "library.db";
    public static final int    DB_VERSION = 1;

    /************************
     * User Table Constants
     ************************/
    public static final String USER_TABLE = "user";

    public static final String USER_ID = "_id";
    public static final int    USER_ID_COL = 0;

    public static final String USER_NAME = "user_name";
    public static final int    USER_NAME_COL = 1;

    public static final String USER_PASSWORD = "user_password";
    public static final int    USER_PASSWORD_COL = 2;

    public static final String USER_ADMIN = "admin";
    public static final int    USER_ADMIN_COL = 3;

    /************************
     * Book Table Constants
     ************************/
    public static final String BOOK_TABLE = "book";

    public static final String BOOK_ID = "_id";
    public static final int    BOOK_ID_COL = 0;

    public static final String BOOK_TITLE = "book_title";
    public static final int    BOOK_TITLE_COL = 1;

    public static final String BOOK_AUTHOR = "book_author";
    public static final int    BOOK_AUTHOR_COL = 2;

    public static final String BOOK_ISBN = "book_isbn";
    public static final int    BOOK_ISBN_COL = 3;

    public static final String BOOK_FEE = "book_fee";
    public static final int    BOOK_FEE_COL = 4;

    /************************
     * Transaction Table Constants
     ************************/
    public static final String TRANSACTION_TABLE = "main_transaction";

    public static final String TRANSACTION_ID = "_id";
    public static final int    TRANSACTION_ID_COL = 0;

    public static final String TRANSACTION_TYPE = "transaction_type";
    public static final int    TRANSACTION_TYPE_COL = 1;

    public static final String TRANSACTION_USER = "user_name";
    public static final int    TRANSACTION_USER_COL = 2;

    public static final String TRANSACTION_DATE = "current_date";
    public static final int    TRANSACTION_DATE_COL = 3;

    /************************
     * Cancel Transaction Table Constants
     ************************/

    public static final String CANCEL_TABLE = "cancel_transaction";

    public static final String CANCEL_ID = "_id";
    public static final int    CANCEL_ID_COL = 0;

    public static final String CANCEL_HOLD_ID = "hold_id";
    public static final int    CANCEL_HOLD_ID_COL = 1;

    public static final String CANCEL_TITLE = "book_title";
    public static final int    CANCEL_TITLE_COL = 2;

    public static final String CANCEL_PICKUP = "pickup_date";
    public static final int    CANCEL_PICKUP_COL = 3;

    public static final String CANCEL_RETURN = "return_date";
    public static final int    CANCEL_RETURN_COL = 4;

    /************************
     * Hold Transaction Table Constants
     ************************/

    public static final String HOLD_TABLE = "hold_transaction";

    public static final String HOLD_ID = "_id";
    public static final int    HOLD_ID_COL = 0;

    public static final String HOLD_RESERVATION = "reservation_number";
    public static final int    HOLD_RESERVATION_COL = 1;

    public static final String HOLD_TOTAL = "transaction_total";
    public static final int    HOLD_TOTAL_COL = 2;

    public static final String HOLD_TITLE = "book_title";
    public static final int    HOLD_TITLE_COL = 3;

    public static final String HOLD_PICKUP = "pickup_date";
    public static final int    HOLD_PICKUP_COL = 4;

    public static final String HOLD_RETURN = "return_date";
    public static final int    HOLD_RETURN_COL = 5;



    /************************
     * CREATE and DROP TABLE SQL Statements
     ************************/
    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + " (" +
                    USER_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT NOT NULL UNIQUE, " +
                    USER_PASSWORD + " TEXT  NOT NULL, " +
                    USER_ADMIN + " INTEGER  NOT NULL);";

    public static final String CREATE_BOOK_TABLE =
            "CREATE TABLE " + BOOK_TABLE + " (" +
                    BOOK_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BOOK_TITLE   + " TEXT NOT NULL UNIQUE, " +
                    BOOK_AUTHOR  + " TEXT NOT NULL, " +
                    BOOK_ISBN    + " TEXT NOT NULL UNIQUE, " +
                    BOOK_FEE     + " REAL NOT NULL);";

    public static final String CREATE_TRANSACTION_TABLE =
            "CREATE TABLE " + TRANSACTION_TABLE + " (" +
                    TRANSACTION_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TRANSACTION_TYPE  + " TEXT NOT NULL, " +
                    TRANSACTION_USER  + " TEXT NOT NULL, " +
                    TRANSACTION_DATE  + " TEXT NOT NULL);";

    public static final String CREATE_CANCEL_TABLE =
            "CREATE TABLE " + CANCEL_TABLE + " (" +
                    CANCEL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CANCEL_HOLD_ID   + " INTEGER NOT NULL, " +
                    CANCEL_TITLE   + " TEXT NOT NULL, " +
                    CANCEL_PICKUP  + " TEXT NOT NULL, " +
                    CANCEL_RETURN    + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + CANCEL_ID + ") REFERENCES " + TRANSACTION_TABLE + "(" + TRANSACTION_ID + "), " +
    "FOREIGN KEY (" + CANCEL_HOLD_ID + ") REFERENCES " + HOLD_TABLE + "(" + HOLD_ID + "));";

    public static final String CREATE_HOLD_TABLE =
            "CREATE TABLE " + HOLD_TABLE + " (" +
                    HOLD_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HOLD_RESERVATION   + " INTEGER NOT NULL, " +
                    HOLD_TOTAL  + " TEXT NOT NULL, " +
                    HOLD_TITLE  + " TEXT NOT NULL, " +
                    HOLD_PICKUP  + " TEXT NOT NULL, " +
                    HOLD_RETURN  + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + HOLD_ID + ") REFERENCES " + TRANSACTION_TABLE + "(" + TRANSACTION_ID + "));";

    public static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE;

    public static final String DROP_BOOK_TABLE =
            "DROP TABLE IF EXISTS " + BOOK_TABLE;

    public static final String DROP_TRANSACTION_TABLE =
            "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;

    public static final String DROP_CANCEL_TABLE =
            "DROP TABLE IF EXISTS " + CANCEL_TABLE;

    public static final String DROP_HOLD_TABLE =
            "DROP TABLE IF EXISTS " + HOLD_TABLE;


    /************************
     * Database and Database Helper Objects
     ************************/
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    /************************
     * Constructor
     ************************/
    public SystemDataBase(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    /************************
     * Private Methods
     ************************/
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    private static LibraryUser getUserFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                LibraryUser user = new LibraryUser(
                        cursor.getString(USER_NAME_COL),
                        cursor.getString(USER_PASSWORD_COL),
                        cursor.getInt(USER_ADMIN_COL)
                );
                return user;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    private static LogConverter getHoldLogFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Log.e("Test1", cursor.getString(1));
                Log.e("Test2", cursor.getString(2));
                Log.e("Test3", cursor.getString(3));
                Log.e("Test4", cursor.getString(4));
                Log.e("Test5", cursor.getString(5));
                Log.e("Test6", cursor.getString(6));
                Log.e("Test7", cursor.getString(7));
                Log.e("Test8", cursor.getString(8));

                return new LogConverter(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(4),
                        cursor.getDouble(5)

                );

            }
            catch(Exception e) {
                return null;
            }
        }
    }

    private static LogConverter getCancelLogFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Log.e("Test1", cursor.getString(1));
                Log.e("Test2", cursor.getString(2));
                Log.e("Test3", cursor.getString(3));
                Log.e("Test4", cursor.getString(4));
                Log.e("Test5", cursor.getString(5));
                Log.e("Test6", cursor.getString(6));
                Log.e("Test7", cursor.getString(7));

                return new LogConverter(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );

            }
            catch(Exception e) {
                return null;
            }
        }
    }
    /************************
     * Public Methods
     ************************/

    public long insertUser(LibraryUser user) throws SQLException{
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getUserName());
        cv.put(USER_PASSWORD, user.getUserPassword());
        cv.put(USER_ADMIN, user.getIsAdmin());

        long rowID;
        this.openWriteableDB();
        try {
            rowID = db.insertOrThrow(USER_TABLE, null, cv);
        } catch (SQLException e) {
            throw e;
        }

        this.closeDB();

        return rowID;
    }

    public LibraryUser getUser(String userName, String userPassword) {
        String where = USER_NAME + "= ? AND " + USER_PASSWORD + "= ?";
        String[] whereArgs = { userName, userPassword };

        this.openReadableDB();
        Cursor cursor = db.query(USER_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        LibraryUser user = getUserFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return user;
    }

    public long insertBook(LibraryBook book) throws SQLException{
        ContentValues cv = new ContentValues();
        cv.put(BOOK_TITLE, book.getTitle());
        cv.put(BOOK_AUTHOR, book.getAuthor());
        cv.put(BOOK_ISBN, book.getIsbn());
        cv.put(BOOK_FEE, book.getFee());

        long rowID;
        this.openWriteableDB();
        try {
            rowID = db.insertOrThrow(BOOK_TABLE, null, cv);
        } catch (SQLException e) {
            throw e;
        }

        this.closeDB();

        return rowID;
    }

    public long insertLog(LogConverter log)throws SQLException{
        ContentValues cv = new ContentValues();
        ContentValues cv2 = null;
        if(log.getTransactionType().equalsIgnoreCase("new account"))
            log.putCV(cv);
        else{
            cv2 = new ContentValues();
            log.putCV(cv, cv2);
        }


        long rowID;
        this.openWriteableDB();
        try {
            rowID = db.insertOrThrow(TRANSACTION_TABLE, null, cv);
        } catch (SQLException e) {
            throw e;
        }

        if(log.getTransactionType().equalsIgnoreCase("Place Hold")){
            cv2.put(HOLD_ID, rowID);
            try {
                db.insertOrThrow(HOLD_TABLE, null, cv2);
            } catch (SQLException e) {
                throw e;
            }
        }

        if(log.getTransactionType().equalsIgnoreCase("Cancel Hold")){
            cv2.put(CANCEL_ID, rowID);
            try {
                db.insertOrThrow(CANCEL_TABLE, null, cv2);
            } catch (SQLException e) {
                throw e;
            }
        }

        this.closeDB();

        return rowID;
    }

    public ArrayList<String> getHoldLogs(String userName) {
        ArrayList<String> list = new ArrayList<>();
        String where = TRANSACTION_USER + "= ?";
        String[] whereArgs = {userName};

        this.openReadableDB();

        String tableQuery = "(SELECT * " +
                "FROM main_transaction " +
                "NATURAL JOIN hold_transaction t1 " +
                "LEFT JOIN cancel_transaction t2 ON t1._id = t2.hold_id " +
                "WHERE t2.hold_id IS NULL)";


        Cursor cursor = db.query(tableQuery,
                null, where, whereArgs, null, null, null);

        while(cursor.moveToNext()){
            list.add(cursor.getInt(0) + "\t" + getHoldLogFromCursor(cursor).toString());
        }

        if(cursor != null)
            cursor.close();
        closeDB();

        if(list.isEmpty())
            list.add("No reservations found!");

        return list;

    }
    public ArrayList<String> getLogs() {
        ArrayList<String> list = new ArrayList<>();
        openReadableDB();
        Cursor cursor = db.query(TRANSACTION_TABLE,
                null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            if(cursor.getString(TRANSACTION_TYPE_COL).equalsIgnoreCase("New Account")) {
                LogConverter temp = new LogConverter(
                        cursor.getString(TRANSACTION_TYPE_COL),
                        cursor.getString(TRANSACTION_USER_COL),
                        cursor.getString(TRANSACTION_DATE_COL));
                list.add(temp.toString());
            } else if(cursor.getString(TRANSACTION_TYPE_COL).equalsIgnoreCase("Place Hold")){
                LogConverter temp = getLog(cursor.getString(TRANSACTION_ID_COL));
                list.add(temp.toString());
            } else {
                LogConverter temp = getCLog(cursor.getString(TRANSACTION_ID_COL));
                list.add(temp.toString());
            }
        }
        if(cursor != null)
            cursor.close();
        closeDB();

        return list;
    }

    public LogConverter getLog(String iD){
        if(db == null)
            openReadableDB();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM main_transaction " +
                "NATURAL JOIN hold_transaction " +
                "WHERE _id = " + iD, null);
        cursor.moveToFirst();
        return getHoldLogFromCursor(cursor);
    }

    public LogConverter getCLog(String iD){
        if(db == null)
            openReadableDB();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM main_transaction " +
                "NATURAL JOIN cancel_transaction " +
                "WHERE _id = " + iD, null);
        cursor.moveToFirst();
        return getCancelLogFromCursor(cursor);
    }

    public ArrayList<String> getBooks(String pickupTime, String returnTime) {
        ArrayList<String> list = new ArrayList<>();
        openReadableDB();

        String holdTransactions = "SELECT * " +
                "FROM hold_transaction t1 " +
                "LEFT JOIN cancel_transaction t2 ON t1._id = t2.hold_id " +
                "WHERE t2.hold_id IS NULL";

        String invalidBooks = "SELECT * " +
                "FROM (" + holdTransactions + ") " +
                "WHERE pickup_date BETWEEN '" + pickupTime + "' AND '" + returnTime+ "' OR " +
                "return_date BETWEEN '" + pickupTime + "' AND '" + returnTime + "'";

        String validBooks = "SELECT t3.book_title, t3.book_author, t3.book_isbn, t3.book_fee " +
                "FROM " + BOOK_TABLE + " as t3 " +
                "LEFT JOIN (" + invalidBooks + ") as t4 ON t3.book_title = t4.book_title " +
                "WHERE t4.book_title IS NULL";

        Cursor cursor = db.rawQuery(validBooks, null);

        while(cursor.moveToNext()) {
            LibraryBook temp = new LibraryBook(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3));
            list.add(temp.toString());
        }

        if(cursor != null)
            cursor.close();
        closeDB();

        if(list.isEmpty())
            list.add("No books available for this time range!");
        return list;
    }

    /************************
     * Database Helper Class
     ************************/
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_BOOK_TABLE);
            db.execSQL(CREATE_TRANSACTION_TABLE);
            db.execSQL(CREATE_CANCEL_TABLE);
            db.execSQL(CREATE_HOLD_TABLE);

            // insert default lists
            db.execSQL("INSERT INTO user(user_name, user_password, admin) VALUES('a@lice5', '@csit100', 0)");
            db.execSQL("INSERT INTO user(user_name, user_password, admin) VALUES('$brian7', '123abc##', 0)");
            db.execSQL("INSERT INTO user(user_name, user_password, admin) VALUES('!chris12!', 'CHRIS12!!', 0)");
            db.execSQL("INSERT INTO user(user_name, user_password, admin) VALUES('!admin2', '!admin2', 1)");

            db.execSQL("INSERT INTO book(book_title, book_author, book_isbn, book_fee) VALUES('Hot Java', 'S. Narayanan', '123-ABC-101', 0.05)");
            db.execSQL("INSERT INTO book(book_title, book_author, book_isbn, book_fee) VALUES('Fun Java', 'Y. Byun', 'ABCDEF-09', 1.00)");
            db.execSQL("INSERT INTO book(book_title, book_author, book_isbn, book_fee) VALUES('Algorithm for Java', 'K. Alice', 'CDE-777-123', 0.25)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("TRANSACTION LOGS", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(SystemDataBase.DROP_BOOK_TABLE);
            db.execSQL(SystemDataBase.DROP_USER_TABLE);
            db.execSQL(SystemDataBase.DROP_TRANSACTION_TABLE);
            db.execSQL(SystemDataBase.DROP_CANCEL_TABLE);
            db.execSQL(SystemDataBase.DROP_HOLD_TABLE);
            onCreate(db);
        }
    }


}
