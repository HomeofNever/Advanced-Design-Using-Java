package com.luox6.epidemic.gui.models;

import java.awt.*;
import java.util.prefs.Preferences;

/**
 * User setting persistence class
 */
public class UserSetting {
    /**
     * Node where APP's setting rest
     */
    private static final String USER_PREF_NODE = "luox6.epidemic.userSetting";

    /* Keys belows are String Key refer to each of the settings */
    public enum keys {
        SUSCEPTIBLE_COLOR,
        INFECTED_COLOR,
        DEAD_COLOR,
        RECOVERED_COLOR,
        VALUE_N, // Integer > 0 random node to infect
        VALUE_S, // Integer > 0 node with >s degree to infect
        VALUE_K, // Integer > 0 BFS k level to infect
        VALUE_D, // Double between 0-1, dead possibility
        VALUE_T, // Integer > 0 dead/recover ticks
        VALUE_LAMBDA, // Double > 0 # to infect = lambda * # of infected
        NUM_THREAD, // Number of thread
        NUM_STEP // step/tick to run
    }

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
    public static Color getSusceptibleColor() {
        return String2Color(prefs.get(keys.SUSCEPTIBLE_COLOR.name(), Color2String(Color.GREEN)));
    }

    public static Color getDeadColor() {
        return String2Color(prefs.get(keys.DEAD_COLOR.name(), Color2String(Color.RED)));
    }

    public static Color getInfectedColor() {
        return String2Color(prefs.get(keys.INFECTED_COLOR.name(), Color2String(Color.BLACK)));
    }

    public static Color getRecoveredColor() {
        return String2Color(prefs.get(keys.RECOVERED_COLOR.name(), Color2String(Color.YELLOW)));
    }

    public static int getValueN() {
        return prefs.getInt(keys.VALUE_N.name(), 2);
    }

    public static int getValueS() {
        return prefs.getInt(keys.VALUE_S.name(), 4);
    }

    public static int getValueK() {
        return prefs.getInt(keys.VALUE_K.name(), 4);
    }

    public static double getValueD() {
        return prefs.getDouble(keys.VALUE_D.name(), 0.25);
    }

    public static int getValueT() {
        return prefs.getInt(keys.VALUE_T.name(), 3);
    }

    public static double getLambda() {
        return prefs.getDouble(keys.VALUE_LAMBDA.name(), 1.25);
    }

    public static int getThread() {
        return prefs.getInt(keys.NUM_THREAD.name(), 4);
    }

    public static int getStep() {
        return prefs.getInt(keys.NUM_STEP.name(), 100);
    }

    public static void setSusceptibleColor(Color c) {
        prefs.put(keys.SUSCEPTIBLE_COLOR.name(), Color2String(c));
    }

    public static void setDeadColor(Color deadColor) {
        prefs.put(keys.DEAD_COLOR.name(), Color2String(deadColor));
    }

    public static void setRecoveredColor(Color c) {
        prefs.put(keys.RECOVERED_COLOR.name(), Color2String(c));
    }

    public static void setInfectedColor(Color c) {
        prefs.put(keys.INFECTED_COLOR.name(), Color2String(c));
    }


    public static void setValueN(int i) {
        prefs.putInt(keys.VALUE_N.name(), i);
    }

    public static void setValueS(int i) {
        prefs.putInt(keys.VALUE_S.name(), i);
    }

    public static void setValueK(int i) {
        prefs.putInt(keys.VALUE_K.name(), i);
    }

    public static void setValueD(double d) {
        prefs.putDouble(keys.VALUE_D.name(), d);
    }

    public static void setValueT(int i) {
        prefs.putInt(keys.VALUE_T.name(), i);
    }

    public static void setLambda(double d) {
        prefs.putDouble(keys.VALUE_LAMBDA.name(), d);
    }

    public static void setThread(int i) {
        prefs.putInt(keys.NUM_THREAD.name(), i);
    }

    public static void setStep(int i) {
        prefs.putInt(keys.NUM_STEP.name(), i);
    }
}
