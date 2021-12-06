package com.gswxxn.hidedevicon.hidedevicon;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class DealWithMethod extends XC_MethodHook {
    private final XC_LoadPackage.LoadPackageParam lpparam;

    public DealWithMethod(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);

        SysInfo sysInfo = new SysInfo(lpparam, param);

        if (sysInfo.getModifiable()) {
            sysInfo.getIsDevelopment().setAccessible(true);
            sysInfo.getIsDevelopment().setBoolean(param.thisObject, false);
        }
    }
}
