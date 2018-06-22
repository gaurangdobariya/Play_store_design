package aa.com.demo_product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class detail extends AppCompatActivity {
    public TextView d_title,d_desc,d_price;
    public String title,desc,price,url;
    public Button cart;
    public  ArrayList<SingleItemModel> cartitem;
    public  ArrayList<SingleItemModel> cartitemNew;
    ImageView d_image;
    public  static SingleItemModel s;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        d_title=findViewById(R.id.tv_Title);
        d_desc=findViewById(R.id.tv_Description);
        d_price=findViewById(R.id.tv_Price);
        cart=findViewById(R.id.v_cart);
        d_image=findViewById(R.id.tv_imageView);
        cartitemNew=new ArrayList<SingleItemModel>();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details of Product");

        Intent i=getIntent();
        Bundle b=i.getExtras();
        title=b.getSerializable("title").toString();
        d_title.setText(title);

        url=b.getSerializable("url").toString();
        Log.e("URL",url);
       // Uri u=Uri.parse(url);
        //d_image.setImageURI(u);
       // new DownLoadImageTask(d_image).execute(url);
        bitmap=StringToBitMap(url);
        d_image.setImageBitmap(bitmap);
        price=b.getSerializable("price").toString();
        d_price.setText(price);
        cartitem=new ArrayList<SingleItemModel>();
        desc="descriptin of item";
        d_desc.setText(desc);


        cart.setOnClickListener(new View.OnClickListener() {
             @Override
              public void onClick(View view) {
                  s=new SingleItemModel(title , desc, price, url);

                 if (null == cartitem) {
                     cartitem = new ArrayList<SingleItemModel>();
                 }

                 //old retrival
                 SharedPreferences prefs = getSharedPreferences("cartfile",MODE_PRIVATE);
                 Gson gson = new Gson();
                 String response=prefs.getString("cartlist" , "");

                 if(response==""){
                     cartitem.add(s);

                 }
                 else {
                     cartitem = gson.fromJson(response, new TypeToken<List<SingleItemModel>>() {}.getType());
                     cartitem.add(s);
                 }

                 SharedPreferences.Editor editor = prefs.edit();
                 String fValue=gson.toJson(cartitem);

                 try {
                     editor.putString("cartlist", fValue);

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 editor.commit();


                 Intent i=new Intent(getApplicationContext(),ListView.class);
                 startActivity(i);
                finish();
        }
    });
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
         Intent i = new Intent(this, MainActivity.class);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }*/
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}

