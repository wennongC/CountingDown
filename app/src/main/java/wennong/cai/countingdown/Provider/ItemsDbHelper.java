package wennong.cai.countingdown.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemsDbHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "ItemsDB.db";
    private final static int DB_VERSION = 1;
    private final static String ITEMS_TABLE_NAME = SchemeItems.Item.TABLE_NAME;

    private final static String ITEMS_TABLE_CREATE =
            "CREATE TABLE " +
                    SchemeItems.Item.TABLE_NAME + " (" +
                    SchemeItems.Item.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    SchemeItems.Item.ITEM_TITLE + " TEXT NOT NULL, " +
                    SchemeItems.Item.ITEM_DESCRIPTION + " TEXT, " +
                    SchemeItems.Item.ITEM_YEAR + " INTERGER NOT NULL, " +
                    SchemeItems.Item.ITEM_MONTH + " INTERGER NOT NULL, " +
                    SchemeItems.Item.ITEM_DAY + " INTERGER NOT NULL)";

    public ItemsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ITEMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME);
        onCreate(db);
    }
}
