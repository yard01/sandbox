package com.github.yard01.sandbox.crib.dwh_crib.files.page0;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dwh_crib.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilesPage0Creator {
    public static EditText fileEditor;
    public static int selectedIndex = -1;
    public static class OpenFileDialog extends AlertDialog.Builder {

        private List<File> files = new ArrayList<File>();
        public static String currentPath = "/";
        private TextView title;
        File currentFile;

        private TextView createTitle(Context context) {
            TextView textView = new TextView(context);
            textView.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
            int itemHeight = getItemHeight(context);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
            textView.setMinHeight(itemHeight);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 0, 0, 0);
            textView.setText(currentPath);
            return textView;
        }

        private  int getItemHeight(Context context) {
            TypedValue value = new TypedValue();
            DisplayMetrics metrics = new DisplayMetrics();
            context.getTheme().resolveAttribute(android.R.attr.rowHeight, value, true);
            getDefaultDisplay(context).getMetrics(metrics);
            return (int)TypedValue.complexToDimension(value.data, metrics);

        }

        public OpenFileDialog(Context context) {

            super(context);
            currentPath = context.getFilesDir().getPath();

            title = createTitle(context);
            LinearLayout linearLayout = createMainLayout(context);
            files.addAll(getFiles(currentPath));
            ListView listView = createListView(context);
            listView.setAdapter(new FileAdapter(context, files));
            linearLayout.addView(listView);
            setCustomTitle(title)
                    .setView(linearLayout)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadFile(currentFile);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null);
        }

        private void rebuildFiles(ArrayAdapter<File> adapter) {
            files.clear();
            files.addAll(getFiles(currentPath));
            adapter.notifyDataSetChanged();
        }

        private void loadFile(File file) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append("\r\n");
                    line = reader.readLine();
                }
                fileEditor.setText(sb.toString());
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private ListView createListView(final Context context) {
            ListView listView = new ListView(context);

            listView.setSelector(R.drawable.listitem_selector);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    final ArrayAdapter<File> adapter = (FileAdapter) adapterView.getAdapter();
                    File file = adapter.getItem(index);

                    //view.setSelected(selectedIndex == position);

                    if (file.isDirectory()) {
                        currentPath = file.getPath();
                        rebuildFiles(adapter);
                        selectedIndex = -1;
                        currentFile = null;
                    } else {
                        currentFile = file;
                        selectedIndex = index;
                        adapter.notifyDataSetChanged();
                       // loadFile(file);
                        //
                        //Files.readAllBytes(Paths.get(file.getPath()); // Charset.defaultCharset());
                    }
                }
            });
            return listView;
        }

        private static Display getDefaultDisplay(Context context) {
            return ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        }

        private static Point getScreenSize(Context context) {
            Point screeSize = new Point();
            getDefaultDisplay(context).getSize(screeSize);
            return screeSize;
        }

        private static int getLinearLayoutMinHeight(Context context) {
            return getScreenSize(context).y;
        }

        private LinearLayout createMainLayout(Context context) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setMinimumHeight(getLinearLayoutMinHeight(context));
            return linearLayout;
        }

    }


    private static class FileAdapter extends ArrayAdapter<File> {

        public FileAdapter(Context context, List<File> files) {
            super(context, android.R.layout.simple_list_item_1, files);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            File file = getItem(position);
            view.setText(file.getName());

            //view.sel
            view.setSelected(position == selectedIndex);

            //if (selectedIndex == position)
            //   view.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
            //else
            //   view.setBackgroundColor(getContext().getResources().getColor(android.R.color.background_light));

            return view;
        }
    }

    private static List<File> getFiles(String directoryPath){
        File directory = new File(directoryPath);

        List<File> fileList = Arrays.asList(directory.listFiles());
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                if (file.isDirectory() && file2.isFile())
                    return -1;
                else if (file.isFile() && file2.isDirectory())
                    return 1;
                else
                    return file.getPath().compareTo(file2.getPath());
            }
        });
        return fileList;
    }

    public static void createContent(final View rootView) {
        fileEditor = rootView.findViewById(R.id.dwh_crib_inner_files_et_text);
        Button btnLoad = rootView.findViewById(R.id.dwh_crib_inner_files_btn_load);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileDialog fileDialog = new OpenFileDialog(rootView.getContext());
                fileDialog.show();
            }
        });

        Button btnSave = rootView.findViewById(R.id.dwh_crib_inner_files_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                builder.setTitle("File Name");

// Set up the input
                final EditText input = new EditText(rootView.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();

                        //File file = new File(rootView.getContext().getFilesDir().getPath() + "/" + m_Text);
                        try {
                            PrintWriter out = new PrintWriter(rootView.getContext().getFilesDir().getPath() + "/" + m_Text);
                            out.print(fileEditor.getText());
                            out.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}
