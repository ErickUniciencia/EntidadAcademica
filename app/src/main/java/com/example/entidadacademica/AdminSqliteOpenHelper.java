package com.example.entidadacademica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public AdminSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ESTUDIANTES );
        db.execSQL(DATABASE_CREATE_ASIGNATURAS);
        db.execSQL(DATABASE_CREATE_DOCENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASIGNATURAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCENTES);
        onCreate(db);
    }
}
