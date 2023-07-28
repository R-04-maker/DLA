package com.astra.polytechnic.helper;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ui.activity.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationHelper {
    public static boolean requiredTextInputValidation(TextInputLayout textInputLayout) {
        String value = textInputLayout.getEditText().getText().toString();

        boolean result = true;
        if(!TextUtils.isEmpty(value)) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setError("Input required!");
            textInputLayout.setErrorEnabled(true);
            shakeEditText(textInputLayout);
            result = false;
        }

        return result;
    }
    public static void shakeEditText(TextInputLayout editText) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(editText.getContext(), R.anim.shake_validation);
        editText.startAnimation(shakeAnimation);
    }
    public static boolean confirmationValidation(TextInputLayout editText1, TextInputLayout editText2) {
        String text1 = editText1.getEditText().getText().toString().trim();
        String text2 = editText2.getEditText().getText().toString().trim();

        if (!text1.equals(text2)) {
            editText2.setError("Password tidak cocok");
            editText2.setErrorEnabled(true);
            shakeEditText(editText2);
            return false;
        }
        return true;
    }
}
