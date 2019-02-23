package graphics.model;

import edu.princeton.cs.algs4.*;

/**
 * @author DateBro
 * @date 2019/2/23
 */

public class GraphicsKruskalMST {

    private Queue<WeightedLabeledEdge> mst;

    public GraphicsKruskalMST(GraphicsEdgeWeightedGraph graphicsEdgeWeightedGraph) {
        mst = new Queue<>();

        // more efficient to build heap by passing array of edges
        MinPQ<WeightedLabeledEdge> pq = new MinPQ<>();
        for (WeightedLabeledEdge e : graphicsEdgeWeightedGraph.edges()) {
            pq.insert(e);
        }

        // run greedy algorithm
        UF uf = new UF(graphicsEdgeWeightedGraph.V());
        while (!pq.isEmpty()) {
            WeightedLabeledEdge e = pq.delMin();
            NumberLabeledVertex labeledV = e.eitherLabeledVertex(), labeledW = e.otherLabeledVertex(labeledV);
            int v = labeledV.getValue();
            int w = labeledW.getValue();
            // v-w does not create a cycle
            if (!uf.connected(v, w)) {
                // merge v and w components
                uf.union(v, w);
                // add edge e to mst
                mst.enqueue(e);
                labeledV.setBorderColor(NumberLabeledVertex.RED);
                labeledW.setBorderColor(NumberLabeledVertex.RED);
                e.setGraphicsColor(WeightedLabeledEdge.RED);
            } else {
                e.setGraphicsColor(WeightedLabeledEdge.GRAY);
            }
        }
    }

    public Iterable<WeightedLabeledEdge> edges() {
        return mst;
    }
}
