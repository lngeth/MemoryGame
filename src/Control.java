public class Control {
    Model model;
    Vue vue;

    /**
     * Constructeur du Controlleur
     * @param model Model
     * @param vue Vue
     */
    public Control(Model model, Vue vue){
        this.model = model;
        this.vue = vue;
    }
}