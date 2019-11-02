package GenerateurPdf;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import config.Config;
import config.Question;
import config.QuestionBoite;
import config.Reponse;

// infos concernant strok, fill, ... : https://stackoverflow.com/a/27959484

// TODO: voir ouverture puis fermeture de stream dans chaque méthode
// ou ouverture de stream à l'instanciation puis fermeture dans save

// TODO: voir si toutes les méthodes doivent throws IOException plutot que try catch

// TODO: faire un choix entre double et float

public class SubjectGenerator {

	private Config config;
	private PDDocument pdDocument;
	private PDRectangle format;
	private int height;
	private int width;
	private static int widthMargin = 20;

	public SubjectGenerator(Config c) {
		this.config = c;
		this.pdDocument = new PDDocument();
		try {
			this.format = SubjectGenerator.getFormatFromString(c.getParam().get("PaperSize"));
		} catch (IncorrectFormatException ife) {
			System.out.println("Format non reconnu. Format défini par défaut sur A4");
			this.format = PDRectangle.A4;
		}
		this.pdDocument.addPage(new PDPage(this.format));
		this.height = (int) this.format.getHeight();
		this.width = (int) this.format.getWidth();
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

	public PDRectangle getFormat() {
		return this.format;
	}

	public void setFormat(PDRectangle format) {
		this.format = format;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public static PDRectangle getFormatFromString(String format) throws IncorrectFormatException {
		switch (format) {
		case "A0":
			return PDRectangle.A0;
		case "A1":
			return PDRectangle.A1;
		case "A2":
			return PDRectangle.A2;
		case "A3":
			return PDRectangle.A3;
		case "A4":
			return PDRectangle.A4;
		case "A5":
			return PDRectangle.A5;
		case "A6":
			return PDRectangle.A6;
		default:
			throw new IncorrectFormatException("Le format spécifié n'est pas supporté.");
		}
	}

	public static void drawDotedLine(PDPageContentStream pdPageContentStream, int xi, int yi, int xf, int yf)
			throws IOException {
		pdPageContentStream.moveTo(xi, yi);
		for (int i = xi; i <= xf; i += 3) {
			pdPageContentStream.lineTo(i, yf);
			pdPageContentStream.moveTo(i + 2, yf);
		}
	}

	public static void drawCircle(PDPageContentStream pdPageContentStream, int cx, int cy, int r) throws IOException {
		// https://stackoverflow.com/a/42836210
		final float k = 0.552284749831f;
		pdPageContentStream.moveTo(cx - r, cy);
		pdPageContentStream.curveTo(cx - r, cy + (k * r), cx - (k * r), cy + r, cx, cy + r);
		pdPageContentStream.curveTo(cx + (k * r), cy + r, cx + r, cy + (k * r), cx + r, cy);
		pdPageContentStream.curveTo(cx + r, cy - (k * r), cx + (k * r), cy - r, cx, cy - r);
		pdPageContentStream.curveTo(cx - (k * r), cy - r, cx - r, cy - (k * r), cx - r, cy);
		pdPageContentStream.fill();
	}

	public static void writeText(PDPageContentStream pdPageContentStream, String text, float x, float y)
			throws IOException {
		pdPageContentStream.beginText();
		pdPageContentStream.newLineAtOffset(x, y);
		pdPageContentStream.showText(text);
		pdPageContentStream.endText();
	}

	public static ArrayList<String> setTextOnMultLines(String text, int widthOffset, PDDocument pdDocument, PDFont font,
			int fontSize) {
		int width = (int) pdDocument.getPage(0).getMediaBox().getWidth();

		ArrayList<String> lines = new ArrayList<>();

		if (text.isBlank()) {
			// si le texte est une chaine vide ou ne contenant que des espaces
			return lines;
		}

		int widthMargin = 60;
		int textSize = 0;

		try {
			textSize = (int) SubjectGenerator.getStrLenWithFont(text, font, fontSize); // taille totale du texte
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		int availableWidth = width - widthOffset - widthMargin; // largeur disponible (marge enlevee)
		int index = 0;

		while (textSize > availableWidth) {
			// tant que la taille du texte restant depasse en largeur
			try {
				while (SubjectGenerator.getStrLenWithFont(text.substring(0, index), font, fontSize) < availableWidth) {
					// tant que la taille de la sous chaine de caractere ne depasse pas
					index++;
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			if (text.substring(0, index).endsWith(" ") || text.substring(0, index).endsWith("-")
					|| text.substring(index - 1, text.length() - 1).startsWith(" ")
					|| text.substring(index - 1, text.length() - 1).startsWith("-")) {

				lines.add(text.substring(0, index));
				text = text.substring(index);
			} else if (text.substring(0, index - 1).endsWith(" ") || text.substring(0, index - 1).endsWith("-")) {
				lines.add(text.substring(0, index - 1));
				text = text.substring(index - 1);
			} else {
				// sinon on coupe le mot
				lines.add(text.substring(0, index - 1) + "-");
				text = text.substring(index - 1);

			}
			try {
				textSize = (int) SubjectGenerator.getStrLenWithFont(text, font, fontSize);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		lines.add(text);
		return lines;
	}

	public static float getStrLenWithFont(String text, PDFont font, int fontSize) throws IOException {
		return (font.getStringWidth(text) / 1000) * fontSize;
	}

	public static float getFontHeight(PDFont font, int fontSize) {
		return ((font.getFontDescriptor().getCapHeight()) / 1000) * fontSize;
	}

	public static boolean questionWillFit(Question q, double heightOffset, double maxHeightOffset, double widthOffset,
			PDDocument pdDocument, PDFont font, int fontSize) throws IOException {
		System.out.println("\n=== Will It Fit ? ===");
		// TODO: voir si on laisse en static ou non
		// ATTENTION : limite = si la question prend plus d'une page a elle seule
		for (String line : SubjectGenerator.setTextOnMultLines(q.getTitre(), (int) widthOffset, pdDocument, font,
				fontSize)) {
			// pour chaque ligne de la question
			heightOffset += SubjectGenerator.getFontHeight(font, fontSize); // SubjectGenerator.getStrLenWithFont(line,
																			// font, fontSize) + 5;
			// on met a jour l'espace vertical pris
		}
		for (Reponse reponse : q.getReponses()) {
			// pour chaque reponse a la question
			for (String intitule : SubjectGenerator.setTextOnMultLines(reponse.getIntitule(), (int) widthOffset,
					pdDocument, font, fontSize)) {
				// pour chaque ligne de la reponse
				heightOffset += SubjectGenerator.getFontHeight(font, fontSize);// SubjectGenerator.getStrLenWithFont(intitule,
																				// font, fontSize) + 17.5;
				// on met a jour l'espace vertical pris
			}
		}
		if (q instanceof QuestionBoite) {
			heightOffset += ((QuestionBoite) q).getNbligne() * 20;
		}
		System.out.println("heightOffset : " + heightOffset);
		System.out.println("maxHeightOffset : " + maxHeightOffset);
		System.out.println("fit : " + (heightOffset < maxHeightOffset));
		return (heightOffset < maxHeightOffset);
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
				float titleWidth = SubjectGenerator.getStrLenWithFont(footer, font, fontSize);
				float titleHeight = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000) * fontSize;
				SubjectGenerator.writeText(pdPageContentStream, footer, (width - titleWidth) / 2, (35 - titleHeight));
				System.out.println("						35 - titleHeight : " + (35 - titleHeight));
				System.out.println("						max" + (this.height - 35 - titleHeight));
				pdPageContentStream.close();
				count++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void generateMarks() {
		// TODO: renommer les variables en fonction de :
		// signification id page
		int max = 60;
		int digA = 1;
		int digB = 1;
		int digC = max;
		for (PDPage page : this.pdDocument.getPages()) {
			try {
				PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
						PDPageContentStream.AppendMode.APPEND, true);
				PDFont font = PDType1Font.TIMES_ROMAN;

				// Set a Color for the marks
				pdPageContentStream.setNonStrokingColor(Color.BLACK);
				pdPageContentStream.setNonStrokingColor(0, 0, 0); // black text
				pdPageContentStream.setFont(font, 9);

				// this.generateRectID(page);
				// generate page's ID
				this.generateRectID(pdPageContentStream);

				// generate circle marks in corners
				SubjectGenerator.drawCircle(pdPageContentStream, 20, this.height - 30, 5); // top left
				SubjectGenerator.drawCircle(pdPageContentStream, this.width - 20, this.height - 30, 5); // top right
				SubjectGenerator.drawCircle(pdPageContentStream, 20, 30, 5); // bottom left
				SubjectGenerator.drawCircle(pdPageContentStream, this.width - 20, 30, 5); // bottom right

				// number (top of page) +n/n/nn+
				SubjectGenerator.writeText(pdPageContentStream, "+" + digA + "/" + digB + "/" + digC + "+",
						(this.width / 2) + 86, this.height - 30);

				pdPageContentStream.close();

				if (digB == max) {
					digB = 1;
					digC = max;
					digA++;
				} else {
					digB++;
					digC--;
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void generateRectID(PDPageContentStream pdPageContentStream) {
		try {
			pdPageContentStream.setLineWidth(0.6f); // largeur du contour des rectangles

			// generate rectangles id (?)
			for (int i = 0; i < 12; i++) {
				// draw first range of rectangles
				pdPageContentStream.addRect(170 + (9 * i), this.height - 15, 9, 9);
				// + 9 * i = position du ieme carre
				// draw second range of rectangles
				pdPageContentStream.addRect(170 + (9 * i), this.height - 27, 9, 9);
				// + 9 * i = position du ieme carre
			}
			pdPageContentStream.stroke(); // stroke
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

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

			// generate rectangle nom

			pdPageContentStream.addRect(this.width - 238, this.height - 196, 155, 50); // RECT
			// text
			SubjectGenerator.writeText(pdPageContentStream, "Ecrivez votre Nom", this.width - 236, this.height - 156);

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

			// num rectangle
			pdPageContentStream.addRect(this.width / 4, this.height - 146, 150, 40);
			String numEtudCons = Config.clearString(this.config.getParam().get("MarkField"));
			SubjectGenerator.writeText(pdPageContentStream, numEtudCons, (this.width / 4) + 2, this.height - 115);

			// note rectangle
			pdPageContentStream.addRect(this.width / 4, this.height - 200, 150, 40);
			SubjectGenerator.writeText(pdPageContentStream, "Note", (this.width / 4) + 2, this.height - 170);

			pdPageContentStream.moveTo((this.width / 4) + 75, this.height - 160);
			pdPageContentStream.lineTo((this.width / 4) + 75, this.height - 200);

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

			// generate rectangles numero etudiant
			for (int i = 0; i <= 9; i++) {
				// draw first range of rectangles
				pdPageContentStream.addRect(80 + (22 * i), this.height - 107, 11, 11);
				// write first range of numbers
				SubjectGenerator.writeText(pdPageContentStream, String.valueOf(i), 80 + (22 * i) + 12,
						this.height - 105);
				// draw second range of rectangles
				pdPageContentStream.addRect(80 + (22 * i), this.height - 124, 11, 11);
				// write second range of numbers
				SubjectGenerator.writeText(pdPageContentStream, String.valueOf(i), 80 + (22 * i) + 12,
						this.height - 122);
			}

			pdPageContentStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void generateHeader() {
		try {

			PDPage page = this.pdDocument.getPage(0);

			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument, page,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;

			pdPageContentStream.setFont(font, 10);

			// draw the separative line
			pdPageContentStream.moveTo(28, this.height - 239);
			pdPageContentStream.lineTo(this.width - 28, this.height - 239);

			// pdPageContentStream.lineTo(width - 28, height - 239);
			pdPageContentStream.stroke(); // stroke

			// Sujet
			// center text : https://stackoverflow.com/a/6531362
			// String subject = "Exemple PT S3T : de 1970 à l’an 2000, 30 ans d’histoire";
			String subject = Config.clearString(this.config.getParam().get("Title"));
			int fontSize = 12;
			pdPageContentStream.setFont(font, fontSize);
			float titleWidth = SubjectGenerator.getStrLenWithFont(subject, font, fontSize);
			float titleHeight = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000) * fontSize;
			SubjectGenerator.writeText(pdPageContentStream, subject, (this.width - titleWidth) / 2,
					(this.height - 57 - titleHeight));

			// String subtitle = "Tricherie : Toutes consultations de sources numériques
			// sont interdites !!";
			String subtitle = Config.clearString(this.config.getParam().get("Presentation"));
			titleWidth = SubjectGenerator.getStrLenWithFont(subtitle, font, fontSize);
			SubjectGenerator.writeText(pdPageContentStream, subtitle, (this.width - titleWidth) / 2,
					(this.height - 71 - titleHeight));
			fontSize = 10;

			// 1st line
			pdPageContentStream.setFont(font, fontSize);
			// String numConsignePart1 = " Veuillez coder votre numéro";
			int heightOffset = this.height - 115;
			String numConsigne = Config.clearString(this.config.getParam().get("StudentField"));

			for (String consigne : SubjectGenerator.setTextOnMultLines(numConsigne, this.width - 200, this.pdDocument,
					font, fontSize)) {
				SubjectGenerator.writeText(pdPageContentStream, consigne, this.width - 238, heightOffset);
				heightOffset -= 10;
			}
			pdPageContentStream.close();

			this.generateNumEtudAreaBis();
			this.generateNameArea();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void generateBody(boolean isCorrected) {
		try {
			PDPageContentStream pdPageContentStream = new PDPageContentStream(this.pdDocument,
					this.pdDocument.getPage(0), PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.HELVETICA_BOLD;

			// Boucle sur les questions
			int fontSize = 10;
			int qIndex = 1;
			float heightOffset = 265;
			int pageIndex = 0;
			int widthOffset = 25;
			float footerSize = this.height - (35 * 2) - SubjectGenerator.getFontHeight(font, fontSize);

			// pour chaque question
			ArrayList<Question> questions = this.config.getQuestions();
			// si les questions doivent etre melangees
			if (this.config.getParam().get("ShuffleQuestions") == "1") {
				Collections.shuffle(questions);
			}
			for (Question q : questions) {
				q.setTitre(Config.clearString(q.getTitre())); // /\ TODO: must be done in Config /\
				if (!SubjectGenerator.questionWillFit(q, heightOffset, footerSize, widthOffset, this.pdDocument, font,
						10)) {
					// si la question va dépasser
					heightOffset = 55;
					this.pdDocument.addPage(new PDPage(this.format));
					pageIndex++;
				}
				PDPage page = this.pdDocument.getPage(pageIndex);
				if (q instanceof QuestionBoite) {
					heightOffset = this.generateOpenQ((QuestionBoite) q, qIndex, this.pdDocument, page, widthOffset,
							heightOffset, isCorrected);
				} else {
					heightOffset = this.generateQCM(q, qIndex, this.pdDocument, page, widthOffset, heightOffset,
							isCorrected);
				}
				qIndex++;
			}
			pdPageContentStream.close();
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public float generateQCM(Question q, int qIndex, PDDocument pdDocument, PDPage curPage, float widthOffset,
			float heightOffset, boolean isCorrected) {
		// TODO: calculer automatiquement widthOffset
		// TODO: regler probleme de debordement de page (verifiee pour chaque question
		// mais pas pour chaque reponse)
		try {
			// Il faut rendre plus générale la génération des Q (en fonction du nombre de
			// réponses, recycler le heightOffset)

			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, curPage,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;
			int fontSize = 10;
			pdPageContentStream.setFont(font, fontSize);
			float titleLength = SubjectGenerator.getStrLenWithFont(q.getTitre(), font, fontSize);

			// Titre
			if (titleLength > (this.width - 20)) {
				// Si titre plus long que largeur page -> mise sur plusieurs lignes
				// TODO: replace Q by config data
				SubjectGenerator.writeText(pdPageContentStream, "Q." + qIndex + " - ", widthOffset,
						this.height - heightOffset);

				for (String line : setTextOnMultLines(q.getTitre(), (int) widthOffset, pdDocument, font, fontSize)) {
					SubjectGenerator.writeText(pdPageContentStream, line, widthOffset + 22, this.height - heightOffset);
					heightOffset += 20;
				}
			} else {
				SubjectGenerator.writeText(pdPageContentStream, "Q." + qIndex + " - " + q.getTitre(), widthOffset,
						this.height - heightOffset);
			}

			ArrayList<Reponse> reponses = q.getReponses();
			// si les reponses doivent etre melangees
			if (this.config.getParam().get("ShuffleAnswers") == "1") {
				Collections.shuffle(reponses);
			}
			// pour chaque reponse
			for (Reponse r : reponses) {
				// on ecrit la reponse
				SubjectGenerator.writeText(pdPageContentStream, r.getIntitule(), widthOffset + 40,
						this.height - heightOffset - 15);
				// on ajoute une zone pour cocher (carre)
				pdPageContentStream.addRect(widthOffset + 20, this.height - heightOffset - 16, 10, 10);
				// si l'on souhait generer le corrige
				if (isCorrected) {
					// si c'est une bonne reponse
					if (r.isJuste()) {
						pdPageContentStream.fillAndStroke(); // carre noirci
					} else {
						pdPageContentStream.stroke(); // carre vide
					}
				} else {
					pdPageContentStream.stroke(); // carre vide
				}
				heightOffset += 17.5;
			}
			heightOffset += 20; // espace blanc entre 2 questions
			pdPageContentStream.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return heightOffset;
	}

	public float generateOpenQ(QuestionBoite q, int qIndex, PDDocument pdDocument, PDPage curPage, float widthOffset,
			float heightOffset, boolean isCorrected) {
		// TODO: stocker dans une variable la largeur de la police au lieu de mettre des
		// valeurs numeriques arbitraires
		try {
			PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, curPage,
					PDPageContentStream.AppendMode.APPEND, true);
			PDFont font = PDType1Font.TIMES_ROMAN;
			int fontSize = 10;
			pdPageContentStream.setFont(font, fontSize);
			float titleLength = SubjectGenerator.getStrLenWithFont(q.getTitre(), font, fontSize);
			float lastLineLenght = titleLength;
			// int widthMargin = 20;

			if (titleLength > (this.width - 20)) {
				// Si titre plus long que largeur page -> mise sur plusieurs lignes
				SubjectGenerator.writeText(pdPageContentStream, "Q." + qIndex + " - ", widthOffset,
						this.height - heightOffset);

				for (String line : setTextOnMultLines(q.getTitre(), (int) widthOffset, pdDocument, font, fontSize)) {
					// pour chaque ligne de la question
					SubjectGenerator.writeText(pdPageContentStream, line, widthOffset + 22, this.height - heightOffset);
					heightOffset += 20; // on met a jour le offset vertical (une ligne = 20)
					lastLineLenght = SubjectGenerator.getStrLenWithFont(line, font, fontSize);
				}
				heightOffset -= 20;
			} else {
				SubjectGenerator.writeText(pdPageContentStream, "Q." + qIndex + " - " + q.getTitre(), widthOffset,
						this.height - heightOffset);
			}

			// on cherche a savoir a partir d'ou on peut mettre le texte des reponses pour
			// etre le plus a droite possible en fonction du nombre de reponses associees
			int maxX = this.width - (int) widthOffset;

			for (Reponse r : q.getReponses()) {
				// pour chaque reponse
				maxX -= (SubjectGenerator.getStrLenWithFont(r.getIntitule(), font, fontSize) + widthMargin);
			}

			if ((lastLineLenght + widthMargin) > maxX) {
				// si la reponse est trop longue pour pouvoir mettre les reponses sur la meme
				// ligne
				heightOffset += 20; // TODO: revoir 20 avec largeur police
			}

			// on affiche les reponses et les cases a cocher associees
			pdPageContentStream.addRect(maxX - 5, this.height - heightOffset - 5,
					((this.width - widthMargin) - (maxX) - 5), 20); // cadre
			pdPageContentStream.setNonStrokingColor(Color.LIGHT_GRAY);
			pdPageContentStream.fill();
			// pdPageContentStream.setNonStrokingColor(Color.BLACK);
			// gris
			for (Reponse r : q.getReponses()) {
				// pour chaque reponse
				pdPageContentStream.setStrokingColor(Color.BLACK);
				pdPageContentStream.setNonStrokingColor(Color.BLACK);
				pdPageContentStream.addRect(maxX, this.height - heightOffset, 10, 10);
				SubjectGenerator.writeText(pdPageContentStream, r.getIntitule(), maxX + 12, this.height - heightOffset);
				if (isCorrected) {
					// si c'est une bonne reponse
					if (r.isJuste()) {
						pdPageContentStream.fillAndStroke(); // carre noirci
					} else {
						pdPageContentStream.stroke(); // carre vide
					}
				} else {
					pdPageContentStream.stroke(); // carre vide
				}
				maxX += SubjectGenerator.getStrLenWithFont(r.getIntitule(), font, fontSize) + 20;
				// taille de la reponse plus du carre
			}
			// heightOffset += 17.5; // ?

			// zone d'ecriture (rectangle)
			// TODO: ameliorer
			int linesLenght = q.getNbligne() * 20; // nb de lignes * taille d'une ligne (20)
			int xLenght = this.width - widthMargin - 50;

			// zone d'ecriture
			heightOffset += 10;
			pdPageContentStream.addRect(widthOffset + 20, this.height - heightOffset - linesLenght, xLenght,
					linesLenght);

			// doted lines
			int yCursor = this.height - (int) heightOffset - 16;
			for (int i = 0; i < q.getNbligne(); i++) {
				yCursor = this.height - (int) heightOffset - 16 - (i * 2); // on descends y de la taille d'une ligne
				// SubjectGenerator.drawDotedLine(pdPageContentStream, widthOffset + 23,
				// this.height - heightOffset - (20 * i), yLenght - 23, this.height -
				// heightOffset - (20 * i));
			}

			heightOffset += linesLenght; // largeur de la zone d'ecriture
			heightOffset += 20; // espace blanc entre 2 questions

			pdPageContentStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return heightOffset;
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
		// devra etre fait dans Exec
		Config config = new Config("E:\\source.txt");
		config.readConfig();
		// -------------------------

		// instanciate a new SubjectGenerator
		SubjectGenerator subjectGenerator = new SubjectGenerator(config);

		// ATTENTION : generateBody est place en premier car c'est elle qui va generer
		// le nombre de page suffisant
		subjectGenerator.generateBody(false); // false = on genere le sujet, pas le corrige
		subjectGenerator.generateHeader();
		subjectGenerator.generateMarks();
		subjectGenerator.generateFooter();

		subjectGenerator.save("E:\\testPDFMarks.pdf");

		System.out.println("PDF saved to the location !!!");
	}
}
