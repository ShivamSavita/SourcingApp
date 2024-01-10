package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.softeksol.paisalo.jlgsourcing.R;

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.circular_progressbar);
        setCancelable(false); // Prevent dismissal by tapping outside
    }
}
