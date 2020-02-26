package shakti.volleymvprecyclerview.app.presenter;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public interface IRecyclerviewPresenter {

    /**
     * This method used to request Recyclerview Api
     *
     * @param context Context of the activity
     * @param mParams Params given by the user
     * @return Value to be triggered
     */
    void validateRecyclerviewRequest(Context context, HashMap<String, String> mParams);


    /**
     * This method will be called when the Recyclerview Api is success
     *
     * @param message  Success Message
     * @param response Response from the api
     */
    void onRecyclerviewResponseSuccess(String message, String response);


    /**
     * This method will be called when the Recyclerview Api is Failed
     *
     * @param message Failure Message
     */
    void onRecyclerviewResponseFailed(String message);


    /**
     * This method will clear the instances and listeners
     */
    void onRecyclerviewPresenterDestroy();


}
