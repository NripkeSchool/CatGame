import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import java.util.Random;

public class CatGame
{
    private EdgeWeightedGraph board;
    private int n; //Size of row/col of the graph
    private boolean[][] marked; //Stores which tiles have been marked or blocked
    private int catPosition;
    private int FREEDOM; //Final value of the "Freedom" or escape square for the cat

    /**
     * Constructor for the CatGame class
     * Initializes the EdgeWeightedGraph, and sets all the valid edges between each tile
     * Adds a set amount of initial blocks
     * @param int for the size of the row/col of the board
     */
    public CatGame(int n)
    {
        this.n = n;
        board = new EdgeWeightedGraph(n*n+1);
        marked = new boolean[n][n];
        catPosition = n*n/2; //Position set to the middle
        FREEDOM = n*n;

        //Add Edges
        for (int row = 0; row<n; row++)
        {
           for (int col = 0; col<n; col++)
           {
             if (row != n-1) 
             {
               addEdge(row, col, row+1, col); //Connect straight down
               if (row % 2 == 0 && col != 0) {addEdge(row, col, row+1, col-1);} //Connect down to the left
               if (row % 2 == 1 && col != n-1) {addEdge(row, col, row+1, col+1);} //Connect down to the right
             }
             if (col != n-1) {addEdge(row, col, row, col+1);} //Connect to the right
             
             //Freedom squares
             if (row == 0 || col == 0 || row == n-1 || col == n-1) {board.addEdge(new CatEdge(getVertex(row, col), FREEDOM));}
           }
        }
        
        int addedBlocks = 5; //Number of added marked tiles at start of the game
        Random ran = new Random();
        for (int i = 0; i<addedBlocks; i++)
        {
           int choice = ran.nextInt(n*n+1); 
           while (marked[choice/n][choice%n] || choice == catPosition) //Run until valid position
           {
              choice = ran.nextInt(n*n+1); 
           }
           mark(choice/n, choice%n); 
        }
    }

    /**
     * Private helper method used to return the vertex
     * equivalent of a given row and column of the board
     * @param int,int first integer is the row, second integer is the column
     * @return integer value of the vertex
     */
    private int getVertex(int row, int col) {return row*n + col;}

    /**
     * Private helper method, creates an edge between two tiles
     * @param int,int,int,int takes two pairs of rows and columns similar to the parameters of getVertex()
     */
    private void addEdge(int row1, col1, row2, col2) {board.addEdge(new CatEdge(getVertex(row1, col1), getVertex(row2, col2)));}

    /**
     * Private helper method, marks a tile by setting its edge weight to infinity
     * @param int,int row and column
     */
    private void mark(int row, int col)
    {
      for (Edge ce : board.adj(getVertex(row, col)))
        {
            CatEdge ce2 = (CatEdge) ce;
            ce2.markEdge();
        }
        marked[row][col] = true;
    }
    
    /**
     * Marks a given tile, and changes the cat's position using
     * Djikstra's Shortest Path Algorithm to the Freedom Square
     * @param int,int row and column
     */
    public void markTile(int row, int col)
    {
        mark(row, col);
        if (catIsTrapped()) {return;}
        
        DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(board, catPosition);
        CatEdge e = (CatEdge) shortestPath.pathTo(FREEDOM).iterator().next();
        catPosition = e.other(catPosition);
    }

    /**
     * Returns if a given tile is marked, or blocked
     * @param int,int row and column
     * @return boolean marked or not
     */
    public boolean marked(int row, int col)
    {
        return marked[row][col];
    }

    /**
     * Returns an int[] array of the current
     * cat position of form {row, col}
     * @return int[2] of form {row, col}
     */
    public int[] getCatTile()
    {
        return new int[]{catPosition/n, catPosition % n};
    }

    /**
     * Returns whether the cat has escaped
     * In other words, if the cat has reached the Freedom Square
     * @return boolean escaped or not escaped
     */
    public boolean catHasEscaped()
    {
        return catPosition == FREEDOM;
    }

    /**
     * Returns whether the cat is trapped
     * In other words, if the cat has no paths to the Freedom Square
     * @return boolean trapped or not trapped
     */
    public boolean catIsTrapped()
    {
        DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(board, catPosition);
        return !shortestPath.hasPathTo(FREEDOM);
    }
}
