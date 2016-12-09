package platform;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import platform.game.KeyBindings;
import platform.game.Simulator;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.Display;
import platform.util.FileLoader;
import platform.util.SwingDisplay;

/**
 * Provides main entry point.
 */
public class Program {

	private static double fps;

	public static void main(String[] args) throws Exception {
		extractResouces();

		BufferedLoader loader = new BufferedLoader(new FileLoader("res/", DefaultLoader.INSTANCE));
		KeyBindings bindings = new KeyBindings(new File("keyboard.properties"));
		bindings.load();

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
					fps = (1 / avg);
					System.out.println(avg + " // " + (1 / avg) + " frames per sec");
				}
			}

			// Close window
		} finally {
			display.close();
		}
	}

	private static void extractResouces() throws IOException, URISyntaxException {
		// Create components
		Path resourcePath = Paths.get("res");
		if (!Files.exists(resourcePath)) {
			Files.createDirectory(resourcePath);
		}

		if (!Files.isDirectory(resourcePath)) {
			System.out.println("Resources path " + resourcePath + " is not a directory !");
			return;
		}

		URI uri = Program.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		Path path = Paths.get(uri.getPath());

		if (uri.getPath().endsWith(".jar") && Files.exists(path)) {
			FileSystem system = FileSystems.newFileSystem(path, ClassLoader.getSystemClassLoader());
			Path p = system.getPath("/res/");
			if (Files.exists(p) && Files.isDirectory(p)) {
				Files.list(p).forEach((f) -> {
					Path target = Paths.get("res", f.getFileName().toString());
					if (!Files.exists(target)) {
						System.out.println("- Extracting " + f.getFileName());
						try {
							Files.copy(f, target);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	/**
	 * Get the number of frames per second
	 *
	 * @return the number of frames per second (FPS)
	 */
	public static double getFps() {
		return fps;
	}
}