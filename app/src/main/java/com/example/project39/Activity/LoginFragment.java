package com.example.project39.Activity;

import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project39.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginFragment extends Fragment {
    private EditText txtUsername;
    private EditText txtPassword;
    private String username;
    private String password;
    private Button btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_login, container, false);
       txtUsername = view.findViewById(R.id.username);
       txtPassword = view.findViewById(R.id.password);
       btnSignUp = view.findViewById(R.id.loginbtn);
       btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               username = txtUsername.getText().toString().trim();
               password = txtPassword.getText().toString().trim();
               if(username.isEmpty() || password.isEmpty()){
                   //si l'utilisateur ne rentre aucune donnee, toast avec message d'erreur apparait
                   String message = "Champs obligatoires";
                   Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
               }
               else{

               }
           }
       });
       return view;
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
            public void onFailure(Call call, IOException e) {
                String message = "Erreur de connection";
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    // dans la ligne ci-dessous le name doit correspondre au nom du parametre json renfermant le resultat
                    String status = jo.getString("status");
                    if(status.equals("KO")){
                        String message = "Parametres incorrects";
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        //ci-dessous mettre la redirection vers la page suivante si authentification reussie
                        // utilise intent si c'est une activity ou bien getFragmentManager si c'est un autre fragment
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}