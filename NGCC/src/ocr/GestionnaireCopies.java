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
		listeCopie = new ArrayList<>();
		
		for(BufferedImage i : copies)
		{
			listeCopie.add(new Copie(i));
			
		}
		applyOcr();
		
	}
	
	public List<BufferedImage> createImagesCopies(String path){

		PdfToImage pdfAnalyzer = new PdfToImage();
		File pdfFile;
		PDDocument document = null;
		//LISTE DES IMAGES 
		List<BufferedImage> images = new ArrayList<>(); // stockera les images (resultat)
		// CONVERT PAGES TO IMAGES
		
		
		try {
			
			
			// nom du fichier pdf à ouvrir (TODO: changer le chemin)
			List<String> files = pdfAnalyzer.listAllFiles(path, ".pdf");
			
			for (String fname : files) {
				pdfFile = new File(fname);
				document = PDDocument.load(pdfFile); // charge le fichier pdf cree pour le traiter
				
				images.addAll(pdfAnalyzer.convertPagesToBWJPG(document,1));
				// appelle la methode qui convertit les pages en images (jpg) noir et blanches
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
		/*
		File img = new File("E:\\S3T\\Projet\\pt-s3t-g4\\pdf\\img_0.jpg");
			
		BufferedImage in;
		try {
			in = ImageIO.read(img);
			
			 Image tmp = in.getScaledInstance(500, 331, Image.SCALE_SMOOTH);
			    BufferedImage dimg = new BufferedImage(500, 331, BufferedImage.TYPE_INT_ARGB);

			    Graphics2D g2d = dimg.createGraphics();
			    g2d.drawImage(tmp, 0, 0, null);
			    g2d.dispose();
			
			
		
			System.out.println(in.getHeight() + " " + in.getWidth());
			JFrame frame = new JFrame();
			frame.getContentPane().add(new JLabel(new ImageIcon(dimg)));
			//frame.getContentPane().add(new JLabel(new ImageIcon(images.get(0))));
			frame.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pdfAnalyzer.saveOnDisk(images, "E:\\S3T\\Projet\\pt-s3t-g4\\pdf\\"); 
			
		
		*/
		//LISTE DES IMAGES COMPRENANT L'IMAGE DE LA NOTE ET DU NUM ETUDIANT
	
		return images;
		
	}
	
	public Map<String,String> createHashMapforCSV(){
		
		HashMap<String,String> temp = new HashMap<>();
		
		
		for(Copie c : listeCopie)
		{	
			
			String numEtu = c.getBase().gethMapImgs().get("NumEtu").getDescription();
			String noteEtu = c.getBase().gethMapImgs().get("Note").getDescription();
			
			temp.put(numEtu, noteEtu);
		}
		
		return temp;
	
	}
	
	public void applyOcr()
	{
		for(Copie c : listeCopie)
		{
			c.getBase().applyOcrForEach();
		}
	}
	
}
