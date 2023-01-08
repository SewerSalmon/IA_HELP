package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class BiomeDisplay extends JPanel{

    // make it swap map not make enterly new frame
    JButton sort[] = new JButton[3];
    ImageIcon image;
    Point imageCorner;
    MyFrame change;
    File[] folder;
    String currentBiome;
    private String allStrings;
    private JTextArea textArea = new JTextArea();
    JComboBox<String> biomes = new JComboBox(){

        @Override public void addItem(Object obj){
        int count = getItemCount();
        String toAdd = (String) obj;

        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            items.add((String)getItemAt(i));
        }

        if(items.size() == 0){
            super.addItem(toAdd);
            return;
        }else{
            if(toAdd.compareTo(items.get(0)) <= 0){
                insertItemAt(toAdd, 0);
            }else{
                int lastIndexOfHigherNum = 0;
                for(int i = 0; i < count; i++){
                    if(toAdd.compareTo(items.get(i)) > 0){
                        lastIndexOfHigherNum = i;
                    }
                }
                insertItemAt(toAdd, lastIndexOfHigherNum+1);
            }
        }
    }
    };

    JLabel label= new JLabel();
    public BiomeDisplay(MyFrame a,String biome){

        this.setBackground(Color.black);
        change = a;
        this.setLayout(null);
        biomes.setBounds(0,0,400,50);
        ActionListener switchDisplay = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                biomeChange((String) biomes.getSelectedItem());
            }
        };
        biomes.addActionListener(switchDisplay);
        add(biomes);

        folder = new File("Map Squares/").listFiles();
        for(File file: folder){
            boolean exists = false;
            String name = file.getName().substring(0,file.getName().length()-4).replaceAll("[0-9]","");
            name =  name.replace(".", "");
            for(int x = 0 ;x<biomes.getItemCount();x++){
                if(name.equals("$biome_"+biomes.getItemAt(x))){
                   exists = true;
               }
            }
            if(!exists&&!name.equals("NULL")&&!name.equals("$biome_empty")) {
                biomes.addItem(name.replace("$biome_",""));
            }
        }
        JButton back = buttonAdd("Back to map",400,0,150,50);
        biomeChange(biome);
        biomes.setSelectedItem(currentBiome);
        JButton next = buttonAdd("Next Biome section",550,0,150,50);
        txtCheck();
        JButton prev = buttonAdd("Previous Biome section",700,0,150,50);

        fileRead();

        JScrollPane scrollBarForTextArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBarForTextArea.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() -300,50,300,(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() -50);
        textArea.setLineWrap(true);textArea.setWrapStyleWord(true);
        label.setVisible(true);
        label.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() -300,0,300,50);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        this.add(label);
        this.add(scrollBarForTextArea);

      }

      private void txtCheck(){
        if(!new File("BiomeNotes/").exists()){
            new File("BiomeNotes/").mkdirs();
        }
        File f = new File("BiomeNotes/" + currentBiome+".txt");
        if(f.exists()){}else {
            try{f.createNewFile();
            }catch (IOException e){
            e.printStackTrace();
        }}
      }

    private void fileRead(){
        allStrings="";
        try{
            FileReader read = new FileReader("BiomeNotes/" + currentBiome+".txt");//change tx to 'biome'.txt
            Scanner scan = new Scanner(read);
            while(scan.hasNextLine()){
                String temp=scan.nextLine();
                allStrings=allStrings+temp;
            }
            textArea.setText(allStrings);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public JButton buttonAdd(String name, int x, int y,  int width, int height) {
        JButton button;
        button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(new ClickButton());
        button.setName(name);
        add(button);
        return button;
    }

    ArrayList<String> imagesInBiome = new ArrayList<String>();
    private class ClickButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
         /*   if (e.getActionCommand().equals("Alphabetical")) {
                String[] names = new String[biomes.getItemCount()];
                for (int i = 0; i < names.length; i++) names[i] = biomes.getItemAt(i);
                names = (String[]) alphabetical(names, false);
                for (int i = 0; i < names.length; i++) {
                   for (int y = 0; y<names.length;y++){
                       if (names[i].equals(biomes.getItemAt(y))){

                          biomes.get(y).setBounds(10+(i*100),0,100,40);
                       }
                   }
                }
            }
            else if (e.getActionCommand().equals("shuffle")) {
                String[] names = new String[biomes.size()];

                for (int i = 0; i < names.length; i++) names[i] = biomes.get(i).getActionCommand();

                names = (String[]) shuffle(names);

                for (int i = 0; i < names.length; i++) {
                    for (int y = 0; y<names.length;y++){
                        if (names[i].equals(biomes.get(y).getActionCommand())){
                            biomes.get(y).setBounds(10+(i*100),0,100,40);
                        }
                    }
                }
            }
            else if (e.getActionCommand().equals("ReverseAlpha")) {
                String[] names = new String[biomes.size()];

                for (int i = 0; i < names.length; i++) names[i] = biomes.get(i).getActionCommand();

                names = (String[]) alphabetical(names, true);

                for (int i = 0; i < names.length; i++) {
                    for (int y = 0; y<names.length;y++){
                        if (names[i].equals(biomes.get(y).getActionCommand())){
                            biomes.get(y).setBounds(10+(i*100),0,100,40);
                        }
                    }
                }
            }*/
            if (e.getActionCommand().equals("Next Biome section")){

            // get last index of array
                int lastIndex = imagesInBiome.size() - 1;
                // save first element
                String  oldFirst = imagesInBiome.get(0);

                // copy the elements from  right to left
                for (int i = 0; i < lastIndex; i++)
                    imagesInBiome.set(i, imagesInBiome.get(i+1));

                // put the first element last
                imagesInBiome.set(lastIndex,oldFirst);
                prevPt = new Point(600,300);
                imageCorner.setLocation(600,300);
                repaint();
            }

            if (e.getActionCommand().equals("Previous Biome section")){
                System.out.println();
                System.out.println("start");
                for(int i = 0; i<imagesInBiome.size();i++){
                    System.out.println(imagesInBiome.get(i));
                }
            // get last index of array
                int lastIndex = imagesInBiome.size() - 1;
                // save last element
                String oldLast = imagesInBiome.get(lastIndex);

                // copy the elements from  left to right
                for (int i = lastIndex; i != 0; i--)
                    imagesInBiome.set(i,imagesInBiome.get(i-1));

                // put the last element first
                imagesInBiome.set(0,oldLast);
                System.out.println("end");
                for(int i = 0; i<imagesInBiome.size();i++){
                    System.out.println(imagesInBiome.get(i));
                }
                prevPt = new Point(600,300);
                imageCorner.setLocation(600,300);
                repaint();
            }


            if(e.getActionCommand().equals("Back to map")){

                    File file;
                    FileWriter writer;
                    try {
                        file = new File("BiomeNotes/" + currentBiome+".txt");
                        writer = new FileWriter(file);
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (FileNotFoundException h) {
                        h.printStackTrace();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                change.toMap();
            }
        }

        public Comparable[]  alphabetical(Comparable[] array, boolean reverse){
            int smallest;
            for(int i = 0; i < array.length; i++)
            {
                smallest = i; // set first element as smallest
                for(int j = i + 1; j < array.length; j++) // find smallest
                    if(array[j].compareTo(array[smallest]) < 0)
                        smallest = j;

                if(smallest != i)
                    swap(array, smallest, i);
            }

            if(reverse){
                for (int i = 0; i < array.length / 2; i++) {
                    swap(array,i,array.length - 1 - i);
                }
            }
            return array;
        }

        public Object[] shuffle(Object[] array){
            // If running on Java 6 or older, use `new Random()` on RHS here
            Random rnd = new Random();
            for (int i = array.length - 1; i > 0; i--)
            {
                swap(array,rnd.nextInt(i + 1),i);
            }
            for (int i = array.length - 1; i > 0; i--)
            {
                swap(array,rnd.nextInt(i + 1),i);
            }
            return array;
        }

        void swap(Object[] array, int index1, int index2){
            Object temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }

    public void biomeChange(String biome){
        File f;
        FileWriter writer;
        try {
            f = new File("BiomeNotes/" + currentBiome+".txt");
            writer = new FileWriter(f);
            writer.write(textArea.getText());
            writer.close();
        } catch (FileNotFoundException h) {
            h.printStackTrace();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        imagesInBiome.clear();

        for(File file: folder){
            if(file.getName().contains("$biome_"+biome+".jpg")){
                imagesInBiome.add(file.getName());
            }
        }

        currentBiome = biome;
        txtCheck();
        fileRead();
        DragPanel("Map Squares/" +imagesInBiome.get(0),new Point(600,300));
        imageCorner.setLocation(prevPt);
        repaint();
        label.setText("BiomeNotes " + currentBiome);

    }



    int WIDTH;
    int HEIGHT;
    Point prevPt = new Point(600,300);
    public void DragPanel(String FileLocation,Point point){
        image = new ImageIcon(FileLocation);
        WIDTH = image.getIconWidth();
        HEIGHT = image.getIconHeight();
        imageCorner = point;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        addMouseListener(clickListener);
        addMouseMotionListener(dragListener);
    }

    boolean ClickedInImage;

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            ClickedInImage = false;
            if (e.getButton() == MouseEvent.BUTTON1) {
                prevPt = e.getPoint();
                ClickedInImage = true;
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!(imageCorner == null)) {

            for(int x = 0;x<imagesInBiome.size();x++){

                   int moveX = 500 * ( Integer.parseInt(imagesInBiome.get(x).split("\\.")[1]) - Integer.parseInt(imagesInBiome.get(0).split("\\.")[1]) );
                   int moveY = 500 * ( Integer.parseInt(imagesInBiome.get(x).split("\\.")[0]) - Integer.parseInt(imagesInBiome.get(0).split("\\.")[0]) );

               int mapsquareY = Integer.parseInt(imagesInBiome.get(x).split("\\.")[0]);
               int mapsquareX = Integer.parseInt(imagesInBiome.get(x).split("\\.")[1]);

                if(mapsquareX/29>=1){
                    moveX= moveX-125;
                }
                if(mapsquareY/55>=1){
                    moveY= moveY-213;
                }

                    (new ImageIcon("Map Squares/" +imagesInBiome.get(x))).paintIcon(this,g,(int)imageCorner.getX() + moveX,(int)imageCorner.getY() + moveY);
                }
            }

    }
}


