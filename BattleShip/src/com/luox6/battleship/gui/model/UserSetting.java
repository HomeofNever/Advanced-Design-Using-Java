package com.luox6.battleship.gui.model;

import java.awt.*;
import java.util.prefs.Preferences;

/**
 * User setting persistence class
 */
public class UserSetting {
    /**
     * Node where APP's setting rest
     */
    private static final String USER_PREF_NODE = "luox6.battleship.userSetting";

    /* Keys belows are String Key refer to each of the settings */
    private static final String HIDDEN_COLOR = "HIDDEN_COLOR";
    private static final String DISCOVER_COLOR = "DISCOVER_COLOR";
    private static final String MARK_COLOR = "MARK_COLOR";
    private static final String GRID_ROW = "GRID_ROW";
    private static final String GRID_COL = "GRID_COL";
    private static final String TIME_LIMIT = "TIME_LIMIT";

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
    public static Color getDiscoverColor() {
        return String2Color(prefs.get(DISCOVER_COLOR, Color2String(Color.BLACK)));
    }

    public static Color getHiddenColor() {
        return String2Color(prefs.get(HIDDEN_COLOR, Color2String(Color.WHITE)));
    }

    public static Color getMarkColor() {
        return String2Color(prefs.get(MARK_COLOR, Color2String(Color.RED)));
    }

    public static void setHiddenColor(Color c) {
        prefs.put(HIDDEN_COLOR, Color2String(c));
    }

    public static void setDiscoverColor(Color deadColor) {
        prefs.put(DISCOVER_COLOR, Color2String(deadColor));
    }

    public static int getGridRow() {
        return Integer.parseInt(prefs.get(GRID_ROW, "8"));
    }

    public static int getGridCol() {
        return Integer.parseInt(prefs.get(GRID_COL, "8"));
    }

    public static void setGridRow(int row) {
        prefs.put(GRID_ROW, String.valueOf(row));
    }

    public static void setGridCol(int col) {
        prefs.put(GRID_COL, String.valueOf(col));
    }

    public static int getTimeLimit() { return Integer.parseInt(prefs.get(TIME_LIMIT, "30")); }

    public static void setTimeLimit(int time) { prefs.put(TIME_LIMIT, String.valueOf(time)); }
}
