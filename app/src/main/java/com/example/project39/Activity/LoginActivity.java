package com.example.project39.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project39.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private String username;
    private String password;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.loginbtn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = txtUsername.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()){
                    //si l'utilisateur ne rentre aucune donnee, toast avec message d'erreur apparait
                    String message = "Champs obligatoires";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else{
                    //authentification();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void authentification(){
        /*
        decommente la ligne avec url et remplace le par le lien vers ton script php en charge de l'authentification
         */
        String url = "http://IPADRESS/android/medicheck/connexion.php?username="+username+"&password="+password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                String message = "Erreur de connection";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    // dans la ligne ci-dessous le name doit correspondre au nom du parametre json renfermant le resultat
                    String status = jo.getString("status");
                    if(status.equals("KO")){
                        String message = "Parametres incorrects";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        //ci-dessous mettre la redirection vers la page suivante si authentification reussie
                        // utilise intent si c'est une activity ou bien getFragmentManager si c'est un autre fragment
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}