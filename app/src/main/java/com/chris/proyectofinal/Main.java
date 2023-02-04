package com.chris.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEdt, usuarioEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        nameEdt = findViewById(R.id.TextName);
        usuarioEdt = findViewById(R.id.textUser);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);


        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEdt.getText().toString().isEmpty() && usuarioEdt.getText().toString().isEmpty()) {
                    Toast.makeText(Main.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(nameEdt.getText().toString(), usuarioEdt.getText().toString());
            }
        });
    }

    private void postData(String name, String usuario) {


        loadingPB.setVisibility(View.VISIBLE);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/users/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        DataModal modal = new DataModal(name, usuario);


        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(Main.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                usuarioEdt.setText("");
                nameEdt.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getUsuario();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}