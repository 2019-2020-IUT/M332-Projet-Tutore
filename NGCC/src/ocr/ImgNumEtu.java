package ocr;

import java.awt.image.BufferedImage;

import ocr_orm.OCR;

public class ImgNumEtu extends Img{

	public ImgNumEtu(BufferedImage img) {
		super(img);
	}

	@Override
	public void applyOcrImg() {
		setDescription(OCR.applyOcrNumber(getImg()));;
	}

}