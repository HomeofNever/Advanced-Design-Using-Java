package com.luox6.conway;

import java.io.*;

import static com.luox6.conway.utils.ConveyFileParser.parse;

/**
* Conway Game of Life Simulator
* @author Xinhao Luo
* @version 0.0.1
*/
public class Main {
    /**
     * Main entry
     * @param args User specify args, should include input, output locations and expected steps.
     */
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("startGUI")) {
            System.out.println("Starting GUI...");
            com.luox6.conway.gui.Main.init();
            return;
        }

        if (args.length != 3) {
            System.err.println("Args: <input> <output> <step>");
            System.exit(1);
        }

        try {
            File seedFile = new File(args[0]);
            BufferedReader seedFileReader = new BufferedReader(new FileReader(seedFile));
            File outputDirectory = new File (args[1]);
            if (outputDirectory.exists()) {
                if (!outputDirectory.isDirectory()) {
                    throw new Exception("Err: [io] Output path must be a directory");
                }
            } else {
                // Try creating directory, including all necessary parent directory
                if (!outputDirectory.mkdirs()) {
                    throw new Exception("Err: [io] Failed to create output directory");
                }
            }
            int num_ticks = Integer.parseInt(args[2]);

            if (num_ticks < 0) {
                throw new Exception("Err: [input] step " + num_ticks + " is invalid");
            }

            ConwayMap c = parse(seedFileReader);
            String seedFileName = seedFile.getName().split("\\.")[0];

            for (int i = 0; i <= num_ticks; i++) {
                String filename = seedFileName + "." + i + ".txt";
                BufferedWriter outputFile = new BufferedWriter(new FileWriter(new File(outputDirectory, filename)));
                outputFile.append(c.toString());
                outputFile.close();
                c = c.tick();
            }

            System.out.println("[success] Conway game of life simulation: no error.");
        } catch (IOException e) {
            System.err.println("An error occur while processing files: ");
            e.printStackTrace();
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Err: [input] unable to parse " + args[2] + " as integer");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
