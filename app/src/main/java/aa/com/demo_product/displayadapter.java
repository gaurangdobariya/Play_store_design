package aa.com.demo_product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by asp_mac_5 on 15/05/18.
 */

public class displayadapter extends RecyclerView.Adapter<displayadapter.ViewHolder> {


    public ArrayList<SingleItemModel> tCartitem;
    private Context context;
    public int cnt;
    private Bitmap bitmap;

    public displayadapter(Context context,ArrayList<SingleItemModel> tCartitem) {
        this.context = context;
        this.tCartitem = tCartitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cart_item, viewGroup, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int i) {
        viewHolder.tvd_title.setText(tCartitem.get(i).getName());
        viewHolder.tvd_desc.setText(tCartitem.get(i).getDescription());
        viewHolder.tvd_price.setText(tCartitem.get(i).getPrice());
      //  Uri uri= Uri.parse(tCartitem.get(i).getUrl());
        cnt=i;
       // viewHolder.tvd_image.setImageURI(uri);
     //   new DownLoadImageTask(viewHolder.tvd_image).execute(tCartitem.get(i).getUrl());
        viewHolder.tvd_image.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return tCartitem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvd_title,tvd_desc,tvd_price;
        ImageView tvd_image;
        public ViewHolder(View view) {
            super(view);
            tvd_title=(TextView)view.findViewById(R.id.tvdTitle);
            tvd_price=(TextView)view.findViewById(R.id.tvdPrice);
            tvd_desc = (TextView)view.findViewById(R.id.tvdDescription);
            tvd_image = (ImageView)view.findViewById(R.id.tvditemImage);
            bitmap=StringToBitMap(tCartitem.get(cnt).getUrl());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,RemoveActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("title",tvd_title.getText().toString().trim());
                b.putSerializable("index",String.valueOf(cnt));
                b.putSerializable("url",tCartitem.get(cnt).getUrl());
                b.putSerializable("price",tvd_price.getText().toString().trim());
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                notifyDataSetChanged();
                //Toast.makeText(context,String.valueOf(cnt),Toast.LENGTH_SHORT).show();
                //((Activity)context).finish();


            }
        });
        }


    }
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
