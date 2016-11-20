package platform;

import java.awt.Color;
import platform.game.Simulator;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.Display;
import platform.util.FileLoader;
import platform.util.Loader;
import platform.util.SwingDisplay;

/**
 * Provides main entry point.
 */
public class Program {

    public static void main(String[] args) throws Exception {
        
        // Create components
        Loader loader = new BufferedLoader(new FileLoader("res/", DefaultLoader.INSTANCE));
        Display display = new SwingDisplay();
        display.setBackground(Color.WHITE);
        try {
            
            // Game loop
            Simulator simulator = new Simulator(loader, args);
            double avg = 0.02;
            double last = display.getTime();
            while (!display.isCloseRequested()) {

                // Do frame
                display.begin();
                simulator.update(display, display);
                display.end();

                // Update framerate
                avg = avg * 0.95 + display.getDeltaTime() * 0.05;
                if (display.getTime() - last > 1) {
                    last = display.getTime();
                    System.out.println(avg);
                }
            }
            
        // Close window
        } finally {
            display.close();
        }
    }

}