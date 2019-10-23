package es.upm.miw.solitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static es.upm.miw.solitarioCelta.models.ResultadoContract.tablaResultado;

public class RepositorioResultados extends SQLiteOpenHelper {

    private static final String NOMBRE_FICHERO = tablaResultado.TABLE_NAME + ".db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tablaResultado.TABLE_NAME + " (" +
                    tablaResultado._ID + " INTEGER PRIMARY KEY," +
                    tablaResultado.COL_NAME_NOMBRE + " TEXT," +
                    tablaResultado.COL_NAME_FECHA + " TEXT," +
                    tablaResultado.COL_NAME_PIEZAS + " INT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + tablaResultado.TABLE_NAME;

    public RepositorioResultados(Context context) {
        super(context, NOMBRE_FICHERO, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, tablaResultado.TABLE_NAME);
    }

    public long add(String nombre, String fecha, Integer piezas) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(tablaResultado.COL_NAME_NOMBRE, nombre);
        values.put(tablaResultado.COL_NAME_FECHA, fecha);
        values.put(tablaResultado.COL_NAME_PIEZAS, piezas);

        long newRowId = db.insert(tablaResultado.TABLE_NAME, null, values);
        return newRowId;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tablaResultado.TABLE_NAME);
        db.close();
    }

    public List<Resultado> readAll() {
        String consultaSQL = "SELECT * FROM " + tablaResultado.TABLE_NAME;
        List<Resultado> listaResultado = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Resultado resultado = new Resultado(
                        cursor.getInt(cursor.getColumnIndex(tablaResultado.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaResultado.COL_NAME_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(tablaResultado.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(tablaResultado.COL_NAME_PIEZAS))
                );
                listaResultado.add(resultado);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return listaResultado;
    }

    /**
     * Recupera la Bebida indicada por el identificador
     *
     * @return Bebida
     */
    @Nullable
    public Resultado get(Long idResultado) {
        Resultado resultado = null;
        String consultaSQL = "SELECT * FROM " + tablaResultado.TABLE_NAME
                + " WHERE " + tablaResultado.COL_NAME_ID + " = " + idResultado;

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        if (cursor.moveToFirst()) {
            resultado = cursor2Resultado(cursor);
        }

        cursor.close();
        db.close();

        return resultado;
    }

    @NonNull
    private Resultado cursor2Resultado(@NonNull Cursor cursor) {
        return new Resultado(
                cursor.getInt(cursor.getColumnIndex(tablaResultado.COL_NAME_ID)),
                cursor.getString(cursor.getColumnIndex(tablaResultado.COL_NAME_NOMBRE)),
                cursor.getString(cursor.getColumnIndex(tablaResultado.COL_NAME_FECHA)),
                cursor.getInt(cursor.getColumnIndex(tablaResultado.COL_NAME_PIEZAS))
        );
    }

}
