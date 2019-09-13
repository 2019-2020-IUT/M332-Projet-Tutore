package util;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

// infor concernant strok, fill, ... : https://stackoverflow.com/a/27959484

public class SubjectGenerator {

	private static void drawCircle(PDPageContentStream contentStream, int cx, int cy, int r) throws IOException {
		// https://stackoverflow.com/a/42836210
		final float k = 0.552284749831f;
		contentStream.moveTo(cx - r, cy);
		contentStream.curveTo(cx - r, cy + (k * r), cx - (k * r), cy + r, cx, cy + r);
		contentStream.curveTo(cx + (k * r), cy + r, cx + r, cy + (k * r), cx + r, cy);
		contentStream.curveTo(cx + r, cy - (k * r), cx + (k * r), cy - r, cx, cy - r);
		contentStream.curveTo(cx - (k * r), cy - r, cx - r, cy - (k * r), cx - r, cy);
		contentStream.fill();
	}

	public static PDDocument generateMarks(PDDocument pdDocument) {
		for (PDPage page : pdDocument.getPages()) {
			try {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page);
				PDFont font = PDType1Font.TIMES_ROMAN;

				int height = (int) page.getMediaBox().getHeight();
				int width = (int) page.getMediaBox().getWidth();

				// Set a Color for the marks
				pdPageContentStream.setNonStrokingColor(Color.BLACK);
				pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
				pdPageContentStream.setFont(font, 9);

				// generate circle marks in corners
				SubjectGenerator.drawCircle(pdPageContentStream, 20, height - 30, 5); // top left
				SubjectGenerator.drawCircle(pdPageContentStream, width - 20, height - 30, 5); // top right
				SubjectGenerator.drawCircle(pdPageContentStream, 20, 30, 5); // bottom left
				SubjectGenerator.drawCircle(pdPageContentStream, width - 20, 30, 5); // bottom right

				pdPageContentStream.setLineWidth(0.6f); // largeur du contour des rectangles

				// generate rectangles id (?)
				for (int i = 0; i < 12; i++) {
					// draw first range of rectangles
					pdPageContentStream.addRect(170 + (9 * i), height - 15, 9, 9);
					// + 9 * i = position du ieme carre
					// draw second range of rectangles
					pdPageContentStream.addRect(170 + (9 * i), height - 27, 9, 9);
					// + 9 * i = position du ieme carre
				}

				// generate rectangles numero etudiant
				for (int i = 0; i <= 9; i++) {
					// draw first range of rectangles
					pdPageContentStream.addRect(80 + (22 * i), height - 107, 11, 11);
					// write first range of numbers
					pdPageContentStream.beginText();
					pdPageContentStream.newLineAtOffset(80 + (22 * i) + 12, height - 105);
					pdPageContentStream.showText(String.valueOf(i));
					pdPageContentStream.endText();
					// draw second range of rectangles
					pdPageContentStream.addRect(80 + (22 * i), height - 124, 11, 11);
					// write second range of numbers
					pdPageContentStream.beginText();
					pdPageContentStream.newLineAtOffset(80 + (22 * i) + 12, height - 122);
					pdPageContentStream.showText(String.valueOf(i));
					pdPageContentStream.endText();
				}

				pdPageContentStream.setFont(font, 10);
				// generate rectangle nom
				pdPageContentStream.addRect(width - 238, height - 196, 155, 50); // RECT
				// text
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(width - 236, height - 156);
				pdPageContentStream.showText("Ecrivez votre Nom");
				pdPageContentStream.endText();
				// dot lines
				pdPageContentStream.moveTo(width - 233, height - 175);
				for (int i = (width - 233); i <= (width - 90); i += 3) {
					pdPageContentStream.lineTo(i, height - 175);
					pdPageContentStream.moveTo(i + 2, height - 175);
				}

				pdPageContentStream.stroke(); // stroke

				// pdPageContentStream.fill();
				pdPageContentStream.closeAndStroke();

				// draw the separative line
				pdPageContentStream.moveTo(28, height - 239);
				pdPageContentStream.lineTo(width - 28, height - 239);

				// pdPageContentStream.lineTo(width - 28, height - 239);
				pdPageContentStream.stroke(); // stroke
				pdPageContentStream.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return pdDocument;
	}

	public void generateHeader(PDDocument pdDocument) {
		try {
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdDocument.getPage(0));
			PDFont font = PDType1Font.HELVETICA_BOLD;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		// Create a Document object.
		PDDocument pdDocument = new PDDocument();
		// Create a Page object
		PDPage pdPage = new PDPage(PDRectangle.A4);

		// Add the page to the document and save the document to a desired file.
		pdDocument.addPage(pdPage);

		pdDocument = SubjectGenerator.generateMarks(pdDocument);

		// pdDocument.save("C:\\Users\\Nico\\Desktop\\testPDFMarks.pdf");
		pdDocument.save("E:\\testPDFMarks.pdf");
		pdDocument.close();
		System.out.println("PDF saved to the location !!!");
	}
}
