/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class prepareImage extends ImageSelector{

    public prepareImage(String imageFile) 
            throws IOException, ImageDecoderException, OperationException {
	super(imageFile); 
    }
    
    public BufferedImage getSrc(){
        return getSourceImage();
    }
    public boolean imageOK() {
	return getSourceImage().getType() == BufferedImage.TYPE_INT_RGB;  
    }

    @Override
    public Vector generateImages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
