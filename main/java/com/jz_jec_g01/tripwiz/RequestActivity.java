package com.jz_jec_g01.tripwiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RequestActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText editTextMailAddress;
    private Button btnRequest;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        mAuth = FirebaseAuth.getInstance();
        btnRequest = findViewById(R.id.btnRequest);
        editTextMailAddress = findViewById(R.id.editTextMailAddress);

        InputFilter inputFilterMail = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(source.toString().matches("^[0-9a-zA-Z@¥.¥_¥¥-]$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };

        InputFilter[] filtersMail = new InputFilter[] { inputFilterMail };
        editTextMailAddress.setFilters(filtersMail);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailAddress = editTextMailAddress.getText().toString();
                EditText mailErr = findViewById(R.id.editTextMailAddress);


                if(!mailAddress.isEmpty()) {
                    sendPasswordReset(mailAddress);
                    /**
                     * 送信ボタンクリック時アラート
                     */
                    Toast.makeText(getApplicationContext(),"ボタンがクリックされました。", Toast.LENGTH_SHORT).show();
                    /**入力リセット*/
                    editTextMailAddress.setText(null);
                } else if(!mailAddress.isEmpty()) {
                    /**
                     * 送信ボタンクリック時エラーアラート
                     */
                    mailErr.setError("メールアドレスを入力してください");
                } else {
                    Toast.makeText(getApplicationContext(), "メールアドレスが\n入力されていません", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * FireBaseパスワードリクエストクラス
     * firebaseにemailの値を渡す役割
     * @param email
     */
    public void sendPasswordReset(String email) {
        // [START send_password_reset]

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "sendPasswordResetEmail", task.getException());
                            Toast.makeText(RequestActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END send_password_reset]
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            findViewById(R.id.editTextMailAddress).setVisibility(View.GONE);
            findViewById(R.id.btnRequest).setVisibility(View.GONE);
        } else {
            findViewById(R.id.editTextMailAddress).setVisibility(View.VISIBLE);
            findViewById(R.id.btnRequest).setVisibility(View.VISIBLE);
        }
    }
}
