package com.astra.polytechnic.helper;

import android.widget.EditText;

public class ValidationHelper {
    public static boolean requiredTextInputValidation(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Teks diperlukan");
            return false;
        }
        return true;
    }

    public static boolean confirmationValidation(EditText editText1, EditText editText2) {
        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();

        if (!text1.equals(text2)) {
            editText2.setError("Teks tidak cocok");
            return false;
        }
        return true;
    }
}
