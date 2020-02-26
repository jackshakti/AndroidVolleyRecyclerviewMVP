package shakti.volleymvprecyclerview.app.view;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public interface IRecyclerviewView {

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

}
