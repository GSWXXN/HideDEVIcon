package com.gswxxn.hidedevicon.showbattery;

import android.content.Context;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryMain {

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> powerCenterA = XposedHelpers.findClass("com.miui.powercenter.a$a", lpparam.classLoader);
        Class<?> utils = XposedHelpers.findClass("com.miui.powercenter.utils.q", lpparam.classLoader);
        try{
            utils.getDeclaredMethod("k", Context.class);
        } catch (NoSuchMethodException e) {
            utils = XposedHelpers.findClass("com.miui.powercenter.utils.r", lpparam.classLoader);
        }

        XposedHelpers.findAndHookMethod(powerCenterA, "run", new DealWithView(powerCenterA, utils));
    }
}
