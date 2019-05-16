package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUtils {

    /**list methode die een lijst teruggeeft van alle files in je directory en onderliggende directories;*/
    public static void list(File folder, List<File> files) {
        if (!folder.isDirectory()) {
            System.out.println("Folder is empty");
        }
        File[] folderArray = folder.listFiles();
        if (folderArray != null)
            for (File file : folderArray) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    list(file.getAbsoluteFile(), files);
                }
            }
    }


    /**geeft extension terug van de filename*/
    public static String getExtension(String filename) {
        String answer = null;
        if (filename == null) {
            answer = null;
        }
        if (filename.contains(".")) {
            answer = filename.substring(filename.lastIndexOf(".") + 1);
        }
        return answer;
    }


    /**Gebruikt hashset om alle extensies in een set te gieten, daarna maak ik met deze set alle folders aan
    Ten slotte giet ik alle bestanden in hun respectievelijke folder.*/
    public static void createAndCopyDirectory(List<File> files, File directory) throws IOException {
        Set<String> newFiles = new HashSet<>();
        //automatische detectering van alle mogelijke extensies in de directory
        for (File file : files) {
            newFiles.add(getExtension(file.getName()));
        }
        //aanmaak van nodige directories
        for (String s : newFiles) {
            if (!new File(directory + "/" + s).exists()) {
                new File(directory + "/" + s).mkdir();
            }
        }
        //copieerwerk met check of de destination al bestaat, indien onbestaande wordt deze aangemaakt en volgegoten
        for (File file : files) {
            if (getExtension(file.getName()) != null) {
                Path source = Paths.get(file.getAbsolutePath());
                Path destination = Paths.get(directory + "/" + getExtension(file.getName()) + "/" + file.getName());
                if (!destination.toFile().exists()) {
                    Files.copy(source, destination);
                }
            }
        }
    }


    /**Maakt een textfile op basis van de sorted directory*/
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
                            file.canRead() ? "x" : "/", "|", file.canWrite() ? "x" : "/", "|",
                            file.isHidden() ? "x" : "/", "|"));
                }
            }
        }
        //checked of de summary al bestaat, indien niet wordt deze aangemaakt
        if (!Paths.get(directory + "/summary.txt").toFile().exists()) {
            Files.createFile(Paths.get(directory + "/summary.txt"));
        }
        Files.write(Paths.get(directory + "/summary.txt"), summary);
    }


}
