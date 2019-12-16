package com.example.design11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    TextView txtResult;
    ImageView imgScann;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtResult = findViewById(R.id.txtResult);
        imgScann = findViewById(R.id.imgScann);
        imgScann.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgScann:
               scanner();
        }
    }

    private void scanner() {
       IntentIntegrator intent = new IntentIntegrator(this);
       intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
       intent.setPrompt("ESCANEAR CÓDIGO");
       intent.setCameraId(0);
       intent.setOrientationLocked(false);
       intent.setBeepEnabled(false);
       intent.setCaptureActivity(CaptureActivityPortrait.class);
       intent.setBarcodeImageEnabled(false);
       intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents() == null){
                Toast.makeText(this, "CANCELASTE ESCANEO", Toast.LENGTH_SHORT).show();
                txtResult.setText("Scan result");
            }else{
                txtResult.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //guardamos en la variable t el texto del campo EditText
        String texto = txtResult.getText().toString();
        //lo "guardamos" en el Bundle
        guardaEstado.putString("text", texto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //recuperamos el String del Bundle
        String texto = recuperaEstado.getString("text");
        //Seteamos el valor del EditText con el valor de nuestra cadena
        txtResult.setText(texto);
    }
}
