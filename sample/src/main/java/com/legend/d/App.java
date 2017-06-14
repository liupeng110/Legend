package com.legend.d;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.lody.legend.Hook;
import com.lody.legend.HookManager;

/**
 * @author Lody
 * @version 1.0
 */
public class App extends Application {

    public static boolean ENABLE_TOAST = true;
    public static boolean ALLOW_LAUNCH_ACTIVITY = true;

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        HookManager.getDefault().applyHooks(App.class);
        HookManager.getDefault().applyHooks(My.class);
    }

    @Hook("android.app.Application::onCreate")
    public static void Application_onCreate(Application app) {
        Toast.makeText(app, "Application => onCreate()..", Toast.LENGTH_SHORT).show();
        HookManager.getDefault().callSuper(app);
    }

    @Hook("com.legend.d.My::test")
    public static int   test( ){
        Log.i("legend","进入hook my_test中" );
//       int c= HookManager.getDefault().callSuper(null);
//        Log.i("legend","进入hook my_test中"+c);
     return 1000;
    }


    @Hook("android.telephony.TelephonyManager::getSimSerialNumber")
    public static String TelephonyManager_getSimSerialNumber(TelephonyManager thiz) {
         return "SimSerialNumber--112345";
    }


    @Hook("android.widget.Toast::show")
    public static void Toast_show(Toast toast) {
        if (ENABLE_TOAST) {
            HookManager.getDefault().callSuper(toast);
        }else {
            toast.setText("hook后的toast");
            HookManager.getDefault().callSuper(toast);
        }
    }

    @Hook("android.app.Activity::startActivity@android.content.Intent")
    public static void Activity_startActivity(Activity activity, Intent intent) {
        if (!ALLOW_LAUNCH_ACTIVITY) {
            Toast.makeText(activity, "I am sorry to turn your Activity down :)"+intent, Toast.LENGTH_SHORT).show();
        }else {
            HookManager.getDefault().callSuper(activity, intent);
        }
    }

}
