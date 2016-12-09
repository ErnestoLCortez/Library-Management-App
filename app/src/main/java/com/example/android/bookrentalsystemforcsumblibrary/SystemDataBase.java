package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.database.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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
     * Hold Transaction Table Constants
     ************************/

    public static final String CANCEL_TABLE = "cancel_transaction";

    public static final String CANCEL_ID = "_id";
    public static final int    CANCEL_ID_COL = 0;

    public static final String CANCEL_TITLE = "book_title";
    public static final int    CANCEL_TITLE_COL = 1;

    public static final String CANCEL_PICKUP = "pickup_date";
    public static final int    CANCEL_PICKUP_COL = 2;

    public static final String CANCEL_RETURN = "return_date";
    public static final int    CANCEL_RETURN_COL = 3;

    /************************
     * Cancel Transaction Table Constants
     ************************/

    public static final String HOLD_TABLE = "hold_transaction";

    public static final String HOLD_ID = "_id";
    public static final int    HOLD_ID_COL = 0;

    public static final String HOLD_RESERVATION = "reservation_number";
    public static final int    HOLD_RESERVATION_COL = 1;

    public static final String HOLD_TOTAL = "transaction_total";
    public static final int    HOLD_TOTAL_COL = 2;



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
                    BOOK_TITLE   + " TEXT NOT NULL, " +
                    BOOK_AUTHOR  + " TEXT NOT NULL, " +
                    BOOK_ISBN    + " TEXT NOT NULL, " +
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
                    CANCEL_TITLE   + " TEXT NOT NULL, " +
                    CANCEL_PICKUP  + " TEXT NOT NULL, " +
                    CANCEL_RETURN    + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + CANCEL_ID + ") REFERENCES " + TRANSACTION_TABLE + "(" + TRANSACTION_ID + "));";

    public static final String CREATE_HOLD_TABLE =
            "CREATE TABLE " + HOLD_TABLE + " (" +
                    HOLD_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HOLD_RESERVATION   + " TEXT NOT NULL, " +
                    HOLD_TOTAL  + " TEXT NOT NULL, " +
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
//    public ArrayList<List> getLists() {
//        ArrayList<List> lists = new ArrayList<List>();
//        openReadableDB();
//        Cursor cursor = db.query(LIST_TABLE,
//                null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            List list = new List();
//            list.setId(cursor.getInt(LIST_ID_COL));
//            list.setName(cursor.getString(LIST_NAME_COL));
//
//            lists.add(list);
//        }
//        if (cursor != null)
//            cursor.close();
//        closeDB();
//
//        return lists;
//    }
//
//
//    public List getList(String name) {
//        String where = LIST_NAME + "= ?";
//        String[] whereArgs = { name };
//
//        openReadableDB();
//        Cursor cursor = db.query(LIST_TABLE, null,
//                where, whereArgs, null, null, null);
//        List list = null;
//        cursor.moveToFirst();
//        list = new List(cursor.getInt(LIST_ID_COL),
//                cursor.getString(LIST_NAME_COL));
//        if (cursor != null)
//            cursor.close();
//        this.closeDB();
//
//        return list;
//    }
//
//    public ArrayList<Task> getTasks(String listName) {
//        String where = TASK_LIST_ID + "= ?";
//        int listID = getList(listName).getId();
//        String[] whereArgs = { Integer.toString(listID) };
//
//        this.openReadableDB();
//        Cursor cursor = db.query(TASK_TABLE, null,
//                where, whereArgs,
//                null, null, null);
//        ArrayList<Task> tasks = new ArrayList<Task>();
//        while (cursor.moveToNext()) {
//            tasks.add(getTaskFromCursor(cursor));
//        }
//        if (cursor != null)
//            cursor.close();
//        this.closeDB();
//
//        return tasks;
//    }
//
//    public Task getTask(int id) {
//        String where = TASK_ID + "= ?";
//        String[] whereArgs = { Integer.toString(id) };
//
//        this.openReadableDB();
//        Cursor cursor = db.query(TASK_TABLE,
//                null, where, whereArgs, null, null, null);
//        cursor.moveToFirst();
//        Task task = getTaskFromCursor(cursor);
//        if (cursor != null)
//            cursor.close();
//        this.closeDB();
//
//        return task;
//    }
//
//    private static Task getTaskFromCursor(Cursor cursor) {
//        if (cursor == null || cursor.getCount() == 0){
//            return null;
//        }
//        else {
//            try {
//                Task task = new Task(
//                        cursor.getInt(TASK_ID_COL),
//                        cursor.getInt(TASK_LIST_ID_COL),
//                        cursor.getString(TASK_NAME_COL),
//                        cursor.getString(TASK_NOTES_COL),
//                        cursor.getString(TASK_COMPLETED_COL),
//                        cursor.getString(TASK_HIDDEN_COL),
//                        cursor.getDouble(TASK_FEE_COL)
//                );
//                return task;
//            }
//            catch(Exception e) {
//                return null;
//            }
//        }
//    }
//
//    public long insertTask(Task task) {
//        ContentValues cv = new ContentValues();
//        cv.put(TASK_LIST_ID, task.getListId());
//        cv.put(TASK_NAME, task.getName());
//        cv.put(TASK_NOTES, task.getNotes());
//        cv.put(TASK_COMPLETED, task.getCompletedDate());
//        cv.put(TASK_HIDDEN, task.getHidden());
//        cv.put(TASK_FEE, task.getFee());
//
//        this.openWriteableDB();
//        long rowID = db.insert(TASK_TABLE, null, cv);
//        this.closeDB();
//
//        return rowID;
//    }
//
//    public int updateTask(Task task) {
//        ContentValues cv = new ContentValues();
//        cv.put(TASK_LIST_ID, task.getListId());
//        cv.put(TASK_NAME, task.getName());
//        cv.put(TASK_NOTES, task.getNotes());
//        cv.put(TASK_COMPLETED, task.getCompletedDate());
//        cv.put(TASK_HIDDEN, task.getHidden());
//        cv.put(TASK_FEE, task.getFee());
//
//        String where = TASK_ID + "= ?";
//        String[] whereArgs = { String.valueOf(task.getId()) };
//
//        this.openWriteableDB();
//        int rowCount = db.update(TASK_TABLE, cv, where, whereArgs);
//        this.closeDB();
//
//        return rowCount;
//    }
//
//    public int deleteTask(long id) {
//        String where = TASK_ID + "= ?";
//        String[] whereArgs = { String.valueOf(id) };
//
//        this.openWriteableDB();
//        int rowCount = db.delete(TASK_TABLE, where, whereArgs);
//        this.closeDB();
//
//        return rowCount;
//    }

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
