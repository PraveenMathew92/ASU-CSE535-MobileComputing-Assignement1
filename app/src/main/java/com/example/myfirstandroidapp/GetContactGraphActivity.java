package com.example.myfirstandroidapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GetContactGraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_contact_graph);

        final TextView inputDate = findViewById(R.id.selected_date);

        final Button selectDateButton = (Button) findViewById(R.id.select_date_button);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = 2011;
                int month = 11;
                int day = 15;

                DatePickerDialog datePickerDialog = new DatePickerDialog(GetContactGraphActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                inputDate.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.subject_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button submit = (Button) findViewById(R.id.submit_subject_and_date);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DownloadFileService.class);
                intent.putExtra("subject_id", spinner.getSelectedItem().toString());
                intent.putExtra("date", inputDate.getText().toString());
                startService(intent);
            }
        });

        TableLayout tableLayout = (TableLayout) findViewById(R.id.contact_graph);
        TableRow tableRowHeader = new TableRow(this);
        {
            TextView textView = new TextView(this);
            textView.setText("subject_id");
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setPadding(10, 2, 10, 2);
            textView.setBackgroundResource(R.drawable.cell_shape);
            tableRowHeader.addView(textView);
        }
        for(String subject_id : getResources().getStringArray(R.array.subject_array)) {
            TextView textView = new TextView(this);
            textView.setText(subject_id);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setPadding(10, 2, 10, 2);
            textView.setBackgroundResource(R.drawable.cell_shape);
            tableRowHeader.addView(textView);
        }
        tableLayout.addView(tableRowHeader);

        for(int i = 1; i<5; i++) {
            TableRow tableRow = new TableRow(this);
            for(int j = 1; j<5; j++) {
                TextView textView = new TextView(this);
                textView.setText(Integer.toString(i*10 + j));
                textView.setPadding(10, 2, 10, 2);
                textView.setBackgroundResource(R.drawable.cell_shape);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
    }
}