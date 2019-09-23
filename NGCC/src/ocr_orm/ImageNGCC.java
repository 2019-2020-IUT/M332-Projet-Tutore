package ocr_orm;

import java.awt.image.*;

import javax.imageio.ImageIO;

import org.apache.pdfbox.rendering.ImageType;

import java.awt.Image;
@SuppressWarnings("unused")
public class ImageNGCC {

	BufferedImage imgcopie ;
	BufferedImage imgNumEtu;
	BufferedImage imgNote;
	
	public ImageNGCC(BufferedImage buf){
		imgcopie=buf; 
		imgNumEtu = imgcopie.getSubimage(0,0, 100, 50);
		imgNote = imgcopie.getSubimage(50,50,100,50);
	}

	public BufferedImage getImgNumEtu() {
		return imgNumEtu;
	}

	public BufferedImage getImgNote() {
		return imgNote;
	}
	
	
	
	
	
	
}
//https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html