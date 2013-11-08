package kr.co.n3soft.n3com.net;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {
    Image image;
    Dimension dim;    

    public ImagePanel(Image image, Dimension dim)
    {    	
        this.image = image;
        this.dim = dim;
//System.out.println(dim.wi1dth);
//System.out.println(dim.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background

        //Draw image at its natural size first.
        //g.drawImage(image, 0, 0, this); //85x62 image

        //Now draw the image scaled.        
        g.drawImage(image, 0,0, dim.width, dim.height, this);
    }
}

