package aa.com.demo_product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RemoveActivity extends AppCompatActivity {

    public TextView r_title,r_desc,r_price;
    public String rtitle,rdesc,rprice,rindex;
    public int index;
    public Button rcart;
    public ArrayList<SingleItemModel> rcartitem;
    public  ArrayList<SingleItemModel> rcartitemNew;
    ImageView r_image;
    String rurl;

    public  static SingleItemModel s;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        r_title=findViewById(R.id.rm_Title);
        r_desc=findViewById(R.id.rm_Description);
        r_price=findViewById(R.id.rm_Price);
        rcart=findViewById(R.id.rm_cart);

        rcartitemNew=new ArrayList<SingleItemModel>();
        r_image=findViewById(R.id.rm_imageView);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CartItem Detail");

        Intent i=getIntent();
        Bundle b=i.getExtras();
        rtitle=b.getSerializable("title").toString();
        r_title.setText(rtitle);
        index=Integer.parseInt(b.getSerializable("index").toString());

        rurl=b.getSerializable("url").toString();
       // Uri url=Uri.parse(rurl);
        //r_image.setImageURI(url);

       // new DownLoadImageTask(r_image).execute(rurl);
        bitmap=StringToBitMap(rurl);
        r_image.setImageBitmap(bitmap);

        rprice=b.getSerializable("price").toString();
        r_price.setText(rprice);
        rcartitem=new ArrayList<SingleItemModel>();
        rdesc="descriptin of item";
        r_desc.setText(rdesc);

        rcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s=new SingleItemModel(rtitle , rdesc, rprice,rurl);


                SharedPreferences prefs = getSharedPreferences("cartfile",MODE_PRIVATE);
                Gson gson = new Gson();
                String response=prefs.getString("cartlist" , "");

                rcartitem = gson.fromJson(response, new TypeToken<List<SingleItemModel>>() {}.getType());
                rcartitem.remove(index);

                SharedPreferences.Editor editor = prefs.edit();
                String fValue=gson.toJson(rcartitem);

                try {
                    editor.putString("cartlist", fValue);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.commit();

                if(rcartitem==null){
                    rcartitem=new ArrayList<SingleItemModel>();
                }

                Toast.makeText(getApplicationContext(),"Item removed",Toast.LENGTH_SHORT).show();
                onBackPressed();
               // Intent i=new Intent(getApplicationContext(),ListView.class);
                //startActivity(i);
                //finish();
            }
        });
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
