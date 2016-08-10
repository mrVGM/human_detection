package imageLib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Test {
	public static void main(String [] args) throws IOException, InterruptedException {
		File f = new File("/home/v/Project/cam/frame.png");
		BufferedImage img = ImageIO.read(f);
		f = new File("/home/v/Project/cam/frame (copy).png");
		BufferedImage img2 = ImageIO.read(f);
		
		Window.display(new Image(img));
		
		System.out.println("der");
		Thread.sleep(5000);
		
		Window.display(new Image(img2));
		
		System.out.println("der3");
	}
}
