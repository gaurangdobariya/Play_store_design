package aa.com.demo_product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;

public class ListView extends AppCompatActivity {

    ArrayList<SingleItemModel> l;
    RecyclerView recycler_view;
    displayadapter adapter;
    SharedPreferences prefs;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_adapter);
         recycler_view= (RecyclerView) findViewById(R.id.my_recycler_view);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cart Item");

        getdata();

    }

    private void getdata() {
        prefs= getSharedPreferences("cartfile", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Intent i = getIntent();
        if(i==null){
            File file= new File("/data/data/.../shared_prefs/cartfile.xml");
            file.delete();
        }

        if (l == null) {
            l = new ArrayList<SingleItemModel>();
        }

        response=prefs.getString("cartlist" , "");
        l = gson.fromJson(response, new TypeToken<List<SingleItemModel>>(){}.getType());


        recycler_view.setHasFixedSize(true);
        adapter = new displayadapter(getApplicationContext(), l);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata();
        adapter.notifyDataSetChanged();

    }
/* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
       startActivity(i);
        finish();
    }*/
}
