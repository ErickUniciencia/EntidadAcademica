package com.example.entidadacademica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateDocente extends AppCompatActivity {
    private EditText nombreDocenteEditText, documentoDocenteEditText, especialidadEditText;
    private AdminSqliteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_docente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_docente);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nombreDocenteEditText = findViewById(R.id.nombreDocenteEditText);
        documentoDocenteEditText = findViewById(R.id.documentoDocenteEditText);
        especialidadEditText = findViewById(R.id.especialidadEditText);
        dbHelper = new AdminSqliteOpenHelper(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void guardarDocente(View view) {
        String nombreDocente = nombreDocenteEditText.getText().toString();
        String documentoDocente = documentoDocenteEditText.getText().toString();
        String especialidad = especialidadEditText.getText().toString();

        if (!nombreDocente.isEmpty() && !documentoDocente.isEmpty() && !especialidad.isEmpty()) {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(AdminSqliteOpenHelper.COLUMN_NOMBRE_DOCENTE, nombreDocente);
                values.put(AdminSqliteOpenHelper.COLUMN_DOCUMENTO_DOCENTE, documentoDocente);
                values.put(AdminSqliteOpenHelper.COLUMN_ESPECIALIDAD, especialidad);

                long newRowId = db.insert(AdminSqliteOpenHelper.TABLE_DOCENTES, null, values);
                db.close();

                if (newRowId != -1) {
                    Toast.makeText(this, "Docente registrado con éxito", Toast.LENGTH_SHORT).show();
                    redirigirARegistroDocente();
                } else {
                    Log.e("CreateDocente", "Error al registrar el docente: newRowId == -1");
                    Toast.makeText(this, "Error al registrar el docente", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("CreateDocente", "Error al registrar el docente", e);
                Toast.makeText(this, "Error al registrar el docente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirigirARegistroDocente() {
        Intent intent = new Intent(this, RegistroDocente.class);
        startActivity(intent);
        finish();
    }
}
