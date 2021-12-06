package com.gswxxn.hidedevicon.showbattery;

import android.annotation.SuppressLint;
import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;

import java.lang.reflect.Field;

public class DealWithView extends XC_MethodHook {
    private final Class<?> powerCenterA;

    public DealWithView(Class<?> powerCenterA) {
        this.powerCenterA = powerCenterA;
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int getResourceId(Context context, String name) {
        Resources r = context.getResources();
        return r.getIdentifier(name, "id", "com.miui.securitycenter");
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
