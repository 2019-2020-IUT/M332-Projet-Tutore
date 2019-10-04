package GenerateurPdf;

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

	private static void drawDotedLine(PDPageContentStream contentStream, int xi, int yi, int xf, int yf)
			throws IOException {
		contentStream.moveTo(xi, yi);
		for (int i = xi; i <= xf; i += 3) {
			contentStream.lineTo(i, yf);
			contentStream.moveTo(i + 2, yf);

		}
		// contentStream.fill();
	}

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

	public static PDDocument generateFooter(PDDocument pdDocument) {
		int nbTotal = pdDocument.getNumberOfPages();
		int count = 1;

		try {
			for (PDPage page : pdDocument.getPages()) {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page,
						PDPageContentStream.AppendMode.APPEND, true);
				PDFont font = PDType1Font.TIMES_ROMAN;
				int width = (int) page.getMediaBox().getWidth();
				int fontSize = 10;
				pdPageContentStream.setFont(font, fontSize);
				String footer = String.valueOf(count) + " / " + String.valueOf(nbTotal);
				float titleWidth = (font.getStringWidth(footer) / 1000) * fontSize;
				float titleHeight = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset((width - titleWidth) / 2, (35 - titleHeight));
				pdPageContentStream.showText(footer);
				pdPageContentStream.endText();
				pdPageContentStream.close();
				count++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return pdDocument;

	}

	public static PDDocument generateMarks(PDDocument pdDocument) {
		for (PDPage page : pdDocument.getPages()) {
			try {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page,
						PDPageContentStream.AppendMode.APPEND, true);
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

				pdPageContentStream.setFont(font, 10);

				// draw the separative line
				pdPageContentStream.moveTo(28, height - 239);
				pdPageContentStream.lineTo(width - 28, height - 239);

				// pdPageContentStream.lineTo(width - 28, height - 239);
				pdPageContentStream.stroke(); // stroke

				pdPageContentStream.setFont(font, 11);

				// number (top of page) +n/n/nn+
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset((width / 2) + 86, height - 30);
				pdPageContentStream.showText("+1/1/60+");
				pdPageContentStream.endText();

				// Sujet
				// center text : https://stackoverflow.com/a/6531362
				String subject = "Exemple PT S3T : de 1970 à l’an 2000, 30 ans d’histoire";
				int fontSize = 12;
				pdPageContentStream.setFont(font, fontSize);
				float titleWidth = (font.getStringWidth(subject) / 1000) * fontSize;
				float titleHeight = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset((width - titleWidth) / 2, (height - 57 - titleHeight));
				pdPageContentStream.showText(subject);
				pdPageContentStream.endText();

				String subtitle = "Tricherie : Toutes consultations de sources numériques sont interdites !!";
				titleWidth = (font.getStringWidth(subtitle) / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset((width - titleWidth) / 2, (height - 71 - titleHeight));
				pdPageContentStream.showText(subtitle);
				pdPageContentStream.endText();

				fontSize = 10;

				// 1st line
				pdPageContentStream.setFont(font, fontSize);
				String numConsignePart1 = "          Veuillez coder votre numéro";

				titleWidth = (font.getStringWidth(numConsignePart1) / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(width - 224, height - 94 - titleHeight);
				pdPageContentStream.showText(numConsignePart1);
				pdPageContentStream.endText();

				// 2nd line
				String numConsignePart2 = " d’étudiant ci-contre et écrire votre nom";

				titleWidth = (font.getStringWidth(numConsignePart2) / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(width - 244, height - 105 - titleHeight);
				pdPageContentStream.showText(numConsignePart2);
				pdPageContentStream.endText();

				// 3rd line
				String numConsignePart3 = " dans la case ci-dessous.";

				titleWidth = (font.getStringWidth(numConsignePart3) / 1000) * fontSize;
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(width - 244, height - 118 - titleHeight);
				pdPageContentStream.showText(numConsignePart3);
				pdPageContentStream.endText();

				pdPageContentStream.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return pdDocument;
	}

	/*
	 * public static PDDocument generateIDPageArea(PDDocument pdDocument) {
	 *
	 * }
	 */

	public static PDDocument generateNameArea(PDDocument pdDocument) {
		try {
			PDPage page = pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;

			// Set a Color for the marks
			pdPageContentStream.setNonStrokingColor(Color.BLACK);
			pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
			pdPageContentStream.setFont(font, 9);

			int height = (int) page.getMediaBox().getHeight();
			int width = (int) page.getMediaBox().getWidth();

			// generate rectangle nom
			
			pdPageContentStream.addRect(width - 238, height - 196, 155, 50); // RECT
			// text
			pdPageContentStream.beginText();
			pdPageContentStream.newLineAtOffset(width - 236, height - 156);
			pdPageContentStream.showText("Ecrivez votre Nom");
			pdPageContentStream.endText();

			// dot lines
			// TODO: Enleve car difficulte d'ocr du fait de la presence des pointilles
			// SubjectGenerator.drawDotedLine(pdPageContentStream, width - 233, height -
			// 175, width - 90, height - 175);
			// SubjectGenerator.drawDotedLine(pdPageContentStream, width - 233, height -
			// 190, width - 90, height - 190);

			pdPageContentStream.stroke(); // stroke

			// pdPageContentStream.fill();
			pdPageContentStream.closeAndStroke();

			pdPageContentStream.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return pdDocument;
	}

	public static PDDocument generateNumEtudAreaBis(PDDocument pdDocument) {
		try {
			PDPage page = pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;

			// Set a Color for the marks
			pdPageContentStream.setNonStrokingColor(Color.BLACK);
			pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
			pdPageContentStream.setFont(font, 9);

			int height = (int) page.getMediaBox().getHeight();
			int width = (int) page.getMediaBox().getWidth();

			// num rectangle
			pdPageContentStream.addRect(width / 4, height - 146, 150, 40);
			pdPageContentStream.beginText();
			pdPageContentStream.newLineAtOffset((width / 4) + 2, height - 115);
			pdPageContentStream.showText("Ecrivez votre Numéro d'étudiant");
			pdPageContentStream.endText();

			// note rectangle
			pdPageContentStream.addRect(width / 4, height - 200, 150, 40);
			pdPageContentStream.beginText();
			pdPageContentStream.newLineAtOffset((width / 4) + 2, height - 170);
			pdPageContentStream.showText("Note");
			pdPageContentStream.endText();

			pdPageContentStream.moveTo((width / 4) + 75, height - 160);
			pdPageContentStream.lineTo((width / 4) + 75, height - 200);

			// dotedlines

			// SubjectGenerator.drawDotedLine(pdPageContentStream, width - 233, height -
			// 175, width - 90, height - 175);

			pdPageContentStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return pdDocument;
	}

	public static PDDocument generateNumEtudArea(PDDocument pdDocument) {
		try {
			PDPage page = pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, page,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;

			// Set a Color for the marks
			pdPageContentStream.setNonStrokingColor(Color.BLACK);
			pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
			pdPageContentStream.setFont(font, 9);

			int height = (int) page.getMediaBox().getHeight();

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

			pdPageContentStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
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
		PDPage pdPage1 = new PDPage(PDRectangle.A4);
		// System.out.println("Height : " + pdPage1.getMediaBox().getHeight());
		// System.out.println("Width : " + pdPage1.getMediaBox().getWidth());
		PDPage pdPage2 = new PDPage(PDRectangle.A4);
		PDPage pdPage3 = new PDPage(PDRectangle.A4);

		// Add the page to the document and save the document to a desired file.
		pdDocument.addPage(pdPage1);
		pdDocument.addPage(pdPage2);
		pdDocument.addPage(pdPage3);

		pdDocument = SubjectGenerator.generateMarks(pdDocument);
		pdDocument = SubjectGenerator.generateNumEtudAreaBis(pdDocument);
		pdDocument = SubjectGenerator.generateNameArea(pdDocument);
		pdDocument = SubjectGenerator.generateFooter(pdDocument);

		// pdDocument.save("C:\\Users\\Nico\\Desktop\\testPDFMarks.pdf");
		pdDocument.save("E:\\testPDFMarks.pdf");
		pdDocument.close();
		System.out.println("PDF saved to the location !!!");
	}
}
