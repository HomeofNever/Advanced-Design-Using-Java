package com.luox6.conway.utils;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;

import java.io.BufferedReader;

public class ConveyFileParser {
    /**
     * Separator between numbers
     */
    public static final String SEPARATOR = ", ";

    /**
     * Parse the seed file and generate ConwayMap representation
     * @param reader seed file reader
     * @return Corresponded ConwayMap representation
     * @throws Exception file format error
     */
    public static ConwayMap parse(BufferedReader reader) throws Exception {
        String line = reader.readLine();
        String[] metaInfo = line.split(SEPARATOR);
        if (metaInfo.length != 2) {
            throw new Exception("Err: [seedFile] unable to identify row/col of the map");
        }

        int row = Integer.parseInt(metaInfo[0]);
        int col = Integer.parseInt(metaInfo[1]);

        if (row < 2 || col < 2) {
            throw new Exception("Err: [seedFile] Invalid row/col, must be positive integer larger than 2");
        }

        ConwayMap c = new ConwayMap(row, col);
        for (int i = 0; i < row; i++) {
            String currentLine = reader.readLine();
            String[] lineList = currentLine.split(SEPARATOR);
            if (lineList.length != col) {
                throw new Exception("Err: [seedFile] Unexpected col length at row index " + i);
            }
            for (int j = 0; j < col; j++) {
                char cellChar = lineList[j].charAt(0);
                int cell = Cell.charToInt(cellChar);
                if (cell == Cell.UNKNOWN_CELL) {
                    throw new Exception("Err: [seedFile] Unexpected cell type " + cellChar);
                }
                c.getCell(i, j).setStatus(cell);
            }
        }

        return c;
    }
}
