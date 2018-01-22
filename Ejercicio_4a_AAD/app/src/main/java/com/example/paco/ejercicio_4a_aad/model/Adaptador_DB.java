package com.example.paco.ejercicio_4a_aad.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by Paco
 */

public class Adaptador_DB {

    //Definición de variables/objetos necesarios
    private static final String DATABASE_NAME = "db_ej4a.db";
    private static final String DATABASE_TABLE_STUDENTS = "estudiantes";
    private static final String DATABASE_TABLE_TEACHERS = "profesores";
    //Ej examen
    private static final String DATABASE_TABLE_ASIGNATURAS = "asignaturas";
    private static final int DATABASE_VERSION = 1;

    //Datos para las tablas
    private static final String NOMBRE_E = "nombre_estudiante", CICLO_E = "ciclo_estudiante", EDAD_E = "edad_estudiante", CURSO_E = "curso_estudiante", NOTA_E = "nota_media_estudiante";
    private static final String NOMBRE_P = "nombre_profesor", CICLO_P = "ciclo_profesor", EDAD_P = "edad_profesor", CURSO_P = "curso_profesor", DESPACHO_P = "despacho_profesor";
    //Ej examen
    private static final String NOMBRE_A = "nombre_asignatura", HORAS_A = "horas_asignatura";

    //Sentencias para crear y borrar tablas
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "+DATABASE_TABLE_STUDENTS+" (_id integer primary key autoincrement, "+NOMBRE_E+" text, "+EDAD_E+" integer, "+CICLO_E+" text, "+CURSO_E+" integer, "+NOTA_E+" text);";
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "+DATABASE_TABLE_TEACHERS+" (_id integer primary key autoincrement, "+NOMBRE_P+" text, "+EDAD_P+" integer, "+CICLO_P+" text, "+CURSO_P+" integer, "+DESPACHO_P+" text);";
    private static final String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_STUDENTS;
    private static final String DROP_TABLE_TEACHERS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_TEACHERS;
    //Ej examen
    private static final String DROP_TABLE_ASIGNATURAS = "DROP TABLE IF EXISTS "+DATABASE_TABLE_ASIGNATURAS;
    private static final String CREATE_TABLE_ASIGNATURAS = "CREATE TABLE "+DATABASE_TABLE_ASIGNATURAS+" (_id integer primary key autoincrement, "+NOMBRE_A+" text, "+HORAS_A+" text);";            //Las horas podrían ser Integer

    //Para poder obtener el contexto de la aplicación que utiliza la BBDD
    private Context contexto;
    //Clase que se utilizará para crear y actualizar la BBDD
    private DbHelper dbHelper;
    //Instancia bd
    private SQLiteDatabase db;

    //En esta lista guardaremos los id's de los elementos mostrados en las consultas
    private ArrayList<Integer> listaIds;

    /**
     *  CONSTRUCTOR
     */
    public Adaptador_DB(Context c){
        contexto=c;
        dbHelper = new DbHelper(contexto, DATABASE_NAME, null, DATABASE_VERSION);

        listaIds = new ArrayList<Integer>();
    }

    /**
     *  MÉTODOS
     */

    public void abrir(){
        try{
            db = dbHelper.getWritableDatabase();
        }
        catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void cerrar(){
        db.close();
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


    //Método para insertar una asignatura en la BBDD
    public void insertarAsignatura(Asignatura a){

        //Recuperamos datos de la asignatura en ContentValues - Método creado en Asignatura.class
        ContentValues datosAsignatura = a.crearContentValues();

        //Insertamos el nuevo registro en la bd
        db.insert(DATABASE_TABLE_ASIGNATURAS, null, datosAsignatura);

    }

    public boolean borrarEstudiante(int id){

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

    public boolean borrarProfesor(int id){

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

    //Método para borrar completamente la BD
    public boolean borrarBD(Context contexto, String nombreBD){
        //Se intenta borrar
        try{
            contexto.deleteDatabase(nombreBD);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


    //Método para obtener todos los estudiantes y los profesores
    public ArrayList<String> obtenerTodos(){

        listaIds.clear();

        ArrayList<String> lista = new ArrayList<String>();

        Cursor cursor;

        String[] columnasEstudiante = new String[]{"_id", NOMBRE_E, CICLO_E, CURSO_E};
        String[] columnasProfesor = new String[]{"_id", NOMBRE_P, CICLO_P, CURSO_P};

        //Obtenemos los profesorse y los añadimos a la lista
        cursor = db.query(DATABASE_TABLE_TEACHERS, columnasProfesor, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                lista.add("Profesor "+cursor.getInt(0) + "    ||    "+cursor.getString(1)+ " ----- " + cursor.getInt(3) + "º " + cursor.getString(2));
                listaIds.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }

        //Cerramos-limpiamos el cursor
        cursor.close();

        //Obtenemos los estudiantes y los añadimos a la lista
        cursor = db.query(DATABASE_TABLE_STUDENTS, columnasEstudiante, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                lista.add("Estudiante "+cursor.getInt(0) + "    ||    "+cursor.getString(1)+ " ----- " + cursor.getInt(3) + "º " + cursor.getString(2));
                //Guardamos Id's en la lista
                listaIds.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }

        //Devolvemos la lista
        return lista;
    }

    //Método para obtener estudiantes
    public ArrayList<String> obtenerEstudiantes(String ciclo, int curso){

        ArrayList<String> lista = new ArrayList<String>();

        //Columnas que recuperaremos de la tabla estudiantes
        String[] columnas = new String[]{"_id", NOMBRE_E, CICLO_E, CURSO_E};

        //Cursor para realizar las consultas
        Cursor cursor;

        //Dependiendo de los datos introducidos, recuperamos unos datos u otros
        if(ciclo == null && curso == 0){
            //Recuperaremos todos los estudiantes
            cursor = db.query(DATABASE_TABLE_STUDENTS, columnas, null, null, null, null, null);

        }
        else if(ciclo == null && curso != 0){
            //Recuperaremos estudiantes según el curso
            cursor = db.query(DATABASE_TABLE_STUDENTS, columnas, CURSO_E+"="+curso, null, null, null, null);
        }
        else if(ciclo != null && curso == 0){
            //Recuperaremos estudiantes según el ciclo
            cursor = db.query(DATABASE_TABLE_STUDENTS, columnas, CICLO_E+"="+"'"+ciclo+"'", null, null, null, null);
        }
        else{
            //Recuperaremos estudiantes según el ciclo y el curso
            cursor = db.query(DATABASE_TABLE_STUDENTS, columnas, CURSO_E+"="+curso+" AND "+CICLO_E+"="+"'"+ciclo+"'", null, null, null, null);
        }

        //Recorremos el cursor
        if(cursor != null && cursor.moveToFirst()) {
            listaIds.clear();
            for (int i = 0; i < cursor.getCount(); i++) {
                lista.add("Estudiante "+cursor.getInt(0) + "    ||    "+cursor.getString(1)+ " ----- " + cursor.getInt(3) + "º " + cursor.getString(2));
                //Guardamos Id's en la lista
                listaIds.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }


        cursor.close();

        //Devolvemos la lista
        return lista;
    }

    //Método para obtener profesores
    public ArrayList<String> obtenerProfesores(String ciclo, int curso){

        ArrayList<String> lista = new ArrayList<String>();

        //Columnas que recuperaremos de la tabla estudiantes
        String[] columnas = new String[]{"_id", NOMBRE_P, CICLO_P, CURSO_P};

        //Cursor para realizar las consultas
        Cursor cursor;

        if(ciclo == null && curso == 0){
            //Recuperaremos todos los profesores
            cursor = db.query(DATABASE_TABLE_TEACHERS, columnas, null, null, null, null, null);
        }
        else if(ciclo == null && curso != 0){
            //Recuperaremos profesores según el curso
            cursor = db.query(DATABASE_TABLE_TEACHERS, columnas, CURSO_P+"="+curso, null, null, null, null);
        }
        else if(ciclo != null && curso == 0){
            //Recuperaremos profesores según el ciclo
            cursor = db.query(DATABASE_TABLE_TEACHERS, columnas, CICLO_P+"="+"'"+ciclo+"'", null, null, null, null);
        }
        else{
            //Recuperaremos profesores según el ciclo y el curso
            cursor = db.query(DATABASE_TABLE_TEACHERS, columnas, CURSO_P+"="+curso+" AND "+CICLO_P+"="+"'"+ciclo+"'", null, null, null, null);
        }

        //Recorremos el cursor
        if(cursor != null && cursor.moveToFirst()) {
            listaIds.clear();
            for (int i = 0; i < cursor.getCount(); i++) {
                lista.add("Profesor "+cursor.getInt(0) + "    ||    "+cursor.getString(1)+ " ---- " + cursor.getInt(3) + "º " + cursor.getString(2));
                listaIds.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }

        cursor.close();

        return lista;
    }

    public ArrayList<String> obtenerAsignaturas(){

        ArrayList<String> listaAsignaturas = new ArrayList<String>();

        //Columnas que queremos recuperar
        String[] columnas = new String[]{"_id", NOMBRE_A, HORAS_A};

        //Cursor para realizar las consultas
        Cursor cursor;

        //Realizamos la consulta
        cursor = db.query(DATABASE_TABLE_ASIGNATURAS, columnas, null, null, null, null, null);

        //Recorremos el cursor
        if(cursor != null && cursor.moveToFirst()) {
            listaIds.clear();
            for (int i = 0; i < cursor.getCount(); i++) {
                //Recuperamos los datos de cada resultado y los montamos como queremos
                listaAsignaturas.add("Asignatura "+cursor.getInt(0) + "   ||   "+cursor.getString(1)+" ---- " + cursor.getInt(2)+" horas");
                cursor.moveToNext();
            }
        }

        //Devolvemos la lista obtenida
        return listaAsignaturas;
    }

    //Método para obtener una lista con los Ids de los estudiantes
    public ArrayList<Integer> getIds(){
        return this.listaIds;
    }


    /**
     *  INNER CLASS
     */
    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Crearemos las dos tablas en la bbdd
            db.execSQL(CREATE_TABLE_STUDENTS);
            db.execSQL(CREATE_TABLE_TEACHERS);
            db.execSQL(CREATE_TABLE_ASIGNATURAS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_STUDENTS);
            db.execSQL(DROP_TABLE_TEACHERS);
            db.execSQL(DROP_TABLE_ASIGNATURAS);
            onCreate(db);
        }

    }
}
