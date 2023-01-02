package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GUI extends JPanel {
    String[] mapSquares;
    String currentSquare;
    MyFrame change;
    JProgressBar progressBar;
    Boolean UnlockedAll = false;
    JButton MAP;
    Cheats ch = new Cheats();

    public GUI(MyFrame a, File save) {


        this.setBackground(Color.WHITE);
        change = a;
        this.setLayout(null);

        if(save == new File("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml")){
            buttonAdd("Cheats",350 , 0, 150, 50);
            this.add(ch);
        }
       MAP = buttonAdd("Unlock entire Map", 0, 0, 150, 50);

        File[] folder = new File("Map Squares/").listFiles();
        mapSquares = new String[folder.length];
        int counter = 0;
        int start = 0;
        for (File file : folder) {
            mapSquares[counter] = file.getName();
            if(file.getName().equals("41.29.$biome_forest.jpg")){
                start=counter;
            }
            counter++;
        }



        currentSquare = mapSquares[start];
        DragPanel("Map Squares/" + currentSquare, new Point(0, 0));
        imageCorner.setLocation(prevPt);
        repaint();
        Progress();
    }

    public void Progress(){
        JTextArea a = new JTextArea("  Map unlock progress (no cheats)");
        a.setBounds(150,0,200,30);
        a.setEditable(false);
        this.add(a);
        a.setVisible(true);

        progressBar = new JProgressBar(0,100);
        progressBar.setBounds(150,30,200,20);
        int numerator = 0;
        int denominator = 0;
        for (String s : mapSquares){
            if (s.contains("empty")||s.contains("$biome_forest")){
            } else if (s.contains("NULL")){
                numerator++;
                denominator++;
            }else {denominator++;}
        }

        int c =  100 - (int) (100 * ((double) numerator/denominator));
         progressBar.setValue(c);
        this.add(progressBar);
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
    }

    public void invisible(){
        setVisible(false);
    }
    public void visible(){
        setVisible(true);
    }

    public JButton buttonAdd(String name, int x, int y, int width, int height) {
        JButton button;
        button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(new ClickButton());
        add(button);
        return button;
    }

    boolean cheatsV = false;
    private class ClickButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Unlock entire Map")){
               MAP.setVisible(false);

               JFrame wait = new JFrame("please wait");
               wait.setVisible(true);
               wait.setBounds(500,500,500,0);

                invisible();


                mapUnlock(wait);
            }
            if(e.getActionCommand().equals("Cheats")){
                cheatsV = !cheatsV;
                ch.setVisible(cheatsV);
                ch.setLocation(350,115);
            }
        }
    }

    public void mapUnlock(JFrame w){
        File dir = new File("Map Squares");
                File[] contents = dir.listFiles();
                if (contents != null) {
                    for (File f : contents) {
                        try {
                            Files.delete(Path.of(f.getPath()));
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                    }
                }

                dir.mkdirs();


                File[] folder = new File("Map").listFiles();

                for (File file : folder) {
                    String name = file.getName();
                    if (Character.isDigit(name.charAt(0))) {
                        try {
                            BufferedImage bi = ImageIO.read(file);
                            ImageIO.write(bi, "jpg", new File("Map Squares/"+name));

                        }catch (IOException h){
                            h.printStackTrace();
                        }
                    }
                }


                folder = new File("Map Squares/").listFiles();
                mapSquares = new String[folder.length];
                int counter = 0;
                int start = 0;
                for (File file : folder) {
                    mapSquares[counter] = file.getName();
                    if(file.getName().equals("41.29.$biome_forest.jpg")){
                        start=counter;
                    }
                    counter++;
                }

                repaint();
                w.dispose();
                visible();
    }


    ImageIcon image;
    int WIDTH;
    int HEIGHT;
    Point imageCorner;
    Point prevPt = new Point(0, 0);

    public void DragPanel(String FileLocation, Point point) {
        image = new ImageIcon(FileLocation);
        WIDTH = image.getIconWidth();
        HEIGHT = image.getIconHeight();
        imageCorner = point;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    Point imagesPositive;
    Point imagesNegative;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagesPositive = new Point(0, 0);
        imagesNegative = new Point(0, 0);

        if (!(imageCorner == null)) {
            image.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

            int currentY =Integer.parseInt(currentSquare.split("\\.")[0]);
            int currentX =Integer.parseInt(currentSquare.split("\\.")[1]);
            int mapsquareY;
            int mapsquareX;


            for (int x = 0; x < mapSquares.length; x++) {
                mapsquareY = Integer.parseInt(mapSquares[x].split("\\.")[0]);
                mapsquareX = Integer.parseInt(mapSquares[x].split("\\.")[1]);
                if (Math.abs(currentY - mapsquareY) <= 3) {
                    if (Math.abs(currentX - mapsquareX) <= 3) {

                        if (mapsquareX - currentX > imagesPositive.getX()) {
                            imagesPositive.setLocation(mapsquareX - currentX, imagesPositive.getY());
                        }
                        if (mapsquareX - currentX < imagesNegative.getX()) {
                            imagesNegative.setLocation(mapsquareX - currentX, imagesNegative.getY());
                        }
                        if (mapsquareY - currentY > imagesPositive.getY()) {
                            imagesPositive.setLocation(imagesPositive.getX(), mapsquareY - currentY);
                        }
                        if (mapsquareY - currentY < imagesNegative.getY()) {
                            imagesNegative.setLocation(imagesNegative.getX(), mapsquareY - currentY);
                        }

                        int moveY = (500 * ((mapsquareY - currentY)));
                        int moveX = (500 * ((mapsquareX - currentX)));




                        if(mapsquareX/29>=1){
                            moveX= moveX-125;
                        }
                        if(mapsquareY/55>=1){
                            moveY= moveY-213;
                        }

                        (new ImageIcon("Map Squares/" + mapSquares[x])).paintIcon(this, g, (int) imageCorner.getX() + moveX, (int) imageCorner.getY() + moveY);
                    }
                }

            }
        }
    }

    boolean ClickedInImage;

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            ClickedInImage = false;
            if (e.getButton() == MouseEvent.BUTTON1) {
                if ((e.getPoint().getX() <= imageCorner.getX() + WIDTH + (500 * imagesPositive.getX()) && e.getPoint().getX() >= imageCorner.getX() + (500 * imagesNegative.getX())) && (e.getPoint().getY() <= imageCorner.getY() + HEIGHT + (500 * imagesPositive.getY()) && e.getPoint().getY() >= imageCorner.getY() + (500 * imagesNegative.getY()))) {
                    prevPt = e.getPoint();
                    int addX = 0;
                    int addY = 0;
                    int checkX = Integer.parseInt(currentSquare.split("\\.")[1]);
                    int checkY = Integer.parseInt(currentSquare.split("\\.")[0]);

                    if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 3;
                        addX = 1500;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 2;
                        addX = 1000;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 1;
                        addX = 500;
                    }
                    if (e.getPoint().getX() - imageCorner.getX() < -1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 3;
                        addX = -1500;
                    }else if (e.getPoint().getX() - imageCorner.getX() < -500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 2;
                        addX = -1000;
                    } else if (e.getPoint().getX() - imageCorner.getX() < 0) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 1;
                        addX = -500;
                    }

                    if (e.getPoint().getY() - imageCorner.getY() > 1500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 3;
                        addY = 1500;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 2;
                        addY = 1000;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 1;
                        addY = 500;
                    }
                    if (e.getPoint().getY() - imageCorner.getY() < -100) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 3;
                        addY = -1500;
                    }else if (e.getPoint().getY() - imageCorner.getY() < -500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 2;
                        addY = -1000;
                    } else if (e.getPoint().getY() - imageCorner.getY() < 0) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 1;
                        addY = -500;
                    }

                    for (int x = 0; x < mapSquares.length; x++) {

                        if ((mapSquares[x].split("\\.")[0]  + "." + mapSquares[x].split("\\.")[1]).compareTo(checkY + "." + checkX)==0) {
                            currentSquare = mapSquares[x];
                            continue;
                        }
                    }

                    imageCorner.setLocation(imageCorner.getX() + addX, imageCorner.getY() + addY);


                    image = new ImageIcon("Map Squares/" + currentSquare);

                    WIDTH = image.getIconWidth();
                    HEIGHT = image.getIconHeight();

                    ClickedInImage = true;
                } else {
                    ClickedInImage = false;
                }

            }
            //need to change
            if (e.getButton() == MouseEvent.BUTTON3) {
                if ((e.getPoint().getX() <= imageCorner.getX() + WIDTH + (500 * imagesPositive.getX()) && e.getPoint().getX() >= imageCorner.getX() + (500 * imagesNegative.getX())) && (e.getPoint().getY() <= imageCorner.getY() + HEIGHT + (500 * imagesPositive.getY()) && e.getPoint().getY() >= imageCorner.getY() + (500 * imagesNegative.getY()))) {
                    int checkX = Integer.parseInt(currentSquare.split("\\.")[1]);
                    int checkY = Integer.parseInt(currentSquare.split("\\.")[0]);

                    if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 3;
                    }else if (e.getPoint().getX() - imageCorner.getX() > 1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 2;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 1;
                    }
                    if (e.getPoint().getX() - imageCorner.getX() < -1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 3;
                    }else if (e.getPoint().getX() - imageCorner.getX() < -500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 2;
                    } else if (e.getPoint().getX() - imageCorner.getX() < 0) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 1;
                    }

                    if (e.getPoint().getY() - imageCorner.getY() > 1500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 3;
                    }else if (e.getPoint().getY() - imageCorner.getY() > 1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 2;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 1;
                    }
                    if (e.getPoint().getY() - imageCorner.getY() < -1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 3;
                    }else if (e.getPoint().getY() - imageCorner.getY() < -500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 2;
                    } else if (e.getPoint().getY() - imageCorner.getY() < 0) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 1;
                    }
                    String biome="";

                    for (int x = 0; x < mapSquares.length; x++) {
                        if (mapSquares[x].contains(checkY + "." + checkX)) {
                            biome = mapSquares[x].split("\\.")[2];
                            continue;
                        }
                    }
                    if(!biome.equals("NULL")&&!biome.equals("$biome_empty")) {
                        change.toBiome(biome.replace("$biome_",""));
                    }

                }
            }
        }
    }
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (ClickedInImage) {
                Point currentPt = e.getPoint();//translate moves object by that much
                imageCorner.translate((int) (currentPt.getX() - prevPt.getX()), (int) (currentPt.getY() - prevPt.getY()));
                prevPt = currentPt;
                repaint();
            }
        }
    }
}