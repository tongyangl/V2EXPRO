package rxjavatest.tycoding.com.iv2ex.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import rxjavatest.tycoding.com.iv2ex.BaseApplication;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;

/**
 * Created by 佟杨 on 2017/5/7.
 */

public class RichTextView extends TextView {

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   public  void  setRichText(String text){
       super.setTextIsSelectable(true);

     if (BaseApplication.isMobile(getContext())&&!BaseApplication.usemobile){
         super.setText(Html.fromHtml(text));
         setMovementMethod(LinkMovementMethod.getInstance());
        return;
     }

       Spanned spanned=Html.fromHtml(text,new MyImageGetter(getContext(),this),null);
       SpannableStringBuilder htmlSpannable;
       if (spanned instanceof SpannableStringBuilder) {
           htmlSpannable = (SpannableStringBuilder) spanned;
       } else {
           htmlSpannable = new SpannableStringBuilder(spanned);
       }
       ImageSpan[] span =htmlSpannable.getSpans(0,htmlSpannable.length(),ImageSpan.class);
   final ArrayList<String>imageUrls=new ArrayList<>();
       for (ImageSpan s:span){

           imageUrls.add(s.getSource());
       }

       for (ImageSpan s:span){
           final int start = htmlSpannable.getSpanStart(s);
           final int end   = htmlSpannable.getSpanEnd(s);
           ClickableSpan clickableSpan=new ClickableSpan() {
               @Override
               public void onClick(View widget) {
                   Intent intent=new Intent(getContext(),photoviewactivity.class);
                   intent.putStringArrayListExtra("list",  imageUrls);

               }
           };

           ClickableSpan[] clickSpans = htmlSpannable.getSpans(start, end, ClickableSpan.class);
           if(clickSpans != null && clickSpans.length != 0) {

               for(ClickableSpan c_span : clickSpans) {
                   htmlSpannable.removeSpan(c_span);
               }
           }

           htmlSpannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       }
       super.setText(htmlSpannable);
       setMovementMethod(LinkMovementMethod.getInstance());
   }
}
