package com.example.entidadacademica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateEstudiante extends AppCompatActivity {
    private EditText nombreEditText, documentoEditText, telefonoEditText, direccionEditText;
    private Spinner sexoSpinner;
    private AdminSqliteOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_estudiante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nombreEditText = findViewById(R.id.nombreEditText);
        documentoEditText = findViewById(R.id.documentoEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        direccionEditText = findViewById(R.id.direccionEditText);
        sexoSpinner = findViewById(R.id.sexoSpinner);
        dbHelper = new AdminSqliteOpenHelper(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        // Manejar la navegación hacia atrás
        onBackPressed();
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle arrow click here
//        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void guardarEstudiante(View view) {
        String nombre = nombreEditText.getText().toString();
        String documento = documentoEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String direccion = direccionEditText.getText().toString();
        String sexo = sexoSpinner.getSelectedItem().toString();

        if (!nombre.isEmpty() && !documento.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() && !sexo.isEmpty()) {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(AdminSqliteOpenHelper.COLUMN_NOMBRE, nombre);
                values.put(AdminSqliteOpenHelper.COLUMN_DOCUMENTO, documento);
                values.put(AdminSqliteOpenHelper.COLUMN_TELEFONO, telefono);
                values.put(AdminSqliteOpenHelper.COLUMN_DIRECCION, direccion);
                values.put(AdminSqliteOpenHelper.COLUMN_SEXO, sexo);

                long newRowId = db.insert(AdminSqliteOpenHelper.TABLE_ESTUDIANTES, null, values);
                db.close();

                if (newRowId != -1) {
                    Toast.makeText(this, "Estudiante guardado con éxito", Toast.LENGTH_SHORT).show();
                    redirigirARegistroEstudiante();
                } else {
                    Log.e("CreateEstudiante", "Error al guardar el estudiante: newRowId == -1");
                    Toast.makeText(this, "Error al guardar el estudiante", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("CreateEstudiante", "Error al guardar el estudiante", e);
                Toast.makeText(this, "Error al guardar el estudiante", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirigirARegistroEstudiante() {
        Intent intent = new Intent(this, RegistroEstudiante.class);
        startActivity(intent);
        finish();
    }
}
