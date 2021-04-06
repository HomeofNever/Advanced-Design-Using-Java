package com.luox6.epidemic.gui.factories;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Factory for all sorts of FileChoosing behavior
 */
public class FileChooser {
    /**
     * FileChooser for single map save
     * @param title window title
     * @return FileChooser set with file only selection mode and override notice
     */
    public static JFileChooser loadMapDialog(String title) {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fne = new FileNameExtensionFilter("Map File", "csv");
        fc.setDialogTitle(title);
        fc.setFileFilter(fne);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        return fc;
    }
}