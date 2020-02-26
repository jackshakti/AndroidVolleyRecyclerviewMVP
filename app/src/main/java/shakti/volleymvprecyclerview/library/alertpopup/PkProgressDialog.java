package shakti.volleymvprecyclerview.library.alertpopup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import shakti.volleymvprecyclerview.R;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class PkProgressDialog {

    private TextView alert_title, alert_progressCount;
    private ProgressBar progressBar;
    private Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PkProgressDialog(Context context) {

        //--------Adjusting Dialog width-----
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.85);//fill only 85% of the screen

        View view = View.inflate(context, R.layout.download_progress_dialog_screen, null);
        dialog = new Dialog(context);
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

        alert_title = dialog.findViewById(R.id.downloadProgress_title_textView);
        alert_progressCount = dialog.findViewById(R.id.downloadProgress_download_percent_textView);
        progressBar = dialog.findViewById(R.id.downloadProgress_progressbar);

    }

    public void show() {
        progressBar.setProgress(0);
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setDialogTitle(String title) {
        alert_title.setText(title);
    }

    @SuppressLint("SetTextI18n")
    public void updateProgressCount(String progressCount) {
        progressBar.setProgress(Integer.parseInt(progressCount));
        alert_progressCount.setText(progressCount + " " + "%");
    }

}
