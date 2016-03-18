package liu.com.mydocuments.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 缺点：当别的空间拿到focused的时候，会停止滚动
 * Created by liuhui on 2016/2/19.
 */
public class MarqueeTextView extends TextView{
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
