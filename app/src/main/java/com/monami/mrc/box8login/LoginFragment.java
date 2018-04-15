package com.monami.mrc.box8login;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    TextInputLayout emailOrPhoneLayout;
    TextInputLayout passwordLayout;
    TextInputEditText emailOrPhone;
    TextInputEditText password;
    String emailOrPhoneValue;
    String passwordValue;
    Button loginBtn;
    HashMap<String, String> details;
    RelativeLayout loginLayout;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailOrPhoneLayout = view.findViewById(R.id.emailorphone_layout);
        passwordLayout = view.findViewById(R.id.password_layout);
        emailOrPhone = view.findViewById(R.id.emailorphone);
        password = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.login);
        loginLayout = view.findViewById(R.id.login_layout);
        details = new HashMap<>();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailOrPhoneValue = emailOrPhone.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
                DBHelper dbHelper = new DBHelper(getActivity());
                details.put("email", "");
                details.put("phone", "");
                details.put("password", "");
                if(loginValidator(emailOrPhoneValue)) {
                    details.put("password", passwordValue);
                    final Cursor cursor = dbHelper.getData(details);
                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                            String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
                            Intent intent = new Intent(getActivity(), LoginSuccessActivity.class);
                            startActivity(intent);


                        } while (cursor.moveToNext());
                    } else {
                        Snackbar.make(loginLayout, "Record Not Found",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(loginLayout, "Invalid Email/Mobile",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean loginValidator(String emailOrPhoneValue){
        boolean flag = false;
        if (isValidEmail(emailOrPhoneValue)){
            details.put("email", emailOrPhoneValue);
            flag = true;
        }
        else if (emailOrPhoneValue.matches("^[0-9]{10}$")){
            details.put("phone", emailOrPhoneValue);
            flag = true;
        }
        return flag;
    }

}
