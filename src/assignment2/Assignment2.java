package assignment2;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.pearsoneduc.ip.gui.ImageSelector;
import com.pearsoneduc.ip.io.ImageDecoderException;
import com.pearsoneduc.ip.op.OperationException;

public class Assignment2 extends ImageSelector {
	
public Assignment2(String imageFile)
	throws IOException, ImageDecoderException, OperationException {
	super(imageFile);
}

//Checks that the image is suitable for simulation
public boolean imageOK() {
	return getSourceImage().getType() == BufferedImage.TYPE_BYTE_GRAY;  
}
	
public int[] getHistogram(BufferedImage img){
	int[] h = new int[256];
    Raster src = img.getRaster();
    int width = getSourceImage().getWidth();
    int height = getSourceImage().getHeight();
    for (int i = 0; i < 256; i++)
    	h[i] = 0;
    for(int y = 0; y < height; y++)
    	for(int x = 0; x < width; x++){
    		int color = src.getSample(x, y, 0);
    		h[color] += 1;
    	}
    
   return h;
}

public BufferedImage generateHistImg(BufferedImage img){
	int[] H = getHistogram(img);
	int width = 256*4;
	int height = 0;
	for(int i = 0; i < H.length; i++)
		height = (H[i] > height) ? H[i] : height; 
	
	BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	WritableRaster dest = destImg.getRaster();
	//generate white background
	for(int x = 0; x < dest.getWidth(); x++)
		for(int y = 0; y < dest.getHeight(); y++)
			dest.setSample(x, y, 0, 255);
	//generate binary image
	for(int grayLevel = 0; grayLevel < 256; grayLevel++){
		int x = grayLevel*4;
		int x1 = x+1;
		for(int i = 0; i < H[grayLevel]; i++){
			int y = (height-1)-i;
			dest.setSample(x, y, 0, 0);
			dest.setSample(x1, y, 0, 0);
		}
	}
	return destImg;
}

public BufferedImage generateEqualizedImg(){
	int width = getSourceImage().getWidth();
	int height = getSourceImage().getHeight();
	Raster src = getSourceImage().getRaster();
	BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	WritableRaster dest = destImg.getRaster();
	
	float a = 1.0f*255/(width*height);	
	int[] h = getHistogram(getSourceImage());
	
	int[] Hc = new int[256];
	Hc[0] = (int) (a*h[0]);
    
	for(int i = 1; i < 256; i++)
		Hc[i] = Hc[i-1] + (int) (a*h[i]);
	
	for(int x = 0; x < width; x++)
		for(int y = 0; y < height; y++){
			int color = src.getSample(x, y, 0);
			dest.setSample(x, y, 0, Hc[color]);
		}

	return destImg;
}
	
	
	
	
@Override
public Vector generateImages() {
	Vector vector = new Vector();

	vector.addElement("Original Image");
    addImage("Original Image", new ImageIcon(getSourceImage()));

    vector.addElement("HistImg.jpg");
    addImage("HistImg.jpg", new ImageIcon(generateHistImg(getSourceImage())));
	
    BufferedImage EqualizedImg = generateEqualizedImg();
    vector.addElement("EqualizedImg.jpg");
    addImage("EqualizedImg.jpg", new ImageIcon(EqualizedImg));
	
    vector.addElement("EqualizedHistImg.jpg");
    addImage("EqualizedHistImg.jpg", new ImageIcon(generateHistImg(EqualizedImg)));

    return vector;
}

	



	
 public static void main(String[] argv) {
	 if (argv.length > 0) {
		 try {
			 JFrame frame = new Assignment2(argv[0]);
			 frame.pack();
			 frame.setVisible(true);
		 }
	     catch (Exception e) {
	    	 System.err.println(e);
	    	 System.exit(1);
	     }
	 }
	 else {
		 System.err.println("usage: java Enlarging <imagefile>");
		 System.exit(1);
	 }
} //Main




}//class
