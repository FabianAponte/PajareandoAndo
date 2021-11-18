package com.example.pajareando.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelDb {

    /**
     * Método para guardar
     *
     * Example
     * Map<String, String> birdInfo = new HashMap<String, String>();
     * birdInfo.put("name", "pablito");
     *
     * @param map
     * @param table
     * @param context
     */
    public static void save(Map<String, String> map, String table, Context context) {
        Database database = new Database(context);
        SQLiteDatabase writeDb = database.getWritableDatabase();

        ContentValues registry = new ContentValues();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            registry.put(entry.getKey(), entry.getValue());
        }

        try {
            writeDb.insertOrThrow(table, null, registry);
            writeDb.close();
            database.close();
        } catch (Exception e) {
           Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método para obtener un elemento y luego se tiene acceso a este por medio de Map
     *
     * Example
     * Map<String, String> bird = Bird.findById(1, Bird.TABLE_NAME, getApplicationContext());
     * Toast.makeText(getApplicationContext(), bird.get("id"), Toast.LENGTH_SHORT).show();
     *
     * @param id
     * @param table
     * @param context
     * @return
     */
    public static Map<String, String> findById(int id, String table, Context context) {
        Database database = new Database(context);
        SQLiteDatabase readDb = database.getReadableDatabase();

        Cursor cursor = readDb.rawQuery("SELECT * FROM " + table + " WHERE id = ?",  new String[]{ id + ""});

        Map<String, String>mapInfo = new HashMap<String, String>();

        if (cursor.moveToFirst()) {
            int column = cursor.getColumnCount();
            for (int i = 0; i < column; i++) {
                mapInfo.put(cursor.getColumnName(i), cursor.getString(i));
            }
        }

        readDb.close();
        database.close();

        return mapInfo;
    }


    /**
     * Método para obtener todos los datos de la tabla
     *
     * Example
     *
     *  List<Map<String, String>> array = ModelDb.getAll(Bird.TABLE_NAME, getApplicationContext());
     *         String algo = "";
     *         for (int i = 0; i < array.size(); i++) {
     *             if (i == 1) {
     *                 algo = array.get(1).get("id");
     *             }
     *         }
     *         Toast.makeText(getApplicationContext(), algo, Toast.LENGTH_SHORT).show();
     *
     * @param table
     * @param context
     * @return
     */
    public static ArrayList<Map<String, String>> getAll(String table, Context context) {
        Database database = new Database(context);
        SQLiteDatabase readDb = database.getReadableDatabase();

        Cursor cursor = readDb.rawQuery("SELECT * FROM " + table, null);

        ArrayList<Map<String, String>> elements = new ArrayList<Map<String, String>>();

        Map<String, String>mapInfo;

        while (cursor.moveToNext()) {
            mapInfo = new HashMap<String, String>();
            int column = cursor.getColumnCount();
            for (int i = 0; i < column; i++) {
                mapInfo.put(cursor.getColumnName(i), cursor.getString(i));
            }
            elements.add(mapInfo);
        }

        readDb.close();
        database.close();

        return elements;
    }

    /**
     * Método para eliminar un elemento
     *
     * @param id
     * @param table
     * @param context
     * @return
     */
    public static int delete(int id, String table, Context context) {
        Database database = new Database(context);
        SQLiteDatabase writeDb = database.getWritableDatabase();

        int itemsDeleted = writeDb.delete(table, "id=?", new String[]{ id + ""});

        writeDb.close();
        database.close();

        return itemsDeleted;
    }
}
