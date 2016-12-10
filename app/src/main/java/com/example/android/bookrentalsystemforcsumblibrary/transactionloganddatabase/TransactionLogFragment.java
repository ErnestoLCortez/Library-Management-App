package com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.bookrentalsystemforcsumblibrary.R;

public class TransactionLogFragment extends Fragment {
    private ArrayAdapter<String> logAdapter;

    public TransactionLogFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("Stuff", "Stuff");
        SystemDataBase db = new SystemDataBase(getContext());

        logAdapter =
                new ArrayAdapter<>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_log, // The name of the layout ID.
                        R.id.list_item_log_textview, // The ID of the textview to populate.
                        db.getLogs());

        View rootView = inflater.inflate(R.layout.fragment_transaction_log, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_log);
        listView.setAdapter(logAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String log = logAdapter.getItem(position);
               // Intent intent = new Intent(getActivity(), DetailActivity.class)
               //         .putExtra(Intent.EXTRA_TEXT, forecast);
               // startActivity(intent);
            }
        });

        return rootView;
    }

}
