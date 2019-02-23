package graphics.model;

import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author DateBro
 * @date 2019/2/22
 */

public class GraphicsEdgeWeightedGraph {

    private int V;
    private int E;
    private Bag<WeightedLabeledEdge>[] adj;
    private ArrayList<NumberLabeledVertex> numberLabeledVertices;

    public GraphicsEdgeWeightedGraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonNegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<WeightedLabeledEdge>[]) new Bag[V];
        numberLabeledVertices = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public GraphicsEdgeWeightedGraph(Iterable<NumberLabeledVertex> vertices, Iterable<WeightedLabeledEdge> edges) {
        ArrayList<NumberLabeledVertex> vertexList = (ArrayList<NumberLabeledVertex>) vertices;
        ArrayList<WeightedLabeledEdge> edgeList = (ArrayList<WeightedLabeledEdge>) edges;

        this.V = vertexList.size();
        this.E = edgeList.size();
        adj = (Bag<WeightedLabeledEdge>[]) new Bag[V];
        numberLabeledVertices = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }

        initVertices(vertexList);
        initEdges(edgeList);
    }

    public GraphicsEdgeWeightedGraph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("Number of vertices must be nonNegative");
            }
            this.E = 0;
            adj = (Bag<WeightedLabeledEdge>[]) new Bag[V];
            numberLabeledVertices = new ArrayList<>();
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>();
            }

            for (int i = 0; i < V; i++) {
                double vertexX = in.readDouble();
                double vertexY = in.readDouble();
                NumberLabeledVertex labeledV = new NumberLabeledVertex(vertexX, vertexY, i);
                numberLabeledVertices.add(labeledV);
            }

            while (!in.isEmpty()) {
                int v = in.readInt();
                int w = in.readInt();
                int weight = in.readInt();
                NumberLabeledVertex labeledV = numberLabeledVertices.get(v);
                NumberLabeledVertex labeledW = numberLabeledVertices.get(w);
                WeightedLabeledEdge edge = new WeightedLabeledEdge(labeledV, labeledW, weight);
                addEdge(edge);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    private void initVertices(Iterable<NumberLabeledVertex> vertices) {
        for (NumberLabeledVertex vertex : vertices) {
            addVertex(vertex);
        }
    }

    private void initEdges(Iterable<WeightedLabeledEdge> edges) {
        for (WeightedLabeledEdge edge : edges) {
            addEdge(edge);
        }
    }

    public void paintKruskalGraph(Graphics2D g2) {
        for (WeightedLabeledEdge e : edges()) {
            e.paintEdge(g2);
        }

        for (NumberLabeledVertex v : numberLabeledVertices()) {
            v.paintVertex(g2);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(NumberLabeledVertex v) {
        if (v.getValue() < 0 || v.getValue() >= V) {
            throw new IllegalArgumentException("vertex " + v.getValue() + " is not between 0 and " + (V - 1));
        }
    }

    public void addVertex(NumberLabeledVertex v) {
        validateVertex(v);
        numberLabeledVertices.add(v);
    }

    public void addEdge(WeightedLabeledEdge e) {
        NumberLabeledVertex labeledV = e.eitherLabeledVertex();
        NumberLabeledVertex labeledW = e.otherLabeledVertex(labeledV);
        int v = labeledV.getValue();
        int w = labeledW.getValue();
        validateVertex(labeledV);
        validateVertex(labeledW);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<WeightedLabeledEdge> adj(NumberLabeledVertex v) {
        validateVertex(v);
        return adj[v.getValue()];
    }

    public Iterable<NumberLabeledVertex> numberLabeledVertices() {
        return numberLabeledVertices;
    }

    public Iterable<WeightedLabeledEdge> edges() {
        Bag<WeightedLabeledEdge> edgeBag = new Bag<>();
        for (NumberLabeledVertex v : numberLabeledVertices) {
            int selfLoops = 0;
            for (WeightedLabeledEdge e : adj(v)) {
                if (e.otherLabeledVertex(v).getValue() > v.getValue()) {
                    edgeBag.add(e);
                }

                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.otherLabeledVertex(v).getValue() == v.getValue()) {
                    if (selfLoops % 2 == 0) {
                        edgeBag.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return edgeBag;
    }

    public WeightedLabeledEdge[] edgeArray() {
        Bag<WeightedLabeledEdge> edgeBag = (Bag<WeightedLabeledEdge>) edges();
        WeightedLabeledEdge[] edgeArray = new WeightedLabeledEdge[edgeBag.size()];
        int i = 0;
        for (WeightedLabeledEdge edge : edgeBag) {
            edgeArray[i++] = edge;
        }
        return edgeArray;
    }
}
