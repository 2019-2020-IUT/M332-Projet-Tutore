package ocr;

import java.awt.image.BufferedImage;



public class ImgNumEtu extends Img{

	public ImgNumEtu(BufferedImage img) {
		super(img);
	}

	@Override
	public void applyOcrImg() {
		setDescription(OCR.applyOcrNumber(getImg()));
		this.sanitizeDesc();
		
	}

}