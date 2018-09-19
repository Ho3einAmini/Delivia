package test.mobileapp.layout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import test.mobileapp.R;
import test.mobileapp.model.DeliveryModel;


/**
 * Created by Hossein on 3/19/2018.
 **/

public class DetailsBS extends BottomSheetDialogFragment
{
    View rootView;
    DeliveryModel model;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.bottom_sheet_info, container, false);
        TextView tvTitle = rootView.findViewById(R.id.bottom_sheet_title);
        SimpleDraweeView imgImage = rootView.findViewById(R.id.bottom_sheet_image);
        tvTitle.setText(model.getDescription());
        Uri uri = Uri.parse(model.getImageUrl());
        imgImage.setImageURI(uri);
        return rootView;
    }

    public void setModel(DeliveryModel model) {
        this.model = model;
    }
}
