package shakti.volleymvprecyclerview.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class NetworkUtil {

    /**
     * Empty constructor
     */
    private NetworkUtil() {

    }

    /**
     * This method is used to get the connectivity status of the string.
     *
     * @param context Activity context
     * @return The connection status
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == 1) {
            status = "Connected";
        } else if (conn == 2) {
            status = "Connected";
        } else if (conn == 0) {
            status = "Not-Connected";
        }
        return status;
    }

    /**
     * This method is used to check the internet connectivity status.
     *
     * @param context Activity context
     * @return The connection status
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return 1;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return 2;
        }
        return 0;
    }

    /**
     * To check the internet connectivity and show error message if network not available.
     *
     * @param context Contains the context
     * @return True if internet is available false
     */
    public static boolean checkNetworkAndShowDialog(Context context) {
        if (!checkNetConnection(context)) {
            //Logger.showShortMessage(context, context.getString(R.string.check_internet));
            return false;
        }
        return true;
    }

    /**
     * Checking internet state.
     *
     * @param context The context
     * @return True if internet is enabled else false
     */
    public static boolean checkNetConnection(Context context) {
        ConnectivityManager miManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo miInfo = miManager.getActiveNetworkInfo();
        boolean networkStatus = false;
        if (miInfo != null && miInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkStatus = true;
        } else if (miInfo != null && miInfo.getType() == ConnectivityManager.TYPE_MOBILE &&
                miInfo.isConnectedOrConnecting())
            networkStatus = true;
        return networkStatus;
    }

    /**
     * To check the internet connectivity and show error message if network not available and
     * save the data in local db.
     *
     * @param context Contains the context
     * @return True if internet is available false
     */
    public static boolean checkNetworkForOfflineDataStorage(Context context) {
        if (!checkNetConnection(context)) {
            //Logger.showShortMessage(context, context.getString(R.string.check_internet_for_sync));
            return false;
        }
        return true;
    }

}
