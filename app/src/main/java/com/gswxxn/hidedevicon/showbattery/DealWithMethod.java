package com.gswxxn.hidedevicon.showbattery;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DealWithMethod extends XC_MethodHook {
    private final Class<?> utils;

    public DealWithMethod(Class<?> utils) {
        this.utils = utils;
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);

        int k2 = (int) XposedHelpers.callStaticMethod(this.utils, "k", param.args[0]);
        int g2 = (int) XposedHelpers.callStaticMethod(this.utils, "g", param.args[0]);
        if (k2 == Integer.MIN_VALUE || Math.abs(g2 - k2) > 5) {
            k2 = g2;
        }

        param.setResult(String.valueOf(k2));
    }
}