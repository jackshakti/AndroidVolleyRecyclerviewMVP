package shakti.volleymvprecyclerview.library.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class AppHelper {

    /**
     * Turn drawable resource into byte array.
     *
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Bitmap bit) {
        // Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = bit;//((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String setFileName() {
        Locale locale1 = new Locale("en", "US");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss",locale1);
        Calendar calendar = Calendar.getInstance(locale1);
        return dateFormat.format(calendar.getTime());
    }

}

