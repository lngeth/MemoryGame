import javax.swing.*;
import java.util.Objects;

public class ImageButton {
    private boolean imageFoundState;
    private ImageIcon frontIcon;
    private ImageIcon backIcon;

    /**
     * Constructeur de ImageButton
     * @param front ImageIcon de la face caché du bouton
     * @param back ImageIcon de la face visible du bouton
     */
    public ImageButton(ImageIcon front, ImageIcon back){
        this.imageFoundState = false;
        this.frontIcon = front;
        this.backIcon = back;
    }

    /**
     * @return l'image cachée du bouton
     */
    public ImageIcon getFrontIcon() {
        return frontIcon;
    }

    /**
     * @param frontIcon l'image cachée du bouton
     */
    public void setFrontIcon(ImageIcon frontIcon) {
        this.frontIcon = frontIcon;
    }

    /**
     * @return l'image visible du bouton
     */
    public ImageIcon getBackIcon() {
        return backIcon;
    }

    /**
     * @param backIcon l'image visible du bouton
     */
    public void setBackIcon(ImageIcon backIcon) {
        this.backIcon = backIcon;
    }

    /**
     * @return true si l'image du bouton est trouvée, faux sinon
     */
    public boolean isImageFoundState() {
        return imageFoundState;
    }

    /**
     * @param imageFoundState true si l'image du bouton est trouvée, faux sinon
     */
    public void setImageFoundState(boolean imageFoundState) {
        this.imageFoundState = imageFoundState;
    }


}