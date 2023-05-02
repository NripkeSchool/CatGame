import edu.princeton.cs.algs4.Edge;

public class CatEdge extends Edge
{
    private double weight = 1; //Weight is constant between each tile
    
    public CatEdge(int to, int from)
    {
        super(to, from, weight);
    }

    /**
     * The markEdge() method is an extension of the Edge class made
     * to allow for "marking" edges, or deleting them in a sense by making
     * their weight POSITIVE_INFINITY
     */
    public void markEdge()
    {
        weight = Double.POSITIVE_INFINITY;
    }

    /**
     * Getter method that returns the weight of this edge
     * @return double weight of the edge
     */
    public double weight()
    {
        return weight;
    }
}
