package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TODO: Ik heb een .gitignore gemaakt met daarin de nodige excludes voor user/project specific files.
// Deze zal de getrackte files niet hiden. Maar dan hebben jullie een template voor het volgende project.

//TODO: Voor jdk 7 werd voor bestanden en mappen gebruikgemaakt van de klasse File.
// Deze heeft evenwel minder mogelijkheden. Je kan deze gebruiken
// maar het wordt aangeraden om voor nieuwe programma's gebruik te maken van de Path klasse.

//TODO: Probeer je methoden in kleinere stukken op te delen,
// je kan gebruik maken van private helper methoden daarvoor.

//TODO: Probeer IOExceptions intern op te vangen in je Methoden en correct af te handelen.

public class FileUtils {

    //TODO: Door deze private constructor hide je de default public constructor
    private FileUtils() {
        super();
    }

    /**
     * list methode die een lijst teruggeeft van alle files in je directory en onderliggende directories;
     */
    //TODO: Mooi gebruik van een recursive method
    public static void list(File folder, List<File> files) {
        if (!folder.isDirectory()) {
            //TODO: Het if statement hierboven gaat na of het File object,
            // een directory is, niet of die directory leeg is.
            System.out.println("Folder is empty");
        }

        //TODO: Je kan hier ook gebruik maken van Files.list(folder.toPath())
        // hierdoor krijg je een stream terug, en zou je eventueel met lambda's en foreach kunnen werken.
        File[] folderArray = folder.listFiles();
        if (folderArray != null)  //TODO: {}, kan bugs voorkomen.
            for (File file : folderArray) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    list(file.getAbsoluteFile(), files);
                }
            }
    }


    /**
     * geeft extension terug van de filename
     */
    public static String getExtension(String filename) {
        String answer = null;
        if (filename == null) {
            answer = null;  //TODO: Redundant code
        }
        if (filename.contains(".")) {  //TODO: if kan een NullPointerException geven!

            answer = filename.substring(filename.lastIndexOf(".") + 1);
        }
        return answer;  //TODO: De mogelijkheid bestaat dat je return value null is. Let hiermee op
    }


    /**
     * Gebruikt hashset om alle extensies in een set te gieten, daarna maak ik met deze set alle folders aan
     * Ten slotte giet ik alle bestanden in hun respectievelijke folder.
     */
    public static void createAndCopyDirectory(List<File> files, File directory) throws IOException {
        Set<String> newFiles = new HashSet<>();
        //automatische detectering van alle mogelijke extensies in de directory
        for (File file : files) {
            newFiles.add(getExtension(file.getName()));
        }
        //aanmaak van nodige directories
        //TODO: Je maakt hier in elke iteratie een nieuw object aan,
        // beter zou zijn om 1 variabele aan te maken en die te hergebruiken.
        for (String s : newFiles) {
            //TODO: Files.createDirectories(Paths.get(s)); zal hetzelfde effect hebben.
            if (!new File(directory + "/" + s).exists()) {
                new File(directory + "/" + s).mkdir();
            }
        }
        //copieerwerk met check of de destination al bestaat, indien onbestaande wordt deze aangemaakt en volgegoten
        for (File file : files) {
            if (getExtension(file.getName()) != null) {
                //TODO: Je kon hier de methode file.toPath() gebruiken.
                Path source = Paths.get(file.getAbsolutePath());
                //TODO: directory.toPath().resolve(getExtension(file.getName())).resolve(file.getName());
                // Probeer op deze manier hard coded seperators te voorkomen.
                Path destination = Paths.get(directory + "/" + getExtension(file.getName()) + "/" + file.getName());
                if (!destination.toFile().exists()) {
                    Files.copy(source, destination);
                }
            }
        }
    }


    /**
     * Maakt een textfile op basis van de sorted directory
     */
    //TODO: Deze methode heeft een te grote kost naar onderhoud toe
    public static void makeTextFile(File directory) throws IOException {
        List<File> sortedFiles = new ArrayList<>();
        FileUtils.list(directory, sortedFiles);
        Set<String> extensions = new HashSet<>();

        for (File file : sortedFiles) {
            extensions.add(getExtension(file.getName()));
        }
        //formateren van 'log'file
        List<String> summary = new ArrayList<>();
        summary.add(String.format("%-52s%5s%10s%5s%10s%5s%10s%5s", "name", "|", "readable", "|", "writeable",
                "|", "hidden", "|"));

        for (String s : extensions) {
            summary.add("\n");
            summary.add(s + ":");
            summary.add("------");
            for (File file : sortedFiles) {
                if (s.equals(file.getParentFile().getName())) {
                    summary.add(String.format("%-52s%5s%10s%5s%10s%5s%10s%5s", file.getName(), "|",
                            file.canRead() ? "x" : "/", "|",
                            file.canWrite() ? "x" : "/", "|",
                            file.isHidden() ? "x" : "/", "|"));
                }
            }
        }
        //checked of de summary al bestaat, indien niet wordt deze aangemaakt
        //TODO: Je kon van directory + "/summary.txt" een final Path variabele gemaakt hebben.
        if (!Paths.get(directory + "/summary.txt").toFile().exists()) {
            Files.createFile(Paths.get(directory + "/summary.txt"));
        }
        Files.write(Paths.get(directory + "/summary.txt"), summary);
    }


}
