package GenerateurPdf;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import config.Config;
import config.Question;

// infos concernant strok, fill, ... : https://stackoverflow.com/a/27959484

// TODO: voir ouverture puis fermeture de stream dans chaque méthode
// ou ouverture de stream à l'instanciation puis fermeture dans save

public class SubjectGenerator {

	private Config config;
	private PDDocument pdDocument;

	public SubjectGenerator(Config c, PDDocument doc) {
		this.config = c;
		this.pdDocument = doc;
	}

	public SubjectGenerator(Config c, int nbPages, PDRectangle format) {
		this.config = c;
		this.pdDocument = new PDDocument();
		for (int i = 0; i < nbPages; i++) {
			this.pdDocument.addPage(new PDPage(format));
		}
	}

	public SubjectGenerator() {
		this(null, null);
	}

	public Config getConfig() {
		return this.config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public PDDocument getPdDocument() {
		return this.pdDocument;
	}

	public void setPdDocument(PDDocument pdDocument) {
		this.pdDocument = pdDocument;
	}

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

	public void generateFooter() {
		int nbTotal = this.pdDocument.getNumberOfPages();
		int count = 1;

		try {
			for (PDPage page : this.pdDocument.getPages()) {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
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
	}

	public void generateMarks() {
		// TODO: divide generateMarks to avoid header's generation for all pages
		// the header part should be added to generateHeader
		for (PDPage page : this.pdDocument.getPages()) {
			try {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
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
	}

	/*
	 * public static PDDocument generateIDPageArea(PDDocument pdDocument) { }
	 */

	public void generateNameArea() {
		try {
			PDPage page = this.pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
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
	}

	public void generateNumEtudAreaBis() {
		try {
			PDPage page = this.pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
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
	}

	public void generateNumEtudArea() {
		try {
			PDPage page = this.pdDocument.getPage(0);
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
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
	}

	public void generateHeader() {
		try {
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument,
					this.pdDocument.getPage(0));
			PDFont font = PDType1Font.HELVETICA_BOLD;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void generateBody() {
		try {
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument,
					this.pdDocument.getPage(0), PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.HELVETICA_BOLD;

			// Boucle sur les questions
			int qIndex = 1;
			int heightOffset = 265;
			int pageIndex = 0;
			System.out.println(this.config.getQuestions());
			for (Question q : this.config.getQuestions()) {
				q.setTitre(q.getTitre().replace("\n", "")); // /\ TODO: must be done in Config /\
				int height = (int) this.pdDocument.getPage(0).getMediaBox().getHeight();
				PDPage page = this.pdDocument.getPage(pageIndex);
				this.generateQ(q, qIndex, this.pdDocument, page, 25, heightOffset);
				qIndex++;
				heightOffset += 100;

				if (heightOffset > (height - 10)) {
					heightOffset = 265;
					pageIndex++;
				}
			}

			pdPageContentStream.close();
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void generateQ(Question q, int qIndex, PDDocument pdDocument, PDPage curPage, int widthOffset,
			int heightOffset) {
		try {
			// Il faut rendre plus générale la génération des Q (en fonction du nombre de
			// réponses, recycler le heightOffset)

			int width = (int) pdDocument.getPage(0).getMediaBox().getWidth();
			int height = (int) pdDocument.getPage(0).getMediaBox().getHeight();

			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, curPage,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;
			int fontSize = 10;
			pdPageContentStream.setFont(font, fontSize);
			float titleLength = (font.getStringWidth(q.getTitre()) / 1000) * fontSize;

			System.out.println(qIndex + ". " + q.getTitre() + " - Width: " + titleLength + "/" + width + " - " + height
					+ " - NbRep: " + q.getReponses().size() + "\n");

			// Titre
			// Si titre plus long que largeur page -> mise sur plusieurs lignes
			if (titleLength > (width - 20)) {
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(widthOffset, height - heightOffset);
				pdPageContentStream.showText("Q." + qIndex + " -");
				pdPageContentStream.endText();

				for (String line : setTextOnMultLines(q.getTitre(), widthOffset, pdDocument, font, fontSize)) {
					pdPageContentStream.beginText();
					pdPageContentStream.newLineAtOffset(widthOffset + 22, height - heightOffset);
					pdPageContentStream.showText(line);
					pdPageContentStream.endText();
					System.out.println("\n" + line + "\n");
					heightOffset += 20;
				}
			} else {
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(widthOffset, height - heightOffset);
				pdPageContentStream.showText("Q." + qIndex + " - " + q.getTitre());
				pdPageContentStream.endText();
			}

			// Reponses (QCM)
			/// Rectangle
			// pdPageContentStream.addRect(widthOffset + 20, height - heightOffset - 15,
			// 100, 100);

			/// Texte
			for (int i = 0; i < q.getReponses().size(); i++) {
				pdPageContentStream.beginText();
				pdPageContentStream.newLineAtOffset(widthOffset + 40, height - heightOffset - 15);
				pdPageContentStream.showText(q.getReponses().get(i).getIntitule());
				pdPageContentStream.endText();
				heightOffset += 17.5;
			}

			pdPageContentStream.close();

			/*
			 * pdPageContentStream.beginText(); pdPageContentStream.newLineAtOffset(80 + (22
			 * * i) + 12, height - 105); pdPageContentStream.showText(String.valueOf(i));
			 * pdPageContentStream.endText(); // draw second range of rectangles
			 * pdPageContentStream.addRect(80 + (22 * i), height - 124, 11, 11); // write
			 * second range of numbers pdPageContentStream.beginText();
			 * pdPageContentStream.newLineAtOffset(80 + (22 * i) + 12, height - 122);
			 * pdPageContentStream.showText(String.valueOf(i));
			 * pdPageContentStream.endText();
			 */
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static ArrayList<String> setTextOnMultLines(String text, int widthOffset, PDDocument pdDocument, PDFont font,
			int fontSize) {
		// TODO: voir si l'on laisse en static ou non
		int width = (int) pdDocument.getPage(0).getMediaBox().getWidth();
		float subStrLength = 0;

		ArrayList<String> lines = new ArrayList<String>();
		int widthMargin = 60;
		int lastSpace = -2;
		int spaceIndex = 0;
		while (text.length() > 0) {
			// System.out.println("1. Si: " + spaceIndex + " - Ls: " + lastSpace + " TxtL: "
			// + text.length());
			spaceIndex = text.indexOf(' ', lastSpace + 2);
			// System.out.println("2. Si: " + spaceIndex + " - Ls: " + lastSpace + " : " +
			// text + "\n");
			String subStr = "";
			// System.out.println("1. SubL: " + subStrLength + " - W: " + (width -
			// widthOffset));

			if (spaceIndex == -1) {
				lines.add(text);
				text = "";
			} else {
				subStr = text.substring(0, spaceIndex);
			}

			try {
				subStrLength = (font.getStringWidth(subStr) / 1000) * fontSize;
			} catch (IOException ioe) {
				System.err.print(ioe.getMessage());
			}

			// System.out.println("2. SubL: " + subStrLength + " - W: " + (width -
			// widthOffset));

			if (subStrLength > (width - widthOffset - widthMargin)) {
				if (lastSpace > 0) {
					lastSpace = spaceIndex;
				}

				// System.out.println("[IF] SubL: " + subStrLength + " - W: " + (width -
				// widthOffset));
				subStr = text.substring(0, lastSpace);
				lines.add(subStr);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == lastSpace) {
				lines.add(text);
				text = "";
				// System.out.println("LTxt: " + text.length());
			} else {
				lastSpace = spaceIndex;
			}

		}

		return lines;
	}

	public void save(String dest) {
		try {
			this.pdDocument.save(dest);
			this.pdDocument.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		// config initialization
		// Config c = new Config("E:\\sourceB.txt"); // SourceB ne contient aucun '\n'
		Config c = new Config("E:\\source.txt");
		c.readConfig();
		// Create a Document object.
		/**
		 * PDDocument pdDocument = new PDDocument(); // Create a Page object PDPage
		 * pdPage1 = new PDPage(PDRectangle.A4); // System.out.println("Height : " +
		 * pdPage1.getMediaBox().getHeight()); // System.out.println("Width : " +
		 * pdPage1.getMediaBox().getWidth()); PDPage pdPage2 = new
		 * PDPage(PDRectangle.A4); PDPage pdPage3 = new PDPage(PDRectangle.A4); PDPage
		 * pdPage4 = new PDPage(PDRectangle.A4); PDPage pdPage5 = new
		 * PDPage(PDRectangle.A4);
		 *
		 * // Add the page to the document pdDocument.addPage(pdPage1);
		 * pdDocument.addPage(pdPage2); pdDocument.addPage(pdPage3);
		 * pdDocument.addPage(pdPage4); pdDocument.addPage(pdPage5);
		 */

		// instanciate a new SubjectGenerator
		// TODO: change PDRectangle.A4 to the format specified in the config
		SubjectGenerator subjectGenerator = new SubjectGenerator(c, 5, PDRectangle.A4);

		subjectGenerator.generateMarks();
		subjectGenerator.generateNumEtudAreaBis();
		subjectGenerator.generateNameArea();
		subjectGenerator.generateFooter();
		subjectGenerator.generateBody();

		subjectGenerator.save("E:\\testPDFMarks.pdf");

		System.out.println("PDF saved to the location !!!");
	}
}
