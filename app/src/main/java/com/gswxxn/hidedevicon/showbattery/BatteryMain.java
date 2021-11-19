package com.gswxxn.hidedevicon.showbattery;

import android.content.Context;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryMain {

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        // Return Battery Value instead of description
        Class<?> Powercenter = XposedHelpers.findClass("com.miui.powercenter.a", lpparam.classLoader);
        Class<?> Utils = XposedHelpers.findClass("com.miui.powercenter.utils.q", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(Powercenter, "b", Context.class, new DealWithMethod(Utils));

        // Modify View
        Class<?> Powercenter$a = XposedHelpers.findClass("com.miui.powercenter.a$a", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(Powercenter$a, "run", new DealWithView(Powercenter$a));
    }
}
