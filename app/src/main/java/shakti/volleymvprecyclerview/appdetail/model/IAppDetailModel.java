package shakti.volleymvprecyclerview.appdetail.model;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public interface IAppDetailModel {

    /**
     * This method used to request AppDetail Api
     *
     * @param context Context of the activity
     * @param mParams Params given by the user
     * @return Value to be triggered
     */
    void validateAppDetailRequest(Context context, HashMap<String, String> mParams);


    /**
     * This method will clear the instances and listeners
     */
    void onAppDetailModelDestroy();


}
