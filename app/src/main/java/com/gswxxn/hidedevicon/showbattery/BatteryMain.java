package com.gswxxn.hidedevicon.showbattery;

import android.content.Context;
import android.os.Build;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.File;

public class BatteryMain {

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        // Return Battery Value instead of description
        Class<?> Powercenter = XposedHelpers.findClass("com.miui.powercenter.a", lpparam.classLoader);
        Class<?> Utils = XposedHelpers.findClass("com.miui.powercenter.utils.q", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(Powercenter, "b", Context.class, new DealWithMethod(Utils));

        // Modify View
        Class<?> Powercenter$a = XposedHelpers.findClass("com.miui.powercenter.a$a", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(Powercenter$a, "run",
                new DealWithView(Powercenter$a, String.valueOf(getPackageVersion(lpparam))));
    }

    public static int getPackageVersion(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            File apkPath = new File(lpparam.appInfo.sourceDir);
            int versionCode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Class<?> pkgParserClass = XposedHelpers.findClass("android.content.pm.PackageParser", lpparam.classLoader);
                Object packageLite = XposedHelpers.callStaticMethod(pkgParserClass, "parsePackageLite", apkPath, 0);
                versionCode = XposedHelpers.getIntField(packageLite, "versionCode");
            } else {
                Class<?> parserCls = XposedHelpers.findClass("android.content.pm.PackageParser", lpparam.classLoader);
                Object pkg = XposedHelpers.callMethod(parserCls.newInstance(), "parsePackage", apkPath, 0);
                versionCode = XposedHelpers.getIntField(pkg, "mVersionCode");
            }
            return versionCode;
        } catch (Throwable e) {
            XposedBridge.log(e);
        }
        return -1;
    }
}
