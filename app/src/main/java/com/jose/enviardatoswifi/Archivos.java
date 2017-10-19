package com.jose.enviardatoswifi;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jose.enviardatoswifi.Dispositivos;
import com.jose.enviardatoswifi.R;

import java.io.File;

public class Archivos extends AppCompatActivity implements View.OnClickListener{
    String boton ;
    Button musica,imagenes,documentos,contactos;
    final int fileRequestID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);

        musica = (Button)findViewById(R.id.btnmusica);
        imagenes = (Button)findViewById(R.id.btnimagenes);
        documentos = (Button)findViewById(R.id.btndocumentos);
        contactos = (Button)findViewById(R.id.btncontactos);
        musica.setOnClickListener(this);
        imagenes.setOnClickListener(this);
        documentos.setOnClickListener(this);
        contactos.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){

            case R.id.btnmusica:
                boton="musica";
                Toast.makeText(this, boton, Toast.LENGTH_SHORT).show();
                intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent,fileRequestID);

                break;
            case R.id.btncontactos:
                boton="contactos";

                break;
            case R.id.btnimagenes:
                boton= "imagenes";
                intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,fileRequestID);
                break;
            case R.id.btndocumentos:
                boton="docuementos";
                intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,fileRequestID);
                break;

        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK && requestCode==fileRequestID){
            Intent intent = new Intent(getApplicationContext(),Dispositivos.class);
            Uri uri = data.getData();
            String picturePath=uri.getPath();
            String [] filePathColumn = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);

            if (cursor!=null){
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath=cursor.getString(columnIndex);
                cursor.close();
            }
            File targeDir = new File(picturePath);
            if (targeDir.isFile()){
                if(targeDir.canRead()){
                  //  intent.putExtra("file",targeDir);

                }
            }
            startActivity(intent);
        }
    }
}
