import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MazeComponent extends JComponent {
    protected int width;
    protected int height;
    protected int cells;
    protected int cellWidth;
    protected int cellHeight;
    DisjointSets DS = new DisjointSets(cells*cells);
    Random random;
    /**
     * Draw a maze of size w*h with c*c cells
     * @param w
     * @param c
     * @param h
     */
    MazeComponent(int w, int h, int c) {
        super();
        cells = c;                // Number of cells
        cellWidth = w/cells;      // Width of a cell
        cellHeight = h/cells;     // Height of a cell
        width =  c*cellWidth;     // Calculate exact dimensions of the component
        height = c*cellHeight;
        setPreferredSize(new Dimension(width+1,height+1));  // Add 1 pixel for the border
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.yellow);                    // Yellow background
        g.fillRect(0, 0, width, height);

        // Draw a grid of cells
        g.setColor(Color.blue);                 // Blue lines
        for (int i = 0; i<=cells; i++) {        // Draw horizontal grid lines
            g.drawLine (0, i*cellHeight, cells*cellWidth, i*cellHeight);
        }
        for (int j = 0; j<=cells; j++) {       // Draw vertical grid lines
            g.drawLine (j*cellWidth, 0, j*cellWidth, cells*cellHeight);
        }

        // Mark entry and exit cells
        paintCell(0,0,Color.green, g);               // Mark entry cell
        drawWall(0, 0, 0, g);                       // Open up entry cell
        paintCell(cells-1, cells-1,Color.pink, g);   // Mark exit cell
        drawWall(cells-1, cells-1, 2, g);            // Open up exit cell

        g.setColor(Color.yellow);                 // Use yellow lines to remove existing walls
        createMaze(cells, g);
    }

    private void createMaze (int c, Graphics g) {
        //TODO Add code to finalize
        removeRandomWall(g);

    }


    // Paints the interior of the cell at position x,y with colour c
    private void paintCell(int x, int y, Color c, Graphics g) {
        int xpos = x*cellWidth;    // Position in pixel coordinates
        int ypos = y*cellHeight;
        g.setColor(c);
        g.fillRect(xpos+1, ypos+1, cellWidth-1, cellHeight-1);
    }


    // Draw the wall w in cell (x,y) (0=left, 1=up, 2=right, 3=down)
    private void drawWall(int x, int y, int w, Graphics g) {
        int xpos = x*cellWidth;    // Position in pixel coordinates
        int ypos = y*cellHeight;

        switch(w){
            case (0):       // Wall to the left
                g.drawLine(xpos, ypos+1, xpos, ypos+cellHeight-1);
                System.out.println("Left");
                break;
            case (1):       // Wall at top
                g.drawLine(xpos+1, ypos, xpos+cellWidth-1, ypos);
                System.out.println("Top");
                break;
            case (2):      // Wall to the right
                g.drawLine(xpos+cellWidth, ypos+1, xpos+cellWidth, ypos+cellHeight-1);
                System.out.println("Right");
                break;
            case (3):      // Wall at bottom
                g.drawLine(xpos+1, ypos+cellHeight, xpos+cellWidth-1, ypos+cellHeight);
                System.out.println("Bottom");
                break;
        }
    }

    /**
     * Removes a random wall inside the maze.
     * @param g
     */
    private void removeRandomWall(Graphics g) {
        int cell = getRandomCell();
        int xPos = getCellXPos(cell);
        int yPos = getCellYPos(cell);
        int cellNumber = getCellNumber(xPos, yPos);
        int wall = getRandomWall(xPos, yPos);
        getNextCell(wall, cellNumber);
        drawWall(xPos, yPos, wall, g);
    }

    /**
     * Returns a cell within the boundaries of the maze.
     * TODO Find out how the fuck this even works at the moment.
     * @return
     */
    private int getRandomCell(){
        Random rn = new Random();
        int randCell = rn.nextInt(cells*cells);
        System.out.println("randCell " + randCell);
        return randCell;
    }

    /**
     * Returns the X-coordinate of the cell
     * @param randCell
     * @return
     */
    private int getCellXPos(int randCell) {
        int randX = randCell/cells;
        System.out.println(randX + "x");
        return randX;
    }

    /**
     * Returns the Y-coordinate of the cell
     * @param randCell
     * @return
     */
    private int getCellYPos(int randCell){
        int randY = randCell%cells;
        System.out.println(randY + "y");
        return randY;
    }

    /**
     * Determines the actual cell number by multiplying the y-coordinate with the max amount of cells in a row and
     * adding the x-coordinate.
     * @param xPos
     * @param yPos
     * @return
     */
    private int getCellNumber(int xPos, int yPos) {
        int cellNumber = yPos*cells+xPos;
        System.out.println("Selected cell number: " + cellNumber);
        return  cellNumber;
    }

    /**
     * Returns a wall that is not part of the border of the maze.
     * @param xCoord
     * @param yCoord
     * @return
     */
    private int getRandomWall(int xCoord, int yCoord){
        Random rn = new Random();
        int wall = rn.nextInt(4);
        while ((xCoord == 0 && wall == 0) || (xCoord == cells-1 && wall == 2)
                || (yCoord == 0 && wall == 1) || (yCoord == cells-1 && wall == 3)){
            wall = rn.nextInt(4);
        }
        System.out.println("Selected wall " + wall);
        return wall;
    }

    /**
     * Determines the cell on the other side of the wall that is selected by getRandomWall
     * @param wall
     * @param cell
     * @return
     */
    private int getNextCell(int wall, int cell) {
        int nextCell = 0;
        if (wall == 0) {
            nextCell = cell - 1; //The cell to the left.
        } else if (wall == 2) {
            nextCell = cell + 1; //The cell to the right.
        } else if (wall == 1) {
            nextCell = cell - cells; //The cell above.
        } else if (wall == 3) {
            nextCell = cell + cells; //The cell below.
        }
        System.out.println("Next cell " + nextCell);
        return nextCell;

    }
}