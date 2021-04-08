package com.luox6.epidemic.model;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Graph {
    // enum
    public enum INITIAL_INFECTED_MODE {RANDOM_N, DEGREE_S, BFS_K}

    // nodes representation
    private final Map<String, HashSet<String>> adjList = new HashMap<>();
    private final Map<String, Node> nodes = new HashMap<>();

    // parameters
    /**
     * Used when ask to randomly mark infected nodes
     */
    private int n = 0;

    /**
     * Used when ask to mark all node with
     * degree greater than s
     */
    private int s = 0;

    /**
     * A single random node is selected as a seed infected node
     * and then the breadth first search (BFS) algorithm is run from that
     * seed node until k total nodes (including the seed node) are visited.
     * Then all these k nodes are marked as infected.
     */
    private int k = 0;

    /**
     * dies with probability d or recovers with probability 1 - d
     */
    private double d = 0.0;

    /**
     * An infected node stays infected for t ticks.
     */
    private int t = 0;

    /**
     * The force of infection as the number of susceptible neighbors
     * of currently infected nodes that become infected in the next
     * tick divided by the number of currently infected nodes.
     */
    private double lambda = 0.0;

    /**
     * Random number generator
     */
    private int seed = 96169;

    /**
     * Num of thread
     */
    private int numThread = 1;

    private ThreadLocal<SplittableRandom> rGenerator;

    /**
     * Number of ticks current Graph has experienced
     */
    private int tick = 0;

    private AtomicInteger infectedCount = new AtomicInteger();
    private AtomicInteger recoveredCount = new AtomicInteger();
    private AtomicInteger deadCount = new AtomicInteger();
    private AtomicInteger susceptibleCount = new AtomicInteger();

    /**
     * Whether graph has infected with init algo
     */
    private Boolean infected = false;

    private final Lock simulationLock = new ReentrantLock();

    private ExecutorService executor;

    /**
     * Default constructor
     */
    public Graph() {
        init();
    }

    /**
     * Copy Constructor
     * This won't make an exact copy of Graph provided,
     * but only copy the graph representation so that you may
     * start a new simulation from this stage
     * Node age tick will also lost
     * @param g prototype of G
     */
    public Graph(Graph g) {
        for (Map.Entry<String, HashSet<String>> m : g.adjList.entrySet()) {
            adjList.put(m.getKey(), new HashSet<>(m.getValue()));
        }
        for (Map.Entry<String, Node> nd : g.nodes.entrySet()) {
            nodes.put(nd.getKey(), new Node(nd.getValue(), true));
        }

        // Parameters
        n = g.n;
        k = g.k;
        s = g.s;
        d = g.d;
        t = g.t;
        lambda = g.lambda;
        numThread = g.numThread;
        seed = g.seed;

        init();

        infectedCount.set(g.getInfectedCount());
        recoveredCount.set(g.getRecoveredCount());
        deadCount.set(g.getDeadCount());
        susceptibleCount.set(g.getSusceptibleCount());
    }

    /**
     * Set graph with new representation
     *
     * @param s List of <Node name, Node name> Entry set
     *          The set represent an edge between nodes
     *          Node will be automatically init if not present in the
     *          graph before
     */
    public void initGraphData(Collection<Map.Entry<String, String>> s) {
        for (Map.Entry<String, String> i : s) {
            String key = i.getKey();
            String value = i.getValue();
            initializeNode(key);
            initializeNode(value);
            adjList.get(key).add(value);
            adjList.get(value).add(key);
        }
    }

    public void init() {
        tick = 0;
        infectedCount = new AtomicInteger();
        recoveredCount = new AtomicInteger();
        deadCount = new AtomicInteger();
        susceptibleCount = new AtomicInteger(nodes.size());
        infected = false;
        initThreadSetting();
        initSeedSetting();
    }

    private void initThreadSetting() {
        executor = Executors.newFixedThreadPool(numThread);
    }

    private void initSeedSetting() {
        rGenerator = ThreadLocal.withInitial(() -> new SplittableRandom(seed));
    }

    private void initializeNode(String s) {
        if (!nodes.containsKey(s)) {
            Node node = new Node(s);
            nodes.put(s, node);
        }
        if (!adjList.containsKey(s)) {
            adjList.put(s, new HashSet<>());
        }
    }

    /**
     * Do one simulation and go to next tick
     * If multiple calls on this method, request will be
     * execute one at a time.
     */
    public void simulate() throws ExecutionException, InterruptedException {
        simulationLock.lock();
        // First, go through all nodes, and set node to death or recovered, if necessary
        // and adjust adj list accordingly
        adjustDeadRecovered();

        // Count how many nodes should be infected
        int nextInfected = (int) Math.ceil(infectedCount.get() * lambda);
        // Only calculate neighbours when necessary
        Set<String> susList = getSusceptibleNeighbors();
        // Infect based on lambda, and update count accordingly
        infectNodes(susList, nextInfected);
        // Notify node to incur if necessary
        tickNodes();
        ++tick;
        simulationLock.unlock();
    }

    /** Tool methods for Simulation **/
    private void tickNodes() throws ExecutionException, InterruptedException {
        String[] nds = adjList.keySet().toArray(new String[0]);
        List<Map.Entry<Integer, Integer>> trunk = truckByThread(nds.length);
        List<Future<List<String>>> threadList = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : trunk) {
            threadList.add(executor.submit(() -> {
                for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                    Node nd = nodes.get(nds[i]);
                    nd.tick();
                }
                return null;
            }));
        }

        for (Future<List<String>> f : threadList) {
            f.get();
        } // Wait for execution
    }

    private void adjustDeadRecovered() throws ExecutionException, InterruptedException {
        String[] nds = adjList.keySet().toArray(new String[0]);
        List<Map.Entry<Integer, Integer>> trunk = truckByThread(nds.length);
        List<Future<List<String>>> threadList = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : trunk) {
            threadList.add(executor.submit(() -> {
                List<String> ls = new LinkedList<>();
                for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                    Node nd = nodes.get(nds[i]);
                    if (nd.getStatus() == Node.STATUS.INFECTIOUS && nd.getInfectedAge() >= t) {
                        infectedCount.getAndDecrement();
                        if (rGenerator.get().nextDouble() <= d) {
                            ls.add(nd.getName());
                            nd.setStatus(Node.STATUS.DEAD);
                            deadCount.getAndIncrement();
                        } else {
                            nd.setStatus(Node.STATUS.RECOVERED);
                            recoveredCount.getAndIncrement();
                        }
                    }
                }

                return ls;
            }));
        }

        for (Future<List<String>> f : threadList) {
            for (String i : f.get()) {
                adjList.remove(i);
                for (HashSet<String> ls : adjList.values()) {
                    ls.remove(i);
                }
            }
        }
    }

    private Set<String> getSusceptibleNeighbors() throws ExecutionException, InterruptedException {
        if (susceptibleCount.get() == 0) {
            return new HashSet<>();
        }
        String[] nds = adjList.keySet().toArray(new String[0]);
        List<Map.Entry<Integer, Integer>> trunk = truckByThread(nds.length);
        List<Future<Set<String>>> threadList = new LinkedList<>();

        // Always find nodes from lower side
        if (susceptibleCount.get() > infectedCount.get()) {
            for (Map.Entry<Integer, Integer> entry : trunk) {
                threadList.add(executor.submit(() -> {
                    Set<String> ls = new HashSet<>();
                    for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                        Node nd = nodes.get(nds[i]);
                        if (nd.getStatus() == Node.STATUS.INFECTIOUS) {
                            for (String ns : adjList.get(nd.getName())) {
                                Node.STATUS status = nodes.get(ns).getStatus();
                                if (status != Node.STATUS.RECOVERED && status != Node.STATUS.INFECTIOUS) {
                                    ls.add(ns);
                                }
                            }
                        }
                    }

                    return ls;
                }));
            }
        } else {
            for (Map.Entry<Integer, Integer> entry : trunk) {
                threadList.add(executor.submit(() -> {
                    Set<String> ls = new HashSet<>();
                    for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                        Node nd = nodes.get(nds[i]);
                        if (nd.getStatus() == Node.STATUS.SUSCEPTIBLE) {
                            for (String ns : adjList.get(nd.getName())) {
                                Node.STATUS status = nodes.get(ns).getStatus();
                                if (status == Node.STATUS.INFECTIOUS) {
                                    ls.add(nds[i]);
                                    break;
                                }
                            }
                        }
                    }

                    return ls;
                }));
            }
        }

        Set<String> res = new HashSet<>();
        for (Future<Set<String>> f : threadList) {
            res.addAll(f.get());
        }

        return res;
    }

    private void infectNodes(Collection<String> c, int shouldInfected) throws ExecutionException, InterruptedException {
        List<Map.Entry<Integer, Integer>> trunk = truckByThread(c.size());
        List<Future<Boolean>> threadList = new LinkedList<>();
        String[] nds = c.toArray(new String[0]);
        if (shouldInfected < c.size()) {
            // choose number of infected
            double possibility = (double) shouldInfected / c.size();
            for (Map.Entry<Integer, Integer> entry : trunk) {
                threadList.add(executor.submit(() -> {
                    for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                        if (rGenerator.get().nextDouble() <= possibility) {
                            // Should be infected
                            Node nd = nodes.get(nds[i]);
                            nd.setStatus(Node.STATUS.INFECTIOUS);
                            infectedCount.getAndIncrement();
                            susceptibleCount.getAndDecrement();
                        }
                    }
                    return true;
                }));
            }
        } else {
            // Always Infected
            for (Map.Entry<Integer, Integer> entry : trunk) {
                threadList.add(executor.submit(() -> {
                    for (int i = entry.getKey(); i <= entry.getValue(); i++) {
                        Node nd = nodes.get(nds[i]);
                        nd.setStatus(Node.STATUS.INFECTIOUS);
                        infectedCount.getAndIncrement();
                        susceptibleCount.getAndDecrement();
                    }
                    return true;
                }));
            }
        }

        for (Future<Boolean> i : threadList) {
            i.get();
        } // Wait for execution
    }

    /**
     * Trunk index by task number and item
     * assume index start from 0 and end with total - 1
     * @param total the number of total items needed to be trucked
     * @return List of Pair<startIndex, endIndex>
     */
    private List<Map.Entry<Integer, Integer>> truckByThread(int total) {
        List<Map.Entry<Integer, Integer>> l = new ArrayList<>();
        if (total != 0) {
            int truckSize = total / numThread;
            if (truckSize == 0) {
                l.add(new AbstractMap.SimpleEntry<>(0, total - 1));
            } else {
                for (int i = 0; i < numThread; i++) {
                    int begin = i * truckSize;
                    l.add(new AbstractMap.SimpleEntry<>(begin, begin + truckSize - 1));
                }
                l.get(l.size() - 1).setValue(total - 1); // Always make up the rest using the last thread
            }
        }

        return l;
    }

    /**
     * Set graph initial infected mode and infect accordingly
     * @param m infection mode
     */
    public void setInitialInfected(INITIAL_INFECTED_MODE m) {
        infected = true;
        Random rnd = new Random(seed);
        switch (m) {
            case RANDOM_N -> {
                if (nodes.size() < n) {
                    throw new RuntimeException("The number of node %d in the graph is not enough to be infected by n=%d".formatted(nodes.size(), n));
                }
                int[] inf = new int[n];
                for (int i = 0; i < n; i++) {
                    inf[i] = rnd.nextInt(adjList.size());
                    susceptibleCount.getAndDecrement();
                    infectedCount.getAndIncrement();
                }
                Arrays.sort(inf);
                int cp = 0;
                Iterator<Node> iter = nodes.values().iterator();
                for (int i : inf) {
                    while (cp < i - 1) {
                        iter.next();
                        cp++;
                    }
                    iter.next().setStatus(Node.STATUS.INFECTIOUS);
                    cp++;
                }
            }
            case DEGREE_S -> {
                for (Map.Entry<String, HashSet<String>> e : adjList.entrySet()) {
                    if (e.getValue().size() > s) {
                        nodes.get(e.getKey()).setStatus(Node.STATUS.INFECTIOUS);
                        susceptibleCount.getAndDecrement();
                        infectedCount.getAndIncrement();
                    }
                }
            }
            case BFS_K -> {
                // Find a random node
                int rand = rnd.nextInt(adjList.size());
                Iterator<Node> itr = nodes.values().iterator();
                for (int i = 0; i < rand - 1; i++) {
                    itr.next();
                }
                Node node = itr.next();
                node.setStatus(Node.STATUS.INFECTIOUS);
                susceptibleCount.getAndDecrement();
                infectedCount.getAndIncrement();

                // Find the rest of the node
                int temp = k - 1;
                List<Node> l = new ArrayList<>();
                for (String no : adjList.get(node.getName())) {
                    l.add(nodes.get(no));
                }
                while (temp > 0) {
                    if (l.size() > 0) {
                        node = l.remove(0);
                        if (node.getStatus() != Node.STATUS.INFECTIOUS) {
                            node.setStatus(Node.STATUS.INFECTIOUS);
                            susceptibleCount.getAndDecrement();
                            infectedCount.getAndIncrement();
                            temp--;
                        }
                        for (String no : adjList.get(node.getName())) {
                            l.add(nodes.get(no));
                        }
                    } else {
                        break; // No enough edge to go
                    }
                }
            }
            default -> throw new RuntimeException("Undefined Initialization process.");
        }
    }

    /**
     * Getters and Setters
     **/
    public int getInfectedCount() {
        return infectedCount.get();
    }

    public int getRecoveredCount() {
        return recoveredCount.get();
    }

    public int getSusceptibleCount() {
        return susceptibleCount.get();
    }

    public int getDeadCount() {
        return deadCount.get();
    }

    public int getTick() {
        return tick;
    }

    public Boolean getInfected() {
        return infected;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        initSeedSetting();
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setNumThread(int numThread) {
        if (numThread >= 1) {
            this.numThread = numThread;
            initThreadSetting();
        } else {
            throw new RuntimeException("Unable to set thread %d".formatted(numThread));
        }
    }
}