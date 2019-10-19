package RecadragePdf;

import java.awt.Color;
import java.awt.image.BufferedImage;

// https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
// https://stackoverflow.com/a/7750416
// https://stackoverflow.com/a/14514504
// https://stackoverflow.com/questions/9589297/findout-if-the-page-is-bw-or-color-in-pdf-using-java
// https://pdfbox.apache.org/2.0/migration.html
// http://www.kscodes.com/java/draw-rectangle-using-apache-pdfbox/
// https://www.javatpoint.com/pdfbox-adding-rectangles
// https://stackoverflow.com/a/33440883

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfAnalyzer {

	public BufferedImage blackWhiteConvert(BufferedImage image) {
		// TODO : voir recursivite
		int width = image.getWidth();
		int height = image.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (image.getRGB(x, y) < 128) {
					image.setRGB(x, y, 0);
				} else {
					image.setRGB(x, y, 255);
				}
			}
		}
		return image;
	}

	public boolean isBlackWhite(BufferedImage image) {
		// TODO : voir recursivite
		int width = image.getWidth();
		int height = image.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((image.getRGB(x, y) != 0) || (image.getRGB(x, y) != 255)) {
					return false;
				}
			}
		}
		return true;
	}

	public ArrayList<BufferedImage> convertPagesToBWJPG(PDDocument document) {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		try {
			int pageCounter = 0;
			for (PDPage page : document.getPages()) {
				System.out.println("page.getRotation() : " + page.getRotation());
				System.out.println("pageCounter : " + pageCounter);
				BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter++, 300, ImageType.BINARY); // BINARY =
																											// noir et
																											// blanc
				images.add(bim);
				System.out.println("Ajout n°" + pageCounter);
			}
			// document.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return images;
	}

	public void saveOnDisk(ArrayList<BufferedImage> images, String originalFileName) {
		int pageCounter = 0;
		try {
			for (BufferedImage img : images) {
				ImageIO.write(img, "JPEG", new File(originalFileName + pageCounter++ + ".jpg"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// public void drawRectangle(int x, int y)

	public static void main(String args[]) throws IOException {

		/*
		 * Pattern p = Pattern.compile("\\d{1,16}"); Matcher m = p.matcher("123456789");
		 * boolean b = m.matches();
		 */

		PdfAnalyzer pdfAnalyzer = new PdfAnalyzer();
		File pdfFile;
		PDDocument document = null;
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		BufferedImage img = null;
		// File f = null;

		// CONVERT PAGES TO IMAGES
		try {
			String pdfFilename = "C:\\Users\\Nico\\Desktop\\M3302 - Sujet S3T 2019.pdf";
			pdfFile = new File(pdfFilename);
			document = PDDocument.load(pdfFile);
			images = pdfAnalyzer.convertPagesToBWJPG(document);
			pdfAnalyzer.saveOnDisk(images, "C:\\Users\\Nico\\Desktop\\M3302 - Sujet S3T 2019.pdf");
		} catch (IOException e) {
			System.out.println(e);
		} /*
			 * if (!pdfAnalyzer.isBlackWhite(img)) { BufferedImage bw_image =
			 * pdfAnalyzer.blackWhiteConvert(img); }
			 */
		// DRAW RECTANGLE ON X,Y
		// Create a Document object.
		PDDocument pdDocument = new PDDocument();

		// Create a Page object
		PDPage pdPage = new PDPage(PDRectangle.A4);
		int height = (int) pdPage.getMediaBox().getHeight();
		int width = (int) pdPage.getMediaBox().getWidth();
		System.out.println(height + " " + width);
		// Add the page to the document and save the document to a desired file.
		pdDocument.addPage(pdPage);
		try {
			// Create a Content Stream
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);

			// Set a Color for the Rectangle
			pdPageContentStream.setNonStrokingColor(Color.BLACK);
			// Give the X, Y coordinates and height and width

			PDFont font = PDType1Font.HELVETICA_BOLD;

			pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
			pdPageContentStream.setFont(font, 9);

			String title = "Evaluation Pattern";

			pdPageContentStream.beginText();
			pdPageContentStream.newLineAtOffset((width / 2) - title.length(), height - 10);
			pdPageContentStream.showText(title);
			pdPageContentStream.endText();

			int count = 0;
			for (int y = height - 50; y > (height / 2); y -= 25) {
				// write some text
				String enonce = "Question " + count + " : ...";
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(10, y);
				pdPageContentStream.showText(enonce);
				pdPageContentStream.endText();
				// draw an empty rectangle
				pdPageContentStream.addRect(50, y - 15, 10, 10); // X (lower left x corner), Y (lower left y corner),
																	// Width,
																	// Height
				pdPageContentStream.closeAndStroke();
				// write some text
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(70, y - 15);
				pdPageContentStream.showText("Question non cochée " + count);
				pdPageContentStream.endText();
				// draw a filled rectangle
				pdPageContentStream.addRect(200, y - 15, 10, 10);
				pdPageContentStream.fill();
				// write some text
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(220, y - 15);
				pdPageContentStream.showText("Question cochée " + count++);
				pdPageContentStream.endText();
			}

			// pdPageContentStream.fill();

			// Once all the content is written, close the stream
			pdPageContentStream.close();

			pdDocument.save("C:\\Users\\Nico\\Desktop\\testPDFRect.pdf");
			pdDocument.close();
			System.out.println("PDF saved to the location !!!");

		} catch (IOException ioe) {
			System.out.println("Error while saving pdf" + ioe.getMessage());
		}
	}
}
