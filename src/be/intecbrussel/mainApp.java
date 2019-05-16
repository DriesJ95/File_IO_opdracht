package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class mainApp {
    public static void main(String[] args) {
        List<File> alleFiles = new ArrayList<>();
        //Hieronder kan je makkelijk source en destination aanpassen
        File teSorterenDirectory = new File("c:/data/Unsorted/test 1");
        File sortedDirectory = new File("c:/data/sorted");

        try {
            /**Maakt een lijst van alle files in heel het onderliggende systeem*/
            FileUtils.list(teSorterenDirectory,alleFiles);
            /**Gebruikt de gemaakte lijst om directories aan te maken en alles te copiëren, originelen blijven behouden.*/
            FileUtils.createAndCopyDirectory(alleFiles, sortedDirectory);
            /**Maakt een textfile aan, genaamd summary.txt in de sortedfolder*/
            FileUtils.makeTextFile(sortedDirectory);

            /**Arbitraire console output om de 'alleFiles' variabele zijn inhoud weer te geven*/
            System.out.println("All files found: ".toUpperCase());
            System.out.println("----------------------");
            for (File file : alleFiles){
                System.out.println(file);
            }
            System.out.println("----------------------");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
