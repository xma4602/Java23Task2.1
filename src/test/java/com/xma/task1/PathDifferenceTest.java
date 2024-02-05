package com.xma.task1;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PathDifferenceTest {

    @Test
    void isExists_returnsTrue_whenAtLeastOnePathExists() throws IOException {
        Path path1 = Files.createTempFile("path1", ".txt");
        Path path2 = Files.createTempFile("path2", ".txt");
        assertTrue(PathDifference.isExists(path1, path2));
    }

    @Test
    void isExists_returnsFalse_whenBothPathsDoNotExist() {
        Path nonExistentPath1 = Path.of("nonexistent1");
        Path nonExistentPath2 = Path.of("nonexistent2");
        assertFalse(PathDifference.isExists(nonExistentPath1, nonExistentPath2));
    }

    @Test
    void isNotExists_returnsFalse_whenAtLeastOnePathExists() throws IOException {
        Path path1 = Files.createTempFile("path1", ".txt");
        Path path2 = Files.createTempFile("path2", ".txt");
        assertFalse(PathDifference.isNotExists(path1, path2));
    }

    @Test
    void isNotExists_returnsTrue_whenBothPathsDoNotExist() {
        Path nonExistentPath1 = Path.of("nonexistent1");
        Path nonExistentPath2 = Path.of("nonexistent2");
        assertTrue(PathDifference.isNotExists(nonExistentPath1, nonExistentPath2));
    }

    @Test
    void isSameFile_returnsTrue_whenPathsAreSameFileAndNotDirectory() throws IOException {
        Path file1 = Files.createTempFile("file1", ".txt");
        Path file2 = Path.of(file1.toString());
        assertTrue(PathDifference.isSameFile(file1, file2));
    }

    @Test
    void isSameFile_returnsFalse_whenPathsAreSameFileButIsDirectory() throws IOException {
        Path directory = Files.createTempDirectory("directory");
        assertFalse(PathDifference.isSameFile(directory, directory));
    }

    @Test
    void isSameDirectory_returnsTrue_whenPathsAreSameDirectory() throws IOException {
        Path directory1 = Files.createTempDirectory("directory1");
        Path directory2 = Path.of(directory1.toString());
        assertTrue(PathDifference.isSameDirectory(directory1, directory2));
    }

    @Test
    void isBiggerFile_returnsTrue_whenPath1IsBiggerThanPath2() throws IOException {
        Path smallerFile = Files.createTempFile("smallerFile", ".txt");
        Path biggerFile = Files.createTempFile("biggerFile", ".txt");
        Files.write(smallerFile, "1".getBytes());
        Files.write(biggerFile, "1111111".getBytes());
        assertTrue(PathDifference.isBiggerFile(biggerFile, smallerFile));
        Files.delete(smallerFile);
        Files.delete(biggerFile);
    }

    @Test
    void isBiggerFile_returnsFalse_whenPath2IsBiggerThanPath1() throws IOException {
        Path smallerFile = Files.createTempFile("smallerFile", ".txt");
        Path biggerFile = Files.createTempFile("biggerFile", ".txt");
        Files.write(smallerFile, "1".getBytes());
        Files.write(biggerFile, "111111".getBytes());
        assertFalse(PathDifference.isBiggerFile(smallerFile, biggerFile));
        Files.delete(smallerFile);
        Files.delete(biggerFile);
    }

    @Test
    void isSmallerFile_returnsTrue_whenPath1IsSmallerThanPath2() throws IOException {
        Path smallerFile = Files.createTempFile("smallerFile", ".txt");
        Path biggerFile = Files.createTempFile("biggerFile", ".txt");
        Files.write(smallerFile, "1".getBytes());
        Files.write(biggerFile, "111111111".getBytes());
        assertTrue(PathDifference.isSmallerFile(smallerFile, biggerFile));
        Files.delete(smallerFile);
        Files.delete(biggerFile);
    }

    @Test
    void isSmallerFile_returnsFalse_whenPath2IsSmallerThanPath1() throws IOException {
        Path smallerFile = Files.createTempFile("smallerFile", ".txt");
        Path biggerFile = Files.createTempFile("biggerFile", ".txt");
        Files.write(smallerFile, "1".getBytes());
        Files.write(biggerFile, "111111".getBytes());
        assertFalse(PathDifference.isSmallerFile(biggerFile, smallerFile));
        Files.delete(smallerFile);
        Files.delete(biggerFile);
    }

    @Test
    void isSameSizeFile_returnsTrue_whenPath1AndPath2HaveTheSameSize() throws IOException {
        Path file1 = Files.createTempFile("file1", ".txt");
        Path file2 = Files.createTempFile("file2", ".txt");
        Files.write(file1, "same".getBytes());
        Files.write(file2, "same".getBytes());
        assertTrue(PathDifference.isSameSizeFile(file1, file2));
    }


    @Test
    void isSameAbsoluteNameDepth_sameNames_sameDepth_shouldReturnTrue() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr/local/share");
        assertTrue(PathDifference.isSameAbsoluteNameDepth(path1, path2));
    }

    @Test
    void isSameAbsoluteNameDepth_sameNames_differentDepth_shouldReturnFalse() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr/local");
        assertFalse(PathDifference.isSameAbsoluteNameDepth(path1, path2));
    }

    @Test
    void isSamePrefix_sameFirstNames_shouldReturnTrue() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr/share");
        assertTrue(PathDifference.isSamePrefix(path1, path2));
    }

    @Test
    void isSamePrefix_differentFirstNames_shouldReturnFalse() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/bin/usr/share");
        assertFalse(PathDifference.isSamePrefix(path1, path2));
    }

    @Test
    void isSameRoot_sameRoot_shouldReturnTrue() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr/share");
        assertTrue(PathDifference.isSameRoot(path1, path2));
    }

    @Test
    void isSameRoot_differentRoot_shouldReturnFalse() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("asd/etc/share");
        assertFalse(PathDifference.isSameRoot(path1, path2));
    }

    @Test
    void isSubpath_path1IsSubpathOfPath2_shouldReturnTrue() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr");
        assertTrue(PathDifference.isSubpath(path1, path2));
    }

    @Test
    void isSubpath_path2IsSubpathOfPath1_shouldReturnFalse() {
        Path path1 = Paths.get("/usr/local");
        Path path2 = Paths.get("/usr/local/bin");
        assertFalse(PathDifference.isSubpath(path1, path2));
    }

    @Test
    void isParentPath_path1IsParentPathOfPath2_shouldReturnTrue() {
        Path path1 = Paths.get("/usr/local");
        Path path2 = Paths.get("/usr/local/bin");
        assertTrue(PathDifference.isParentPath(path1, path2));
    }

    @Test
    void isParentPath_path2IsParentPathOfPath1_shouldReturnFalse() {
        Path path1 = Paths.get("/usr/local/bin");
        Path path2 = Paths.get("/usr/local");
        assertFalse(PathDifference.isParentPath(path1, path2));
    }
}