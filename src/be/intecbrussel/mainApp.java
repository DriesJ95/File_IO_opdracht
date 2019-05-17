package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO: Probeer consequent te zijn in de benamingen.
// Ofwel ga je voor nederlandse benamingen of engelse[meer aangeraden],
// maar niet door elkaar.

public class mainApp {

    //TODO: Er zit nog teveel logica in je main methode,
    // probeer deze in je util klasse te houden.
    public static void main(String[] args) {

        //TODO: Het lijkt me eenvoudiger om de ArrayList in de FileUtils klasse bij te houden.
        List<File> alleFiles = new ArrayList<>();
        //Hieronder kan je makkelijk source en destination aanpassen
        //TODO: Probeer gebruik te maken van de Path klasse boven de File klasse[voor jdk 7]
        File teSorterenDirectory = new File("C:/data/unsorted");
        File sortedDirectory = new File("c:/data/sorted");
        //TODO: Je source en destination kan je beter onderbrengen in final variabelen.

        try {
            //TODO: Je gebruikt hier de Javadoc je kon beter gebruik maken van single line comments
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
