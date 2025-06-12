package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//Methma Lochanie Rathnayaka
public class Main {
    private static LinkedHashMap<Integer, List<Integer>> adjList = new LinkedHashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "InputFile.txt";
        parse(fileName);
        if (isAcyclic())
        {
            System.out.println("Yes");
            System.out.println("Directed Graph is Acyclic i.e. not a Cycle");
        }
        else {
            System.out.println("No");
            System.out.println("Directed Graph is Cyclic");
            isCyclic();
        }
    }

    // Find and print all unique cycles in the graph
    public static void isCyclic() {
        Set<Integer> visited = new HashSet<>();
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> cyclesList = new ArrayList<>();

        for (int node : adjList.keySet()) {
            existingCycles(node, visited, path, cyclesList);
        }
        System.out.println("Cycle in the graph: ");
        for (List<Integer> cycle : cyclesList) {
            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i));
                if (i < cycle.size() - 1) {
                    System.out.print("->");
                }
            }
        }
    }
    //Function to find cycles
    public static void existingCycles(int node, Set<Integer> visited, List<Integer> path, List<List<Integer>> cycles) {
        visited.add(node);
        path.add(node);

        for (int neighbor : adjList.get(node)) {
            int index = path.indexOf(neighbor);
            if (index != -1) {
                List<Integer> cycle = new ArrayList<>(path.subList(index, path.size()));
                cycle.add(neighbor);
                if (isCycleUnique(cycles, cycle)) {
                    cycles.add(cycle);
                }
            } else if (!visited.contains(neighbor)) {
                existingCycles(neighbor, visited, path, cycles);
            }
        }
        path.remove(path.size() - 1);
        visited.remove(node);
    }

    // Function to check the if a cycles are unique
    public static boolean isCycleUnique(List<List<Integer>> cycles, List<Integer> cycle) {
        Set<Integer> cycleSet = new HashSet<>(cycle);
        for (List<Integer> existingCycle : cycles) {
            Set<Integer> existingCycleSet = new HashSet<>(existingCycle);
            if (cycleSet.equals(existingCycleSet)) {
                return false;
            }
        }
        return true;
    }

    //Function to remove the vertex if the vertex is a sink
    public static void removeVertex(int vertex) {
        //remove only the key which existing as a sink vertex
        if (adjList.containsKey(vertex)) {
            adjList.remove(vertex);
        }
        for (List<Integer> neighbors : adjList.values()) {
            for (int value = 0; value < neighbors.size(); value++) {
                //if the sink vertex added as a value to a list then remove those values also
                if (neighbors.get(value) == vertex)
                {
                    neighbors.remove(Integer.valueOf(vertex));
                }
            }
        }
    }

    //Function to find sink vertices
    public static int findSink() {
        for (int vertex : adjList.keySet()) {
            if (adjList.get(vertex).isEmpty()) {
                return vertex;
            }
        }
        return 0;
    }

    //Function to add input file elements to the adjacency list
    public static void parse(String filename){
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextInt()) {
                int startVertexInt = scanner.nextInt();
                int endVertexInt = scanner.nextInt();
                int key2 = endVertexInt;

                if (!adjList.containsKey(startVertexInt)) {
                    adjList.put(startVertexInt, new ArrayList());
                }
                // Check if the value is already in the ArrayList
                if (adjList.get(startVertexInt).contains(endVertexInt)) {
                    System.out.println("The edge pair " + startVertexInt + " " + endVertexInt + " is already added to the adjacency List.");
                } else {
                    // Add the value to the ArrayList corresponding to the key
                    adjList.get(startVertexInt).add(endVertexInt);
                    System.out.println("Added the edge pair " + startVertexInt + " " + endVertexInt + " to the adjacency List.");
                }

                // Check if the second key already exists in the LinkedHashMap
                if (!adjList.containsKey(key2)) {
                    adjList.put(key2, new ArrayList<>());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static boolean isAcyclic() {
        int vertex ;
        do {
            vertex = findSink();
            if (vertex == 0)
            {
                return false;
            }
            else
            {
                System.out.println("Removed vertex is:" + vertex);
                removeVertex(vertex);

            }
        }while (!(adjList.isEmpty()));
        return true;
    }
}