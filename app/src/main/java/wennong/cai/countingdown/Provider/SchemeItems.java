package wennong.cai.countingdown.Provider;

import android.net.Uri;

public class SchemeItems {
    public static final String CONTENT_AUTHORITY = "wennong.cai.items.provider";
    public static final String PATH_VERSION = "items";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class Item{
        // Content Uri = Content Authority + Path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VERSION).build();

        // Use MIME type prefix android.cursor.dir/ for returning multiple items
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/wennong.cai.countingdown.Provider";
        // Use MIME type prefix android.cursor.item/ for returning a single item
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/wennong.cai.countingdown.Provider";


        public static final String TABLE_NAME = "items";

        public static final String ID = "_id";
        public static final String ITEM_TITLE = "item_title";
        public static final String ITEM_DESCRIPTION = "item_description";
        public static final String ITEM_DUE = "item_due";


        public static final String[] PROJECTION = new String[]{
                Item.ID,
                Item.ITEM_TITLE,
                Item.ITEM_DESCRIPTION,
                Item.ITEM_DUE
        };

        private Item(){}    // prevent initiating this Scheme Object by accident.

    }
}
