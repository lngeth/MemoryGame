import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu extends Control implements ActionListener {
    /**
     * Constructeur du controleur des boutons Menu
     * @param model Model
     * @param vue Vue
     */
    public ControlMenu(Model model, Vue vue){
        super(model, vue);
        super.vue.setControlMenu(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int gameSize = super.model.getGameSize();
        switch (e.getActionCommand()) {
            case "newGame" -> {
                gameSize = super.model.getGameSize();
                super.model = new Model(gameSize);
                super.vue.resetGame(super.model);
            }
            case "tryAgain" -> {
                gameSize = super.model.getGameSize();
                super.model = new Model(gameSize);
                super.vue.resetGame(super.model);
                super.vue.looserWindow.dispose();
            }
            case "bestScores" -> super.vue.scoreWindow.setVisible(true);
            case "okButtonWinWindow" -> super.vue.scoreWindow.dispose();
            case "okButtonLooseWindow" -> super.vue.looserWindow.dispose();
            case "difficultyLow" -> {
                super.model = new Model(3);
                super.vue.resetGame(super.model);
            }
            case "difficultyMedium" -> {
                super.model = new Model(4);
                super.vue.resetGame(super.model);
            }
            case "difficultyHigh" -> {
                super.model = new Model(5);
                super.vue.resetGame(super.model);
            }
        }
    }
}
