import edu.princeton.cs.algs4.DjikstraUndirectedSP;

public class CatGame
{
    private EdgeWeightedGraph board;
    private int n;
    private boolean[][] marked;
    private int catPosition;
    private int FREEDOM;
    public CatGame(int n)
    {
        this.n = n;
        board = new EdgeWeightedGraph(n*n+1);
        marked = new boolean[n][n];
        catPosition = (n/2)*(n/2);
        FREEDOM = n*n + 1;

        //Do the inside edges first
        for (int row = 1; row<n-1; row++)
        {
            for (int col = 1; col<n-1; col++)
            {   
                int v = getVertex(row, col); //Current vertex

                //Add all 6 edges
                board.addEdge(new CatEdge(v, getVertex(row-1, col+1)));
                board.addEdge(new CatEdge(v, getVertex(row-1, col-1)));

                board.addEdge(new CatEdge(v, getVertex(row, col+1)));
                board.addEdge(new CatEdge(v, getVertex(row, col-1)));

                board.addEdge(new CatEdge(v, getVertex(row+1, col+1)));
                board.addEdge(new CatEdge(v, getVertex(row+1, col-1)));
            }
        }

        //Do outside edge
        for (int col = 0; col<n-1; col++)
        {
            board.addEdge(new CatEdge(getVertex(0, col), getVertex(0, col+1)));
            board.addEdge(new CatEdge(getVertex(0, col), FREEDOM));

            board.addEdge(new CatEdge(getVertex(n-1, col), getVertex(n-1, col+1)));
            board.addEdge(new CatEdge(getVertex(n-1, col), FREEDOM));
        }

        board.addEdge(new CatEdge(getVertex(0, n-1), FREEDOM));
        board.addEdge(new CatEdge(getVertex(n-1, n-1), FREEDOM));

        for (int row = 0; row<n-1; row++)
        {
            board.addEdge(new CatEdge(getVertex(row, 0), getVertex(row+1, 0)));
            board.addEdge(new CatEdge(getVertex(row, 0), FREEDOM));

            board.addEdge(new CatEdge(getVertex(row, n-1), getVertex(row+1, n-1)));
            board.addEdge(new CatEdge(getVertex(row, n-1), FREEDOM));
        }

        board.addEdge(new CatEdge(getVertex(0, 0), FREEDOM));
        board.addEdge(new CatEdge(getVertex(n-1, 0), FREEDOM));
    }

    private int getVertex(int row, int col) {return row*n + col;}

    public void markTile(int row, int col)
    {
        DjikstraUndirectedSP shortestPath = new DjikstraUndirectedSP(board, catPosition);
        CatEdge e = (CatEdge) shortestPath.pathTo(FREEDOM).iterator().next();
        catPosition = e.other(catPosition);

        for (Edge ce : adj[getVertex(row, col)])
        {
            ce = (CatEdge) ce;
            ce.markEdge();
        }
        marked[row][col] = true;
    }

    public boolean marked(int row, int col)
    {
        return marked[row][col];
    }

    public int[] getCatTile() //Return next cat move
    {
        return new int[]{catPosition/n, catPosition % n};
    }

    public boolean catHasEscaped()
    {
        return catPosition == FREEDOM;
    }

    public boolean catIsTrapped()
    {
        DjikstraUndirectedSP shortestPath = new DjikstraUndirectedSP(board, catPosition);
        return shortestPath.distTo[FREEDOM] == Double.POSITIVE_INFINITY;
    }
}
