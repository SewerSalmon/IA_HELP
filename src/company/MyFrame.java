package company;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MyFrame extends JFrame {
    BiomeDisplay BD;
    GUI gui;

    MyFrame(File saveFile) {

        this.setTitle(saveFile.getName());
        new DiscoverBiomes(saveFile);
        gui = new GUI(this,saveFile);
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),  (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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