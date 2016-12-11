package com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase;

import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryBook;
import com.example.android.bookrentalsystemforcsumblibrary.R;

import java.text.NumberFormat;

public class NewBook extends AppCompatActivity {

    String title;
    String author;
    String isbn;
    String fee;
    double dFee;
    String confirmationMessage;

    AlertDialog errorDialog;
    AlertDialog confirmationDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        errorDialog = buildErrorBox();
        confirmationDialog = buildConfirmBox();
    }

    private boolean checkEmptyField(int viewID){
        TextView view = ((TextView)findViewById(viewID));
        String text = view.getText().toString();
        if(text.length() < 1){
            Toast.makeText(getBaseContext(), view.getContentDescription().toString() + " Cannot be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private AlertDialog buildErrorBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Information Entered is Invalid!").setTitle("ERROR");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        return builder.create();
    }

    private AlertDialog buildConfirmBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createBook();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
        return builder.create();
    }

    private LibraryBook createLibraryBook(){
        return new LibraryBook(title, author, isbn, dFee);
    }

    private void updateStringValues(){
        title = ((TextView)findViewById(R.id.title_field)).getText().toString();
        author = ((TextView)findViewById(R.id.author_field)).getText().toString();
        isbn = ((TextView)findViewById(R.id.isbn_field)).getText().toString();
        fee = ((TextView)findViewById(R.id.fee_field)).getText().toString();

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        try {
            dFee = Double.parseDouble(fee);
            fee = nf.format(dFee);
        } catch(NumberFormatException e) {
            fee = "";
        }
        confirmationMessage = "Is this following information correct?\n" +
                "Title: " + title +
                "\nAuthor: " + author +
                "\nISBN: " + isbn +
                "\nFee: " + fee;
    }

    public void createBook(View view) {
        updateStringValues();
        confirmationDialog.setMessage(confirmationMessage);
        confirmationDialog.show();
    }
    public void createBook(){
        if(!(checkEmptyField(R.id.title_field) && checkEmptyField(R.id.author_field) && checkEmptyField(R.id.isbn_field) && checkEmptyField(R.id.fee_field))){
            errorDialog.show();
            return;
        }

        SystemDataBase db = new SystemDataBase(this);
        LibraryBook book = createLibraryBook();
        try {
            db.insertBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Book Already Exists!", Toast.LENGTH_SHORT).show();
            errorDialog.show();
            return;
        }

        Toast.makeText(getBaseContext(), "Book Added Successfully!", Toast.LENGTH_SHORT).show();

        finish();
    }

}
