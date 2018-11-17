package com.example.user.customizeddialogsharon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener {

    ListView lv;
    TextView tv;
    TextView tv2;
    String[] sidra = new String[20];

    double x = 0;
    double a = 0;
    double number1;
    double meOrMa;
    boolean isHandasit;
    int p = 1;

    AlertDialog.Builder adb;
    Button openDialog;

    String addOrMulti;

    Switch heshbonitOrHandasit;

    LinearLayout myDialog;
    EditText firstNumber, mehaberOrMachpil;
    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        openDialog = findViewById(R.id.openDialog);

        for(int i = 0; i<20; i++){
            sidra[i] = "0.0";
        }

        lv.setOnItemClickListener(this);
        lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sidra);
        lv.setAdapter(adp);

        lv.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        p = position + 1;
        tv.setText("מקום האיבר בסדרה: " + p + "\n" + "האיבר הראשון בסדרה: " + number1 + "\n");

        if (!isHandasit) {
            tv2.setText("ההפרש: " + meOrMa + "\n" + "סכום הסדרה מהאיבר הראשון עד לאיבר זה: " + ((p) * (2 * number1 + (position) * meOrMa)) / 2);
        } else {
            tv2.setText("המנה: " + meOrMa + "\n" + "סכום הסדרה מהאיבר הראשון עד לאיבר זה: " + ((number1 * ((Math.pow(meOrMa, p)) - 1)) / (meOrMa - 1)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Credits");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent t = new Intent(this, Credits.class);
        startActivity(t);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("פרטים נוספים");
        menu.add("מיקום האיבר");
        menu.add("סכום הסדרה עד לאיבר זה");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        p = info.position + 1;
        String st = item.getTitle().toString();
        if (st.equals("מיקום האיבר"))
            Toast.makeText(this, "המיקום הוא: " + p, Toast.LENGTH_LONG).show();
        else if (st.equals("סכום הסדרה עד לאיבר זה")) {
            String addOrMulti = getIntent().getStringExtra("mehOrMach");
            if (addOrMulti.equals("מחבר")) {
                Toast.makeText(this, "סכום הסדרה מהאיבר הראשון עד לאיבר זה: " + ("" + ((p) * (2 * number1 + (p - 1) * meOrMa)) / 2).replace("E", "10^"), Toast.LENGTH_LONG).show();
            } else if (addOrMulti.equals("מכפיל")) {
                Toast.makeText(this, "סכום הסדרה מהאיבר הראשון עד לאיבר זה: " + ((number1 * ((Math.pow(meOrMa, p)) - 1)) / (meOrMa - 1)), Toast.LENGTH_LONG).show();
            }
        }
        return super.onContextItemSelected(item);
    }

    public void openDialog(View view) {
        myDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        heshbonitOrHandasit = (Switch) myDialog.findViewById(R.id.heshbonitOrHandasit);
        firstNumber = (EditText) myDialog.findViewById(R.id.firstNumber);
        mehaberOrMachpil = (EditText) myDialog.findViewById(R.id.mehaberOrMachpil);

        adb = new AlertDialog.Builder(this);

        adb.setView(myDialog);

        adb.setTitle("Please enter data");
        //רק כשלוחצים קורה משהו

        adb.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (firstNumber.getText().toString().equals(".") || firstNumber.getText().toString().equals("-") || firstNumber.getText().toString().isEmpty()
                        || mehaberOrMachpil.getText().toString().equals(".") || mehaberOrMachpil.getText().toString().equals("-") ||  mehaberOrMachpil.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "You didn't enter all the information", Toast.LENGTH_LONG).show();
                else
                {
                    number1 = Double.parseDouble(firstNumber.getText().toString());
                    meOrMa = Double.parseDouble(mehaberOrMachpil.getText().toString());
                    sidra[0] = String.valueOf(number1);
                    if (heshbonitOrHandasit.isChecked())
                    {
                        isHandasit = true;
                        for(int i = 1; i<20; i++){
                            sidra[i] = Double.toString(Double.parseDouble(sidra[i-1])*meOrMa);
                        }
                    }
                    else
                    {
                        isHandasit = false;
                        for(int i = 1; i<20; i++){
                            sidra[i] = Double.toString(Double.parseDouble(sidra[i-1])+meOrMa);
                        }
                    }
                    adp.notifyDataSetChanged();
                    tv.setText("");
                    tv2.setText("");
                }
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i<20; i++){
                    sidra[i] = "0.0";
                }
                number1 = 0;
                meOrMa = 0;
                tv.setText("");
                tv2.setText("");
                adp.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        AlertDialog ad = adb.create();
        ad.show();
    }
}