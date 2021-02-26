package com.luox6.conway.gui.factories;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Factory for all sorts of FileChoosing behavior
 */
public class FileChooser {
    public static JFileChooser conwayMapDialog(String title) {
        JFileChooser fc = new confirmFileChooser();
        FileNameExtensionFilter fne = new FileNameExtensionFilter("Text File", "txt");
        fc.setDialogTitle(title);
        fc.setFileFilter(fne);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        return fc;
    }

    public static JFileChooser conwayMapMultipleFileDialog(String title) {
        JFileChooser fc = new confirmFileChooser();
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);

        return fc;
    }
}

/**
 * This FileChooser class add an confirm before overwriting file
 */
class confirmFileChooser extends JFileChooser {
    @Override
    public void approveSelection() {
        File f = getSelectedFile();
        if (f.exists() && getDialogType() == SAVE_DIALOG) {
            int result;
            if (f.isDirectory()) {
                result = JOptionPane.showConfirmDialog(this, "Please make sure selected directory does not contain file with name matched output format, it may be overwrite!", "Continue?", JOptionPane.YES_NO_CANCEL_OPTION);
            } else {
                result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            }
            switch (result) {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    }
}
