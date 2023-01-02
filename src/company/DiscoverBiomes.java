package company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class DiscoverBiomes {
    File[] folder;
    public DiscoverBiomes(File saveFile){

        String alreadyfound = "";
        try {
            FileReader read = new FileReader(System.getProperty("user.home")+"/NoitaHelp/OverallSave.txt");
            Scanner scan = new Scanner(read);
            while (scan.hasNextLine()) {
                String temp = scan.nextLine();
                alreadyfound = alreadyfound + temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            String newdiscoveries = "";
        if(new File("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml").exists()) {
            try {
                FileReader read = new FileReader("C:/Users/User/AppData/LocalLow/Nolla_Games_Noita/save00/player.xml");
                Scanner scan = new Scanner(read);
                while (scan.hasNextLine()) {
                    String temp = scan.nextLine();
                    newdiscoveries = newdiscoveries + temp;
                }
                if (newdiscoveries.contains("$biome_???")){
                    try (//true = new line false = replace whole file
                         FileWriter fw = new FileWriter(new File(System.getProperty("user.home")+"/NoitaHelp/OverallSave.txt"), true);
                         PrintWriter pw = new PrintWriter(fw)
                    ){
                        pw.println("$biome_Diamond");
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {
            FileReader read = new FileReader("ID.txt");
            Scanner scan = new Scanner(read);
            while (scan.hasNextLine()) {
                String i = scan.nextLine();

                if(!alreadyfound.contains(i) && newdiscoveries.contains(i)) {
                    try (//true = new line false = replace whole file
                         FileWriter fw = new FileWriter(new File(System.getProperty("user.home") + "/NoitaHelp/OverallSave.txt"), true);
                         PrintWriter pw = new PrintWriter(fw);

                    ) {
                        pw.println(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    File dir = new File("Map Squares");
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File f : contents) {
                try {
                Files.delete(Path.of(f.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dir.mkdirs();


        folder = new File("Map").listFiles();

        String allStrings = "$biome_empty,$biome_forest";
        try {
            FileReader read = new FileReader(saveFile);
            Scanner scan = new Scanner(read);
            while (scan.hasNextLine()) {
                String temp = scan.nextLine();
                allStrings = allStrings + ","+temp;
            }
            if (allStrings.contains("$biome_???")){
                allStrings = allStrings + ","+"$biome_Diamond";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (File file : folder) {
            boolean exists = false;
            String name = file.getName();
            if (Character.isDigit(name.charAt(0))) {
                String temp = name.substring(0,file.getName().length()-4).replaceAll("[0-9]","");
                temp =  temp.replace(".", "");
                if (allStrings.contains(temp)) {
                        try {
                            BufferedImage bi = ImageIO.read(file);
                            ImageIO.write(bi, "jpg", new File("Map Squares/"+name));

                        }catch (IOException e){
                            e.printStackTrace();
                        }

                } else {
                    try {


                        File x = folder[folder.length-1];
                        BufferedImage bi = ImageIO.read(x);
                        name  = name.split("\\.")[0]+"."+name.split("\\.")[1]+".NULL.jpg";



                        if((name.split("\\.")[1].equals("57")||name.split("\\.")[1].equals("28"))&&(name.split("\\.")[0].equals("109")|| name.split("\\.")[0].equals("54")) ){
                            Image result = bi.getSubimage(0,0,375,287);
                            bi = new BufferedImage(result.getWidth(null), result.getHeight(null),bi.getType());
                            Graphics g = bi.createGraphics();
                            g.drawImage(result, 0, 0, null);
                            g.dispose();
                        }else if(name.split("\\.")[0].equals("109")|| name.split("\\.")[0].equals("54")){
                            Image result = bi.getSubimage(0,0,500,287);
                            bi = new BufferedImage(result.getWidth(null), result.getHeight(null),bi.getType());
                            Graphics g = bi.createGraphics();
                            g.drawImage(result, 0, 0, null);
                            g.dispose();
                        }else if(name.split("\\.")[1].equals("57")||name.split("\\.")[1].equals("28")){

                            Image result = bi.getSubimage(0,0,375,500);
                            bi = new BufferedImage(result.getWidth(null), result.getHeight(null),bi.getType());
                            Graphics g = bi.createGraphics();
                            g.drawImage(result, 0, 0, null);
                            g.dispose();
                        }

                        ImageIO.write(bi, "jpg", new File("Map Squares/"+name));



                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }



            }
        }
    }


}
