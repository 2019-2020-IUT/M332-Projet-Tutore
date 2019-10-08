package ocr;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class Rogneur {


	
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
		int ratioX = (imgOriginale.getWidth()/595);
		int ratioY = (imgOriginale.getWidth()/841);
				
		int numEtuX1 = ratioX*((595/4)+ 4)+10;
		int numEtuY1 = ratioY*115 + 275;
		
		int numEtuX2 = ratioX*((595/4)+150);
		int numEtuY2 = ratioY*146 + 310;
		
		BufferedImage temp = imgOriginale.getSubimage(numEtuX1, numEtuY1, numEtuX2-numEtuX1, numEtuY2-numEtuY1);
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(temp)));
		//frame.getContentPane().add(new JLabel(new ImageIcon(images.get(0))));
		frame.setVisible(true);
		
		return new ImgNumEtu(temp);
		
	}
	
	// rogne la partie de la note
	public static Img rogneurNote(BufferedImage imgOriginale)
	{
		int ratioX = (imgOriginale.getWidth()/595);
		int ratioY = (imgOriginale.getWidth()/841);
		
		int numNoteX1 = ratioX*((595/4) + 4)+60;
		int numNoteY1 = ratioY*160+400;
		
		int numNoteX2 = ratioX*((595/4) +80);
		int numNoteY2 = ratioY*200+400;
		
		
		BufferedImage temp = imgOriginale.getSubimage(numNoteX1, numNoteY1, numNoteX2-numNoteX1, numNoteY2-numNoteY1);
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(temp)));
		//frame.getContentPane().add(new JLabel(new ImageIcon(images.get(0))));
		frame.setVisible(true);
		
		return new ImgNote(temp);
	}
	
	// rogne la partie du format de la note
	/*public static Img rogneurFormatNote(BufferedImage imgOriginale)
	{
		return imgOriginale.getSubimage(x, y, w, h);
	}*/
}
