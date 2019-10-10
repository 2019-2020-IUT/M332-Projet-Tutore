package lecturePdf;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class Recadrage {
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
			//System.out.println(e);
		}
	}
	
	public Recadrage(BufferedImage image) {
		img=image;
	}
	public Recadrage(){
	}

	public boolean estDroite1() {	//determine si l'image png/jpg du pdf est droite  (doit etre en noir et blanc)
		int count=0;
		boolean stop=false;
		for (int ty=0; ty<100 && !stop;ty++) {
			for (int tx=0;tx<img.getWidth();tx++) {

				Color tmp=new Color(img.getRGB(tx, ty));
				if (tmp.getGreen()<20) {
					count++;
					stop=true;
				}
			}
		}
		if (count>img.getWidth()/5.4 && count <img.getWidth()/5)
			return true;
		return false;
	}
	
	
	
	public boolean estDroite(){
		int count=0;
		boolean stop=false;
		for (int ty=0; ty<100 && !stop;ty++) {
			for (int tx=0;tx<img.getWidth();tx++) {
	
				Color tmp=new Color(img.getRGB(tx, ty));				
				if (tmp.getGreen()<20) {
					count++;
				}
			}
			if (count>img.getWidth()/5.4 && count <img.getWidth()/5)
				return true;
			count=0;
			if (ty>img.getHeight()*0.1)
				stop=true;
		}
		if (entreeEnvers())
			this.img=Recadrage.rotate(img, 180);
		return false;
	}
	
	public boolean aLEnvers() {
		boolean stop2=false;
		int count=0;
		for (int ty2=img.getHeight()-100; ty2<img.getHeight() && !stop2;ty2++) {
			
			for (int tx2=0;tx2<img.getWidth();tx2++) {
				
				Color tmp=new Color(img.getRGB(tx2, ty2));
				if (tmp.getGreen()<20) {
					count++;
				}
			}
			if (count>img.getWidth()/5.4 && count <img.getWidth()/5) {
				return true;
			}
			count=0;
			if (ty2>img.getHeight()*0.1)
				stop2=true;
		}
		return false;
	}
	
	public boolean entreeEnvers() {
		boolean stop2=false;
		int count=0;
		int maxwid=img.getWidth();
		for (int ty2=img.getHeight()-100; ty2<img.getHeight() && !stop2;ty2++) {	
			
			for (int tx2=(int)(maxwid*0.1);tx2<maxwid*0.9;tx2++) {
				
				Color tmp=new Color(img.getRGB(tx2, ty2));
				if (tmp.getGreen()<20) {
					count++;						//detecte si le nombre de pix noir present au milieu haut
				}									// de la copie correspond au carré sinon swap
			}
			if (ty2>img.getHeight()*0.1)
				stop2=true;
		
		}
		if (count<img.getWidth()/5.4) 
			return true;
			
		return false;
	
	}
	

	public void setImage(BufferedImage entree){
		this.img=entree;
	}
	
	
	
	
	
	
	public BufferedImage automation() throws IOException {
		if (!this.estDroite()) {
			int[][] points=RdB();
			double angle=getAngle(points);
			    
		    img=rotate(img,angle);
		    if (aLEnvers())
				this.img=Recadrage.rotate(img, 180);
		    String nomImage="sortie";
			File nomfichier = new File("C:\\Users\\Xxsafirex\\Desktop\\Image\\" + nomImage + ".jpg");// ou jpg
			ImageIO.write(img, "JPG", nomfichier);//ou JPG
			
		}	
		return img;
	}

	
	
	public ArrayList<BufferedImage> listAutomation(ArrayList<BufferedImage>list){
		ArrayList<BufferedImage> retour =new ArrayList<BufferedImage>();
		
		for (BufferedImage imag:list) {
			this.img=imag;
			try {
				this.automation();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			retour.add(this.img);
		}
		return retour;
	}
	
	
	
	
	
	
	public int[][] RdB() {		// cherche les 4 points noirs
		int[][] roar = new int[img.getWidth()*img.getHeight()][2];

		int[][] regroupY = new int[img.getWidth()*img.getHeight()][2];
		int boucleY=0;int groupYi=0;
		int i=0;
		int diamMax=0;
		int radius=8;			// 19= limite de detection de checkCircle
		for (int ty=0; ty<img.getHeight();ty++) {
			for (int tx=0;tx<img.getWidth();tx++) {

				Color tmp=new Color(img.getRGB(tx, ty));
				if (tmp.getGreen()<20) { 	//si le pixel  est noir
					if (checkCircle(tx,ty,radius) ) {	//verifie si un cercle de radius entoure le pixel
						roar[i][0]=tx;
						roar[i][1]=ty;
						i++;
					}
				}
			}
		}
		//System.out.println("fin");
		int tempora=0;
		int tmp=1;
		int roar2[][]=new int [img.getWidth()*img.getHeight()][2];
		roar2[0]=roar[0];
		for (int l=0;l<img.getHeight()*img.getWidth() && (roar[l][1]!=0 || roar[l][0]!=0);l++) {
			if((roar[l][0]-roar2[tmp-1][0])<5 || roar[l][1]-roar2[tmp-1][1]<5 ){		//x-(x-1)>5 ou y-(y-1)>5
				roar2[tmp][0]=roar[l][0];			//efface le precedent roar2 si il était a 1 pixel de diff
				roar2[tmp][1]=roar[l][1];
			}
			else {
				tmp++;
				roar2[tmp]=roar[l];
			}
			
		}
		
		
		//boucle de determination des points noirs
		//System.out.println("roar2debut");
		int points[][]=new int [4][2];
		int lastRoar[][]=new int [roar2.length][2]; int boucl=0; int lasti=0;
		int t=0;
		for (int l=1;l<=img.getHeight()*img.getWidth() && (roar2[l-1][1]!=0 || roar2[l-1][0]!=0);l++) {


			
			int diffx=roar2[l][0]-roar2[l-1][0];
			int diffy=roar2[l][1]-roar2[l-1][1];
			int diff=Math.abs(diffx)+Math.abs(diffy);
			if (diff>img.getWidth()*0.85)
			{
				points[t]=roar2[l];
				t++;
			}
			
			if (diffx<10 && diffy<10) {
				boucl++;
			}
			else {
				lastRoar[lasti][0]=roar2[l-boucl/2][0];
				lastRoar[lasti][1]=roar2[l-boucl/2][1];
				lasti++;boucl=0;
			}
		}
		
		
		
		//System.out.println("lasrorar");
		for (int l=0;l<=lastRoar.length && (lastRoar[l][1]!=0 || lastRoar[l][0]!=0);l++) {
			//System.out.println("x: "+lastRoar[l][0]+"   y: "+lastRoar[l][1]);	//reste a grouper les coord pour avoir le centre des ronds
		}
		
		
		for (int l=0;l<=lastRoar.length && (lastRoar[l][1]!=0 || lastRoar[l][0]!=0);l++) {	
			boolean test=true;
			int maxPoint=0;
			for (int li=0;li<=points.length && (points[li][1]!=0 || points[li][0]!=0);li++) {
				int diffx=	Math.abs(lastRoar[l][0]-points[li][0]);
				int diffy=	Math.abs(lastRoar[l][1]-points[li][1]);
				boolean testx=		diffx>img.getWidth()*0.85 	|| diffx<img.getWidth()*0.2;	//diff   <0.1 ou >0.8 x la largeur de feuille
				boolean testy=		diffy>img.getHeight()*0.8 	|| diffy<img.getWidth()*0.2;
				boolean Repeat=	diffx+diffy>img.getWidth()*0.2;	 //si point deja présent

				if (!Repeat || (!testx || !testy) )	// si 0.2>diffx>0.8 ou "diffy" et  
				{
					test=false;
				}
				maxPoint=li;
			}
			
			if(test && maxPoint<2) {
				//System.out.println(lastRoar[l][0]+"   "+lastRoar[l][1]);
				points[maxPoint+1][0]=lastRoar[l][0];
				points[maxPoint+1][1]=lastRoar[l][1];
			}
		}
		//System.out.println("point");
		/*for (int l=1;l<=points.length;l++) {
			System.out.println("x: "+points[l-1][0]+"   y: "+points[l-1][1]);	
		}*/

		return points;

	}






	public boolean checkCircle(int x,int y,int radius) {
		double pi=Math.PI; //3.14
		Color tmp;
		for (double k=-1;k<=1;k+=0.05) {						// de 0 à 2 pi
			int px=x+(int)Math.round(radius*Math.cos(k*pi));		//px = pos x du contour du supposé cercle
			int py=y+(int)Math.round(radius*Math.sin(k*pi));		//diam calculé +/- 42 pixels
			if(py<0)py=0;
			if(py>=img.getHeight())py=img.getHeight()-1;
			if(px<0)px=0;
			if (px>=img.getWidth())px=img.getWidth()-1;
			//System.out.println("px ="+px+"  /"+img.getWidth());
			//System.out.println("py ="+py+"  /"+img.getHeight());
			tmp=new Color(img.getRGB(px, py));
			if (tmp.getGreen()>20) {		//si pixel == blanc
				return false;
			}
		}
		return true;
	}


	public double getAngle(int[][] tab) {
		double[] res= new double[4];int resi=0;
		int cmpt=0;
		int angle=0;
		for (int i=0;i<tab.length;i++) {
			if (!(tab[i][0]==0) || !(tab[i][1]==0))
				cmpt++;
		}
		//System.out.println("cmpt = "+cmpt);
		
		if (cmpt<=1) {
			 return 0; //RIP CODE
		}
		
		for (int i=1;i<tab.length && (!(tab[i][0]==0) || !(tab[i][1]==0));i++) {
			for(int c=1;i<tab.length && (tab[c][0]!=0 || tab[c][1]!=0);i++)
			{
				int diffx=tab[i-1][0]-tab[i][0];
				int diffy=tab[i-1][1]-tab[i][1];
				
				double yb=tab[i][1];
				double xb=tab[i][0];
				double ya=tab[i-1][1];
				double xa=tab[i-1][0];
				
				double pointy,pointx;
				pointy=yb; 				//pointy/x = coord du 3e point de triangle rectangle
				pointx=xa;				
				//System.out.println("xb"+ xb +   "           xa "+xa);
	
				double dhypo=Math.sqrt(Math.pow(xb-xa,2)+Math.pow(yb-ya,2));//(yb-ya)/(xb-xa);
				
				
				double dadj=Math.sqrt(Math.pow(xb-pointx, 2)+Math.pow(yb-pointy, 2));	//adjacent / rapport a xb,yb
				
				//System.out.println("dadj "+dadj + " dhypo "+dhypo);
				
				
				if (dhypo<img.getWidth() && dhypo!=0) {	//deux points selectionnés sont des diagonales
					double retour=Math.acos(dadj/dhypo)*(180/Math.PI);
					if (retour>90/2)
						retour=180-90-retour;
					
					if((xa<xb && ya<yb )||( xb<xa && yb<ya))				//point de droite plus haut que celui de gauche
						return -retour;
					else
						return retour;
				}
				/*else {			//deux points sont en diagonnale
					double retour=Math.acos(dadj/dhypo)*(180/Math.PI);		// ne marche pas 
					return (Math.abs(45-retour)/2);
					
				}*/
			
				
			}
			
			
		}		
		return 0;
	}



	//rotation de https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
	public static BufferedImage rotate(BufferedImage bimg, double angle) {		
	    int w = bimg.getWidth();    
	    int h = bimg.getHeight();

	    BufferedImage rotated = new BufferedImage(w, h, bimg.getType());  
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawImage(bimg, null, 0, 0);
	    graphic.dispose();
	    return rotated;
	}


}
