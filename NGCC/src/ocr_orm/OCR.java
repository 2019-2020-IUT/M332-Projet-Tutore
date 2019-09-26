package ocr_orm;
import java.awt.image.BufferedImage;
 

import net.sourceforge.tess4j.Tesseract; 
import net.sourceforge.tess4j.TesseractException; 
  
public class OCR {
	
	

	public String getOCR(BufferedImage img) {
		//FAIRE L'OCR
		Tesseract tesseract = new Tesseract();
		String str="";
		try {
			tesseract.setOcrEngineMode(2);
			tesseract.setTessVariable("tessedit_char_whitelist", "A-Za-z1-9");
			str=tesseract.doOCR(img);
		} catch (TesseractException e) {
			
			e.printStackTrace();
		}
		
	        
	    return str;
	}
}
