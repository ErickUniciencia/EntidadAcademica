package com.example.entidadacademica;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatriculaAsignatura extends AppCompatActivity {

    private ListView listViewAsignaturas;
    private ArrayAdapter<String> asignaturasAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matricula_asignatura);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listViewAsignaturas = findViewById(R.id.asignaturasListView);
        asignaturasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewAsignaturas.setAdapter(asignaturasAdapter);

        searchView = findViewById(R.id.searchView);
        // Agregar un TextWatcher al SearchView para la búsqueda en tiempo real
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No es necesario en este caso
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Realizar la búsqueda en la lista y actualizarla
                asignaturasAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Llama a la función para cargar y mostrar los registros de estudiantes
        cargarListaMateriaAsignaturas();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void goCreate(View view){
        Intent i = new Intent (this, CreateMatriculaAsignatura.class);
        startActivity(i);
    }

    private void cargarListaMateriaAsignaturas() {
        // Abre la base de datos en modo lectura
        AdminSqliteOpenHelper dbHelper = new AdminSqliteOpenHelper(this);
        ArrayList<ListMatriculaAsignatura> matriculas = dbHelper.obtenerMatriculasAsignaturasConNombres();

        if (matriculas != null && !matriculas.isEmpty()) {
            // Limpia el adaptador existente del ListView
            asignaturasAdapter.clear();

            // Agrega los datos de las matrículas al adaptador del ListView
            for (ListMatriculaAsignatura matricula : matriculas) {
                String nombreEstudiante = matricula.getNombreEstudiante();
                String nombreAsignatura = matricula.getNombreAsignatura();
                String nombreDocente = matricula.getNombreDocente();

                // Agrega los datos al adaptador
                String matriculaString = "Estudiante: " + nombreEstudiante + "\n" +
                        "Asignatura: " + nombreAsignatura + "\n" +
                        "Docente: " + nombreDocente;
                asignaturasAdapter.add(matriculaString);
            }
        }  else {
            // Maneja el caso en el que no se obtengan matrículas de asignaturas
        }
    }
}

