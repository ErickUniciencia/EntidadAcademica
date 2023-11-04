package com.example.entidadacademica;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AdminSqliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EstudiantesDB.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ESTUDIANTES = "estudiantes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DOCUMENTO = "documento";
    public static final String COLUMN_TELEFONO = "telefono"; // Nueva columna para el teléfono
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_SEXO = "sexo";

    public static final String TABLE_ASIGNATURAS = "asignaturas";
    public static final String COLUMN_ID_ASIGNATURA = "_id";
    public static final String COLUMN_NOMBRE_ASIGNATURA = "nombre_asignatura";
    public static final String COLUMN_CODIGO_ASIGNATURA = "codigo_asignatura";
    public static final String COLUMN_CREDITOS = "creditos";

    public static final String TABLE_DOCENTES = "docentes";
    public static final String COLUMN_ID_DOCENTE = "_id";
    public static final String COLUMN_NOMBRE_DOCENTE = "nombre_docente";
    public static final String COLUMN_DOCUMENTO_DOCENTE = "documento_docente";
    public static final String COLUMN_ESPECIALIDAD = "especialidad";

    public static final String TABLE_MATRICULA_ASIGNATURA = "matricula_asignatura";
    public static final String COLUMN_ID_MATRICULA_ASIGNATURA = "_id";
    public static final String COLUMN_MA_ID_ESTUDIANTE = "id_estudiante";
    public static final String COLUMN_MA_ID_ASIGNATURA = "id_asignatura";
    public static final String COLUMN_MA_ID_DOCENTE = "id_docente";

    // Sentencia SQL para crear la tabla de estudiantes
    private static final String DATABASE_CREATE_ESTUDIANTES  = "create table " + TABLE_ESTUDIANTES + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NOMBRE + " text not null, " +
            COLUMN_DOCUMENTO + " text not null, " +
            COLUMN_TELEFONO + " text not null, " +
            COLUMN_DIRECCION + " text not null, " +
            COLUMN_SEXO + " text not null);";

    private static final String DATABASE_CREATE_ASIGNATURAS = "create table " + TABLE_ASIGNATURAS + " (" +
            COLUMN_ID_ASIGNATURA + " integer primary key autoincrement, " +
            COLUMN_NOMBRE_ASIGNATURA + " text not null, " +
            COLUMN_CODIGO_ASIGNATURA + " text not null, " +
            COLUMN_CREDITOS + " integer not null);";

    private static final String DATABASE_CREATE_DOCENTES = "create table " + TABLE_DOCENTES + " (" +
            COLUMN_ID_DOCENTE + " integer primary key autoincrement, " +
            COLUMN_NOMBRE_DOCENTE + " text not null, " +
            COLUMN_DOCUMENTO_DOCENTE + " text not null, " +
            COLUMN_ESPECIALIDAD + " text not null);";

    private static final String DATABASE_CREATE_MATRICULA_ASIGNATURA = "CREATE TABLE " + TABLE_MATRICULA_ASIGNATURA + " (" +
            COLUMN_ID_MATRICULA_ASIGNATURA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MA_ID_ESTUDIANTE + " INTEGER, " +
            COLUMN_MA_ID_ASIGNATURA + " INTEGER, " +
            COLUMN_MA_ID_DOCENTE + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_MA_ID_ESTUDIANTE + ") REFERENCES " + TABLE_ESTUDIANTES + "(" + COLUMN_ID + "), " +
            "FOREIGN KEY (" + COLUMN_MA_ID_ASIGNATURA + ") REFERENCES " + TABLE_ASIGNATURAS + "(" + COLUMN_ID + "), " +
            "FOREIGN KEY (" + COLUMN_MA_ID_DOCENTE + ") REFERENCES " + TABLE_DOCENTES + "(" + COLUMN_ID + "));";

    public AdminSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ESTUDIANTES );
        db.execSQL(DATABASE_CREATE_ASIGNATURAS);
        db.execSQL(DATABASE_CREATE_DOCENTES);
        db.execSQL(DATABASE_CREATE_MATRICULA_ASIGNATURA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASIGNATURAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCENTES);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_MATRICULA_ASIGNATURA);
        onCreate(db);
    }

    public ArrayList<Estudiante> obtenerEstudiantes() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Realiza una consulta SQL para obtener los estudiantes
        String query = "SELECT * FROM " + TABLE_ESTUDIANTES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Lee los datos del cursor y crea objetos Estudiante
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));

                Estudiante estudiante = new Estudiante(id, nombre);
                estudiantes.add(estudiante);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return estudiantes;
    }

    // Método para obtener una lista de asignaturas desde la base de datos
    public ArrayList<Asignatura> obtenerAsignaturas() {
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Realiza una consulta SQL para obtener las asignaturas
        String query = "SELECT * FROM " + TABLE_ASIGNATURAS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Lee los datos del cursor y crea objetos Asignatura
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ASIGNATURA));
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_ASIGNATURA));

                Asignatura asignatura = new Asignatura(id, nombre);
                asignaturas.add(asignatura);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return asignaturas;
    }

    // Método para obtener una lista de docentes desde la base de datos
    public ArrayList<Docente> obtenerDocentes() {
        ArrayList<Docente> docentes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Realiza una consulta SQL para obtener los docentes
        String query = "SELECT * FROM " + TABLE_DOCENTES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Lee los datos del cursor y crea objetos Docente
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DOCENTE));
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_DOCENTE));

                Docente docente = new Docente(id, nombre);
                docentes.add(docente);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return docentes;
    }
    public ArrayList<ListMatriculaAsignatura> obtenerMatriculasAsignaturasConNombres() {
        ArrayList<ListMatriculaAsignatura> matriculas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Realiza una consulta SQL para obtener las matrículas de asignaturas con los nombres
        String query = "SELECT " +
                "ma." + COLUMN_ID_MATRICULA_ASIGNATURA + ", " +
                "ma." + COLUMN_MA_ID_ESTUDIANTE + ", " +
                "ma." + COLUMN_MA_ID_ASIGNATURA + ", " +
                "ma." + COLUMN_MA_ID_DOCENTE + ", " +
                "e." + COLUMN_NOMBRE + " AS nombre_estudiante, " +
                "a." + COLUMN_NOMBRE_ASIGNATURA + " AS nombre_asignatura, " +
                "d." + COLUMN_NOMBRE_DOCENTE + " AS nombre_docente " +
                "FROM " + TABLE_MATRICULA_ASIGNATURA + " AS ma " +
                "INNER JOIN " + TABLE_ESTUDIANTES + " AS e ON ma." + COLUMN_MA_ID_ESTUDIANTE + " = e." + COLUMN_ID + " " +
                "INNER JOIN " + TABLE_ASIGNATURAS + " AS a ON ma." + COLUMN_MA_ID_ASIGNATURA + " = a." + COLUMN_ID_ASIGNATURA + " " +
                "INNER JOIN " + TABLE_DOCENTES + " AS d ON ma." + COLUMN_MA_ID_DOCENTE + " = d." + COLUMN_ID_DOCENTE;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Lee los datos del cursor y crea objetos ListMatriculaAsignatura
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MATRICULA_ASIGNATURA));
                int idEstudiante = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_ID_ESTUDIANTE));
                int idAsignatura = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_ID_ASIGNATURA));
                int idDocente = cursor.getInt(cursor.getColumnIndex(COLUMN_MA_ID_DOCENTE));
                String nombreEstudiante = cursor.getString(cursor.getColumnIndex("nombre_estudiante"));
                String nombreAsignatura = cursor.getString(cursor.getColumnIndex("nombre_asignatura"));
                String nombreDocente = cursor.getString(cursor.getColumnIndex("nombre_docente"));

                ListMatriculaAsignatura matricula = new ListMatriculaAsignatura(id, idEstudiante, idAsignatura, idDocente);
                matricula.setNombreEstudiante(nombreEstudiante);
                matricula.setNombreAsignatura(nombreAsignatura);
                matricula.setNombreDocente(nombreDocente);

                matriculas.add(matricula);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return matriculas;
    }


}
