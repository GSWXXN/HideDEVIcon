package com.gswxxn.hidedevicon;

import com.gswxxn.hidedevicon.hidedevicon.HDIMain;
import com.gswxxn.hidedevicon.showbattery.BatteryMain;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam.packageName.equals("com.android.systemui")) {
            HDIMain.hook(lpparam);
        }

        if (lpparam.packageName.equals("com.miui.securitycenter")) {
            BatteryMain.hook(lpparam);
        }
    }
}
