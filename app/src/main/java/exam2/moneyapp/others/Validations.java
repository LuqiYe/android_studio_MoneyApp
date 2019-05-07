package exam2.moneyapp.others;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import exam2.moneyapp.R;



/**
 * Created by BHUPESH on 3/1/2017.
 */

public class Validations {

    public static boolean valEdit1(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(activity.getString(R.string.edit_error_msg) + " " + name);
                return false;
            } else {
                return true;
            }
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(activity.getString(R.string.edit_error_msg) + " " + name);
            editText.requestFocus();
            return false;
        }
    }
    public static boolean valEdit(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditPassword1(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(activity.getString(R.string.edit_error_msg) + " " + name);
                return false;
            } else {
                if (editText.getText().toString().length() > 5) {
                    return true;
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(activity.getString(R.string.pass_lngth_error));
                    return false;
                }
            }
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(activity.getString(R.string.edit_error_msg) + " " + name);
            return false;
        }
    }
    public static boolean valEditPassword(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().length() > 5) {
                    return true;
                } else {
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.pass_lngth_error), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditEmail(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(activity.getString(R.string.edit_error_msg) + " " + name);
                return false;
            } else {
                if (editText.getText().toString().contains("@") && editText.getText().toString().contains(".")) {
                    return true;
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(activity.getString(R.string.invalid_email));
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditEmail(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().contains("@") && editText.getText().toString().contains(".")) {
                    return true;
                } else {
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditMob(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().length() <=12) {
                    String str = editText.getText().toString();
                    boolean valid = false;
                    for (int i = 0; i < str.length(); i++) {
                        try {
                            int x = Integer.parseInt(str.charAt(i) + "");
                            valid = true;
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.invalid_mobile), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    if (valid) {
                        return true;
                    } else {
                        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.invalid_mobile), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.invalid_mobile), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.edit_error_msg) + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
