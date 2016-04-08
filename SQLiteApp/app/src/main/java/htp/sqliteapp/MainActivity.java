package htp.sqliteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener {
    DatabaseHelper myDB;
    private EditText dateOfEvents;
    private DatePickerDialog dateOfEventPickerDialog;
    private SimpleDateFormat date;

    private Spinner spinnerLocation;
    private TextView textViewTime;
    private TimePicker timePicker;
    private int h;
    private int m;
    static final int TIME_DIALOG = 999;
    private EditText time;

    private Button btn;
    private EditText org;
    private EditText nameOfEvents;

    Calendar newDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        date = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        myDB = new DatabaseHelper(this);

        findViewsById();

        setDateTimeField();
        // time
        setCurrentTimeOnView();
        addButtonListener();
        AddData();

    }


    private void findViewsById() {
        textViewTime = (EditText) findViewById(R.id.txtTime);
        nameOfEvents = (EditText) findViewById(R.id.txtEvent);
        org = (EditText) findViewById(R.id.txtOrg);
        spinnerLocation = (Spinner) findViewById(R.id.spinner);
        btn = (Button) findViewById(R.id.btnSubmit);
        dateOfEvents = (EditText) findViewById(R.id.txtDate);
        dateOfEvents.setInputType(InputType.TYPE_NULL);
        dateOfEvents.requestFocus();

    }

    // Date
    private void setDateTimeField() {
        dateOfEvents.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        dateOfEventPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfEvents.setText(date.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dateOfEventPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfEvents.setText(date.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == dateOfEvents) {
            dateOfEventPickerDialog.show();
        } else if (view == dateOfEvents) {
            dateOfEventPickerDialog.show();
        }
    }

    // Time
    // display current time

    public void setCurrentTimeOnView() {
        textViewTime = (TextView) findViewById(R.id.txtTime);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        final Calendar c = Calendar.getInstance();
        h = c.get(Calendar.HOUR_OF_DAY);
        m = c.get(Calendar.MINUTE);
        textViewTime.setText(new StringBuilder().append(padding_str(h)).append(":").append(padding_str(m)));
        // set current time into timepicker
        timePicker.setCurrentHour(h);
        timePicker.setCurrentMinute(m);
    }

    public void addButtonListener() {
        time = (EditText) findViewById(R.id.txtTime);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG:
                return new TimePickerDialog(this, timePickerListener, h, m, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            h = selectedHour;
            m = selectedMinute;
            textViewTime.setText(new StringBuilder().append(padding_str(h)).append(":").append(padding_str(m)));
            timePicker.setCurrentHour(h);
            timePicker.setCurrentMinute(m);
        }


    };

    private static String padding_str(int a) {
        if (a >= 10)
            return String.valueOf(a);
        else
            return "0" + String.valueOf(a);
    }


    //add Data SQLite
    public void AddData() {
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(nameOfEvents.getText().toString(),
                                spinnerLocation.getSelectedItem().toString(),
                                dateOfEvents.getText().toString(),
                                textViewTime.getText().toString(),
                                org.getText().toString());
                        if (isInserted = true) {
                            AlertDialog dialog = new AlertDialog.Builder(
                                    MainActivity.this).create();
                            dialog.setTitle("Message");
                            dialog.setMessage("Data Inserted Successfull");
                            dialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Data Inserted Sucessfull", Toast.LENGTH_LONG).show();
                                }
                            });
                            dialog.show();
                        } else
                            Toast.makeText(MainActivity.this, "Data Inserted Unsucessfull", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
