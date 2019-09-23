package ocr_orm;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
;



public class ListeImageNGCC {

	private ArrayList<ImageNGCC> listeImage = new ArrayList<ImageNGCC>();
	
	
	public ListeImageNGCC(ArrayList<BufferedImage> liste) {
		
		for(int i=0; i<liste.size();i++)
		{
			//ON AJOUTE UNE IMAGE NGCC CREE A PARTIR DE CHAQUE IMAGE DE LA LISTE D'IMAGE BUF
			listeImage.add(new ImageNGCC(liste.get(i)) );
		}
	}

	public ArrayList<ImageNGCC> getListeImage(ArrayList<BufferedImage> liste) {
		return listeImage;
	}
	
	public int taille() {
		return listeImage.size();
	}
	
	public HashMap<String, String> doOCR(){
		HashMap<String,String> maMap = new HashMap<String, String>();
		OCR ocr = new OCR();
		for (int i=0;i<listeImage.size();i++) {
			String strNote; 
			String strNumEtu;
			strNote = ocr.getOCR(listeImage.get(i).getImgNote());
			strNumEtu = ocr.getOCR(listeImage.get(i).getImgNumEtu());
			maMap.put(strNumEtu, strNote);
		}
		return maMap;
	}
	
}
