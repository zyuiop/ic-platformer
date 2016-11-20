package platform.util;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Swing and AWT implementation of Output and Input interfaces.
 * @see Output
 * @see Input
 */
@SuppressWarnings("serial")
public class SwingDisplay implements Display, KeyListener, MouseListener, MouseWheelListener {

    // Encapsulates a collection of buttons
    private static class ButtonManager {
        
        private Map<Integer, Button> current;
        private Map<Integer, Boolean> buffer;
        
        private ButtonManager() {
            current = new HashMap<>();
            buffer = new HashMap<>();
        }
        
        public Button get(int key) {
            Button state = current.get(key);
            if (state == null)
                return Button.UP;
            return state;
        }
        
        public void set(int key, boolean value) {
            buffer.put(key, value);
        }
        
        public void update() {
            for (Map.Entry<Integer, Boolean> entry : buffer.entrySet())
                current.put(entry.getKey(), get(entry.getKey()).updated(entry.getValue()));
        }
    }
    
    // Rendering-related objects
    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy strategy;
    private Graphics2D graphics;
    private Box box;
    
    // Input-related objects
    private double deltaTime, time;
    private long before;
    private Vector mouseLocation;
    private ButtonManager keyboardButtons;
    private ButtonManager mouseButtons;
    private int mouseScroll;
    private int mouseScrollBuffer;
    private Button focus;
    private volatile boolean closeRequested;
    
    /** Creates a new display */
    public SwingDisplay() {
        
        // Create canvas
        canvas = new Canvas();
        canvas.setFocusable(true);
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.setIgnoreRepaint(true);
        canvas.setBackground(Color.BLACK);
        
        // Create input buffers
        deltaTime = 0.0;
        time = 0.0;
        before = System.nanoTime();
        keyboardButtons = new ButtonManager();
        mouseButtons = new ButtonManager();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseWheelListener(this);
        
        // Create frame
        frame = new JFrame();
        frame.setFocusable(false);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setIgnoreRepaint(true);
        frame.setBackground(Color.BLACK);
        frame.add(canvas);
        frame.pack();
        frame.setSize(1024, 768);
        frame.setVisible(true);
        
        // Add window manager
        focus = Button.UP;
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        closeRequested = false;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                closeRequested = true;
            }
        });

    }

    @Override
    public void setBackground(Color color) {
        canvas.setBackground(color);
    }
    
    @Override
    public void begin() {

        // Setup double buffering if needed
        if (strategy == null) {
            canvas.createBufferStrategy(2);
            strategy = canvas.getBufferStrategy();
        }
        
        // Recreate graphic context
        graphics = (Graphics2D)strategy.getDrawGraphics();
        
        // Get current size
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        box = new Box(Vector.ZERO, new Vector(width, height));

        // Clear background
        graphics.setColor(canvas.getBackground());
        graphics.fillRect(0, 0, width, height);

        // Enable anti-aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Set transform to have origin at lower left corner
        graphics.setTransform(new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, height));
        
        // Update mouse location
        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, canvas);
        mouseLocation = new Vector(point.getX(), height - point.getY());
        
        // Update input buffers
        synchronized (this) {
            keyboardButtons.update();
            mouseButtons.update();
            mouseScroll = mouseScrollBuffer;
            mouseScrollBuffer = 0;
        }
        
        // Update time
        long now = System.nanoTime();
        deltaTime = (now - before) * 1e-9;
        if (deltaTime > 0.1) {
            System.out.println("Delta time too large (" + deltaTime + "), clamped to 0.1.");
            deltaTime = 0.1;
        }
        time += deltaTime;
        before = now;
        
        // Update focus
        focus = focus.updated(canvas.hasFocus());
    }
    
    @Override
    public void end() {

        // Dispose context
        graphics.dispose();
        graphics = null;
        box = null;
        
        // Flip buffer
        strategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void close() {
        frame.dispose();
    }
    
    @Override
    public boolean isCloseRequested() {
        return closeRequested;
    }
    
    @Override
    public Box getBox() {
        return box;
    }
    
    @Override
    public void drawSprite(Sprite sprite, Box location) {
        drawSprite(sprite, location, 0.0);
    }

    @Override
    public void drawSprite(Sprite sprite, Box location, double angle) {
        drawSprite(sprite, location, angle, 1.0);
    }

    @Override
    public void drawSprite(Sprite sprite, Box location, double angle, double transparency) {
        if (transparency <= 0.0)
            return;
        
        // Center sprite at origin
        AffineTransform center = AffineTransform.getTranslateInstance(sprite.getWidth() / -2.0, sprite.getHeight() / -2.0);
        
        // Rescale sprite according to box (images have inverted Y-axis)
        AffineTransform scale = AffineTransform.getScaleInstance(location.getWidth() / sprite.getWidth(), -location.getHeight() / sprite.getHeight());
        
        // Rotate sprite
        AffineTransform rotate = AffineTransform.getRotateInstance(angle);
        
        // Move to desired location
        AffineTransform move = AffineTransform.getTranslateInstance(location.getCenter().getX(), location.getCenter().getY());
        
        // Combine everything
        AffineTransform transform = move;
        transform.concatenate(rotate);
        transform.concatenate(scale);
        transform.concatenate(center);
        
        // Draw image with alpha modifier
        if (transparency < 1.0) {
            Composite original = graphics.getComposite();
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)transparency);
            graphics.setComposite(composite);
            graphics.drawImage(sprite.getImage(), transform, null);
            graphics.setComposite(original);
        } else
            graphics.drawImage(sprite.getImage(), transform, null);
    }
    
    @Override
    public double getTime() {
        return time;
    }

    @Override
    public double getDeltaTime() {
        return deltaTime;
    }

    @Override
    public Vector getMouseLocation() {
        return mouseLocation;
    }

    @Override
    public Button getMouseButton(int index) {
        return mouseButtons.get(index);
    }

    @Override
    public int getMouseScroll() {
        return mouseScroll;
    }
    
    @Override
    public Button getKeyboardButton(int code) {
        return keyboardButtons.get(code);
    }

    @Override
    public Button getFocus() {
        return focus;
    }
    
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        keyboardButtons.set(e.getKeyCode(), true);
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        keyboardButtons.set(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public synchronized void mousePressed(MouseEvent e) {
        mouseButtons.set(e.getButton(), true);
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        mouseButtons.set(e.getButton(), false);
    }

    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {
        mouseScrollBuffer -= e.getWheelRotation();
    }
    
}
