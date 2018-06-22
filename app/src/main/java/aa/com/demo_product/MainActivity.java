package aa.com.demo_product;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ArrayList<SectionDataModel> allSampleData;
    ArrayList<SingleItemModel> tCartitem;
    RecyclerView my_recycler_view;
    Toolbar mToolbar;
    TextView iCart;
    public int row, column;
    private int cnt;
    public String path;
    android.os.Handler h1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iCart = findViewById(R.id.imageCart);
        mToolbar = findViewById(R.id.mToolbar);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        Intent in = getIntent();
        Bundle b1 = in.getBundleExtra("create");
        SharedPreferences pref = getSharedPreferences("datafile", MODE_PRIVATE);

        if (b1 != null) {
            row = Integer.parseInt(b1.getSerializable("Row").toString());
            column = Integer.parseInt(b1.getSerializable("Column").toString());
            path = b1.getSerializable("Path").toString();
           // Log.e("FirstUrl", path);

            //add row column to shared preference
            SharedPreferences.Editor edt = pref.edit();
            edt.putString("nRow", String.valueOf(row));
            edt.putString("nColumn", String.valueOf(column));
            edt.putString("iPath", path);
            edt.commit();
        } else {
            //read data from shared preferance
            row = Integer.parseInt(pref.getString("nRow", String.valueOf(1)));
            column = Integer.parseInt(pref.getString("nColumn", String.valueOf(1)));
            path = String.valueOf(pref.getString("iPath", String.valueOf("")));
         //   Log.e("SecondUrl", path);
        }
        // Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mToolbar.setTitle("Product Category");

        allSampleData = new ArrayList<SectionDataModel>();
        tCartitem = new ArrayList<SingleItemModel>();

        createDummyData();

        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);


        SharedPreferences prefs = getSharedPreferences("cartfile", MODE_PRIVATE);
        Gson gson = new Gson();
        String response = prefs.getString("cartlist", "");

        if (response == "") {

            cnt = 0;

        } else {
            tCartitem = gson.fromJson(response, new TypeToken<List<SingleItemModel>>() {}.getType());
            cnt = tCartitem.size();
        }


        iCart.setText("CART (" + String.valueOf(cnt) + ")");
        iCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListView.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        Bundle b1 = in.getBundleExtra("create");
        SharedPreferences pref = getSharedPreferences("datafile", MODE_PRIVATE);

        if (b1 != null) {
            row = Integer.parseInt(b1.getSerializable("Row").toString());
            column = Integer.parseInt(b1.getSerializable("Column").toString());
            path = b1.getSerializable("Path").toString();
            Log.e("FirstUrl", path);

            //add row column to shared preference
            SharedPreferences.Editor edt = pref.edit();
            edt.putString("nRow", String.valueOf(row));
            edt.putString("nColumn", String.valueOf(column));
            edt.putString("iPath", path);
            edt.commit();
        } else {
            //read data from shared preferance
            row = Integer.parseInt(pref.getString("nRow", String.valueOf(1)));
            column = Integer.parseInt(pref.getString("nColumn", String.valueOf(1)));
            path = String.valueOf(pref.getString("iPath", String.valueOf("")));
            Log.e("SecondUrl", path);
        }
        // Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mToolbar.setTitle("Product Category");

        allSampleData = new ArrayList<SectionDataModel>();
        tCartitem = new ArrayList<SingleItemModel>();

        createDummyData();

        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);


        SharedPreferences prefs = getSharedPreferences("cartfile", MODE_PRIVATE);
        Gson gson = new Gson();
        String response = prefs.getString("cartlist", "");

        if (response == "") {

            cnt = 0;

        } else {
            tCartitem = gson.fromJson(response, new TypeToken<List<SingleItemModel>>() {}.getType());
            cnt = tCartitem.size();
        }


        iCart.setText("CART (" + String.valueOf(cnt) + ")");
        iCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListView.class));
            }
        });




        iCart.setText("CART (" + String.valueOf(cnt) + ")");


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        iCart.setText("CART (" + String.valueOf(cnt) + ")");

    }

    public void createDummyData() {
        for (int i = 1; i <= row; i++) {
            SectionDataModel dm = new SectionDataModel();
            dm.setHeaderTitle("Section " + i);
            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j < column; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "Description " + j, "Price " + i + j, path));
            }
            dm.setAllItemsInSection(singleItem);
            allSampleData.add(dm);

        }
        iCart.setText("CART (" + String.valueOf(cnt) + ")");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("cartfile", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        finish();

    }
}
