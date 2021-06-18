public class Appli {
    public static void main(String[] args) {
        Model model = new Model();
        Vue vue = new Vue(model);
        ControlGroup control = new ControlGroup(model, vue);
    }
}
