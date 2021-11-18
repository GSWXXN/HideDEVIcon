package com.gswxxn.hidedevicon.hidedevicon;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HDIMain {
    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> clazz = XposedHelpers.findClass(
                "com.android.systemui.statusbar.phone.MiuiCollapsedStatusBarFragment", lpparam.classLoader);

        XposedHelpers.findAndHookMethod(clazz, "showSystemIconArea", boolean.class, new DealWithMethod(lpparam));
        XposedHelpers.findAndHookMethod(clazz, "hideSystemIconArea", boolean.class, new DealWithMethod(lpparam));
    }
}
