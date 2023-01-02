package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class SelectSave extends JPanel {
    private final JFrame frame = new JFrame("Choose");

    private String ClassicPath = "C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml"; // save place that noitta makes
    private String CollectivePath = System.getProperty("user.home")+"/NoitaHelp/OverallSave.txt";
    private File ClassicSave = new File(ClassicPath);
    private File CollectiveSave = new File(CollectivePath);

    public SelectSave()  {
        setSize(200,300);
        frame.setSize(200, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        this.setLayout(null);

        try {
            fileCreation();
        }catch (IOException e){}

        buttonCreate();
    }

    private void fileCreation() throws IOException {
        if(!((new File(System.getProperty("user.home")+"/NoitaHelp")).exists())){
            Files.createDirectories(Paths.get(System.getProperty("user.home")+"/NoitaHelp"));
        }
        if((new File("Map")).listFiles().length!=6381){
            System.out.println("error when downloading please reistall");
        }


    }

    private void buttonCreate(){
        if(ClassicSave.exists()) {
            JButton button1;
            button1 = new JButton("Current Save");
            button1.setBounds(17,10,150,50);
            button1.addActionListener(new ClickButton());
            add(button1);
        }

        if(CollectiveSave.exists()) {
            //read
            JButton button2;
            button2 = new JButton("Overall Save");
            button2.setBounds(17, 70, 150, 50);
            button2.addActionListener(new ClickButton());
            add(button2);
        }else {
            try{
                CollectiveSave.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            JButton button2;
            button2 = new JButton("Overall Save");
            button2.setBounds(17, 70, 150, 50);
            button2.addActionListener(new ClickButton());
            add(button2);
        }


        JButton button3;
        button3 = new JButton("Choose Save");
        button3.setBounds(17,130,150,50);
        button3.addActionListener(new ClickButton());
        add(button3);

        frame.setVisible(true);
    }

    private class ClickButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Current Save")){
                MyFrame mf = new MyFrame(ClassicSave);
                frame.dispose();
            }else if (e.getActionCommand().equals("Overall Save")){
                MyFrame mf = new MyFrame(CollectiveSave);
                frame.dispose();
            }else {// need toi sor out below
                JFrame ff = new JFrame();
               ff.setSize(120, 50);
               ff.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
               ff.setLayout(null);
                File selectedFile = null;
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(ff);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
                assert selectedFile != null;
                if(selectedFile.getName().endsWith(".txt")){
                    MyFrame mf = new MyFrame(selectedFile);
                    frame.dispose();
                }else {

                    JLabel l = new JLabel("That file is not allowed");
             l.setBounds(0,0,200,10);
                    ff.add(l);
                    ff.setVisible(true);
                }

            }

        }
    }


}
