import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Classe Model du jeu Memory
 * @author NGETH Laurent
 */
public class Model {
    private ImageIcon backImage;
    private ImageIcon[] listImage;
    private int gameSize;
    private ImageButton[][] gridButton;
    private int nbTryLeft;
    private boolean sessionState;
    private ArrayList<Float> bestScores;

    /**
     * Generate a default game with low difficulty 3x3
     */
    public Model(){
        this.gameSize = 3;
        this.initListImage();
        this.setBackImage();
        this.initGridButton();
        this.initBestScores();
        this.nbTryLeft = 3;
        this.sessionState = false;
    }

    /**
     * Generate a game Model with n x n grid
     * @param n int, the number of line and column
     */
    public Model(int n){
        this.setGameConfig(n);
        this.initListImage();
        this.setBackImage();
        this.initGridButton();
        this.initBestScores();
        this.sessionState = false;
    }

    /**
     * @return un tableau 2D d'ImageButton
     */
    public ImageButton[][] getGridButton() {
        return gridButton;
    }

    /**
     * @param gridButton tableau 2D d'ImageButton
     */
    public void setGridButton(ImageButton[][] gridButton) {
        this.gridButton = gridButton;
    }

    /**
     * @return retourne l'ImageIcon du dos de l'image du JButton
     */
    public ImageIcon getBackImage() {
        return backImage;
    }

    /**
     * Initialise l'image pour le dos du JButton
     */
    public void setBackImage() {
        ImageIcon temp = new ImageIcon(Objects.requireNonNull(getClass().getResource("Art/Icon/question.png")));
        Image prov = temp.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        this.backImage = new ImageIcon(prov);
    }

    /**
     * @return la taille du jeu, c'est à dire de l'abscisse ou ordonnée de la grille 2D
     */
    public int getGameSize() {
        return gameSize;
    }


    /**
     * Fixe la taille du jeu
     * @param gameSize la taille du jeu
     */
    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    /**
     * @return un boolean, vrai si le jeu est en marche, faux sinon
     */
    public boolean isSessionState() {
        return sessionState;
    }

    /**
     * Mets le jeu en marche ou en arrêt
     * @param sessionState vrai si en marche, faux sinon
     */
    public void setSessionState(boolean sessionState) {
        this.sessionState = sessionState;
    }

    /**
     * @return le nombre de tentative restante
     */
    public int getNbTryLeft() {
        return nbTryLeft;
    }

    /**
     * @param nbTryLeft le nombre de tentative pour le jeu
     */
    public void setNbTryLeft(int nbTryLeft) {
        this.nbTryLeft = nbTryLeft;
    }

    /**
     * @return la liste des meilleurs temps de jeu
     */
    public ArrayList<Float> getBestScores() {
        return bestScores;
    }

    /**
     * @param bestScores fixe une liste de score
     */
    public void setBestScores(ArrayList<Float> bestScores) {
        this.bestScores = bestScores;
    }

    /**
     * @return la liste d'ImageIcon
     */
    public ImageIcon[] getListImage() {
        return listImage;
    }

    /**
     * @param listImage la liste d'ImageIcon
     */
    public void setListImage(ImageIcon[] listImage) {
        this.listImage = listImage;
    }

    /**
     * Initialise le tableau d'image du jeu à partir du dossier d'image
     */
    public void initListImage(){
        this.listImage = new ImageIcon[13];
        try{
            File folder = new File("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Art/ImageButton");
            int cpt = 0;
            Image prov;
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())){
                this.listImage[cpt] = new ImageIcon(folder.getPath()+"/"+fileEntry.getName());
                prov = this.listImage[cpt].getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
                this.listImage[cpt] = new ImageIcon(prov);
                cpt++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise de manière aléatoire la grille 2D d'ImageButton
     */
    public void initGridButton(){
        this.gridButton = new ImageButton[this.gameSize][this.gameSize];
        ArrayList<ImageIcon> listToShuffle = new ArrayList<>();
        for (int i = 0; i < Math.floor(this.gameSize*this.gameSize/2) ; i++){
            listToShuffle.add(this.listImage[i]);
            listToShuffle.add(this.listImage[i]);
        }
        if (this.gameSize == 5 || this.gameSize == 3)
            listToShuffle.add(this.listImage[12]);
        int count = 0;
        Collections.shuffle(listToShuffle);
        for (int j = 0; j < this.gameSize; j++){
            for (int k = 0; k < this.gameSize; k++) {
                this.gridButton[j][k] = new ImageButton(listToShuffle.get(count),this.backImage);
                if (listToShuffle.get(count) == this.listImage[12])
                    this.gridButton[j][k].setImageFoundState(true);
                count++;
            }
        }
    }

    /**
     * Fixe la taille du jeu et le nombre de tentative en fonction de la taille du jeu
     * @param n la taille du jeu
     */
    public void setGameConfig(int n){
        this.gameSize = n;
        switch (n) {
            case 3 -> this.nbTryLeft = 3;
            case 4 -> this.nbTryLeft = 8;
            case 5 -> this.nbTryLeft = 12;
        }
    }

    /**
     * Vérifie si les images ont été trouvées
     * @return true si toutes les images ont été trouvés, faux sinon
     */
    public boolean isWinning(){
        for (int i = 0; i < this.gridButton.length ; i++)
            for (int j = 0; j < this.gridButton[i].length; j++)
                if (!this.gridButton[i][j].isImageFoundState())
                    return false;
        return true;
    }

    /**
     * Fixe tous les ImageButton en état false (pas trouvé)
     */
    public void hasLost() {
        for (int i = 0; i < this.gridButton.length; i++)
            for (int j = 0; j < this.gridButton[i].length; j++)
                this.gridButton[i][j].setImageFoundState(false);
    }

    /**
     * Initialise la liste des scores en fonction de la taille du jeu
     * Prend 3 fichiers data différents en fonction de la difficulté du jeu
     */
    public void initBestScores(){
        this.bestScores = new ArrayList<>();
        int cpt;
        String s = null;
        try {
            cpt=0;
            BufferedReader br;
            if (this.gameSize == 3){
                br = new BufferedReader(new FileReader("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresEasy.txt"));
            } else if (this.gameSize == 4){
                br = new BufferedReader(new FileReader("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresMedium.txt"));
            } else {
                br = new BufferedReader(new FileReader("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresHard.txt"));
            }
            while ((s = br.readLine()) != null && s != ""){
                this.bestScores.add(Float.parseFloat(s));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(this.bestScores);
    }

    /**
     * Sauvegarde le score dans le fichier des scores sous certaines conditions
     * @param t le temps en Float du score réalisé en gagnant
     */
    public void saveBestScores(Float t) {
        boolean changeData = false;
        if (this.bestScores.size() == 3){
            for (int i = 0; i<this.bestScores.size(); i++){
                if (t < this.bestScores.get(i)){
                    this.bestScores.add(i, t);
                    this.bestScores.remove(3);
                    changeData = true;
                    break;
                }
            }
        } else {
            this.bestScores.add(t);
            Collections.sort(this.bestScores);
            changeData = true;
        }
        if (changeData){
            try {
                BufferedWriter bw;
                if (this.gameSize == 3){
                    bw = new BufferedWriter(new FileWriter("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresEasy.txt"));
                } else if (this.gameSize == 4){
                    bw = new BufferedWriter(new FileWriter("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresMedium.txt"));
                } else {
                    bw = new BufferedWriter(new FileWriter("/home/lngeth/Documents/Laurent/web/MemoryGame/src/Data/ScoresHard.txt"));
                }
                for (int j = 0; j < this.bestScores.size(); j++){
                    bw.write("" +this.bestScores.get(j));
                    bw.newLine();
                }
                bw.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(this.bestScores.toString());
    }
}