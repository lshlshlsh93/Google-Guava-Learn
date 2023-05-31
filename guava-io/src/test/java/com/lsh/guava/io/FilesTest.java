package com.lsh.guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/5/27 15:39
 */
public class FilesTest {

    private final String SOURCE_FILE = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\source.txt";

    private final String TARGET_FILE = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\target.txt";

    @Test
    public void testCopyFileWithGuavaFiles() throws IOException {
        File sourceFile = new File(SOURCE_FILE);
        File targetFile = new File(TARGET_FILE);
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(), equalTo(true));
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(sourceHashCode, equalTo(targetHashCode));
    }

    @Test
    public void testCopyFileWithNioFiles() throws IOException {
        java.nio.file.Files.copy(
                Paths.get("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources", "io", "source.txt"),
                Paths.get("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources", "io", "target.txt"),
                StandardCopyOption.REPLACE_EXISTING
        );
        File targetFile = new File(TARGET_FILE);
        assertThat(targetFile.exists(), equalTo(true));
    }

    @Test
    public void testMoveFile() throws IOException {
        try {
            File targetFile = new File(TARGET_FILE);
            Files.move(new File(SOURCE_FILE), targetFile);
            // target file exist
            assertThat(targetFile.exists(), equalTo(true));
            // source file not exist
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }

    }

    @Test
    public void testToString() throws IOException {
        String str = "today we are eating fish.\n" + "hhh.";
        List<String> stringList = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(stringList);
        assertThat(result, equalTo(str));
    }


    @Test
    public void testProcessToString() throws IOException {
        List<Integer> integerList = Files
                .asCharSource(new File(SOURCE_FILE), Charsets.UTF_8)
                .readLines(new LineProcessor<>() {
                    private final List<Integer> lengthList = new ArrayList<>();

                    @Override
                    public boolean processLine(String line) throws IOException {
                        if (line.length() == 0) return false;
                        lengthList.add(line.length());
                        return true;
                    }

                    @Override
                    public List<Integer> getResult() {
                        return lengthList;
                    }
                });
        System.out.println(integerList);
    }


    @Test
    public void testFileHashSha256() throws IOException {
        File file = new File(SOURCE_FILE);
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode.toString());
    }

    @Test
    public void testFileWrite() throws IOException {
        String source = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\test.txt";
        File sourceFile = new File(source);
        sourceFile.deleteOnExit();
        String content = "this is test content.";
        Files.asCharSink(sourceFile, Charsets.UTF_8).write(content);
        String actually = Files.asCharSource(sourceFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content));
    }


    @Test
    public void testFileAppend() throws IOException {
        String source = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\test.txt";
        File sourceFile = new File(source);
        sourceFile.deleteOnExit();
        CharSink charSink = Files.asCharSink(sourceFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write("Content1");
        String realContent = Files.asCharSource(sourceFile, Charsets.UTF_8).read();
        assertThat(realContent, equalTo("Content1"));
        charSink.write("Content2");
        realContent = Files.asCharSource(sourceFile, Charsets.UTF_8).read();
        assertThat(realContent, equalTo("Content1Content2"));
    }

    @Test
    public void testTouchFile() throws IOException {
        String source = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\test.txt";
        File touchFile = new File(source);
        touchFile.deleteOnExit();
        Files.touch(touchFile);
        assertThat(touchFile.exists(), equalTo(true));
    }

    @Test
    public void testRecursive() {
        List<File> fileList = new ArrayList<>();
        this.recursiveList(new File("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main"), fileList);
        fileList.forEach(System.out::println);
    }

    private void recursiveList(File file, List<File> fileList) {
        if (file.isHidden()) {
            return;
        }
        if (file.isFile()) {
            fileList.add(file);
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                recursiveList(f, fileList);
            }
        }
    }

    @Test
    public void testTreeFilesPreOrder() {
        File root = new File("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main");
        Files.fileTraverser().depthFirstPreOrder(root).forEach(System.out::println);
    }

    @Test
    public void testTreeFilesPostOrder() {
        File root = new File("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main");
        Files.fileTraverser().depthFirstPostOrder(root).forEach(System.out::println);
    }

    @Test
    public void testTreeFilesBreadthFirst() {
        File root = new File("D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main");
        Files.fileTraverser().breadthFirst(root).forEach(System.out::println);
    }

    @After
    public void tearDown() {
        File file = new File(TARGET_FILE);
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                System.out.println("delete file success.");
            }
        }

    }

}
