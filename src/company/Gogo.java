package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Gogo extends JPanel{
    JLabel label= new JLabel();
    JFrame f = new JFrame("change images");
    File chosenFile= new File("a");
    File[] allfiles =new File("Map").listFiles();

    public Gogo(){

        f.setSize(1920, 1080);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.add();
        f.setVisible(true);
        this.setLayout(null);
        f.add(this);
        label.setVisible(true);
        label.setBounds(700,550,1000,50);
        this.add(label);
        JButton button1;
        button1 = new JButton("next");
        button1.setBounds(400,0,150,50);
        button1.addActionListener(new ClickButton());
        this.add(button1);
        button1 = new JButton("next+");
        button1.setBounds(100,0,150,50);
        button1.addActionListener(new ClickButton());
        this.add(button1);
        button1 = new JButton("back+");
        button1.setBounds(250,0,150,50);
        button1.addActionListener(new ClickButton());
        this.add(button1);
        button1 = new JButton("back");
        button1.setBounds(550,0,150,50);
        button1.addActionListener(new ClickButton());
        this.add(button1);

        int yval = 50;
        int xval = 0;

        try {
            FileReader read = new FileReader("ID.txt");
            Scanner scan = new Scanner(read);
            while (scan.hasNextLine()) {
                buttonAdd(scan.nextLine(),xval,yval,200,50);
                yval = yval+50;
                if(yval==850){
                    yval = 50;
                    xval = xval+200;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

repaint();
    }
    public void buttonAdd(String name, int x, int y, int width, int height) {
        JButton button;
        button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(new ClickButton());
        add(button);
    }


    ImageIcon image;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!(image == null)) {
            image.paintIcon(this, g, (int) 700, (int) 50);
        }

    }
int intel =-1;
    private class ClickButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getActionCommand().equals("next")){
                intel++;
                    chosenFile = allfiles[intel];
               label.setText(chosenFile.getName());
               image = new ImageIcon(chosenFile.getPath());  label.setText(chosenFile.getName());
               repaint();
           }else if(e.getActionCommand().equals("back")){
               intel--;
               chosenFile = allfiles[intel];
               label.setText(chosenFile.getName());
               image = new ImageIcon(chosenFile.getPath());  label.setText(chosenFile.getName());
               repaint();
           }else if(e.getActionCommand().equals("next+")){
                intel = intel+58;
                chosenFile = allfiles[intel];
                label.setText(chosenFile.getName());
                image = new ImageIcon(chosenFile.getPath());  label.setText(chosenFile.getName());
                repaint();
           }else if(e.getActionCommand().equals("back+")){
                intel = intel-58;
                chosenFile = allfiles[intel];
                label.setText(chosenFile.getName());
                image = new ImageIcon(chosenFile.getPath());  label.setText(chosenFile.getName());
                repaint();
           }else {
               chosenFile.renameTo(new File("Map/" + chosenFile.getName().split("\\.")[0]+"."+chosenFile.getName().split("\\.")[1]+"."+e.getActionCommand()+"."+chosenFile.getName().split("\\.")[3]));
               allfiles =new File("Map").listFiles();
               chosenFile = allfiles[intel];
               label.setText(chosenFile.getName()); repaint();
           }

           }
    }

}
