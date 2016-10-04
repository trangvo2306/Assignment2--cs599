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
public class CarSpeed{
    
    public CarSpeed(java.awt.image.BufferedImage image1, BufferedImage image2, Object par2, Object par3){
        
        int w = image1.getWidth();
        int h = image1.getHeight();
        
        int w2 = image2.getWidth();
        int h2 = image2.getHeight();
        
        if( w != w2 || h != h2)
            System.err.println("Please put in same size images");
        else 
            testImage(image1, image2);
    }
    
    public BufferedImage testImage(BufferedImage img1, BufferedImage img2){
        Raster src = img1.getRaster();
        Raster src2 = img2.getRaster();
        
        int w = img1.getWidth();
        int h = img1.getHeight();
        
        BufferedImage destImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster dest = destImg.getRaster();
        
        for(int x = 0; x < w ; x++)
            for(int y = 0; y < h ; y++){
                int color = src.getSample(x,y, 0) - src2.getSample(x,y,0);
                dest.setSample(x, y, 0, color);
            }
        return destImg;
    }
   
    public Vector createImages(BufferedImage result){
        Vector v = new Vector();
        
        int w = result.getWidth();
        int h = result.getHeight();
        
        v.addElement("Result");
        addImage("Result", newImageIcon(testImage()))
        
        return v;
    }
     public static void main(String[] argv) throws IOException, ImageDecoderException, OperationException {
	 if (argv.length > 0) {
             
            prepareImage pImage = new prepareImage(argv[0]);
            prepareImage pImage2 = new prepareImage(argv[1]);
            
            if(pImage.imageOK() && pImage2.imageOK()){
                try {
                    JFrame frame;
                    frame = new CarSpeed(pImage.getSrc(), pImage2.getSrc());
                    frame.pack();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                     System.err.println(e);
                     System.exit(1);
                }
            }
            else{
                System.err.println("Wrong Type");
		 System.exit(1);
            }
	 }
	 else {
		 System.err.println("usage: java Enlarging <imagefile>");
		 System.exit(1);
	 }
} //Main
    
}
