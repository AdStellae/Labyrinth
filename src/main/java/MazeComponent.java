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
    // Draw a maze of size w*h with c*c cells
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
        System.out.println("after create maze");
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

        System.out.println("in draw wall");


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
    private void removeRandomWall(Graphics g) {
        Random rn = new Random();
        int cell = getRandomCell();
        int xCoord = getCellXPos(cell);
        int yCoord = getCellYPos(cell);

        System.out.println(xCoord + "x");
        System.out.println(yCoord + "y");

        int wall = getRandomWall(xCoord, yCoord);

        drawWall(xCoord, yCoord, wall, g);

    }

    private int getCellXPos(int randCell) {
        int randX = randCell/cells;
        return randX;
    }
    private int getCellYPos(int randCell){
        int randY = randCell%cells;
        return randY;
    }
    private int getRandomCell(){
        Random rn = new Random();
        int randCell = rn.nextInt(cells*cells-1);
        return randCell;
    }
    private int getRandomWall(int xCoord, int yCoord){
        Random rn = new Random();
        int wall = rn.nextInt(4);

        while ((xCoord == 0 && wall == 0) || (xCoord == cells-1 && wall == 2) || (yCoord == 0 && wall == 1) || (yCoord == cells-1 && wall == 3)){
            wall = rn.nextInt(4);
        }

        return wall;
    }

}
