package ocr;

import java.awt.image.BufferedImage;
import java.util.Map;

public class ImagesCopie {

	private Map<String,Img> hMapImgs;
	
	
	public ImagesCopie(BufferedImage imgOriginale) {
		
		
		hMapImgs = Rogneur.createHMapImgs(imgOriginale);
	}
	
	public void applyOcrForEach() {
		
		for(String s : hMapImgs.keySet())
		{
			hMapImgs.get(s).applyOcrImg();
		}
	}
}
