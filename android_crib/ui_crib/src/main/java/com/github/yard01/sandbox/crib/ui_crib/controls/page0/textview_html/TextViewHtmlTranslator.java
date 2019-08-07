package com.github.yard01.sandbox.crib.ui_crib.controls.page0.textview_html;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

import com.github.yard01.sandbox.crib.ui_crib.CommonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

public class TextViewHtmlTranslator {

    public static class ImgProvider {
        private static final String DEFTYPE = "drawable";
        //final Context context;

        final BitmapFactory.Options options = new BitmapFactory.Options();

        private final Html.ImageGetter htmlImageGetter;


        public ImgProvider(final Context context) {
            //this.context = context;

            this.htmlImageGetter =new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    //Log.d("source", ""+source);
                    Rect rect = null;
                    int delimPos =source.indexOf(":");
                    if (delimPos > 0) {
                        try {
                            JSONArray jsonRect = new JSONArray(source.substring(delimPos + 1));
                            //rect = null; new Rect();
                            rect = new Rect(jsonRect.getInt(0),jsonRect.getInt(1), jsonRect.getInt(2), jsonRect.getInt(3));
                            //JSONArray
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        source = source.substring(0, delimPos);

                    }

                    //source.substring();
                    int resId = context.getResources().getIdentifier(source, "drawable", context.getPackageName());

                    Drawable ret = context.getResources().getDrawable(resId);
                    if (rect != null) {
                        options.inScaled = false;
                        Drawable result = new BitmapDrawable(context.getResources(), Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), resId, options),rect.left,rect.top,rect.width(),rect.height()));
                        result.setBounds(0, 0, rect.width(), rect.height());
                        return result;
                    }

                    ret.setBounds(0, 0, ret.getIntrinsicWidth(), ret.getIntrinsicHeight());
                    //else ret.setBounds(rect);

                    //Rect r = new Rect("");
                    //Log.d("source", ""+rect);

                    return ret;
                }
            };
        }

        public Html.ImageGetter getHtmlImageGetter() {
            return htmlImageGetter;
        }
    }

    public static class TagProvider {
        private static final String FONTS_PATH = "fonts/";
        private static final String CUSTOM_FONT_PREFIX = "custom_font_";
        private static final String COLOR_PREFIX = "color_";
        private static final String JSON_FONT_FAMILY = "font-family";
        private static final String JSON_COLOR = "color";
        private static final String JSON_BACKGROUND = "background";
        private static final String JSON_BOLD = "bold";
        private static final String JSON_ITALIC = "italic";
        private static final String JSON_STRIKE_THRU = "strikethru";
        private static final String JSON_UNDERLINE = "underline";
        private static final String JSON_TEXT_SIZE = "size";

        protected final Html.TagHandler htmlTagHandler;


        public Html.TagHandler getHtmlTagHandler() {
            return htmlTagHandler;
        }

        public TagProvider(final Context context) {
            htmlTagHandler = new Html.TagHandler() {
                public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                    int color = -1;
                    int bgcolor = -1;
                    int textSize = -1;
                    String fontName = "";
                    boolean bold = false;
                    boolean italic = false;
                    boolean strikeThru = false;
                    boolean underline = false;


                    if (tag.startsWith(COLOR_PREFIX)) color = Color.parseColor("#" + tag.substring(COLOR_PREFIX.length()));

                    if (tag.startsWith(CUSTOM_FONT_PREFIX)) {

                        String font = CommonTools.readStringAsset(context,FONTS_PATH + tag.substring(CUSTOM_FONT_PREFIX.length()) +".json","UTF-8");
                        //Log.d("font", font);

                        try {
                            JSONObject custom_font = new JSONObject(font);
                            fontName = custom_font.optString(JSON_FONT_FAMILY);

                            String foreground = custom_font.optString(JSON_COLOR);

                            String background = custom_font.optString(JSON_BACKGROUND);


                            bold = custom_font.optBoolean(JSON_BOLD);
                            italic = custom_font.optBoolean(JSON_ITALIC);
                            strikeThru = custom_font.optBoolean(JSON_STRIKE_THRU);
                            underline = custom_font.optBoolean(JSON_UNDERLINE);
                            textSize = custom_font.optInt(JSON_TEXT_SIZE);


                            //Log.d("font", ""+fontName);
                            if (foreground == null | foreground == "") color = -1;
                            else color = Color.parseColor(foreground);

                            if (background == null | background == "") bgcolor = -1;
                            else bgcolor = Color.parseColor(background);

                            if (textSize == 0) textSize = -1;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    /*
                    Object span = new RichTextStyle(context, color, bgcolor, textSize, bold, italic, strikeThru,underline, fontName);//null;

                    if (span != null) processSpan(opening, output, span);
                    */
                }
            };
        }

        void processSpan(boolean opening, Editable output, Object span) {
            int len = output.length();
            if (opening) {
                output.setSpan(span, len, len, Spannable.SPAN_MARK_MARK);
            } else {
                Object[] objs = output.getSpans(0, len, span.getClass());
                int where = len;
                if (objs.length > 0) {
                    for(int i = objs.length - 1; i >= 0; --i) {
                        if (output.getSpanFlags(objs[i]) == Spannable.SPAN_MARK_MARK) {
                            where = output.getSpanStart(objs[i]);
                            output.removeSpan(objs[i]);
                            break;
                        }
                    }
                }

                if (where != len) {
                    output.setSpan(span, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        //Html.TagHandler
    }

    public class RichTextStyle extends CharacterStyle {
        public static final int NONE = -1;
        public static final float ITALIC_SKEW = -0.25f;
        //public static final int color_def = 0;
        //public static final int size_def = 0;

        final int color, bgColor, textSize;
        final boolean fakeBoldText, italicText, strikeThruText, underlineText;
        final String fontName;
        final Context parent;

        public RichTextStyle(Context parent, int color, int bgColor, int textSize, boolean fakeBoldText, boolean italicText,
                             boolean strikeThruText, boolean underlineText, String fontName) {
            this.color = color;
            this.bgColor = bgColor;
            this.textSize = textSize;
            this.fakeBoldText = fakeBoldText;
            this.italicText = italicText;
            this.strikeThruText = strikeThruText;
            this.underlineText = underlineText;
            this.fontName = fontName;
            this.parent = parent;
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            if (color != NONE) tp.setColor(color);
            if (bgColor != NONE) tp.bgColor = bgColor;
            if (textSize != NONE) tp.setTextSize(textSize);

            tp.setFakeBoldText(fakeBoldText);
            tp.setStrikeThruText(strikeThruText);
            tp.setUnderlineText(underlineText);
            tp.setTextSkewX(italicText ? ITALIC_SKEW : 0);

            //Typeface.create("", Typeface.NORMAL);
            if (this.fontName != null && this.fontName != "")
                tp.setTypeface(Typeface.create(this.fontName, Typeface.NORMAL));
            tp.setTypeface(FontProvider.getFont(parent, fontName));
            //Typeface.create()
            //private Typeface cursiveTTF = Typeface.createFromAsset(SchoolPoints.mainActivity.getAssets(),"fonts/lc-chalk.ttf");

        }
    }

    public static class FontProvider {
        public final static String FONT_PATH = "fonts/";
        public static Typeface getFont(Context context, String fontName) {
            if (fontName.startsWith(FONT_PATH))
                return Typeface.createFromAsset(context.getAssets(),fontName);
            else
                return Typeface.create(fontName, Typeface.NORMAL);
        }
    }

}
