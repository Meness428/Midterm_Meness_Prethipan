package com.meness.midterm_meness_prethipan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText inputNumber;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> tableItems = new ArrayList<>();
    public static Set<Integer> historySet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inputNumber = findViewById(R.id.inputNumber);
        listView = findViewById(R.id.listView);
        Button btnGenerate = findViewById(R.id.btnGenerate);
        Button btnHistory = findViewById(R.id.btnHistory);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableItems);
        listView.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> {
            String numText = inputNumber.getText().toString();
            if (!numText.isEmpty()) {
                int num = Integer.parseInt(numText);
                tableItems.clear();
                for (int i = 1; i <= 10; i++) {
                    tableItems.add(num + " x " + i + " = " + (num * i));
                }
                adapter.notifyDataSetChanged();
                historySet.add(num);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = tableItems.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete row?")
                    .setMessage("Do you want to delete \"" + item + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        tableItems.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + item, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}