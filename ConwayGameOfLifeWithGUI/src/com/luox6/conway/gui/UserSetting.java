package com.luox6.conway.gui;

import java.awt.*;
import java.util.prefs.Preferences;

public class UserSetting {
    private static final String USER_PREF_NODE = "luox6.conway.userSetting";
    private static final String ALIVE_COLOR = "ALIVE_COLOR";
    private static final String DEAD_COLOR = "DEAD_COLOR";
    private static final String TEXT_COLOR = "TEXT_COLOR";

    private static final String SHOW_SURVIVAL = "SHOW_SURVIVAL";

    private static final Preferences prefs = Preferences.userRoot().node(USER_PREF_NODE);

    private static String Color2String(Color c) {
        return String.valueOf(c.getRGB());
    }

    private static Color String2Color(String s) {
        return new Color(Integer.parseInt(s));
    }

    public static Color getAliveColor() {
        return String2Color(prefs.get(ALIVE_COLOR, Color2String(Color.RED)));
    }

    public static Color getDeadColor() {
        return String2Color(prefs.get(DEAD_COLOR, Color2String(Color.WHITE)));
    }

    public static Color getTextColor() {
        return String2Color(prefs.get(TEXT_COLOR, Color2String(Color.BLACK)));
    }

    public static boolean getSurvivalStatus() {
        return prefs.getBoolean(SHOW_SURVIVAL, true);
    }

    public static void setAliveColor(Color c) {
        prefs.put(ALIVE_COLOR, Color2String(c));
    }

    public static void setDeadColor(Color deadColor) {
        prefs.put(DEAD_COLOR, Color2String(deadColor));
    }

    public static void setShowSurvivalTimes(boolean showSurvivalTimes) {
        prefs.putBoolean(SHOW_SURVIVAL, showSurvivalTimes);
    }

    public static void setTextColor(Color textColor) {
        prefs.put(TEXT_COLOR, Color2String(textColor));
    }
}
