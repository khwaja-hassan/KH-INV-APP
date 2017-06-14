package com.example.khwaja.androidhive;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewProductActivity extends AppCompatActivity {


    EditText inputName, inputPrice, inputDesc;
    Button  btnCreateProduct;

    // url to create new product
    //private static String url_create_product = "http://api.androidhive.info/android_connect/create_product.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

        // Create button
        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
               // new CreateNewProduct().execute();
                String url_create_product = "http://192.168.1.2:8888/androidhive/create_product.php";
                StringRequest sq = new StringRequest(Request.Method.POST, url_create_product, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("Inside response");
                        //System.out.println(response);
                        String json = response ;

                        try {

                            JSONObject obj = new JSONObject(json);

                            Log.d("Androidhive", obj.toString());
                            Integer success = obj.getInt("success");
                            String message = obj.getString("message");
                            if(success == 0) {
                                System.out.println("Missing Data");
                                Toast.makeText(getApplicationContext(),"Error! Enter All the attributes for the product",Toast.LENGTH_LONG).show();
                            }
                            else {
                                System.out.println("Success");
                                Toast.makeText(getApplicationContext(),"Success! Add Another Product?",Toast.LENGTH_LONG).show();
                                EditText name=(EditText) findViewById(R.id.inputName);
                                name.setText("");
                                EditText price=(EditText) findViewById(R.id.inputPrice);
                                price.setText("");
                                EditText desc=(EditText) findViewById(R.id.inputDesc);
                                desc.setText("");

                            }


                        } catch (Throwable t) {
                            Log.e("Androidhive", "Could not parse malformed JSON: \"" + json + "\"");
                        }

                        //Toast.makeText(NewProductActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    VolleyError volleyError;
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //System.out.println(error);
                        //Toast.makeText(NewProductActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                        String json = null;

                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            switch(response.statusCode){
                                case 400:
                                    json = new String(response.data);
                                    json = trimMessage(json, "message");
                                    if(json != null) displayMessage(json);
                                    break;
                            }
                            //Additional cases
                        }
                    }

                    public String trimMessage(String json, String key){
                        String trimmedString = null;

                        try{
                            JSONObject obj = new JSONObject(json);
                            trimmedString = obj.getString(key);
                        } catch(JSONException e){
                            e.printStackTrace();
                            return null;
                        }

                        return trimmedString;
                    }

                    //Somewhere that has access to a context
                    public void displayMessage(String toastString){
                        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
                    }

                }) {
                    protected Map<String, String > getParams() {
                        Map<String, String> parr = new HashMap<String, String>();

                        parr.put("name", inputName.getText().toString());
                        parr.put("price", inputPrice.getText().toString());
                        parr.put("description", inputDesc.getText().toString());

                        return parr;

                    }
                };

                AppController.getInstance().addToRequestQueue(sq);
                //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });
    }


}