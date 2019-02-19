package wennong.cai.countingdown.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

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
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
