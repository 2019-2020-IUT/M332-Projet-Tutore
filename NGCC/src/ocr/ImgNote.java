package ocr;

import java.awt.image.BufferedImage;



public class ImgNote extends Img{

	public ImgNote(BufferedImage img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applyOcrImg() {
		setDescription(OCR.applyOcrNumber(getImg()));
	}

}
