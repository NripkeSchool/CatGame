import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class EdgeWeightedGraph extends EdgeWeightedGraph
{
    public void updateEdge(int v) //Mark the vertex and set all edges to INFINITY lim x--> 0 (1/x^2)
    {
        for (Edge e : adj[v])
        {
            e.weight = Double.POSITIVE_INFINITY;
        }
    }
}