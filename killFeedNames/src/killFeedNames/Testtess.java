package killFeedNames;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Testtess {

	public static void main(String[] args) {
		boolean doOCR = true;
		boolean doImageWork = false;
		BufferedImage img = null;
		BufferedImage img0 = null;
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		BufferedImage img3 = null;
		BufferedImage img4 = null;
		BufferedImage img5 = null;
		File f = null;
		String[] pathnames;
		File f2 = new File("C:\\Users\\Bneib\\Desktop\\images\\");
		File f3 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\");
		// File imageFile = new File("C:\\Users\\Bneib\\Desktop\\ERROR.png");
		pathnames = f2.list();
		if (doImageWork) {
			for (int i = 0; pathnames.length > i;) {
				int whitePixels = 0;
				int blackPixelLine = 0;
				// boolean viable = true;
				File colorImageFile = new File("C:\\Users\\Bneib\\Desktop\\images\\" + pathnames[i]);
				try {
					img = ImageIO.read(colorImageFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = copy.createGraphics();
				g2d.setColor(Color.WHITE); // Or what ever fill color you want...
				g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
				g2d.drawImage(img, 0, 0, null);
				g2d.dispose();

				img = img.getSubimage(2182, 119, 50, 23);

				int width = img.getWidth();
				int height = img.getHeight();

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int p = img.getRGB(x, y);

						int a = (p >> 24) & 0xff;
						int r = (p >> 16) & 0xff;
						int g = (p >> 8) & 0xff;
						int b = p & 0xff;
						if (r < 190 || g < 190 || b < 190) {
							img.setRGB(x, y, Color.BLACK.getRGB());

						}

						else {
							p = (a << 24) | (r << 16) | (g << 8) | b;
							img.setRGB(x, y, p);
						}

					}
				}
				try {
					f = new File("C:\\Users\\Bneib\\Desktop\\ocred\\ocredFile" + i + ".png");
					ImageIO.write(img, "png", f);
				} catch (IOException e) {
					System.out.println(e);
				}
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int p = img.getRGB(x, y);

						int a = (p >> 24) & 0xff;
						int r = (p >> 16) & 0xff;
						int g = (p >> 8) & 0xff;
						int b = p & 0xff;

						// calculate average
						// int avg = (r + g + b) / 3;
						// replace RGB value with avg
						// p = (a << 24) | (avg << 16) | (avg << 8) | avg;
						// else if (r + g + b < 280) {
						// img.setRGB(x, y, Color.WHITE.getRGB());

						// }

						if (r > 226 && r < 256 && g > 205 && g < 256 && b > 195 && b < 256) {
							img.setRGB(x, y, Color.BLACK.getRGB());

						}

						if (r > 190 || g > 190 || b > 190) {
							img.setRGB(x, y, Color.WHITE.getRGB());
							whitePixels++;

						} else {
							img.setRGB(x, y, Color.BLACK.getRGB());

						}

					}
				}
				/*
				 * for (int y = 1; y < height - 1; y++) { for (int x = 1; x < width - 1; x++) {
				 * if (img.getRGB(x - 1, y) != Color.WHITE.getRGB() && img.getRGB(x + 1, y) !=
				 * Color.WHITE.getRGB() && img.getRGB(x, y - 1) != Color.WHITE.getRGB() &&
				 * img.getRGB(x, y + 1) != Color.WHITE.getRGB()) { img.setRGB(x, y,
				 * Color.WHITE.getRGB()); }
				 * 
				 * } }
				 * 
				 * for (int y = 1; y < height - 1; y++) { for (int x = 1; x < width - 1; x++) {
				 * int sides = 0; if (img.getRGB(x - 1, y) == Color.WHITE.getRGB()) { sides++; }
				 * 
				 * if (img.getRGB(x + 1, y) == Color.WHITE.getRGB()) { sides++; } if
				 * (img.getRGB(x, y - 1) == Color.WHITE.getRGB()) { sides++; } if (img.getRGB(x,
				 * y + 1) == Color.WHITE.getRGB()) { sides++; }
				 * 
				 * if (sides >= 3) { img.setRGB(x, y, Color.BLACK.getRGB()); } } }
				 */
				try {
					f = new File("C:\\Users\\Bneib\\Desktop\\grayscaled\\grayScaledFile" + i + ".png");
					ImageIO.write(img, "png", f);
				} catch (IOException e) {
					System.out.println(e);
				}

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int p = img.getRGB(x, y);

						if (p == Color.WHITE.getRGB()) {
							whitePixels++;
						}
					}

				}
				width = img.getWidth();
				height = img.getHeight();
				int foundLine = 0;
				int lastLine = 0;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {

						// int p = img.getRGB(x, y);
						if (img.getRGB(x, y) != Color.WHITE.getRGB()) {
							blackPixelLine++;

						}

					}
					if (blackPixelLine >= height - 2) {
						foundLine++;

						blackPixelLine = 0;
					}
					if (foundLine == 2) {
						if (lastLine == x - 1) {

							System.out.println("cropping from" + x);
							img = img.getSubimage(x, 0, 50 - (x + 3), 23);
							break;
						} else if (lastLine != x - 1) {
							foundLine = 0;
						}
					}
					lastLine = x;
					blackPixelLine = 0;
				}
				width = img.getWidth();
				height = img.getHeight();
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {

						if (img.getRGB(x, y) == Color.WHITE.getRGB()) {
							img.setRGB(x, y, Color.BLACK.getRGB());
						}

						else if (img.getRGB(x, y) == Color.BLACK.getRGB()) {
							img.setRGB(x, y, Color.WHITE.getRGB());
						}

					}
				}

				if (whitePixels > 30) {
					try {
						f = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\killCount" + i + ".png");
						ImageIO.write(img, "png", f);
					} catch (IOException e) {
						System.out.println(e);
					}
					whitePixels = 0;
				}

				i++;
			}
		}
		if (doOCR)

		{

			// String sameAsLast = "1";
			pathnames = f3.list();
			for (int i = 2; pathnames.length > i + 3; i++) {
				int averageBlackPixelCount = 3;
				int futureAverageCount = 3;
				int blackPixelCount0 = 1;
				int blackPixelCount1 = 1;
				int blackPixelCount2 = 1;
				int blackPixelCount3 = 1;
				int blackPixelCount4 = 1;
				int blackPixelCount5 = 1;
				File colorImageFile0 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i]);
				File colorImageFile1 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i - 1]);
				File colorImageFile2 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i - 2]);
				File colorImageFile3 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i + 1]);
				File colorImageFile4 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i + 2]);
				File colorImageFile5 = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i + 3]);

				try {
					img0 = ImageIO.read(colorImageFile0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					img1 = ImageIO.read(colorImageFile1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					img2 = ImageIO.read(colorImageFile2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					img3 = ImageIO.read(colorImageFile3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					img4 = ImageIO.read(colorImageFile4);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					img5 = ImageIO.read(colorImageFile5);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int width0 = img0.getWidth();
				int height0 = img0.getHeight();
				for (int y = 0; y < height0; y++) {
					for (int x = 0; x < width0; x++) {
						int p = img0.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount0++;
						}

					}

				}

				int width1 = img1.getWidth();
				int height1 = img1.getHeight();
				for (int y = 0; y < height1; y++) {
					for (int x = 0; x < width1; x++) {
						int p = img1.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount1++;
						}

					}

				}
				int width2 = img2.getWidth();
				int height2 = img2.getHeight();
				for (int y = 0; y < height2; y++) {
					for (int x = 0; x < width2; x++) {
						int p = img2.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount2++;
						}

					}

				}
				int width3 = img3.getWidth();
				int height3 = img3.getHeight();
				for (int y = 0; y < height3; y++) {
					for (int x = 0; x < width3; x++) {
						int p = img3.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount3++;
						}

					}

				}
				int width4 = img4.getWidth();
				int height4 = img4.getHeight();
				for (int y = 0; y < height4; y++) {
					for (int x = 0; x < width4; x++) {
						int p = img4.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount4++;
						}

					}

				}
				int width5 = img5.getWidth();
				int height5 = img5.getHeight();
				for (int y = 0; y < height5; y++) {
					for (int x = 0; x < width5; x++) {
						int p = img5.getRGB(x, y);

						if (p == Color.BLACK.getRGB()) {
							blackPixelCount5++;
						}

					}

				}

				averageBlackPixelCount = (blackPixelCount0 + blackPixelCount1 + blackPixelCount2) / 3;

				futureAverageCount = (blackPixelCount3 + blackPixelCount4 + blackPixelCount5) / 3;

				if (Math.abs((100 * (averageBlackPixelCount - futureAverageCount) / futureAverageCount)) > 0) {
					File colorImageFile = new File("C:\\Users\\Bneib\\Desktop\\viableKillCount\\" + pathnames[i]);
					try {
						img = ImageIO.read(colorImageFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = copy.createGraphics();
					g2d.setColor(Color.WHITE); // Or what ever fill color you want...
					g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
					g2d.drawImage(img, 0, 0, null);
					g2d.dispose();
					ITesseract instance = new Tesseract(); // JNA Interface Mapping
					// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
					instance.setDatapath("C:\\Tess4J\\tessdata"); // path to tessdata directory
					// instance.setOcrEngineMode(0);
					instance.setPageSegMode(7);
					instance.setTessVariable("tessedit_char_whitelist", "0123456789");

					try {
						String result = instance.doOCR(img);
						System.out.println(result);
					} catch (TesseractException e) {
						System.err.println(e.getMessage());
					}
				}

			}

		}
	}
}
