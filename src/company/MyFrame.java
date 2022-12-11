package company;
import javax.swing.*;
import java.io.File;

public class MyFrame extends JFrame {
    BiomeDisplay BD;
    GUI gui;

    MyFrame(File saveFile) {
        this.setTitle(saveFile.getName());
       new DiscoverBiomes(saveFile);
        gui = new GUI(this);
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gui);
        this.setVisible(true);
    }

    public void toMap(){
        BD.setVisible(false);
        gui.setVisible(true);
    }
    public void toBiome(String biome){
        gui.setVisible(false);
        BD = new BiomeDisplay(this,biome);
        add(BD);
        BD.setVisible(true);
    }

}