package com.yaroslav_f.rts_perceptron;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Perceptron perceptron;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perceptron = new Perceptron();

        Spinner spinner_lrate = findViewById(R.id.spinner_lrate);
        Spinner spinner_time_deadline = findViewById(R.id.spinner_time_deadline);
        Spinner spinner_maxiter = findViewById(R.id.spinner_maxiter);

        ArrayAdapter<String> lrate_adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spinner_lrate_arr));
        ArrayAdapter<String> td_adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spinner_time_deadline_arr));
        ArrayAdapter<String> maxi_adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spinner_maxiter_arr));

        lrate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        td_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxi_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_lrate.setAdapter(lrate_adapter);
        spinner_time_deadline.setAdapter(td_adapter);
        spinner_maxiter.setAdapter(maxi_adapter);
    }

    public void train(View view) {
        Spinner spinner_lrate = findViewById(R.id.spinner_lrate);
        Spinner spinner_time_deadline = findViewById(R.id.spinner_time_deadline);
        Spinner spinner_maxiter = findViewById(R.id.spinner_maxiter);
        TextView tvw1 = findViewById(R.id.tVw1);
        TextView tvw2 = findViewById(R.id.tVw2);
        TextView tVelapsed = findViewById(R.id.tVelapsed);
        TextView tViter = findViewById(R.id.tViter);
        TextView tVresult = findViewById(R.id.tVresult);
        boolean res = perceptron.train(
                new double [][]{{0,6}, {1,5}, {3,3}, {2,4}},
                4,
                Double.parseDouble(spinner_lrate.getSelectedItem().toString()),
                Integer.parseInt(spinner_maxiter.getSelectedItem().toString()),
                Double.parseDouble(spinner_time_deadline.getSelectedItem().toString()));
        tvw1.setText(String.format("w1: %s", perceptron.w1));
        tvw2.setText(String.format("w2: %s", perceptron.w2));
        tVelapsed.setText(String.format("elapsed (ms): %s", perceptron.elapsed));
        tViter.setText(String.format("iterations: %s", perceptron.iter));
        if (res) {
            tVresult.setText(R.string.res_success);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Finished successfully")
                    .setMessage(String.format("iterations: %s", perceptron.iter))
                    .setCancelable(true)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        } else {
            tVresult.setText(R.string.res_failure);
        }
    }
}
