package platform;

import java.awt.Color;
import java.io.File;
import platform.game.KeyBindings;
import platform.game.Simulator;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.Display;
import platform.util.FileLoader;
import platform.util.SwingDisplay;
import platform.util.sounds.JavaSoundLoader;
import platform.util.sounds.SoundLoader;

/**
 * Provides main entry point.
 */
public class Program {

	private static double fps;

	public static void main(String[] args) throws Exception {

		// Create components
		SoundLoader sl = new JavaSoundLoader("resources/");

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
					fps = (1 / avg);
					System.out.println(avg + " // " + (1 / avg) + " frames per sec");
				}
			}

			// Close window
		} finally {
			display.close();
		}
	}

	/**
	 * Get the number of frames per second
	 *
	 * @return
	 */
	public static double getFps() {
		return fps;
	}
}