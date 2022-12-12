package company;


import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        //File[] allfiles =new File("Map").listFiles();

       // for (File f:allfiles) {

        //    String a = f.getName();
        //   if(a.contains("x")){
        //       System.out.println(a);
        //   }
       // }

//$biome_

     //new Gogo();
        // new AllDataGUI();
        new SelectSave();
     /*   int r = -1;
        int c = 0;

        File[] f = new File("Q4").listFiles();

        for (int x = 0;x<f.length;x++){

            String name = f[x].getName();
            int num = Integer.parseInt((name.split("_"))[1].replace(".jpg", ""))-1;

                r = (num - (num%29))/29;
                c = (num%29);
            String temp = (r+55)+"."+(c+29)+".X.jpg";
            f[x].renameTo(new File("Q4/"+temp));



        }
*/

    }


}