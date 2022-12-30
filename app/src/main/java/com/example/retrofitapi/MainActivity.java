package com.example.retrofitapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;

    String api="https://reqres.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdt.getText().toString().isEmpty() || jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEdt.getText().toString(), jobEdt.getText().toString());
            }
        });
    }

    private void postData(String userName,String job) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        DataModel model = new DataModel(userName, job);
        Call<DataModel> call = retrofitAPI.createPost(model);

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                jobEdt.setText("");
                nameEdt.setText("");
                DataModel data = response.body();
                String details = "Response Code : " + response.code() + "\nName : " +data.getName() + "\n" + "Job : " + data.getJob();
                responseTV.setText(details);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}

