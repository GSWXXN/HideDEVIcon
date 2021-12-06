package com.gswxxn.hidedevicon;

import com.gswxxn.hidedevicon.hidedevicon.HDIMain;
import com.gswxxn.hidedevicon.showbattery.BatteryMain;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookEntry implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if ("com.android.systemui".equals(lpparam.packageName)) {
            HDIMain.hook(lpparam);
        }

        if ("com.miui.securitycenter".equals(lpparam.packageName)) {
            BatteryMain.hook(lpparam);
        }
    }
}
