package com.example.v2ex.widget;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.v2ex.MyApplication;
import com.example.v2ex.ui.activity.photoviewactivity;
import com.example.v2ex.utils.SomeUtils;

import java.util.ArrayList;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by yw on 2015/5/10.
 */
public class RichTextView extends TextView {

    public RichTextView(Context context) {
        super(context);

    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRichText(String text) {

        setTextIsSelectable(true);

        if (!MyApplication.noImg && SomeUtils.isMobile(getContext())) {
            super.setText(Html.fromHtml(text));

            setMovementMethod(LinkMovementMethod.getInstance());
            return;
        }

        Spanned spanned = Html.fromHtml(text, new AsyncImageGetter(getContext(), this), null);
        SpannableStringBuilder htmlSpannable;
        if (spanned instanceof SpannableStringBuilder) {
            htmlSpannable = (SpannableStringBuilder) spanned;
        } else {
            htmlSpannable = new SpannableStringBuilder(spanned);
        }

        ImageSpan[] spans = htmlSpannable.getSpans(0, htmlSpannable.length(), ImageSpan.class);
        final ArrayList<String> imageUrls = new ArrayList<String>();

        for (ImageSpan span : spans) {
            final String imageUrl = span.getSource();


            imageUrls.add(imageUrl);
        }

        for (int i=0;i<spans.length;i++){
            final int start = htmlSpannable.getSpanStart(spans[i]);
            final int end = htmlSpannable.getSpanEnd(spans[i]);


            final int finalI = i;
            final ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(getContext(), photoviewactivity.class);
                    intent.putExtra("position", finalI);
                    intent.putStringArrayListExtra("list", imageUrls);
                    getContext().startActivity(intent);
                }
            };

            ClickableSpan[] clickSpans = htmlSpannable.getSpans(start, end, ClickableSpan.class);
            if (clickSpans != null && clickSpans.length != 0) {

                for (ClickableSpan c_span : clickSpans) {
                    htmlSpannable.removeSpan(c_span);
                }
            }

            htmlSpannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        }



        super.setText(htmlSpannable);
        setMovementMethod(LinkMovementMethod.getInstance());
    }
}
