package wennong.cai.countingdown.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ItemsProvider extends ContentProvider {
    private static final int ITEMS = 100;
    private static final int ITEMS_ID = 200;
    private static final UriMatcher sUriMatcher = createUriMatcher();

    private static UriMatcher createUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SchemeItems.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, SchemeItems.PATH_VERSION, ITEMS);
        uriMatcher.addURI(authority, SchemeItems.PATH_VERSION + "/#", ITEMS_ID);

        return uriMatcher;
    }

    private ItemsDbHelper mDbHelper;

    @Override
    public String getType(Uri uri) {
        switch ((sUriMatcher.match(uri))) {
            case ITEMS:
                return SchemeItems.Item.CONTENT_TYPE;
            case ITEMS_ID:
                return SchemeItems.Item.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemsDbHelper(getContext());
        // Content Provider created
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int uriType = sUriMatcher.match(uri);
        long rowId;

        switch (uriType) {
            case ITEMS:
                rowId = db.insertOrThrow(SchemeItems.Item.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null); //this will trigger an eventual redraw
                return ContentUris.withAppendedId(SchemeItems.Item.CONTENT_URI, rowId);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Use SQLiteQueryBuilder for querying db
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SchemeItems.Item.TABLE_NAME);
        String id;

        // Match Uri pattern
        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case ITEMS:
                break;
            case ITEMS_ID:
                selection = SchemeItems.Item.ID + " = ? ";
                id = uri.getLastPathSegment();
                selectionArgs = new String[]{id};
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(
                db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int uriType = sUriMatcher.match(uri);
        int updateCount = 0;
        switch (uriType) {
            case ITEMS:
                updateCount = db.update(SchemeItems.Item.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEMS_ID:
                String id = uri.getLastPathSegment();
                updateCount = db.update(SchemeItems.Item.TABLE_NAME,
                        values,
                        SchemeItems.Item.ID + " =" + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), // append selection to query if selection is not empty
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int uriType = sUriMatcher.match(uri);
        int deletionCount = 0;

        switch (uriType) {
            case ITEMS:
                deletionCount = db.delete(SchemeItems.Item.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEMS_ID:
                String id = uri.getLastPathSegment();
                deletionCount = db.delete(
                        SchemeItems.Item.TABLE_NAME,
                        SchemeItems.Item.ID + " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), // append selection to query if selection is not empty
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return deletionCount;
    }
}
