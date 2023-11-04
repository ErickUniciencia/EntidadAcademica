package com.example.entidadacademica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAsignatura extends AppCompatActivity {
    private EditText nombreAsignaturaEditText, codigoAsignaturaEditText, creditosEditText;
    private AdminSqliteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_asignatura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_asignatura);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nombreAsignaturaEditText = findViewById(R.id.nombreAsignaturaEditText);
        codigoAsignaturaEditText = findViewById(R.id.codigoAsignaturaEditText);
        creditosEditText = findViewById(R.id.creditosEditText);
        dbHelper = new AdminSqliteOpenHelper(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void guardarAsignatura(View view) {
        String nombreAsignatura = nombreAsignaturaEditText.getText().toString();
        String codigoAsignatura = codigoAsignaturaEditText.getText().toString();
        String creditos = creditosEditText.getText().toString();

        if (!nombreAsignatura.isEmpty() && !codigoAsignatura.isEmpty() && !creditos.isEmpty()) {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(AdminSqliteOpenHelper.COLUMN_NOMBRE_ASIGNATURA, nombreAsignatura);
                values.put(AdminSqliteOpenHelper.COLUMN_CODIGO_ASIGNATURA, codigoAsignatura);
                values.put(AdminSqliteOpenHelper.COLUMN_CREDITOS, creditos);

                long newRowId = db.insert(AdminSqliteOpenHelper.TABLE_ASIGNATURAS, null, values);
                db.close();

                if (newRowId != -1) {
                    Toast.makeText(this, "Asignatura registrada con éxito", Toast.LENGTH_SHORT).show();
                    redirigirARegistroAsignatura();
                } else {
                    Log.e("CreateAsignatura", "Error al registrar la asignatura: newRowId == -1");
                    Toast.makeText(this, "Error al registrar la asignatura", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("CreateAsignatura", "Error al registrar la asignatura", e);
                Toast.makeText(this, "Error al registrar la asignatura", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirigirARegistroAsignatura() {
        Intent intent = new Intent(this, RegistroAsignatura.class);
        startActivity(intent);
        finish();
    }
}
