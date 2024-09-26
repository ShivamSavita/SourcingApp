package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softeksol.paisalo.jlgsourcing.R;

public class OTPDialog extends AlertDialog {

    private EditText otpEditText;
    private Button submitButton;
    private OTPDialogListener listener;

    public OTPDialog(Context context, OTPDialogListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.otp_dialog_layout, null);
        setView(view);

        otpEditText = view.findViewById(R.id.editTextOTP);
        submitButton = view.findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpEditText.getText().toString();
                listener.onOTPProvided(otp);
                dismiss();
            }
        });
    }

    public interface OTPDialogListener {
        void onOTPProvided(String otp);
    }
}
