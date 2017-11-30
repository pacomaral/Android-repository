package com.example.paco.ejercicio_4a_aad.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paco
 */

public class Adaptador_DB {

    //Definición de variables/objetos necesarios
    private static final String DATABASE_NAME = "db_ej4a.db";
    private static final String DATABASE_TABLE_STUDENTS = "estudiantes";
    private static final String DATABASE_TABLE_TEACHERS = "profesores";
    private static final int DATABASE_VERSION = 1;

    //Datos para las tablas
    private static final String NOMBRE_E = "nombre_estudiante", CICLO_E = "ciclo_estudiante", EDAD_E = "edad_estudiante", CURSO_E = "curso_estudiante", NOTA_E = "nota_media_estudiante";
    private static final String NOMBRE_P = "nombre_profesor", CICLO_P = "ciclo_profesor", EDAD_P = "edad_profesor", CURSO_P = "curso_profesor", DESPACHO_P = "despacho_profesor";

    //Sentencias para crear y borrar tablas
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "+DATABASE_TABLE_STUDENTS+" (_id integer primary key autoincrement, "+NOMBRE_E+" text, "+EDAD_E+" integer, "+CICLO_E+" text, "+CURSO_E+" integer, "+NOTA_E+" real);";
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "+DATABASE_TABLE_TEACHERS+" (_id integer primary key autoincrement, "+NOMBRE_P+" text, "+EDAD_P+" integer, "+CICLO_P+" text, "+CURSO_P+" integer, "+DESPACHO_P+" text);";
    private static final String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_STUDENTS;
    private static final String DROP_TABLE_TEACHERS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_TEACHERS;

    //Para poder obtener el contexto de la aplicación que utiliza la BBDD
    private Context contexto;
    //Clase que se utilizará para crear y actualizar la BBDD
    private DbHelper dbHelper;
    //Instancia bd
    private SQLiteDatabase db;

    //Constructor
    public Adaptador_DB(Context c){
        contexto=c;
        dbHelper = new DbHelper(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void abrir(){
        try{
            db = dbHelper.getWritableDatabase();
        }
        catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    //Método para insertar un estudiante en la BBDD
    public void insertarEstudiante(Estudiante e){

        //Recuperamos datos del estudiante en contentValues
        ContentValues datosEstudiante = e.crearContentValues();

        //Insertamos el nuevo registro a la bd
        db.insert(DATABASE_TABLE_STUDENTS, null, datosEstudiante);

    }

    //Método para insertar un profesor en la BBDD
    public void insertarProfesor(Profesor p){

        //Recuperamos datos del profesor en contentValues
        ContentValues datosProfesor = p.crearContentValues();

        //Insertamos el nuevo registro a la bd
        db.insert(DATABASE_TABLE_TEACHERS, null, datosProfesor);
    }

    public boolean borrarEstudiante(int id){

        //Si la Id es superior al número de filas de la tabla de estudiantes, no se podrá borrar esta.
        if(id > (int) DatabaseUtils.queryNumEntries(db, DATABASE_TABLE_STUDENTS)) {
            return false;
        }

        //Si se puede borrar, lo intentamos
        else{
            try {
                db.delete(
                        DATABASE_TABLE_STUDENTS,
                        "_id = ?",
                        new String[]{String.valueOf(id)});
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public boolean borrarProfesor(int id){
        //Si la Id es superior al número de filas de la tabla de estudiantes, no se podrá borrar esta.
        if(id > (int) DatabaseUtils.queryNumEntries(db, DATABASE_TABLE_TEACHERS)) {
            return false;
        }

        //Si se puede borrar, lo intentamos
        else{
            try {
                db.delete(
                        DATABASE_TABLE_TEACHERS,
                        "_id = ?",
                        new String[]{String.valueOf(id)});
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    //Método para borrar completamente la BD
    //public boolean borrarBD(String nombreBD){
        //Boolean b = db.deleteDatabase(nombreBD);
    //}


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Crearemos las dos tablas en la bbdd
            db.execSQL(CREATE_TABLE_STUDENTS);
            db.execSQL(CREATE_TABLE_TEACHERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_STUDENTS);
            db.execSQL(DROP_TABLE_TEACHERS);
            onCreate(db);
        }

    }
}
