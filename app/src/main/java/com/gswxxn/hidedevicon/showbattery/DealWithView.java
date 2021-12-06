package com.gswxxn.hidedevicon.showbattery;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import java.lang.reflect.Field;

public class DealWithView extends XC_MethodHook {
    private final Class<?> powerCenterA;
    private final Class<?> utils;
    private final Class<?> fonts;

    public DealWithView(Class<?> powerCenterA, Class<?> utils, Class<?> fonts) {
        this.powerCenterA = powerCenterA;
        this.utils = utils;
        this.fonts = fonts;
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int getResourceId(Context context, String name) {
        Resources r = context.getResources();
        return r.getIdentifier(name, "id", "com.miui.securitycenter");
    }

    /**
        Original code from
            com.miui.powercenter.a$a.b(Context)
     */
    public String getTemperatureValue(Class<?> utils, Context context) {

        int k2 = (int) XposedHelpers.callStaticMethod(utils, "k", context);
        int g2 = (int) XposedHelpers.callStaticMethod(utils, "g", context);
        if (k2 == Integer.MIN_VALUE || Math.abs(g2 - k2) > 5) {
            k2 = g2;
        }

        return String.valueOf(k2);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        Context context = AndroidAppHelper.currentApplication().getApplicationContext();

        int currentTemperatureValue = getResourceId(context, "current_temperature_value");
        int tempeValueContainer = getResourceId(context, "tempe_value_container");
        if (currentTemperatureValue== 0 || tempeValueContainer == 0) {
            return;
        }

        Field field = powerCenterA.getDeclaredField("a");
        field.setAccessible(true);
        View view = (View) field.get(param.thisObject);

        // Set Temperature Value Attribute
        TextView valueView = view.findViewById(currentTemperatureValue);
        LinearLayout.LayoutParams valueParams = (LinearLayout.LayoutParams) valueView.getLayoutParams();
        valueParams.topMargin = 0;

        valueView = new TextView(context);
        valueView.setLayoutParams(valueParams);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 36.399998);
        valueView.setPadding(0, dp2px(context, (float) -3.539978), 0, 0);
        valueView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        valueView.setGravity(Gravity.TOP | Gravity.START);
        valueView.setTextColor(Color.parseColor("#333333"));
        valueView.setTypeface((Typeface) XposedHelpers.callStaticMethod(fonts, "a", context));
        valueView.setText(getTemperatureValue(utils, context));

        // Set Temperature Sign Attribute
        TextView temperatureSign = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dp2px(context, (float) 49.099983));
        textParams.setMarginStart(dp2px(context, (float) 3.599976));
        temperatureSign.setLayoutParams(textParams);

        temperatureSign.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 13.099977);
        temperatureSign.setPadding(0, dp2px(context, (float) 25.3999), 0, 0);
        temperatureSign.setText("℃");
        temperatureSign.setTypeface((Typeface) XposedHelpers.callStaticMethod(fonts, "a"), Typeface.BOLD);
        temperatureSign.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        temperatureSign.setTextColor(Color.parseColor("#333333"));

        // reset tempe_value_container
        LinearLayout linearLayout = view.findViewById(tempeValueContainer);
        linearLayout.removeAllViews();
        linearLayout.addView(valueView);
        linearLayout.addView(temperatureSign);
    }
}
