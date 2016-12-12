package com.example.android.bookrentalsystemforcsumblibrary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.DetailActivity;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;

import java.io.Serializable;
import java.net.InterfaceAddress;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission_group.CALENDAR;
import static com.example.android.bookrentalsystemforcsumblibrary.R.id.container;

public class PlaceHoldActivity extends AppCompatActivity {

    private int pickupYear, pickupMonth, pickupDay, dropoffYear, dropoffMonth, dropoffDay, currentYear, currentMonth, currentDay;
    private int pickupHour, pickupMinute, dropoffHour, dropoffMinute;
    private boolean isPickupDateSet, isPickupTimeSet, isDropoffDateSet, isDropoffTimeSet;
    private Date pickupDate, returnDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDay = cal.get(Calendar.DAY_OF_MONTH);

        isPickupDateSet = false;
        isPickupTimeSet = false;
        isDropoffDateSet = false;
        isDropoffTimeSet = false;

        updateTextViews();
    }

    public void datePicker(View view){
        int id = view.getId();
        DatePickerDialog.OnDateSetListener dialog = null;

        switch (id) {
            case R.id.pickup_date_button:
                dialog = pickupDateListener;
                break;
            case R.id.dropoff_date_button:
                dialog = dropoffDateListener;
                break;
        }

        createDatePicker(dialog, currentYear, currentMonth, currentDay).show();

    }

    public void timePicker(View view){
        int id = view.getId();
        TimePickerDialog.OnTimeSetListener dialog = null;

        switch (id) {
            case R.id.pickup_time_button:
                dialog = pickupTimeListener;
                break;
            case R.id.dropoff_time_button:
                dialog = dropoffTimeListener;
                break;
        }

        createTimePicker(dialog).show();

    }

    private DatePickerDialog createDatePicker(DatePickerDialog.OnDateSetListener x, int a, int b, int c){
        return new DatePickerDialog(this, x, a, b, c);
    }

    private TimePickerDialog createTimePicker(TimePickerDialog.OnTimeSetListener x){
        return new TimePickerDialog(this, x, 12, 0, false);
    }

    private void updateTextViews(){
        String pickupDate, dropoffDate;

        if(isPickupDateSet && isPickupTimeSet)
            pickupDate = formatDateTime(pickupYear, pickupMonth, pickupDay, pickupHour, pickupMinute);
        else
            pickupDate = "Not Selected";

        if(isDropoffDateSet && isDropoffTimeSet)
            dropoffDate = formatDateTime(dropoffYear, dropoffMonth, dropoffDay, dropoffHour, dropoffMinute);
        else
            dropoffDate = "Not Selected";


        ((TextView)findViewById(R.id.selected_pickup_date)).setText(pickupDate);
        ((TextView)findViewById(R.id.selected_dropoff_date)).setText(dropoffDate);

        if((isPickupDateSet && isPickupTimeSet)&&(isDropoffDateSet && isDropoffTimeSet)){
            compareDates(pickupDate, dropoffDate);
        }
    }

    private void compareDates(String date1, String date2){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate1 = formatter.parse(date1);
            Date mDate2 = formatter.parse(date2);
            compareDates(mDate1, mDate2);
        } catch (ParseException e){
            e.printStackTrace();
        }

    }
    private void compareDates(Date date1, Date date2){
        long diff = date2.getTime() - date1.getTime();
        long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(dayDiff > 7){
            Toast.makeText(getBaseContext(), "Greater than 7 days ", Toast.LENGTH_SHORT).show();
        }
        else{
            updateListView(date1, date2);
        }
    }


    private String formatDateTime(int year, int month, int day, int hrs, int min){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(year-1900, month, day, hrs, min);
        return sdfDate.format(now);
    }

    private void updateListView(final Date pickupDate, final Date returnDate){
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SystemDataBase db = new SystemDataBase(this);

        final ArrayAdapter<String> bookAdapter =
                new ArrayAdapter<>(
                        this, // The current context (this activity)
                        R.layout.list_item_log, // The name of the layout ID.
                        R.id.list_item_log_textview, // The ID of the textview to populate.
                        db.getBooks(sdfDate.format(pickupDate), sdfDate.format(returnDate)));

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.listview_book);
        listView.setAdapter(bookAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String book = bookAdapter.getItem(position);
                startNextActivity(book);
            }
        });
    }

    private void startNextActivity(String book){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class)
                .putExtra(Intent.EXTRA_TEXT, book)
                .putExtra("PICKUPDATE", pickupDate)
                .putExtra("RETURNDATE", returnDate);
        startActivity(intent);
        finish();
    }

    private DatePickerDialog.OnDateSetListener pickupDateListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayofMonth) {
            pickupYear = year;
            pickupMonth = monthOfYear;
            pickupDay = dayofMonth;
            isPickupDateSet = true;
            updateTextViews();
        }
    };

    private DatePickerDialog.OnDateSetListener dropoffDateListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayofMonth) {
            dropoffYear = year;
            dropoffMonth = monthOfYear;
            dropoffDay = dayofMonth;
            isDropoffDateSet = true;
            updateTextViews();
        }
    };

    private TimePickerDialog.OnTimeSetListener pickupTimeListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pickupHour = hourOfDay;
            pickupMinute = minute;
            isPickupTimeSet = true;
            updateTextViews();
        }
    };

    private TimePickerDialog.OnTimeSetListener dropoffTimeListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dropoffHour = hourOfDay;
            dropoffMinute = minute;
            isDropoffTimeSet = true;
            updateTextViews();
        }
    };
}
