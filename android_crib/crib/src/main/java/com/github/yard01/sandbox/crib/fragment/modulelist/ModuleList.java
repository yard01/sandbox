package com.github.yard01.sandbox.crib.fragment.modulelist;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ModuleList {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final String RESOURCE_SUFFIX = ".exm.properties";
    private static final String COMMON_EXAMPLES_SUFFIX = ".exm";
    private static final String COMMON_PROPERTIES_SUFFIX = ".properties";

    private static final String FRAGMENT_PROPERTY = "fragment";
    private static final String NAME_PROPERTY = "name";
    private static final String DESCRIPTION_PROPERTY = "description";

    private static final String NONAME = "no name";

    private static final int time_delay = 3000;
    //private static final int COUNT = 25;

    static {
        // Add some sample items.
        //DummyItem di = new DummyItem("com.github.yard01.sandbox.crib.ui_crib.UICribFragment", "Graphic UI", "User Inteface Examples");
        //addItem(di);
        //for (int i = 1; i <= COUNT - 1; i++) {
        //    addItem(createDummyItem(i));
        //}
        //DummyItem di = new DummyItem("#", "Loa", "User Inteface Examples");
        //addItem(di);
    }

    public static int getCount() {
        return ITEMS.size();
    }

    public static void fillModuleList(Context context) {
        try {
            Thread.sleep(time_delay);

            ITEMS.clear();
            ITEM_MAP.clear();
            Properties properties = new Properties();
            Properties localeProperties = new Properties();

                    //Log.d("properties", UICrib.class.getName());
            //ListResourceBundle.
            //ResourceBundle myResources = ResourceBundle.getBundle("myresbnd"); //, currentLocale);

            //Log.d("properties", "*-" + myResources);

            //Log.d("properties", "*begin " + ModuleList.class.getClassLoader().getResources("/").nextElement());
            //getClass();
            ;
            //Arrays.stream(list).filter(s -> s.endsWith(RESOURCE_SUFFIX))
            Locale currentLocale = context.getResources().getConfiguration().locale;

            String[] list = context.getAssets().list("");

            for (String path : list) {
                int pos_sfx = path.indexOf(RESOURCE_SUFFIX);
                if (pos_sfx >= 0) {
                    properties.load(context.getAssets().open(path));

                    StringBuilder sb = new StringBuilder();
                    sb.append(path.substring(0, pos_sfx));
                    sb.append(COMMON_EXAMPLES_SUFFIX);
                    sb.append(".");
                    sb.append(currentLocale.toString());
                    sb.append(COMMON_PROPERTIES_SUFFIX);

                    String locale_properties_file_name = sb.toString();
                    String fragment_name = properties.getProperty(FRAGMENT_PROPERTY);
                    String default_name = properties.getProperty(NAME_PROPERTY, NONAME);
                    String default_description = properties.getProperty(DESCRIPTION_PROPERTY, "");

                    try {
                        localeProperties.clear();
                        localeProperties.load(context.getAssets().open(locale_properties_file_name));
                        addItem(fragment_name,
                                localeProperties.getProperty(NAME_PROPERTY, default_name),
                                localeProperties.getProperty(DESCRIPTION_PROPERTY, default_description)
                        );
                    } catch (IOException ioe) {
                        addItem(fragment_name, default_name, default_description);
                    }

                    //addItem(properties.getProperty(FRAGMENT_PROPERTY), properties.getProperty(NAME_PROPERTY, NONAME), properties.getProperty(DESCRIPTION_PROPERTY, ""));

                }

            }

            /*
            for (URL url : Collections.list(ModuleList.class.getClassLoader().getResources(path))) {
                Log.d("properties", "*" + url.getPath());

                if (url.getPath().endsWith(RESOURCE_SUFFIX)) {
                    properties.load(url.openStream());
                    Log.d("properties", "" + properties.getProperty(FRAGMENT_PROPERTY));
                }
                //fillModuleList();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("properties", "***" + e.getMessage() );

        }
    }

    public static void addItem(String id, String name, String description) {
        addItem(new DummyItem(id, name, description));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\n"+i+" More details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
