public class ControlGroup {
    Model model;
    Vue vue;
    ControlButton controlButton;
    ControlMenu controlMenu;

    /**
     * Constructeur du regroupeur des controlleurs
     * @param model Model
     * @param vue Vue
     */
    public ControlGroup(Model model, Vue vue){
        this.model = model;
        this.vue = vue;
        this.controlButton = new ControlButton(model,this.vue);
        this.controlMenu = new ControlMenu(model,this.vue);

        vue.display();
    }
}
