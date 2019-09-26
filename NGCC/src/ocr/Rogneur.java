package ocr;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Rogneur {

	// Retourne une hashmap contenant une image et la description de son contenu
	public static Map<String, Img> createHMapImgs(BufferedImage imgOriginale) {
		
		Map<String,Img> temp = new HashMap<>();
		temp.put("NumEtu", rogneurFormatNote(BufferedImage imgOriginale));
		temp.put("Note", rogneurFormatNote(BufferedImage imgOriginale));
		temp.put("FormatNote", rogneurFormatNote(BufferedImage imgOriginale));
		
		return temp;
	}

	// rogne la partie du numEtu
	public Img rogneurNumEtu(BufferedImage imgOriginale)
	{
		
		// A FAIRE
		//return new ImgNum();
	}
	
	// rogne la partie de la note
	public Img rogneurNote(BufferedImage imgOriginale)
	{
		
		// A FAIRE
		//return new ImgNum();
	}
	
	// rogne la partie du format de la note
	public Img rogneurFormatNote(BufferedImage imgOriginale)
	{
		
		// A FAIRE
		//return new ImgNum();
	}
}
