package com.gswxxn.hidedevicon.hidedevicon;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;

public class SysInfo {
    private final Field IS_DEVELOPMENT_VERSION;
    private final Boolean Modifiable;

    public SysInfo (XC_LoadPackage.LoadPackageParam lpparam, XC_MethodHook.MethodHookParam param) throws NoSuchFieldException, IllegalAccessException {
        Class<?> build = XposedHelpers.findClass("miui.os.Build", lpparam.classLoader);
        this.IS_DEVELOPMENT_VERSION = build.getDeclaredField("IS_DEVELOPMENT_VERSION");
        this.Modifiable = IS_DEVELOPMENT_VERSION.getBoolean(param.thisObject);
    }

    public Field getIS_DEVELOPMENT_VERSION() {
        return this.IS_DEVELOPMENT_VERSION;
    }

    public boolean getModifiable() {
        return this.Modifiable;
    }

}
