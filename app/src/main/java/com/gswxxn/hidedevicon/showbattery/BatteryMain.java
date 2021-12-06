package com.gswxxn.hidedevicon.showbattery;

import android.content.Context;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.File;

public class BatteryMain {

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        // Return Battery Value instead of description
        Class<?> powerCenter = XposedHelpers.findClass("com.miui.powercenter.a", lpparam.classLoader);
        Class<?> utils = XposedHelpers.findClass("com.miui.powercenter.utils.q", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(powerCenter, "b", Context.class, new DealWithMethod(utils));

        // Modify View
        Class<?> powerCenterA = XposedHelpers.findClass("com.miui.powercenter.a$a", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(powerCenterA, "run",
                new DealWithView(powerCenterA, String.valueOf(getPackageVersionName(lpparam))));
    }

    public static String getPackageVersionName(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            File apkPath = new File(lpparam.appInfo.sourceDir);
            String versionName;
            Class<?> parserCls = XposedHelpers.findClass("android.content.pm.PackageParser", lpparam.classLoader);
            Object pkg = XposedHelpers.callMethod(parserCls.newInstance(), "parsePackage", apkPath, 0);
            versionName = (String) XposedHelpers.getObjectField(pkg, "mVersionName");

            return versionName;
        } catch (Throwable e) {
            XposedBridge.log(e);
        }
        return null;
    }
}
