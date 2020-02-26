package shakti.volleymvprecyclerview.appdetail.view;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public interface IAppDetailModelView {

    /**
     * This method will be called when the AppDetail Api is success
     *
     * @param message  Success Message
     * @param response Response from the api
     */
    void onAppDetailViewResponseSuccess(String message, String response);

    /**
     * This method will be called when the AppDetail Api is Failed
     *
     * @param message Failure Message
     */
    void onAppDetailViewResponseFailed(String message);

}
