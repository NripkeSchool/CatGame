import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import java.util.Random;

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
        catPosition = n*n/2;
        FREEDOM = n*n;

        //Add Edges
        for (int row = 0; row<n; row++)
        {
           for (int col = 0; col<n; col++)
           {
             if (row != n-1) 
             {
               board.addEdge(new CatEdge(getVertex(row, col), getVertex(row+1, col)));
               if (row % 2 == 0 && col != 0) {board.addEdge(new CatEdge(getVertex(row, col), getVertex(row+1, col-1)));}
               if (row % 2 == 1 && col != n-1) {board.addEdge(new CatEdge(getVertex(row, col), getVertex(row+1, col+1)));}
             }
             if (col != n-1) {board.addEdge(new CatEdge(getVertex(row, col), getVertex(row, col+1)));}
             
             //Freedom squares
             if (row == 0 || col == 0 || row == n-1 || col == n-1) {board.addEdge(new CatEdge(getVertex(row, col), FREEDOM));}
           }
        }
        
        int addedBlocks = 5;
        Random ran = new Random();
        for (int i = 0; i<addedBlocks; i++)
        {
           int choice = ran.nextInt(n*n+1); 
           while (marked[choice/n][choice%n] || choice == catPosition)
           {
              choice = ran.nextInt(n*n+1); 
           }
           mark(choice/n, choice%n); 
        }
    }

    private int getVertex(int row, int col) {return row*n + col;}
    private void mark(int row, int col)
    {
      for (Edge ce : board.adj(getVertex(row, col)))
        {
            CatEdge ce2 = (CatEdge) ce;
            ce2.markEdge();
        }
        marked[row][col] = true;
    }
    
    public void markTile(int row, int col)
    {
        mark(row, col);
        if (catIsTrapped()) {return;}
        
        DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(board, catPosition);
        CatEdge e = (CatEdge) shortestPath.pathTo(FREEDOM).iterator().next();
        catPosition = e.other(catPosition);
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
        DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(board, catPosition);
        return !shortestPath.hasPathTo(FREEDOM);
    }
}
