package test.mobileapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import test.mobileapp.R;
import test.mobileapp.layout.DetailsActivity;
import test.mobileapp.model.DeliveryModel;
import test.mobileapp.model.LoadMoreModel;

public class DeliveryAdapter  extends LoadMoreHelper<DeliveryAdapter.DeliveryHolder, DeliveryModel>  {
    public DeliveryAdapter(List<DeliveryModel> deliveryModels) {
        this.adapterModel = deliveryModels;
    }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View rootView;
        if(viewType == 1)
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_delivery_list, parent, false);
        else
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
        return new DeliveryHolder(rootView , viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeliveryHolder holder, int position)
    {
        LoadMoreModel model = adapterModel.get(position);
        if(model instanceof DeliveryModel) {
            DeliveryModel deliveryModel = (DeliveryModel) model;
            holder.setData(deliveryModel);
        }
    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }


    class DeliveryHolder extends RecyclerView.ViewHolder {
        Context context;
        String id;
        SimpleDraweeView sdImage;
        TextView tvTitle;
        CardView cardView;
        DeliveryHolder(View itemView, int type) {
            super(itemView);
            if(type != 1)
                return;
            sdImage = itemView.findViewById(R.id.delivery_image);
            tvTitle = itemView.findViewById(R.id.delivery_title);
            context = itemView.getContext();
            cardView = itemView.findViewById(R.id.delivery_card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(DeliveryModel model:adapterModel)
                    {
                        if(model.getId().equals(id))
                        {
                            Intent intent = new Intent(context , DetailsActivity.class);
                            intent.putExtra("model" ,model);
                            context.startActivity(intent);
                            return;
                        }
                    }
                }
            });
        }

        public void setData(DeliveryModel model)
        {
            Uri uri = Uri.parse(model.getImageUrl());
            RoundingParams roundingParams = RoundingParams.asCircle();
            roundingParams.setBorderWidth(2f);
            roundingParams.setBorderColor(Color.LTGRAY);
            sdImage.getHierarchy().setRoundingParams(roundingParams);
            sdImage.setImageURI(uri);
            tvTitle.setText(model.getDescription());
            id = model.getId();
        }
    }

}
