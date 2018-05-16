package com.icare_clinics.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Sreekanth.P on 17-08-2017.
 */

public class MyTextWatcher implements TextWatcher {
    boolean isChange=false;
    EditText et;
    public void setView(EditText view)
    {
        this.et = view;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (charSequence.toString().length()<=0){
            isChange = false;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(!isChange && charSequence.toString().length()<=2)
        {
            String str = et.getText().toString();
            isChange = true;
            et.setText(str.substring(0,1).toUpperCase() + str.substring(1));
            et.setSelection( et.getText().length(),  et.getText().length());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
