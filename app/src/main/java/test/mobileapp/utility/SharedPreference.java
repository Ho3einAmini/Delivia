package test.mobileapp.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import test.mobileapp.model.DeliveryModel;

public class SharedPreference {
    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setShopCategory(Context context, String jDeliveries , int page)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("jDeliveries" + page, jDeliveries);
        editor.apply();
    }

    public static List<DeliveryModel> getShopCategory(Context context , int page)
    {
        String jDeliveries = getSharedPreferences(context).getString("jDeliveries" + page, "");
        if (jDeliveries.equals(""))
            return null;
        Gson gson= new Gson();
        return gson.fromJson(jDeliveries, new TypeToken<List<DeliveryModel>>(){}.getType());
    }

    public static void deleteAll(Context context)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

}
