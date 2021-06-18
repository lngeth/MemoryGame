import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Classe Vue de l'application Memory
 * @author NGETH Laurent
 */
public class Vue extends JFrame{
    Model model;
    public JMenuItem newGame;
    public JMenuItem bestScores;
    public JMenuItem difficultyLow;
    public JMenuItem difficultyMedium;
    public JMenuItem difficultyHigh;
    public JButton[][] button;
    public JButton okButtonWinWindow;
    public JButton okButtonLooseWindow;
    public JButton tryAgain;
    public JLabel timeTook;
    public JLabel tryLeft;
    public JLabel lbScores;
    public JLabel[] score;
    public JDialog scoreWindow;
    public JDialog looserWindow;
    public Chrono c;

    /**
     * Constructeur de la Vue du jeu Memory
     * @param model contient les données du Memory
     */
    public Vue(Model model){
        this.model = model;
        initAttribut();
        initBestScores();
        createWidget();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialise les boutons de jeu avec des ActionListeners spécifique à ControlButton et fournit une ActionCommand
     * @param controlButton instance de ControlButton
     */
    public void setControlButton(ControlButton controlButton){
        if (button[0][0].getActionListeners().length < 1){
            for (int i = 0; i < this.button.length; i++)
                for (int j = 0; j < this.button[0].length; j++){
                    this.button[i][j].addActionListener(controlButton);
                    this.button[i][j].setActionCommand(i+""+j);
                }
        }
    }

    /**
     * Initialise les boutons de menu avec des ActionListener spécifique à ControlMenu et fournit une ActionCommand
     * @param controlMenu instance de ControlMenu
     */
    public void setControlMenu(ControlMenu controlMenu){
        this.newGame.addActionListener(controlMenu);
        this.newGame.setActionCommand("newGame");
        this.bestScores.addActionListener(controlMenu);
        this.bestScores.setActionCommand("bestScores");
        this.okButtonWinWindow.addActionListener(controlMenu);
        this.okButtonWinWindow.setActionCommand("okButtonWinWindow");
        this.okButtonLooseWindow.addActionListener(controlMenu);
        this.okButtonLooseWindow.setActionCommand("okButtonLooseWindow");
        this.tryAgain.addActionListener(controlMenu);
        this.tryAgain.setActionCommand("tryAgain");
        this.difficultyLow.addActionListener(controlMenu);
        this.difficultyLow.setActionCommand("difficultyLow");
        this.difficultyMedium.addActionListener(controlMenu);
        this.difficultyMedium.setActionCommand("difficultyMedium");
        this.difficultyHigh.addActionListener(controlMenu);
        this.difficultyHigh.setActionCommand("difficultyHigh");
    }

    /**
     * affiche la vue
     */
    public void display(){
        setVisible(true);
    }

    /**
     * rend la vue non visible
     */
    public void undisplay(){
        setVisible(false);
    }

    /**
     * Permet de connaitre la position d'un bouton dans la grille 2D à partir de son ActionCommand
     * @param actionCommand un String de la position du JButton
     * @return un tableau d'int avec les coordonnées x et y du JButton, si pas de JButton retourne null
     */
    public int[] posButton(String actionCommand){
        for (int i = 0; i < this.button.length; i++)
            for (int j = 0; j < this.button[0].length; j++){
                if (actionCommand.equals(this.button[i][j].getActionCommand()))
                    return new int[]{i,j};
            }
        return null;
    }

    /**
     * Initialise les attributs de classe (composants) de la Vue
     */
    public void initAttribut(){
        this.scoreWindow = new JDialog(this, "Best Scores");
        this.scoreWindow.setSize(300,200);
        this.scoreWindow.setLocationRelativeTo(null);
        this.scoreWindow.setResizable(false);
        this.scoreWindow.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        this.looserWindow = new JDialog(this, "You loose!");
        this.looserWindow.setSize(300,200);
        this.looserWindow.setLocationRelativeTo(null);
        this.looserWindow.setResizable(false);
        this.looserWindow.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        //init button
        this.button = new JButton[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                this.button[i][j] = new JButton();
                this.button[i][j].setPreferredSize(new Dimension(100,100));
            }
        }
        this.newGame = new JMenuItem("Nouvelle partie");
        this.bestScores = new JMenuItem("Meilleurs scores");
        this.difficultyLow = new JMenuItem("Easy 3x3");
        this.difficultyMedium = new JMenuItem("Medium 4x4");
        this.difficultyHigh = new JMenuItem("Hard 5x5");
        this.okButtonWinWindow = new JButton("OK");
        this.okButtonLooseWindow = new JButton("OK");
        this.tryAgain = new JButton("Try again");

        //init image Button
        for (int i = 0; i < this.model.getGameSize(); i++){
            for (int j = 0; j < this.model.getGameSize(); j++){
                this.button[i][j].setDisabledIcon(this.model.getGridButton()[i][j].getFrontIcon());
                this.button[i][j].setIcon(this.model.getGridButton()[i][j].getBackIcon());
            }
        }

        this.lbScores = new JLabel("Best scores :");
        this.timeTook = new JLabel("Time : ");
        this.tryLeft = new JLabel("Try left : "+this.model.getNbTryLeft());
        c = new Chrono(this.timeTook);
    }

    /**
     * Initialise le JLabel des scores à partir du model
     */
    public void initBestScores(){
        score = new JLabel[model.getBestScores().size()];
        for (int i = 0; i < model.getBestScores().size(); i++){
            score[i] = new JLabel(i+1+"- "+this.model.getBestScores().get(i));
        }
    }

    /**
     * Réinitialise la vue à partir du nouveau Model
     * @param model le nouveau Model
     */
    public void resetGame(Model model){
        this.model = model;
        this.getContentPane().removeAll();
        this.scoreWindow.getContentPane().removeAll();

        for (int i = 0; i < this.model.getGameSize(); i++){
            for (int j = 0; j < this.model.getGameSize(); j++){
                hideImage(this.button[i][j]);
                this.button[i][j].setDisabledIcon(this.model.getGridButton()[i][j].getFrontIcon());
                this.button[i][j].setIcon(this.model.getGridButton()[i][j].getBackIcon());
                this.button[i][j].setPreferredSize(new Dimension(100,100));
            }
        }
        this.initBestScores();
        this.timeTook = new JLabel("Time : ");
        this.tryLeft = new JLabel("Try left : "+this.model.getNbTryLeft());
        this.c = new Chrono(this.timeTook);

        this.createWidget();
        this.scoreWindow.revalidate();
        this.scoreWindow.repaint();
        this.revalidate();
        this.repaint();
        this.pack();
    }

    /**
     * Rend le JButton non cliquable et affiche l'icon de l'autre coté
     * @param button un JButton
     */
    public void showImage(JButton button){
        button.setEnabled(false);
    }

    /**
     * Rend le JButton cliquable et remet l'icon de dos
     * @param button un JButton
     */
    public void hideImage(JButton button){
        button.setEnabled(true);
    }

    /**
     * Arrète le chrono, affiche toutes les images des JButton, les rends non cliquables et affiche le JDialog de défaite
     */
    public void loose() {
        this.c.terminate();
        for (int i = 0; i < this.model.getGameSize(); i++){
            for (int j = 0; j< this.model.getGameSize(); j++){
                showImage(this.button[i][j]);
            }
        }
        this.model.hasLost();
        this.model.setSessionState(false);
        this.looserWindow.setVisible(true);
    }

    /**
     * Arrete le chrono, enregistre le score, affiche le JDialog de victoire
     */
    public void win(){
        this.c.terminate();
        //afficher les best scores
        this.model.saveBestScores(c.getT());
        this.initBestScores();
        /*for (int i = 0; i < this.score.length; i++){
            this.score[i].setText(i+1 +"- "+model.getBestScores().get(i));
        }*/
        this.scoreWindow.getContentPane().removeAll();
        createWidget();
        this.scoreWindow.setVisible(true);
    }

    /**
     * Creer les composants de la Vue
     */
    public void createWidget(){
        // Menu bar
        JMenu optionMenu = new JMenu("Option");
        optionMenu.add(this.newGame);
        optionMenu.add(this.bestScores);

        JMenu difficultyMenu = new JMenu("Difficulty");
        difficultyMenu.add(this.difficultyLow);
        difficultyMenu.add(this.difficultyMedium);
        difficultyMenu.add(this.difficultyHigh);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(optionMenu);
        menuBar.add(difficultyMenu);

        //Win Dialog Window
        JLabel lbPodium = new JLabel();
        createLabelImageIcon(lbPodium,"Art/Icon/podium.png");

        JPanel leftPodiumIcon = new JPanel();
        leftPodiumIcon.add(lbPodium);

        JPanel scoresPan = new JPanel();
        scoresPan.setLayout(new BoxLayout(scoresPan, BoxLayout.Y_AXIS));
        scoresPan.add(this.lbScores); //add score to scores Panel
        for (JLabel label : score) {
            scoresPan.add(label);
        }

        JPanel mainPanWinDialog = new JPanel();
        mainPanWinDialog.add(leftPodiumIcon);
        mainPanWinDialog.add(scoresPan);
        mainPanWinDialog.add(this.okButtonWinWindow);

        //Lost Dialog Window
        JLabel labSadImage = new JLabel();
        createLabelImageIcon(labSadImage,"Art/Icon/sad.png");

        JPanel sadImagePanel = new JPanel();
        sadImagePanel.add(labSadImage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.tryAgain);
        buttonPanel.add(this.okButtonLooseWindow);

        JPanel mainPanLostDialog = new JPanel();
        mainPanLostDialog.setLayout(new BoxLayout(mainPanLostDialog, BoxLayout.Y_AXIS));
        mainPanLostDialog.add(sadImagePanel);
        mainPanLostDialog.add(buttonPanel);

        this.scoreWindow.add(mainPanWinDialog);
        this.looserWindow.add(mainPanLostDialog);

        JPanel timePan = new JPanel();
        timePan.add(this.timeTook);

        JPanel tryLeftPan = new JPanel();
        tryLeftPan.add(this.tryLeft);

        GridLayout gridLayout = new GridLayout(this.model.getGameSize(), this.model.getGameSize());
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);
        JPanel gamePan = new JPanel(gridLayout);
        for (int i = 0; i < this.model.getGameSize(); i++)
            for (int j = 0; j < this.model.getGameSize(); j++)
                gamePan.add(this.button[i][j]);

        JPanel generalPan = new JPanel();
        generalPan.setLayout(new BoxLayout(generalPan, BoxLayout.Y_AXIS));
        generalPan.add(timePan);
        generalPan.add(gamePan);
        generalPan.add(tryLeftPan);
        setJMenuBar(menuBar);
        setContentPane(generalPan);

        /*
        for (int i= 0; i < this.button.length; i++){
            for (int j = 0; j < this.button[i].length; j++){
                System.out.println(this.button[i][j].getDisabledIcon());
            }
        }*/
    }

    /**
     * Initialise les images permanentes de la Vue non dépendante du model
     * @param label JLabel
     * @param path un String du chemin de l'image
     */
    public void createLabelImageIcon(JLabel label, String path){
        ImageIcon prov = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        Image image = prov.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        label.setIcon(new ImageIcon(image));
    }
}
