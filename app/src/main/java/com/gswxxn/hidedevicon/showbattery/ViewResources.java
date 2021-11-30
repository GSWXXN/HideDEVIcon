package com.gswxxn.hidedevicon.showbattery;

import de.robv.android.xposed.XposedBridge;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ViewResources {
    public int current_temperature_value = -1;
    public int tempe_value_container = -1;

    public ViewResources(String versionCode) {
        try {
            InputStreamReader isr = new InputStreamReader(
                    Objects.requireNonNull(
                            this.getClass().getClassLoader()).getResourceAsStream("assets/view_config.json"));
            BufferedReader bfr = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONObject root = new JSONObject(stringBuilder.toString());
            String current_temperature_value = root.getJSONObject("current_temperature_value").getString(versionCode);
            String tempe_value_container = root.getJSONObject("tempe_value_container").getString(versionCode);

            this.current_temperature_value = Integer.parseInt(current_temperature_value, 16);
            this.tempe_value_container = Integer.parseInt(tempe_value_container, 16);
        } catch (IOException e) {
            XposedBridge.log(e);
        } catch (JSONException ignored) {}
    }
}
