package com.example.mytranslate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button imageBtn,translateBtn;
    EditText inputTextView;
    TextView resultView;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = Volley.newRequestQueue(this);
        resultView = findViewById(R.id.resultView);
        translateBtn = findViewById(R.id.translateView);
        imageBtn = findViewById(R.id.imageBtn);

        Translate();
        Refresh();

    }

    private void Translate() {

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();


            }
        });

    }

    private void Refresh() {
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputTextView != null) {
                    inputTextView.setText(null);
                    resultView.setText(null);
                }
            }
        });

    }

    private void sendRequest() {
        inputTextView = findViewById(R.id.inputTextView);
        String inputText = inputTextView.getText().toString().toLowerCase();
        if (inputText.isEmpty()) {
            inputTextView.setError("Type Word!");
            inputTextView.requestFocus();
        }

        String url = "https://translate.ge/api/" + inputText;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("value");
                    String word = jsonObject1.getString("Text");
                    resultView.setText(word);


                } catch (JSONException e) {
                    resultView.setText("The word was not found!/ასეთი სიტყვა არ მოიძებნა!");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);
    }
}
