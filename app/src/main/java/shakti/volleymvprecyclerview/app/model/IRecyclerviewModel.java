package shakti.volleymvprecyclerview.app.model;


import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public interface IRecyclerviewModel {

    /**
     * This method used to request Recyclerview Api
     *
     * @param context Context of the activity
     * @param mParams Params given by the user
     * @return Value to be triggered
     */
    void validateRecyclerviewRequest(Context context, HashMap<String, String> mParams);


    /**
     * This method will clear the instances and listeners
     */
    void onRecyclerviewModelDestroy();


}
