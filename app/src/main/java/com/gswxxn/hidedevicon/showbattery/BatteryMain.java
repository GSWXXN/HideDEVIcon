package com.gswxxn.hidedevicon.showbattery;

import android.content.Context;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryMain {

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> Powercenter = XposedHelpers.findClass("com.miui.powercenter.a", lpparam.classLoader);
        Class<?> Utils = XposedHelpers.findClass("com.miui.powercenter.utils.q", lpparam.classLoader);

        XposedHelpers.findAndHookMethod(Powercenter, "b", Context.class, new DealWithMethod(Utils));
    }
}
