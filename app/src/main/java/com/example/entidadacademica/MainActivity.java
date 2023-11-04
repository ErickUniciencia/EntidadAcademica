package com.example.entidadacademica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goRegistroEstudiante(View view){
        Intent i = new Intent (this, RegistroEstudiante.class);
        startActivity(i);
    }

    public void goRegistroAsignatura(View view){
        Intent i = new Intent (this, RegistroAsignatura.class);
        startActivity(i);
    }

    public void goRegistroDocente(View view){
        Intent i = new Intent (this, RegistroDocente.class);
        startActivity(i);
    }

    public void goMatricula(View view){
        Intent i = new Intent (this, MatriculaAsignatura.class);
        startActivity(i);
    }
}