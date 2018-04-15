package com.monami.mrc.box8login;


import android.content.Intent;
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
public class SignUpFragment extends Fragment {
    TextInputLayout nameLayout;
    TextInputLayout phoneLayout;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    TextInputEditText name;
    TextInputEditText phone;
    TextInputEditText email;
    TextInputEditText password;
    String nameValue;
    String phoneValue;
    String emailValue;
    String passwordValue;
    Button signUpBtn;
    RelativeLayout signUpLayout;
    HashMap<String, String> details;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        nameLayout = view.findViewById(R.id.name_layout);
        phoneLayout = view.findViewById(R.id.phone_layout);
        emailLayout = view.findViewById(R.id.email_layout);
        passwordLayout = view.findViewById(R.id.password_layout);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        signUpBtn = view.findViewById(R.id.signup);
        signUpLayout = view.findViewById(R.id.signup_layout);
        final DBHelper dbHelper = new DBHelper(getActivity());
        details = new HashMap<>();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameValue = name.getText().toString().trim();
                phoneValue = phone.getText().toString().trim();
                emailValue = email.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
                details.put("email", "");
                details.put("phone", "");
                details.put("password", "");
                if(signUpValidator(phoneValue, emailValue)) {
                    if (dbHelper.emailPhoneChecker(details)){
//                        Toast.makeText(getActivity(), "Email/Phone Number already exists", Toast.LENGTH_SHORT).show();
                        Snackbar.make(signUpLayout, "Email/Mobile already exists",Snackbar.LENGTH_SHORT).show();
                    }else{
                        dbHelper.insertData(nameValue, phoneValue, emailValue, passwordValue);
                        Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginSuccessActivity.class);
                        getActivity().startActivity(intent);


                    }
//                Toast.makeText(getActivity(), "Name:"+nameValue+" Phone:"+phoneValue+" Email:"+emailValue+
//                        " Password:"+passwordValue,Toast.LENGTH_SHORT).show();

                }else{
                    Snackbar.make(signUpLayout, "Invalid Email/Mobile",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean signUpValidator(String phoneValue, String emailValue){
        boolean flag = false;
        if ((isValidEmail(emailValue)) && (phoneValue.matches("^[0-9]{10}$"))) {
            details.put("email", emailValue);
            details.put("phone", phoneValue);
            flag = true;
        }
        return flag;
    }

}
