package com.xma.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String directoryPath;

        do {
            System.out.print("Введите путь к директории: ");
            directoryPath = scanner.next();
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path) || !Files.isDirectory(path)) {
                System.out.println("Это не существующая деректория");
            } else {
                PathDepthFileVisitor fileVisitor = new PathDepthFileVisitor();
                try {
                    Files.walkFileTree(path, fileVisitor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Глубина вложенности: " + fileVisitor.getMaxDepth());
            }
        }
        while (!directoryPath.isEmpty());
    }
}