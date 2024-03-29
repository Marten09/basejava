package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(".\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printAllFiles(dir,"");
    }
    private static void printAllFiles(File dir, String indent) {
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (File files : listFiles) {
                if (files.isFile()) {
                    System.out.println(indent + "File: " + files.getName());
                } else if (files.isDirectory()) {
                    System.out.println(indent + "Directory: " + files.getName());
                    printAllFiles(files,indent + " ");
                }
            }
        }
    }
}
