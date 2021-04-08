package com.luox6.epidemic.utils;

import com.luox6.epidemic.model.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parser {
    /**
     * Read a graph from file
     * @param f fileReader of the file
     */
    public static Graph loadGraph(FileReader f) throws IOException {
        BufferedReader reader = new BufferedReader(f);
        String line;
        List<Map.Entry<String, String>> ls = new LinkedList<>();
        while((line = reader.readLine()) != null) {
            String[] nodes = line.split(",");
            if (nodes.length > 2) {
                for (int i = 1; i < nodes.length; i++) {
                    ls.add(new AbstractMap.SimpleEntry<>(nodes[0], nodes[i]));
                }
            } else {
                ls.add(new AbstractMap.SimpleEntry<>(nodes[0], nodes[1]));
            }
        }

        Graph g = new Graph();
        g.initGraphData(ls);
        g.init();
        return g;
    }
}
