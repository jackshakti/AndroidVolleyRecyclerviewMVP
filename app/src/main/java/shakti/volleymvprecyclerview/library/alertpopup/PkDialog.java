package shakti.volleymvprecyclerview.library.alertpopup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import shakti.volleymvprecyclerview.R;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class PkDialog {

    private Context mContext;
    private Button Bt_action, Bt_dismiss;
    private TextView alert_title, alert_message;
    private ImageView Iv_alert;
    private Dialog dialog;
    private boolean isPositiveAvailable = false;
    private boolean isNegativeAvailable = false;
    private String sTitle = "";

    public PkDialog(Context context) {
        this.mContext = context;

        //--------Adjusting Dialog width-----
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);//fill only 80% of the screen

        View view = View.inflate(mContext, R.layout.pk_dialog, null);
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setLayout(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
            }
        });

        alert_title = view.findViewById(R.id.pkDialog_title_textView);
        alert_message = view.findViewById(R.id.pkDialog_message_textView);
        Bt_action = view.findViewById(R.id.pkDialog_ok_button);
        Bt_dismiss = view.findViewById(R.id.pkDialog_cancel_button);
        Iv_alert = view.findViewById(R.id.pkDialog_alert_imageView);

    }

    public void show() {

        /*
         * Enable or Disable positive Button
         */
        if (isPositiveAvailable) {
            Bt_action.setVisibility(View.VISIBLE);
        } else {
            Bt_action.setVisibility(View.GONE);
        }


        /*
         * Enable or Disable Negative Button
         */
        if (isNegativeAvailable) {
            Bt_dismiss.setVisibility(View.VISIBLE);
        } else {
            Bt_dismiss.setVisibility(View.GONE);
        }


        /*
         * Hiding the Title when user won't provide title
         */
        if (sTitle.trim().length() > 0) {
            alert_title.setVisibility(View.VISIBLE);
        } else {
            alert_title.setVisibility(View.GONE);
        }

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setDialogTitle(String title) {
        sTitle = title;
        alert_title.setText(title);
    }

    public void setDialogMessage(String message) {
        alert_message.setText(message);
    }

    public void setAlertImage(int drawable) {
        Iv_alert.setImageResource(drawable);
    }

    public void setCancelOnTouchOutside(boolean value) {
        dialog.setCanceledOnTouchOutside(value);
    }

    /*Action Button for Dialog*/
    public void setPositiveButton(String text, final View.OnClickListener listener) {
        isPositiveAvailable = true;
        Bt_action.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/proxima_nova_medium.ttf"));
        Bt_action.setText(text);
        Bt_action.setOnClickListener(listener);
    }

    public void setNegativeButton(String text, final View.OnClickListener listener) {
        isNegativeAvailable = true;
        Bt_dismiss.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/proxima_nova_medium.ttf"));
        Bt_dismiss.setText(text);
        Bt_dismiss.setOnClickListener(listener);
    }

}
