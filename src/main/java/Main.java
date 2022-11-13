import javax.swing.*;

public class Main {
    private static final int WIDTH = 800;  // Size of the window in pixels
    private static final int HEIGHT = 800;

    static int cells=10;    // The size of the maze is cells*cells (default is 20*20)

    public static void main(String[] args) {

        // Get the size of the maze from the command line
        if (args.length > 0) {
            try {
                cells = Integer.parseInt(args[0]);  // The maze is of size cells*cells
            } catch (NumberFormatException e) {
                System.err.println("Argument " + args[0] + " should be an integer");
                System.exit(-1);
            }
        }
        // Check that the size is valid
        if ( (cells <= 1) || (cells > 100) ) {
            System.err.println("Invalid size, must be between 2 and 100 ");
            System.exit(-1);
        }
        Runnable r = new Runnable() {
            public void run() {
                // Create a JComponent for the maze
                MazeComponent mazeComponent = new MazeComponent(WIDTH, HEIGHT, cells);
                // Change the text of the OK button to "Close"
                UIManager.put("OptionPane.okButtonText", "Close");
                JOptionPane.showMessageDialog(null, mazeComponent, "Maze " + cells + " by " + cells,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };

        SwingUtilities.invokeLater(r);
    }
}
