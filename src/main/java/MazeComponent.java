import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MazeComponent extends JComponent {
    protected int width;
    protected int height;
    protected int cells;
    protected int cellWidth;
    protected int cellHeight;
    DisjointSets DS;
    /**
     * Draws a grid of size w*h with c*c cells
     * @param w - the width.
     * @param c - the number of cells.
     * @param h - the height.
     */
    MazeComponent(int w, int h, int c) {
        super();
        cells = c;                // Number of cells
        cellWidth = w/cells;      // Width of a cell
        cellHeight = h/cells;     // Height of a cell
        width =  c*cellWidth;     // Calculate exact dimensions of the component
        height = c*cellHeight;
        setPreferredSize(new Dimension(width+1,height+1));  // Add 1 pixel for the border
        DS = new DisjointSets(cells*cells);
    }

    /**
     * Draws the start and exit point, then draws the maze.
     * @param g the <code>Graphics</code> object to protect
     */
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

    /**
     * Removes walls between cells until all cells are in one union.
     * @param g
     */
    private void createMaze (int cells, Graphics g) {
        while (DS.getS()[DS.find(0)] != -(cells*cells)) {
            int cell = getRandomCell();
            int xPos = getCellXPos(cell);
            int yPos = getCellYPos(cell);
            int wall = getRandomWall(xPos, yPos);
            int nextCell = getNextCell(wall, cell);
            if (deterMineIfInUnion(cell, nextCell) == false) {
                DS.union(DS.find(cell), DS.find(nextCell));
                drawWall(xPos, yPos, wall, g);
            }
        }
    }

    /**
     * Paints the interior of the cell at position x,y with colour c
     * @param x - the x-coordinate.
     * @param y - the y-coordinate.
     * @param c - the colour.
     * @param g
     */
    private void paintCell(int x, int y, Color c, Graphics g) {
        int xpos = x*cellWidth;    // Position in pixel coordinates
        int ypos = y*cellHeight;
        g.setColor(c);
        g.fillRect(xpos+1, ypos+1, cellWidth-1, cellHeight-1);
    }

    /**
     * Draw the wall w in cell (x,y) (0=left, 1=up, 2=right, 3=down)
     * @param x - the x-coordinate.
     * @param y - the y-coordinate.
     * @param w - the wall.
     * @param g
     */
    private void drawWall(int x, int y, int w, Graphics g) {
        int xpos = x*cellWidth;    // Position in pixel coordinates
        int ypos = y*cellHeight;

        switch(w){
            case (0):       // Wall to the left
                g.drawLine(xpos, ypos+1, xpos, ypos+cellHeight-1);
                break;
            case (1):       // Wall at top
                g.drawLine(xpos+1, ypos, xpos+cellWidth-1, ypos);
                break;
            case (2):      // Wall to the right
                g.drawLine(xpos+cellWidth, ypos+1, xpos+cellWidth, ypos+cellHeight-1);
                break;
            case (3):      // Wall at bottom
                g.drawLine(xpos+1, ypos+cellHeight, xpos+cellWidth-1, ypos+cellHeight);
                break;
        }
    }

    /**
     * Returns a selected cell within the boundaries of the maze.
     * @return Returns the cell.
     */
    private int getRandomCell(){
        Random rn = new Random();
        int bound = cells*cells;
        int randCell = rn.nextInt(bound);
        System.out.println("Selected cell: " + randCell);
        return randCell;
    }

    /**
     * Returns the X-coordinate of the cell.
     * @param randCell - the selected cell.
     * @return Returns the x-coordinate.
     */
    private int getCellXPos(int randCell) {
        int randX = randCell%cells;
        System.out.println(randX + "x");
        return randX;
    }

    /**
     * Returns the Y-coordinate of the cell.
     * @param randCell - the selected cell.
     * @return Returns the y-coordinate.
     */
    private int getCellYPos(int randCell){
        int randY = randCell/cells;
        System.out.println(randY + "y");
        return randY;
    }

    /**
     * Returns a wall that is not part of the border of the maze.
     * @param xPos - the x-coordinate of the cell.
     * @param yPos - the y-coordinate of the cell.
     * @return Returns the selected wall.
     */
    private int getRandomWall(int xPos, int yPos){
        Random rn = new Random();
        int wall = rn.nextInt(4);
        while ((xPos == 0 && wall == 0) || (xPos == cells-1 && wall == 2)
                || (yPos == 0 && wall == 1) || (yPos == cells-1 && wall == 3)){
            wall = rn.nextInt(4);
        }
        System.out.println("Selected wall: " + wall);
        return wall;
    }

    /**
     * Determines the cell on the other side of the wall that is selected by getRandomWall.
     * @param wall - the selected wall.
     * @param cell - the selected cell.
     * @return Returns the adjacent cell.
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
        System.out.println("Next cell: " + nextCell);
        return nextCell;
    }

    /**
     * Determines if the cell and the nextCell are in union.
     * @param cell - the number of the cell.
     * @param nextCell - the number of the selected adjacent cell.
     * @return Returns true if they are in union, and false if they are not.
     */
    private boolean deterMineIfInUnion(int cell, int nextCell) {
        if (DS.find(cell) == DS.find(nextCell)) {
            return true;
        }
        else {
            return false;
        }
    }
}