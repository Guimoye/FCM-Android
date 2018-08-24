package com.example.user.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private EditText nombre,dni;
    private Button btn;
    private Activity activity;
    private String snombre,sdni,token;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token    =   FirebaseInstanceId.getInstance().getToken();
        activity        =   this;

        if(getIntent().getExtras()!=null){
           url = getIntent().getExtras().getString("url");
        }


        nombre  =   (EditText) findViewById(R.id.nombre);
        dni     =   (EditText) findViewById(R.id.dni);
        btn     =   (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snombre =   nombre.getText().toString();
                sdni    =   dni.getText().toString();


                Log.e("tokeeennn",token);
                if(snombre.length()!=0 && sdni.length()!=0){
                    Log.e("sqlll","http://beta.focusit.pe/Notificacion/request/ws_register.php?token="+token+"&nombre="+snombre+"&dni="+sdni+"");
                    RequestQueue queue          = Volley.newRequestQueue(activity);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                           // "http://192.168.1.120/Notificacion/request/ws_register.php?token="+token+"&nombre="+snombre+"&dni="+sdni+"",
                            "http://beta.focusit.pe/Notificacion/request/ws_register.php?token="+token+"&nombre="+snombre+"&dni="+sdni+"",




                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(response.length()==7){
                                        Toast.makeText(activity,"registrado con exito",Toast.LENGTH_SHORT).show();
                                    nombre.setText("");
                                    dni.setText("");
                                    }
                                /*
                                try {
                                    JSONObject jsonObject   = new JSONObject(response);
                                    Log.e("json",jsonObject.toString());
                                    if(jsonObject.getString("ok").equals("true")){
                                        startActivity(new Intent(Login_Activity.this, Home_Activity.class));
                                        finish();
                                    }else {
                                        //new MensajeToast().showToast(activity,activity.getResources().getString(R.string.comunicate));
                                        new Alert_img(activity,activity.getResources().getString(R.string.comunicate),true);
                                    }
                                } catch (JSONException e) {e.printStackTrace();}

*/
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity,"error en servidor",Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);
                }else{
                    Toast.makeText(activity,"llene todos los campos ",Toast.LENGTH_SHORT).show();
                }



            }
        });

      if(url !=null){
          Log.e("url",url);
          new Alert_img(activity,url);
      }
    }


}
