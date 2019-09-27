package ocr;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Rogneur {

	// Retourne une hashmap contenant une image et la description de son contenu
	public static Map<String, Img> createHMapImgs(BufferedImage imgOriginale) {
		
		Map<String,Img> temp = new HashMap<>();
		temp.put("NumEtu", rogneurNumEtu(imgOriginale));
		temp.put("Note", rogneurNote(imgOriginale));
		//temp.put("FormatNote", rogneurFormatNote(imgOriginale));
		
		return temp;
	}

	// rogne la partie du numEtu
	public static Img rogneurNumEtu(BufferedImage imgOriginale)
	{
		return (new ImgNumEtu(imgOriginale.getSubimage((imgOriginale.getWidth()/4)+4
				, imgOriginale.getHeight()-imgOriginale.getHeight()+115
				, (imgOriginale.getWidth()/4+150)-(imgOriginale.getWidth()/4+4) 
				, imgOriginale.getHeight()-imgOriginale.getHeight()+146-(imgOriginale.getHeight()-imgOriginale.getHeight()+115) )));
	}
	
	// rogne la partie de la note
	public static Img rogneurNote(BufferedImage imgOriginale)
	{
		return (new ImgNumEtu(imgOriginale.getSubimage((imgOriginale.getWidth()/4)+4
				, imgOriginale.getHeight()-imgOriginale.getHeight()+160
				, (imgOriginale.getWidth()/4+150)-(imgOriginale.getWidth()/4+4) 
				, imgOriginale.getHeight()-imgOriginale.getHeight()+200-(imgOriginale.getHeight()-imgOriginale.getHeight()+160) )));
	}
	
	// rogne la partie du format de la note
	/*public static Img rogneurFormatNote(BufferedImage imgOriginale)
	{
		return imgOriginale.getSubimage(x, y, w, h);
	}*/
}
