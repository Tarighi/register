package com.tarighi.register;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {


    private List<UserInfo> Datasource;
    public RvAdapter(List<UserInfo> datasource)
    {
        Datasource=datasource;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycler_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       UserInfo currentUser =Datasource.get(position);
        holder.txtName.setText(currentUser.GetFullName());
        holder.txtMobile.setText(Long.toString(currentUser.Mobile));
        if(currentUser.AVATAR!=null && currentUser.AVATAR.length()>0) {
            Uri selectedImage = Uri.parse(currentUser.AVATAR);
            holder.imgAvatar.setImageURI(selectedImage);
            holder.imgAvatar.setTag(selectedImage.toString());
        }
    }

    @Override
    public int getItemCount() {
        return Datasource.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtMobile;
        RoundedImageView imgAvatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtName);
            txtMobile=itemView.findViewById(R.id.txtMobile);
            imgAvatar=(RoundedImageView)itemView.findViewById(R.id.imgAvatar);
        }
    }
}
