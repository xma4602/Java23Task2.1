package com.xma.task2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class PathDepthFileVisitor implements FileVisitor<Path> {
    private int maxDepth = -1;
    private int rootDepth;

    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        if (maxDepth < 0 && dir.toFile().list().length > 0) {
            maxDepth = 0;
            rootDepth = dir.getNameCount();
            return FileVisitResult.CONTINUE;
        }
        maxDepth = Math.max(maxDepth, dir.getNameCount() - rootDepth);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        return FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
