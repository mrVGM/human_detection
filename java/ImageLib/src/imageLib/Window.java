package imageLib;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JRootPane;


public class Window {
	private static JFrame frame = null;
	private static JLabel currentImage = null;
	
	public static void display(Image image) {
		if (frame == null) {
			frame = new JFrame();
			//frame.setUndecorated(true);
			//frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			currentImage = new JLabel(new ImageIcon(image.img));
			frame.add(currentImage);
			frame.pack();
			frame.setVisible(true);
		}
		else {
			frame.remove(currentImage);
			currentImage = new JLabel(new ImageIcon(image.img));
			frame.add(currentImage);
			frame.revalidate();
		}
	}
	/*public static Canvas getCanvas() {
		if (canvas != null) {
			return canvas;
		}
		JFrame frame = new JFrame();
		frame = new JFrame();
		canvas = new Canvas();
		canvas.setSize(640, 480);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		
		return canvas;
	}*/
}
