package ocr;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract; 
import net.sourceforge.tess4j.TesseractException; 
  
public abstract class OCR {
	
	

	public static String applyOcrNumber(BufferedImage img) {
		//FAIRE L'OCR
		Tesseract tesseract = new Tesseract();
		String str="";
		try {
			tesseract.setDatapath("Tess4J");
			tesseract.setLanguage("eng");
			//tesseract.setOcrEngineMode(2);
			tesseract.setTessVariable("tessedit_char_whitelist",",0123456789");
			str=tesseract.doOCR(img);
		} catch (TesseractException e) {
			
			e.printStackTrace();
		}
		
	        
	    return str;
	}
}
