import javax.swing.*;
import java.text.DecimalFormat;

public class Chrono extends Thread {
    JLabel temps;
    boolean demarre;
    private float t;

    Chrono(JLabel temps ){
        this.temps=temps;
    }

    public void run(){
        demarre=true;
        this.t=0;

        while (demarre) {
            try{
                this.sleep(100);
                t+=0.1;
                //System.out.println(t);
                DecimalFormat df = new DecimalFormat("#######0.0");
                String str = df.format(t);
                temps.setText("Time : "+str);
            }
            catch(InterruptedException e){

            }
        }
    }

    public void terminate(){
        demarre=false;
    }

    public float getT() {
        return t;
    }
}