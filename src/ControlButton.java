import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe Controller
 * @author NGETH Laurent
 */
public class ControlButton extends Control implements ActionListener {
    public JButton[] button = new JButton[2];

    /**
     * Constructeur du controlleur des JButtons
     * @param model Model
     * @param vue Vue
     */
    public ControlButton(Model model, Vue vue) {
        super(model, vue);
        super.vue.setControlButton(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!super.vue.model.isSessionState()){
            super.vue.model.setSessionState(true);
            super.vue.c.start();
        }
        if (this.button[0] == null){
            this.button[0] = (JButton) e.getSource();
            super.vue.showImage(this.button[0]);
        } else if (this.button[1] == null){
            this.button[1] = (JButton) e.getSource();
            super.vue.showImage(this.button[1]);
            Timer timer;
            int delay = 700;
            if (this.button[0].getDisabledIcon() == this.button[1].getDisabledIcon()){
                ActionListener taskRight = e12 -> {
                    ControlButton.super.vue.model.getGridButton()[vue.posButton(button[0].getActionCommand())[0]][vue.posButton(button[0].getActionCommand())[1]].setImageFoundState(true);
                    ControlButton.super.vue.model.getGridButton()[vue.posButton(button[1].getActionCommand())[0]][vue.posButton(button[1].getActionCommand())[1]].setImageFoundState(true);
                    ControlButton.this.button[0] = null;
                    ControlButton.this.button[1] = null;
                    if (ControlButton.super.vue.model.isWinning()){
                        ControlButton.super.vue.win();
                    }
                };
                timer = new Timer(delay, taskRight);
                animationRightOrWrong(true);
            } else {
                ActionListener taskWrong = e1 -> {
                    ControlButton.super.vue.model.setNbTryLeft(ControlButton.super.vue.model.getNbTryLeft()-1);
                    ControlButton.super.vue.tryLeft.setText("Try left : "+ ControlButton.super.vue.model.getNbTryLeft());
                    ControlButton.super.vue.hideImage(button[0]);
                    ControlButton.super.vue.hideImage(button[1]);
                    ControlButton.this.button[0] = null;
                    ControlButton.this.button[1] = null;
                    if (ControlButton.super.vue.model.getNbTryLeft() == 0){
                        ControlButton.super.vue.loose();
                    }
                };
                timer = new Timer(delay, taskWrong);
                animationRightOrWrong(false);
            }
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Affiche une animation de clignotement des 2 boutons sélectionnés
     * @param trueOrFalse si vrai mets le background des 2 boutons séléctionnés en Vert, si faux les mets en rouge
     */
    public void animationRightOrWrong(boolean trueOrFalse){
        Color color;
        if (trueOrFalse)
            color = Color.GREEN;
        else
            color = Color.RED;
        new Thread(() -> {
            try {
                Thread.sleep(200);
                ControlButton.this.button[0].setBackground(color);
                ControlButton.this.button[1].setBackground(color);
                Thread.sleep(150);
                ControlButton.this.button[0].setBackground(null);
                ControlButton.this.button[1].setBackground(null);
                Thread.sleep(150);
                ControlButton.this.button[0].setBackground(color);
                ControlButton.this.button[1].setBackground(color);
                Thread.sleep(150);
                ControlButton.this.button[0].setBackground(null);
                ControlButton.this.button[1].setBackground(null);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }).start();
    }
}
