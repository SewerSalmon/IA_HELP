package company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Cheats extends JPopupMenu implements ActionListener {


    public Cheats (){

        JMenuItem spawnTeleport = new JMenuItem("Spawn teleport");
        add(spawnTeleport);

        JMenuItem addGold = new JMenuItem("Infinite gold");
        add(addGold);

        JMenuItem editspells = new JMenuItem("Infinite spells");
        add(editspells);

        spawnTeleport.addActionListener(this);
        addGold.addActionListener(this);
        editspells.addActionListener(this);
    }

    public void visible(){
        this.setVisible(true);
    }
    public void invisible(){
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem clickedMenuItem = (JMenuItem) source;
            String menuText = clickedMenuItem.getText();
            if(menuText.equals("Spawn teleport")){
                String allStrings = "";
                   try{
                       Path path = Paths.get("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml");
                       List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                       lines.set(8 - 1, "    position.x=\"227\" " );
                       Files.write(path, lines, StandardCharsets.UTF_8);
                       lines.set(9 - 1, " position.y=\"-83\" " );
                       Files.write(path, lines, StandardCharsets.UTF_8);
                   }catch (Exception e) {
                       throw new RuntimeException(e);
                   }
                JOptionPane.showMessageDialog(null, "The character in your save has been teleported");

            }
            else if(menuText.equals("Infinite spells")){
                String allStrings = "";
                try{
                    Path path = Paths.get("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/world_state.xml");
                    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    lines.set(47 - 1, "    perk_infinite_spells=\"1\" " );
                    Files.write(path, lines, StandardCharsets.UTF_8);

                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "You now have infinite spells in your save");
            }
            else if(menuText.equals("Infinite gold")){
                File fileToBeModified = new File("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml");

                int changeline = -1;
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));

                    String line = reader.readLine();
                    int linecount = 0;
                    while (line != null)
                    {
                        if (line.contains("mHasReachedInf=")){
                            changeline = linecount;
                        }
                        line = reader.readLine();
                        linecount++;
                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

               try {
                    Path path = Paths.get("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml");
                    List<String> lines = null;
                    lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    lines.set(changeline, "    mHasReachedInf=\"1\" " );
                    Files.write(path, lines, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "Your now have infinite gold in your save");
            }
        }
    }

}
