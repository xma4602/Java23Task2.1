package com.xma.task1;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static com.xma.task1.PathDifferenceStatus.*;

public class PathDifference {
    public static List<PathDifferenceStatus> difference(Path path1, Path path2) throws IOException {
        List<PathDifferenceStatus> statuses = new ArrayList<>();

        if (isNotExists(path1, path2)) {
            statuses.add(NOT_EXISTS);
        } else if (isSameFile(path1, path2)) {
            if (isSameDirectory(path1, path2)) {
                statuses.add(SAME_DIRECTORY);
            } else {
                statuses.add(SAME_FILE);
                if (isBiggerFile(path1, path2)) {
                    statuses.add(BIGGER_FILE);
                } else if (isSmallerFile(path1, path2)) {
                    statuses.add(SMALLER_FILE);
                } else {
                    statuses.add(SAME_SIZE_FILE);
                }
            }

        }
        if (isSameAbsoluteNameDepth(path1, path2)) {
            statuses.add(SAME_ABSOLUTE_NAME_DEPTH);
        }
        if (isSameRoot(path1, path2)) {
            statuses.add(SAME_ROOT);
            if (isSamePrefix(path1, path2)) {
                statuses.add(SAME_PREFIX);
                if (isSubpath(path1, path2)) {
                    statuses.add(SUBPATH);
                } else if (isParentPath(path1, path2)) {
                    statuses.add(PARENT_PATH);
                }
            }
        }


        return statuses;
    }

    public static boolean isExists(Path path1, Path path2) {
        return Files.exists(path1) || Files.exists(path2);
    }

    public static boolean isNotExists(Path path1, Path path2) {
        return !Files.exists(path1) || !Files.exists(path2);
    }

    public static boolean isSameFile(Path path1, Path path2) throws IOException {
        return Files.isSameFile(path1, path2) && !Files.isDirectory(path1);
    }

    public static boolean isSameDirectory(Path path1, Path path2) throws IOException {
        return Files.isSameFile(path1, path2) && Files.isDirectory(path1);
    }

    public static boolean isBiggerFile(Path path1, Path path2) throws IOException {
        return isExists(path1, path2) && Files.size(path1) > Files.size(path2);
    }

    public static boolean isSmallerFile(Path path1, Path path2) throws IOException {
        return isExists(path1, path2) && Files.size(path1) < Files.size(path2);
    }

    public static boolean isSameSizeFile(Path path1, Path path2) throws IOException {
        return isExists(path1, path2) && Files.size(path1) == Files.size(path2);
    }

    public static boolean isSameAbsoluteNameDepth(Path path1, Path path2) {
        return path1.toAbsolutePath().getNameCount() == path2.toAbsolutePath().getNameCount();
    }

    public static boolean isSamePrefix(Path path1, Path path2) {
        return path1.getName(0).equals(path2.getName(0));
    }

    public static boolean isSameRoot(Path path1, Path path2) {
        return path1.getRoot().equals(path2.getRoot());
    }

    public static boolean isSubpath(Path path1, Path path2) {
        return path1.startsWith(path2) && !path2.startsWith(path1);
    }

    public static boolean isParentPath(Path path1, Path path2) {
        return !path1.startsWith(path2) && path2.startsWith(path1);
    }

}
