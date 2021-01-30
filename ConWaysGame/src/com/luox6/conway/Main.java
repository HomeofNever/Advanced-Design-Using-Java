package com.luox6.conway;

import java.io.*;

/**
 * Console interface handling and file parsing
 */
public class Main {

    /**
     * Separator between numbers
     */
    public static final String SEPARATOR = ", ";

    /**
     * Main entry
     * @param args User specify args, should include input, output locations and expected steps.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Args: <input> <output> <step>");
            System.exit(1);
        }

        try {
            BufferedReader seedFile = new BufferedReader(new FileReader(args[0]));
            BufferedWriter outputFile = new BufferedWriter(new FileWriter(args[1]));

            int num_ticks = Integer.parseInt(args[2]);

            if (num_ticks < 0) {
                throw new Exception("Err: step " + num_ticks + " is invalid");
            }

            ConwayMap c = parse(seedFile);
            outputFile.write(c.toString());

            for (int i = 0; i < num_ticks; i++) {
                c.tick();
                outputFile.append(c.toString());
            }

            outputFile.close();

            System.out.println("Conway game of life simulation: no error.");
        } catch (IOException e) {
            System.err.println("An error occur while processing files: ");
            e.printStackTrace();
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Err: unable to parse " + args[2] + " as Integer");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

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
            throw new Exception("Err: [seedFile] cannot parse row/col");
        }

        int row = Integer.parseInt(metaInfo[0]);
        int col = Integer.parseInt(metaInfo[1]);

        if (row < 0 || col < 0) {
            throw new Exception("Err: [seedFile] invalid row/col, must be positive integer");
        }

        ConwayMap c = new ConwayMap(row, col);
        for (int i = 0; i < row; i++) {
            String currentLine = reader.readLine();
            String[] lineList = currentLine.split(SEPARATOR);
            if (lineList.length != col) {
                throw new Exception("Err: Unexpected col length at row index " + i);
            }
            for (int j = 0; j < col; j++) {
                char cellChar = lineList[j].charAt(0);
                int cell = ConwayMap.charToInt(cellChar);
                if (cell == ConwayMap.UNKNOWN_CELL) {
                    throw new Exception("Err: unexpected cell type " + cellChar);
                }
                c.setCell(i, j, cell);
            }
        }

        return c;
    }
}
