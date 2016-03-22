package liu.com.mydocuments.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by liuhui on 16/3/21.
 */
public class JumpUtil {
    public static void jump(Context context,Class<? extends Activity> targetClass){
        Intent intent = new Intent(context,targetClass);
        context.startActivity(intent);
    }
    public static void jumpWithBoundle(Context context,Class<? extends Activity> targetClass,Bundle bundle){
        Intent intent = new Intent(context,targetClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
