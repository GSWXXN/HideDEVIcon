package com.gswxxn.hidedevicon.showbattery;

import de.robv.android.xposed.XposedBridge;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewResources {
    public int currentTemperatureValue = -1;
    public int tempeValueContainer = -1;

    public ViewResources(String versionName) {
        try {
            StringBuilder stringBuilder = openJson();
            Map<String, String> configMap = readJson(stringBuilder, versionName);


            this.currentTemperatureValue = Integer.parseInt(Objects.requireNonNull(configMap.get("currentTemperatureValue")), 16);
            this.tempeValueContainer = Integer.parseInt(Objects.requireNonNull(configMap.get("tempeValueContainer")), 16);
        } catch (IOException | JSONException e) {
            XposedBridge.log(e);
        }
    }

    public StringBuilder openJson() throws IOException {
        InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(
                        this.getClass().getClassLoader()).getResourceAsStream("assets/view_config.json"));
        BufferedReader bfr = new BufferedReader(isr);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bfr.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder;
    }

    public Map<String, String> readJson(StringBuilder stringBuilder, String versionName) throws JSONException {
        Map<String, String> map = new HashMap<>();
        JSONObject root = new JSONObject(stringBuilder.toString());
        try{
            map.put("currentTemperatureValue", root.getJSONObject("current_temperature_value").getString(versionName));
            map.put("tempeValueContainer", root.getJSONObject("tempe_value_container").getString(versionName));
        }catch (JSONException e) {
            String defaultVersion = root.getString("default");
            map.put("currentTemperatureValue", root.getJSONObject("current_temperature_value").getString(defaultVersion));
            map.put("tempeValueContainer", root.getJSONObject("tempe_value_container").getString(defaultVersion));
        }
        return map;
    }
}
