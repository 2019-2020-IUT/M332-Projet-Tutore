package ocr_orm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.pdfbox.pdmodel.PDDocument;

public class Main {

	public static void main(String args[]) throws IOException {

		PdfToImage pdfAnalyzer = new PdfToImage();
		File pdfFile;
		PDDocument document = null;
		//LISTE DES IMAGES 
		ArrayList<BufferedImage> images = new ArrayList<>(); // stockera les images (resultat)
		//HASHMAP POUR LE CSV
		HashMap<String,String> listeNumNote = new HashMap<String, String>();
		
		// CONVERT PAGES TO IMAGES
		try {
			String pdfFilesDirectory = "C:\\Users\\ph807242\\eclipse-workspace\\PT\\pdf\\";
			// nom du fichier pdf à ouvrir (TODO: changer le chemin)
			List<String> files = pdfAnalyzer.listAllFiles(pdfFilesDirectory, ".pdf");
			for (String fname : files) {
				pdfFile = new File(fname);
				document = PDDocument.load(pdfFile); // charge le fichier pdf cree pour le traiter
				images.addAll(pdfAnalyzer.convertPagesToBWJPG(document));
				// appelle la methode qui convertit les pages en images (jpg) noir et blanches
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		
		//LISTE DES IMAGES COMPRENANT L'IMAGE DE LA NOTE ET DU NUM ETUDIANT
		ListeImageNGCC liNGCC = new ListeImageNGCC(images);
		
		listeNumNote = liNGCC.doOCR();
		
		
	}
}
