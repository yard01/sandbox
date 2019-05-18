package com.github.yard01.sandbox.crib.ui_crib.controls.page0;

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
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.CommonTools;
import com.github.yard01.sandbox.crib.ui_crib.controls.page0.textview_html.TextViewHtmlTranslator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

public class ControlsPage0Creator {
    public static final String LOG_TAG = "controls";
    public static void createContent(final View view) {
        String txt_html = CommonTools.readStringAsset(view.getContext(),"text/easy_text.html", "UTF-8" );
        TextView txtView = view.findViewById(R.id.controls_textView_txt);
        txtView.setMovementMethod(new ScrollingMovementMethod()); // Для работы скроллинга
        TextView htmlView = view.findViewById(R.id.controls_textView_html);
        htmlView.setMovementMethod(new ScrollingMovementMethod()); //Для работы скроллинга
        txtView.setText(txt_html);

        Spanned spannedText = Html.fromHtml(txt_html, (new TextViewHtmlTranslator.ImgProvider(view.getContext())).getHtmlImageGetter(),
                (new TextViewHtmlTranslator.TagProvider(view.getContext())).getHtmlTagHandler()
        );
        Spannable reversedText = revertSpanned(spannedText);

        htmlView.setText(reversedText);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick","Click!");
            }
        });
    }

    static final Spannable revertSpanned(Spanned stext) {
        Object[] spans = stext.getSpans(0, stext.length(), Object.class);
        Spannable ret = Spannable.Factory.getInstance().newSpannable(stext.toString());
        if (spans != null && spans.length > 0) {
            for(int i = spans.length - 1; i >= 0; --i) {
                ret.setSpan(spans[i], stext.getSpanStart(spans[i]), stext.getSpanEnd(spans[i]), stext.getSpanFlags(spans[i]));
            }
        }

        return ret;
    }



}
