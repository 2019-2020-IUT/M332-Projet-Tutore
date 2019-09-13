package lecturePdf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Recadrage  {
	 String dir="";
	 String filename="";
	 BufferedImage img;
	 
	 
	 public Recadrage(String directory,String file) {
		 dir=directory;
		 filename=file;
		 File f =new File(dir+filename);
		 
		 try {
			 img=ImageIO.read(f);
		 } catch (IOException e) {
				System.out.println(e);
			}
	 }
	 
    public boolean estDroite() {	//determine si l'image png/jpg du pdf est droite
		int count=0;
		boolean stop=false;
		for (int ty=0; ty<100 && !stop;ty++) {
			for (int tx=0;tx<img.getWidth();tx++) {
				
				Color tmp=new Color(img.getRGB(tx, ty));
				if (tmp.getGreen()<10) {
					count++;
					stop=true;
				}
			}
		}
		if (count>img.getWidth()/5.4 && count <img.getWidth()/5)
			return true;
		return false;
    }
    
    
    
    public static void main(String avg[]) 
    {
    	Recadrage rec=new Recadrage ("P:\\Bureau\\ptut s3\\eclipse\\image\\","sujet0.jpg");
    	System.out.println(rec.estDroite());
    }

}
