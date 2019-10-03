package ocr;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;


public class GestionnaireCopies {

	private List<Copie> listeCopie;
	
	private List<BufferedImage> copies;
	
	public GestionnaireCopies(String chemin) {
		
		copies = createImagesCopies(chemin);
		listeCopie = new ArrayList<Copie>();
		
		for(BufferedImage i : copies)
		{
			listeCopie.add(new Copie(i));
		}
	}
	
	public List<BufferedImage> createImagesCopies(String path){

		PdfToImage pdfAnalyzer = new PdfToImage();
		File pdfFile;
		PDDocument document = null;
		//LISTE DES IMAGES 
		List<BufferedImage> images = new ArrayList<>(); // stockera les images (resultat)
		// CONVERT PAGES TO IMAGES
		try {
			String pdfFilesDirectory = "/Users/louis/Desktop/IUT_S3/PT/pt-s3t-g4/pdf";
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
	
		return images;
		
	}
	
	public HashMap<String,String> createHashMapforCSV(){
		
		HashMap<String,String> temp = new HashMap<>();
		for(Copie c : listeCopie)
		{
			temp.put(c.getBase().gethMapImgs().get("NumEtu").getDescription(), c.getBase().gethMapImgs().get("Note").getDescription());	
		}
		return temp;
	
	}
	
}
