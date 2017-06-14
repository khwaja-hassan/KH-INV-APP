package com.example.khwaja.androidhive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static android.graphics.Color.parseColor;

public class products extends AppCompatActivity {

    static String[] spaceProbeHeaders = {"Item-ID","Name","Price", "Desc"};
    static String[][] spaceProbes = {
            {"1","Pioneer","1234","Test"},
            {"2","Khwaja","1235","Test1"},
            {"3","Kyan","1236","Test2"},
            {"4","Sameena","1237","Test3"},
            {"5","Ginger","1238","Test4"},
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        final TableView<String[]> tableView = (TableView <String[]>) findViewById(R.id.tableView);

        //SET Table Property
        tableView.setHeaderBackgroundColor(parseColor("#2ecc71"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, spaceProbeHeaders));
        tableView.setColumnCount(4);


        tableView.setDataAdapter(new SimpleTableDataAdapter(products.this, spaceProbes));
        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(products.this,((String[])clickedData)[1],Toast.LENGTH_SHORT).show();

            }
        });


    }
}
