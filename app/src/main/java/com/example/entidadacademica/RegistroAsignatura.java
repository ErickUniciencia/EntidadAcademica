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

public class RegistroAsignatura extends AppCompatActivity {

    private ListView listViewAsignaturas;
    private ArrayAdapter<String> asignaturasAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_asignatura);
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
        cargarListaAsignaturas();

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
        Intent i = new Intent (this, CreateAsignatura.class);
        startActivity(i);
    }

    private void cargarListaAsignaturas() {
        // Abre la base de datos en modo lectura
        AdminSqliteOpenHelper dbHelper = new AdminSqliteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define las columnas que deseas mostrar en la lista
        String[] columns = {AdminSqliteOpenHelper.COLUMN_NOMBRE_ASIGNATURA, AdminSqliteOpenHelper.COLUMN_CODIGO_ASIGNATURA, AdminSqliteOpenHelper.COLUMN_CREDITOS};

        // Realiza una consulta a la tabla de asignaturas y obtén un cursor
        Cursor cursor = db.query(AdminSqliteOpenHelper.TABLE_ASIGNATURAS, columns, null, null, null, null, null);

        // Limpia el adaptador antes de agregar nuevos datos
        asignaturasAdapter.clear();

        // Recorre el cursor y agrega los datos a la lista
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndex(AdminSqliteOpenHelper.COLUMN_NOMBRE_ASIGNATURA));
            String codigo = cursor.getString(cursor.getColumnIndex(AdminSqliteOpenHelper.COLUMN_CODIGO_ASIGNATURA));
            String creditos = cursor.getString(cursor.getColumnIndex(AdminSqliteOpenHelper.COLUMN_CREDITOS));
            asignaturasAdapter.add("Codigo: "+codigo + "\nNombre: " + nombre + "\nCreditos: " + creditos);
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
    }
}

