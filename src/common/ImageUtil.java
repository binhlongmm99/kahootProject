package common;

import java.io.IOException;
import java.io.InputStream;
 
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
 
public class ImageUtil {
 
    //Get image
    public static Image getImage(Display display, String resourcePath) {
        InputStream input = null;
        try {
            input = ImageUtil.class.getResourceAsStream(resourcePath);
            Image image = new Image(display, input);
            return image;
        } finally {
            closeQuietly(input);
        }
    }
 
    private static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
        }
    }
    
}
