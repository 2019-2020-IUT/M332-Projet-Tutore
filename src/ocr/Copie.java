package ocr;

import java.awt.image.BufferedImage;

public class Copie {

	ImagesCopie base;

	public Copie(BufferedImage img) {
		this.base = new ImagesCopie(img); 
	}

	public ImagesCopie getBase() {
		return base;
	}

	public void setBase(ImagesCopie base) {
		this.base = base;
	}
	
	
}
