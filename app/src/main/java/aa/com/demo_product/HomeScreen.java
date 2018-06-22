package aa.com.demo_product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

public class HomeScreen extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    Button l_create,i_Select;
    EditText lRow,lColumn;
    String nRow,nColumn,path;
    Uri iPath;
    int iRow,iColumn;
    ImageView hImage;
    TextView u_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        l_create=findViewById(R.id.create);
        lRow=findViewById(R.id.row);
        lColumn=findViewById(R.id.column);
        i_Select=findViewById(R.id.iSelect);
        u_image=findViewById(R.id.uImage);
        hImage=findViewById(R.id.himage);
       // hImage.setVisibility(View.INVISIBLE);
        hImage.setVisibility(View.INVISIBLE);
        i_Select.setVisibility(View.INVISIBLE);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Screen");
        actionBar.setIcon(R.drawable.homescreen);

        path="https://www.google.com/images/srpr/logo11w.png";
        u_image.setText("URL  is : "+path);
        new DownLoadImageTask(hImage).execute(path);
        hImage.setVisibility(View.INVISIBLE);

        i_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

        l_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nRow=lRow.getText().toString().trim();
                nColumn=lColumn.getText().toString().trim();

                iRow=Math.round(Float.parseFloat(nRow));
                iColumn=Math.round(Float.parseFloat(nColumn));


                new DownLoadImageTask(hImage).execute(path);
                hImage.setVisibility(View.INVISIBLE);
                Bitmap bitmap = ((BitmapDrawable)hImage.getDrawable()).getBitmap();
             //   hImage.setVisibility(View.INVISIBLE);


                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("Row",String.valueOf(iRow));
                b.putSerializable("Column",String.valueOf(iColumn));
                b.putSerializable("Path",String.valueOf(BitMapToString(bitmap)));                                                           //for gallery change over here
                i.putExtra("create",b);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (resultCode != Activity.RESULT_OK && resultData != null) {
            return;
        } else {
            iPath = resultData.getData();

        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

}
