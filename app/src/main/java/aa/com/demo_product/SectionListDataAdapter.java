package aa.com.demo_product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    private  int cnt;
    private Bitmap bitmap;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());
        holder.tvPrice.setText(singleItem.getPrice());
        //Uri uri=Uri.parse(itemsList.get(i).getUrl());
       //new DownLoadImageTask(holder.itemImage).execute(itemsList.get(i).getUrl());
        holder.itemImage.setImageBitmap(bitmap);
        //holder.itemImage.invalidate();
        cnt=i;


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }



    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvPrice,tvDesc;
        protected ImageView itemImage;
        private  Context context;
        String url;


        public SingleItemRowHolder(final View view) {
            super(view);
            context=view.getContext();

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.tvPrice=(TextView)view.findViewById(R.id.tvPrice);

            bitmap=StringToBitMap(itemsList.get(cnt).getUrl());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int selectedItemPosition = Integer.parseInt(tvTitle.getText().toString().trim());
                    Intent i =new Intent(context,detail.class);
                    Bundle b=new Bundle();
                    b.putSerializable("title",tvTitle.getText().toString().trim());
                    b.putSerializable("price",tvPrice.getText().toString().trim());
                    b.putSerializable("url",itemsList.get(cnt).getUrl().toString());
                //    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(b);
                    context.startActivity(i);
                    notifyDataSetChanged();
                   // ((Activity)context).finish();



                }
            });


        }

    }
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }



}