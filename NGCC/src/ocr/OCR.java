package ocr;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract; 
import net.sourceforge.tess4j.TesseractException; 
  
public class OCR {
	
	

	public static String applyOcrNumber(BufferedImage img) {
		//FAIRE L'OCR
		Tesseract tesseract = new Tesseract();
		String str="";
		try {
			tesseract.setOcrEngineMode(2);
			tesseract.setTessVariable("tessedit_char_whitelist","0-9");
			str=tesseract.doOCR(img);
		} catch (TesseractException e) {
			
			e.printStackTrace();
		}
		
	        
	    return str;
	}
}
