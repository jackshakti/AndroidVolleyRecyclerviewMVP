package shakti.volleymvprecyclerview.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import shakti.volleymvprecyclerview.R;
import shakti.volleymvprecyclerview.iconstant.IConstant;
import shakti.volleymvprecyclerview.library.alertpopup.PkDialog;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class Utils {

    private static Dialog dialog;
    private static final String TAG = Utils.class.getSimpleName();

    /**
     * Declaration for OnBackPress Method
     */
    public static String NoAnimation = "0";
    public static String EnterExitAnimation = "1";
    public static String FadeAnimation = "2";
    public static String SlideDownAnimation = "3";


    /**
     * validating Phone Number
     */
    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || TextUtils.isEmpty(target) || target.length() <= 5) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }


    /**
     * validating Email Address
     */
    public static boolean isValidEmailOld(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    /**
     * validating Email Address New
     */
    public static boolean isValidEmail(String email) {

        if (email.startsWith(".")) {
            return false;
        } else if (email.contains("..")) {
            return false;
        } else if (email.contains(".@")) {
            return false;
        } else {
            return EMAIL_ADDRESS_PREM.matcher(email).matches();
        }
    }

    private static final Pattern EMAIL_ADDRESS_PREM
            = Pattern.compile(
            "^[a-zA-Z0-9-]{1,30}+(?:\\.[a-zA-Z0-9!#$%&'*/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                    "[a-zA-Z](?:[a-zA-Z-]*[a-zA-Z])?$"
    );


    /**
     * validating Password
     */
    public static boolean isValidPassword(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }


    /**
//     * (			# Start of group
//     * (?=.*\d)		#   must contains one digit from 0-9
//     * (?=.*[a-z])		#   must contains one lowercase characters
//     * (?=.*[A-Z])		#   must contains one uppercase characters
//     * (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
//     * .		#     match anything with previous condition checking
//     * {6,20}	#        length at least 6 characters and maximum of 20
//     * )			# End of group
//     */
    private static final Pattern PASSWORD_REGEX
            = Pattern.compile("((?=.*\\d)(?=.*[A-Z])).{6,20}");

    /**
     * Show Log message
     */
    public static void log(String TAG, String message) {
        if (IConstant.showLog) {
            try {
                int maxLogSize = 500;
                for (int i = 0; i <= message.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > message.length() ? message.length() : end;
                    Log.e(TAG, message.substring(start, end));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Show println message
     */
    public static void print(String TAG, String message) {
        if (IConstant.showLog) {
            try {
                int maxLogSize = 500;
                for (int i = 0; i <= message.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > message.length() ? message.length() : end;
                    System.out.println("---------" + TAG + "--------" + message.substring(start, end));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Method to show ProgressBar
     */
    public static void showProgressDialog(Context context) {

        //---Adjusting Dialog width---
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.85);//fill only 85% of the screen

        View view = View.inflate(context, R.layout.custom_progress_dialog, null);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
            }
        });

        ImageView Iv_loader = view.findViewById(R.id.custom_progress_dialog_loader_imageView);

        Glide.with(context.getApplicationContext())
                .load(R.drawable.ic_done)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(Iv_loader);

        dialog.show();
    }

    /**
     * Method to dismiss ProgressBar
     */
    public static void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    /**
     * Method to Show Toast Message
     */
    public static void toast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to Show Toast Message
     */
    public static void toastLong(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /**
     * Method to Show Single Alert
     */
    public static void alert(Context context, String title, String message, String sPositiveText, int alert) {
        final PkDialog mDialog = new PkDialog(context);
        mDialog.setDialogTitle(title);
        mDialog.setDialogMessage(message);

        /*
         * 0 = Negative
         * 1 = Positive
         */
        if (alert == 1) {
            mDialog.setAlertImage(R.drawable.ic_done);
        } else {
            mDialog.setAlertImage(R.drawable.ic_error);
        }

        mDialog.setPositiveButton(sPositiveText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }


    /**
     * Method to Show No Internet Connection Alert
     */
    public static void noInternetAlert(final Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);//fill only 80% of the screen

        View view = View.inflate(context, R.layout.no_internet_connection_dialog, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setLayout(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button Rl_close = view.findViewById(R.id.noInternetDialog_close_button);
        TextView Tv_settings = view.findViewById(R.id.noInternetDialog_settings_textView);

        Rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                /*Intent intent = ((Activity) context).getIntent();
                ((Activity) context).finish();
                context.startActivity(intent);*/
            }
        });

        Tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        dialog.show();
    }


    /**
     * Method to Show Coming Soon Alert
     */
    public static void alertComingSoon(Context context) {
        final PkDialog mDialog = new PkDialog(context);
        mDialog.setDialogTitle("");
        mDialog.setDialogMessage(context.getResources().getString(R.string.app_comingSoon));
        mDialog.setAlertImage(R.drawable.ic_error);

        mDialog.setPositiveButton(context.getResources().getString(R.string.app_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }


    /**
     * Method to show Alert if new version of App is Available in playStore
     */
    public static void versionUpdateAlert(final Activity activity, String title, String alert) {
        final PkDialog mDialog = new PkDialog(activity);
        mDialog.setDialogTitle(title);
        mDialog.setDialogMessage(alert);
        mDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                activity.finish();
                String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        mDialog.show();
        activity.finish();
    }


    /**
     * Method to change App Language
     */
    @SuppressWarnings("deprecation")
    public static void setAppLanguage(Context context, String languageCode) {

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, dm);
    }

    /**
     * Method will be called during Start and Finish of Activity
     */
    public static void animateStartActivity(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
        ((Activity) context).overridePendingTransition(R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
    }

    public static void animateFinishActivity(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit);
        ((Activity) context).overridePendingTransition(R.anim.animate_card_enter, R.anim.animate_card_exit);
    }

    public static void animateSlideActivity(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
    }

    public static void animateSlideDownActivity(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_slide_down_enter, R.anim.animate_slide_down_exit);
    }

    public static void animateFadeActivity(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    /**
     * Method will be called when phone back button is pressed
     */
    public static void onBackPress(Activity activity, String sAnimType) {

        /*
         * 0 = No Animation
         * 1 = Enter/Exit Animation
         * 2 = Fade In/Out Animation
         * 3 = Slide Up/Down Animation
         */
        switch (sAnimType) {
            case "0":
                activity.finish();
                break;
            case "1":
                activity.finish();
                activity.overridePendingTransition(R.anim.animate_card_enter, R.anim.animate_card_exit);
                break;
            case "2":
                activity.finish();
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case "3":
                activity.finish();
                activity.overridePendingTransition(R.anim.animate_slide_down_enter, R.anim.animate_slide_down_exit);
                break;
        }

    }


    /**
     * Method to check empty data, if Empty data is present make it as "Nil"
     */
    public static String checkEmpty(Context context, String data) {
        if (data == null || data.equalsIgnoreCase("null") || data.trim().length() == 0) {
            return context.getResources().getString(R.string.app_nil);
        } else {
            return data;
        }
    }

    /**
     * Method to Load html content into the textView
     */
    public static void loadHtml(TextView textView, String data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(data));
        }
    }

    public static void expandLayout(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapseLayout(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = (initialHeight - (int) (initialHeight * interpolatedTime));
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }



    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    private static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }


    /**
     * Method to show Date Calender Dialog
     */
    public static void showDateCalender(final Context context, final TextView tv_date) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpDialog =
                new DatePickerDialog(context, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                        String year1 = String.valueOf(selectedYear);
                        String month1 = String.valueOf(selectedMonth + 1);
                        String day1 = String.valueOf(selectedDay);
                        String[] month_day = getDay_Month(month1, day1);

                        try {
                            String sDate = year1 + "-" + month_day[1] + "-" + month_day[0];

                            /*
                             * Function to block User on selecting old Dates
                             * ---Some Phone(Irish mobile) minimum Date is not working, so we are checking this condition---
                             */
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            String toDate = sdf.format(new Date());

                            if (!sDate.equals(toDate) && sdf.parse(sDate).before(new Date())) {
                                Utils.toast(context, context.getResources().getString(R.string.app_validDate));
                            } else {
                                if (Locale.getDefault().getLanguage().equals("ar")) {
                                    tv_date.setText(year1 + "-" + month_day[1] + "-" + month_day[0]);
                                } else {
                                    tv_date.setText(month_day[0] + "-" + month_day[1] + "-" + year1);
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);

        DatePicker datePicker = dpDialog.getDatePicker();
        datePicker.setMinDate(c.getTimeInMillis());

        dpDialog.setCancelable(false);
        dpDialog.show();
    }


    /**
     * Method to show Time Picker Dialog
     */
    public static void showTimePicker(Context context, final TextView tv_time) {

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(context, R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time

        mTimePicker.show();

    }


    private static String[] getDay_Month(String month, String day) {
        String[] month_day = new String[4];
        if (day.length() == 1) {
            day = "0" + day;
            month_day[0] = day;
        } else
            month_day[0] = day;
        if (month.length() == 1) {
            month = "0" + month;
            month_day[1] = month;
        } else
            month_day[1] = month;
        return month_day;
    }


    @SuppressLint("SimpleDateFormat")
    public static String changeDateFormat(String sDate) {

        String sFormattedDate = "";

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sFormattedDate = output.format(input.parse(sDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sFormattedDate;
    }


    /**
     * Function to get distance between two view
     */
    public static int getDistanceBetweenViews(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int b = firstView.getMeasuredHeight() + firstPosition[1];
        int t = secondPosition[1];
        return Math.abs(b - t);
    }


    /*
     * Function to check the Selected Time is greater than Current Time
     */
    public static boolean compareTwoTime(String selectedTime) {

        try {

            SimpleDateFormat dateFormat;
            if (Locale.getDefault().getLanguage().equals("ar")) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.ENGLISH);
            } else {
                dateFormat = new SimpleDateFormat("dd-MM-yyyy kk:mm", Locale.ENGLISH);
            }

            return dateFormat.parse(dateFormat.format(new Date())).before(dateFormat.parse(selectedTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    /*
     * Function to open Url on facebook
     */
    public static void facebookIntent(Context context, String url) {

        if (NetworkUtil.checkNetConnection(context)) {

            if (checkValidUrl(url)) {

                PackageManager packageManager = context.getPackageManager();
                Uri uri = Uri.parse(url);
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

            } else {
                Utils.toast(context, context.getResources().getString(R.string.app_validUrl));
            }

        } else {
            Utils.noInternetAlert(context);
        }
    }


    private static boolean checkValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
