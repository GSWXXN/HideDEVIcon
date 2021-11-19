package com.gswxxn.hidedevicon.hidedevicon;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HDIMain {
    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> clazz = XposedHelpers.findClass(
                "com.android.systemui.MiuiVendorServices", lpparam.classLoader);

        XposedHelpers.findAndHookMethod(clazz, "setSettingsDefault", new DealWithMethod(lpparam));
    }
}
