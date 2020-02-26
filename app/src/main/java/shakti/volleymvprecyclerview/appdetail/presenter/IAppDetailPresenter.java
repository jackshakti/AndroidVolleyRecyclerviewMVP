package shakti.volleymvprecyclerview.appdetail.presenter;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public interface IAppDetailPresenter {


    /**
     * This method used to request AppDetail Api
     *
     * @param context Context of the activity
     * @param mParams Params given by the user
     * @return Value to be triggered
     */
    void validateAppDetailRequest(Context context, HashMap<String, String> mParams);


    /**
     * This method will be called when the AppDetail Api is success
     *
     * @param message  Success Message
     * @param response Response from the api
     */
    void onResponseSuccess(String message, String response);


    /**
     * This method will be called when the AppDetail Api is Failed
     *
     * @param message Failure Message
     */
    void onResponseFailed(String message);


    /**
     * This method will clear the instances and listeners
     */
    void onAppDetailPresenterDestroy();

}
