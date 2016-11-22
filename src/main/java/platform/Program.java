package platform;

import java.awt.Color;
import java.io.File;
import java.util.logging.Logger;

import platform.game.Simulator;
import platform.game.KeyBindings;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.Display;
import platform.util.FileLoader;
import platform.util.sounds.SoundLoader;
import platform.util.sounds.TinySoundLoader;
import platform.util.SwingDisplay;

/**
 * Provides main entry point.
 */
public class Program {

    public static void main(String[] args) throws Exception {
        
        // Create components
        SoundLoader sl;
        try {
            Class.forName("kuusisto.tinysound.TinySound");
            sl = new TinySoundLoader("resources/");
        } catch (ClassNotFoundException ex) {
            Logger.getGlobal().severe("No sound library found, falling back to silent.");
            sl = SoundLoader.DUMMY_LOADER;
        }

        BufferedLoader loader = new BufferedLoader(new FileLoader("resources/", DefaultLoader.INSTANCE), sl);
        KeyBindings bindings = new KeyBindings(new File("keyboard.properties"));
        bindings.load();

        Display display = new SwingDisplay();
        display.setBackground(Color.WHITE);
        try {
            
            // Game loop
            Simulator simulator = new Simulator(loader, loader, args);
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