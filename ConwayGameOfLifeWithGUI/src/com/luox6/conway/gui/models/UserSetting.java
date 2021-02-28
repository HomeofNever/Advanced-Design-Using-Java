package com.luox6.conway.gui.models;

import java.awt.*;
import java.util.prefs.Preferences;

/**
 * User setting persistence class
 */
public class UserSetting {
    /**
     * Node where APP's setting rest
     */
    private static final String USER_PREF_NODE = "luox6.conway.userSetting";

    /* Keys belows are String Key refer to each of the settings */
    private static final String ALIVE_COLOR = "ALIVE_COLOR";
    private static final String DEAD_COLOR = "DEAD_COLOR";
    private static final String TEXT_COLOR = "TEXT_COLOR";
    private static final String SHOW_SURVIVAL = "SHOW_SURVIVAL";
    private static final String MAX_LEVEL_SHADE = "MAX_LEVEL_SHADE";
    private static final String OUTPUT_FILE_FORMAT = "OUTPUT_FILE_FORMAT";

    /**
     * Java Preference Object
     */
    private static final Preferences prefs = Preferences.userRoot().node(USER_PREF_NODE);

    /**
     * Convert Color Object to RGB String representation
     * @param c Color to be converted
     * @return String of integer representation of RGB value
     */
    private static String Color2String(Color c) {
        return String.valueOf(c.getRGB());
    }

    /**
     * Convert a String of RGB integer representation into Color object
     * @param s String of RGB integer representation
     * @return Color of represented RGB
     */
    private static Color String2Color(String s) {
        return new Color(Integer.parseInt(s));
    }

    /* Methods below are settings' getters and setters */
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

    public static String getOutputFilesFormat() {
        return prefs.get(OUTPUT_FILE_FORMAT, "result.%1d.txt");
    }

    public static void setOutputFilesFormat(String s) {
        prefs.put(OUTPUT_FILE_FORMAT, s);
    }

    public static int getMaxLevelShade() {
        return prefs.getInt(MAX_LEVEL_SHADE, 5);
    }

    public static void setMaxLevelShade(int level) {
        prefs.putInt(MAX_LEVEL_SHADE, level);
    }
}
