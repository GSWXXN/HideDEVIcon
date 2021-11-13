package com.gswxxn.hidedevicon;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;

public class main implements IXposedHookLoadPackage {
    Class<?> clazz, Build;
    Field IS_DEVELOPMENT_VERSION;
    Boolean Modifiable;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.android.systemui")) {
            return;
        }

        clazz = XposedHelpers.findClass(
                "com.android.systemui.statusbar.phone.MiuiCollapsedStatusBarFragment", lpparam.classLoader);

        XposedHelpers.findAndHookMethod(clazz, "showSystemIconArea", boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                Build = XposedHelpers.findClass("miui.os.Build", lpparam.classLoader);
                IS_DEVELOPMENT_VERSION = Build.getDeclaredField("IS_DEVELOPMENT_VERSION");

                Modifiable = IS_DEVELOPMENT_VERSION.getBoolean(param.thisObject);
                if (!Modifiable) {
                    return;
                }

                IS_DEVELOPMENT_VERSION.setAccessible(true);
                IS_DEVELOPMENT_VERSION.setBoolean(param.thisObject, false);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                if (!Modifiable) {
                    return;
                }

                IS_DEVELOPMENT_VERSION.setBoolean(param.thisObject, true);
                IS_DEVELOPMENT_VERSION.setAccessible(false);
            }
        });

        XposedHelpers.findAndHookMethod(clazz, "hideSystemIconArea", boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                Build = XposedHelpers.findClass("miui.os.Build", lpparam.classLoader);
                IS_DEVELOPMENT_VERSION = Build.getDeclaredField("IS_DEVELOPMENT_VERSION");

                Modifiable = IS_DEVELOPMENT_VERSION.getBoolean(param.thisObject);
                if (!Modifiable) {
                    return;
                }

                IS_DEVELOPMENT_VERSION.setAccessible(true);
                IS_DEVELOPMENT_VERSION.setBoolean(param.thisObject, false);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                if (!Modifiable) {
                    return;
                }

                IS_DEVELOPMENT_VERSION.setBoolean(param.thisObject, true);
                IS_DEVELOPMENT_VERSION.setAccessible(false);
            }
        });
    }
}
