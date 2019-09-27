package ocr;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Rogneur {

	// Retourne une hashmap contenant une image et la description de son contenu
	public static Map<String, Img> createHMapImgs(BufferedImage imgOriginale) {
		
		Map<String,Img> temp = new HashMap<>();
		temp.put("NumEtu", rogneurFormatNote(imgOriginale));
		temp.put("Note", rogneurFormatNote(imgOriginale));
		temp.put("FormatNote", rogneurFormatNote(imgOriginale));
		
		return temp;
	}

	// rogne la partie du numEtu
	public static Img rogneurNumEtu(BufferedImage imgOriginale)
	{
		return imgOriginale.getSubimage(x, y, w, h);
	}
	
	// rogne la partie de la note
	public static Img rogneurNote(BufferedImage imgOriginale)
	{
		return imgOriginale.getSubimage(x, y, w, h);
	}
	
	// rogne la partie du format de la note
	public static Img rogneurFormatNote(BufferedImage imgOriginale)
	{
		return imgOriginale.getSubimage(x, y, w, h);
	}
}
