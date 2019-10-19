package RecadragePdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfToImage {
	public BufferedImage blackWhiteConvert(BufferedImage image) {
		// Convertit une image en image en noir et blanc
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
		// verifie si une image est en noir et blanc
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
		// convertit chaque page d'un document pdf en image noir et blanc
		// retourne une array liste d'images
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

	public void saveOnDisk(ArrayList<BufferedImage> images, String originalFileDir) {
		// sauvegarde sur le disque les images
		int pageCounter = 0;
		try {
			for (BufferedImage img : images) {
				ImageIO.write(img, "JPEG", new File(originalFileDir + "img_" + pageCounter++ + ".jpg"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {

		PdfAnalyzer pdfAnalyzer = new PdfAnalyzer();
		File pdfFile;
		PDDocument document = null;
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>(); // stockera les images (resultat)

		// CONVERT PAGES TO IMAGES
		try {
			String pdfFilename = "DOC-sujet.pdf"; // nom du fichier pdf à ouvrir (TODO: changer le chemin)
			pdfFile = new File(pdfFilename); // cree un fichier pdf (non sauvegarde sur le disque)
			document = PDDocument.load(pdfFile); // charge le fichier pdf cree pour le traiter
			images = pdfAnalyzer.convertPagesToBWJPG(document); // appelle la methode qui convertit les pages en images
																// (jpg) noir et blanches
			pdfAnalyzer.saveOnDisk(images, "C:\\Users\\kg403211\\eclipse-workspace\\QCM\\src\\RecadragePdf\\Pdf"); // sauvegarde les images au chemin specifie (TODO: changer le chemin)
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
