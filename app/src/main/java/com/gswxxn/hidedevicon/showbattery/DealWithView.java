package com.gswxxn.hidedevicon.showbattery;

import android.annotation.SuppressLint;
import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import java.lang.reflect.Field;

public class DealWithView extends XC_MethodHook {
    private final Class<?> powerCenterA;

    public DealWithView(Class<?> powerCenterA) {
        this.powerCenterA = powerCenterA;
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static String getPackageVersionName(Context context) {
        String versionName = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;

        }catch (PackageManager.NameNotFoundException e) {
            XposedBridge.log(e);
        }
        return versionName;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        Context context = AndroidAppHelper.currentApplication().getApplicationContext();

        ViewResources viewResources = new ViewResources(getPackageVersionName(context));

        int currentTemperatureValue = viewResources.currentTemperatureValue;
        int tempeValueContainer = viewResources.tempeValueContainer;
        if (currentTemperatureValue== -1 || tempeValueContainer == -1) {
            return;
        }

        Field field = powerCenterA.getDeclaredField("a");
        field.setAccessible(true);
        View view = (View) field.get(param.thisObject);

        // Set Temperature Value Attribute
        @SuppressLint("ResourceType") TextView valueView = view.findViewById(currentTemperatureValue);
        LinearLayout.LayoutParams valueParams = (LinearLayout.LayoutParams) valueView.getLayoutParams();
        valueParams.topMargin = 0;
        valueView.setLayoutParams(valueParams);

        valueView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 36.399998);
        valueView.setPadding(0, dp2px(context, (float) -3.539978), 0, 0);
        valueView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        valueView.setGravity(Gravity.TOP | Gravity.START);

        // Set Temperature Sign Attribute
        @SuppressLint("ResourceType") LinearLayout linearLayout = view.findViewById(tempeValueContainer);
        TextView temperatureSign = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dp2px(context, (float) 49.099983));
        textParams.setMarginStart(dp2px(context, (float) 3.599976));
        temperatureSign.setLayoutParams(textParams);

        temperatureSign.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 13.099977);
        temperatureSign.setPadding(0, dp2px(context, (float) 25.3999), 0, 0);
        temperatureSign.setText("℃");
        temperatureSign.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        temperatureSign.setTextColor(Color.parseColor("#333333"));

        linearLayout.addView(temperatureSign);
    }
}
