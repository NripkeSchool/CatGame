import edu.princeton.cs.algs4.DjikstraUndirectedSP;

public class CatGame
{
    private EdgeWeightedGraph board;
    private int n;
    public CatGame(int n)
    {
        this.n = n;
        board = new EdgeWeightedGraph(n*n+1);

        //Do the inside edges first
        for (int row = 1; row<n-1; row++)
        {
            for (int col = 1; col<n-1; col++)
            {   
                int v = n*row + col; //Current vertex

                //Add all 6 edges
                board.addEdge(new Edge(v, v-n, 1));
                board.addEdge(new Edge(v, v-n+1, 1));

                board.addEdge(new Edge(v, v-1, 1));
                board.addEdge(new Edge(v, v+1, 1));

                board.addEdge(new Edge(v, v+n, 1));
                board.addEdge(new Edge(v, v+n+1, 1));
            }
        }

        //Do outside edges
        for (int col = 0; col<n-1; col++)
        {
            board.addEdge(new Edge(col, col+1, 1));
            board.addEdge(new Edge(col, n*n+1, 0));
        }
        board.addEdge(new Edge(n-1, n*n+1, 0));

        for (int col = 0; col<n-1; col++)
        {
            board.addEdge(new Edge(col + n*(n-1), col + n*(n-1) + 1, 1));
            board.addEdge(new Edge(col + n*(n-1), n*n+1, 0));
        }
        board.addEdge(new Edge(n*n, n*n+1, 0));

        for (int row = 0; row<n-1; row++)
        {
            board.addEdge(new Edge(row*n, row*n+n, 1));
            board.addEdge(new Edge(row*n, n*n+1, 0));
        }

        for (int row = 0; row<n-1; row++)
        {
            board.addEdge(new Edge(getVertex(row, n-1), getVertex(row+1, n-1), 1));
            board.addEdge(new Edge(getVertex(row, n-1), n*n+1, 0));
        }
    }

    private int getVertex(int row, int col) {return row*n + col;}

    public void markTile(int row, int col)
    {

    }

    public boolean marked(int row, int col)
    {

    }

    public int[] getCatTile() //Return next cat move
    {

    }

    public boolean catHasEscaped()
    {

    }

    public boolean catIsTrapped()
    {

    }
}