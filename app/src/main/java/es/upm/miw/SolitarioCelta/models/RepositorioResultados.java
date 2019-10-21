package es.upm.miw.SolitarioCelta.models;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static es.upm.miw.SolitarioCelta.models.ResultadoContract.tablaResultado;

public class RepositorioResultados extends SQLiteOpenHelper {


    private static final String NOMBRE_FICHERO = tablaResultado.TABLE_NAME + ".db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tablaResultado.TABLE_NAME + " (" +
                    tablaResultado._ID + " INTEGER PRIMARY KEY," +
                    tablaResultado.COL_NAME_NOMBRE + " TEXT," +
                    tablaResultado.COL_NAME_FECHA + " INT," +
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
}
