package com.gswxxn.hidedevicon.hidedevicon;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;

public class SysInfo {
    private final Field isDevelopment;
    private final Boolean Modifiable;

    public SysInfo (XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param) throws NoSuchFieldException, IllegalAccessException {
        Class<?> build = XposedHelpers.findClass("miui.os.Build", lpparam.classLoader);
        this.isDevelopment = build.getDeclaredField("IS_DEVELOPMENT_VERSION");
        this.Modifiable = isDevelopment.getBoolean(param.thisObject);
    }

    public Field getIsDevelopment() {
        return this.isDevelopment;
    }

    public boolean getModifiable() {
        return this.Modifiable;
    }

}
