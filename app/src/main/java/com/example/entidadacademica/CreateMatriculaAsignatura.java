package com.example.entidadacademica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class CreateMatriculaAsignatura extends AppCompatActivity {
    private Spinner idEstudianteSpinner, idAsignaturaSpinner, idDocenteSpinner;

    private AdminSqliteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_matricula_asignatura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_matricula_asignatura);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        idEstudianteSpinner = findViewById(R.id.idEstudianteSpinner);
        idAsignaturaSpinner = findViewById(R.id.idAsignaturaSpinner);
        idDocenteSpinner = findViewById(R.id.idDocenteSpinner);
        dbHelper = new AdminSqliteOpenHelper(this);

        cargarDatosEnSpinners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void cargarDatosEnSpinners() {
        // Supongamos que tienes funciones para obtener los datos de la base de datos:
         ArrayList<Estudiante> estudiantes = dbHelper.obtenerEstudiantes();
         ArrayList<Asignatura> asignaturas = dbHelper.obtenerAsignaturas();
         ArrayList<Docente> docentes = dbHelper.obtenerDocentes();

        // Crea adaptadores para los Spinners
        ArrayAdapter<Estudiante> estudianteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estudiantes);
        estudianteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idEstudianteSpinner.setAdapter(estudianteAdapter);

        ArrayAdapter<Asignatura> asignaturaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, asignaturas);
        asignaturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idAsignaturaSpinner.setAdapter(asignaturaAdapter);

        ArrayAdapter<Docente> docenteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docentes);
        docenteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idDocenteSpinner.setAdapter(docenteAdapter);
    }

    public void guardarMatriculaAsignatura(View view) {
        Estudiante estudiante = (Estudiante) idEstudianteSpinner.getSelectedItem();
        Asignatura asignatura = (Asignatura) idAsignaturaSpinner.getSelectedItem();
        Docente docente = (Docente) idDocenteSpinner.getSelectedItem();

        if (estudiante != null && asignatura != null && docente != null) {
            try {
                int idEstudiante = estudiante.getId(); // Obtener el ID del estudiante
                int idAsignatura = asignatura.getId(); // Obtener el ID de la asignatura
                int idDocente = docente.getId();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(AdminSqliteOpenHelper.COLUMN_MA_ID_ESTUDIANTE, idEstudiante);
                values.put(AdminSqliteOpenHelper.COLUMN_MA_ID_ASIGNATURA, idAsignatura);
                values.put(AdminSqliteOpenHelper.COLUMN_MA_ID_DOCENTE, idDocente);

                long newRowId = db.insert(AdminSqliteOpenHelper.TABLE_MATRICULA_ASIGNATURA, null, values);
                db.close();

                if (newRowId != -1) {
                    Toast.makeText(this, "Matrícula de asignatura registrada con éxito", Toast.LENGTH_SHORT).show();
                    redirigirARegistroMatriculaAsignatura();
                } else {
                    Log.e("CreateMatriculaAsignatura", "Error al registrar la matrícula de asignatura: newRowId == -1");
                    Toast.makeText(this, "Error al registrar la matrícula de asignatura", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("CreateMatriculaAsignatura", "Error al registrar la matrícula de asignatura", e);
                Toast.makeText(this, "Error al registrar la matrícula de asignatura", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirigirARegistroMatriculaAsignatura() {
        Intent intent = new Intent(this, MatriculaAsignatura.class);
        startActivity(intent);
        finish();
    }
}
