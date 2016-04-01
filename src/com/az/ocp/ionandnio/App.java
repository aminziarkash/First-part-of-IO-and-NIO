package com.az.ocp.ionandnio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.*;


public class App {

    public static void main(final String[] args) {

        final App application = new App();

        application.createAFileExample();

        application.fileWriterAndFileReaderExample();

        application.combineIOClassesExample();

        application.anotherCombineIOClassesExample();

        application.workingWithFilesAndDirectoriesExample();

        application.createPathExample();

        application.creatingFilesAndDirectoriesNIO2Example();

        application.copyMoveAndDeleteFilesExample();

        application.retrieveInformationAboutAPathExample();

        application.normalizingAPathExample();

        application.resolvingAPathExample();

        application.relativizingAPathExample();

        application.cleanDirectories();
    }

    private void relativizingAPathExample() {
        System.out.println("RELATIVIZING A PATH\n");
        final Path absolute1 = Paths.get("/home/java");
        final Path absolute2 = Paths.get("/usr/local");
        final Path absolute3 = Paths.get("/home/java/temp/music.mp3");
        final Path relative1 = Paths.get("temp");
        final Path relative2 = Paths.get("temp/music.pdf");
        System.out.println("1: " + absolute1.relativize(absolute3));
        System.out.println("2: " + absolute3.relativize(absolute1));
        System.out.println("3: " + absolute1.relativize(absolute2));
        System.out.println("4: " + relative1.relativize(relative2));
        try {
            System.out.println("5: " + absolute1.relativize(relative1));// BAD
        } catch (IllegalArgumentException e) {
            System.out.println("\nWARNING\t:\tCannot reltivize an absolute with relative path!");
        }

        addSeparator();
    }

    private void resolvingAPathExample() {
        System.out.println("RESOLVING A PATH\n");
        final Path absolute = Paths.get("/home/java");
        final Path relative = Paths.get("dir");
        final Path file = Paths.get("Model.pdf");
        System.out.println("1: " + absolute.resolve(relative));
        System.out.println("2: " + absolute.resolve(file));
        System.out.println("3: " + relative.resolve(file));
        System.out.println("4: " + relative.resolve(absolute)); // BAD Can't do this
        System.out.println("5: " + file.resolve(absolute)); // BAD Can't do this
        addSeparator();
    }

    private void normalizingAPathExample() {
        System.out.println("NORMALIZING A PATH\n");
        System.out.println("/a/./b/./c\t:\t" + Paths.get("/a/./b/./c").normalize());
        System.out.println(".classpath\t:\t" + Paths.get(".classpath").normalize());
        System.out.println("/a/b/c/..\t:\t" + Paths.get("/a/b/c/..").normalize());
        System.out.println("../a/b/c\t:\t" + Paths.get("../a/b/c").normalize());

        addSeparator();
    }

    private void retrieveInformationAboutAPathExample() {
        System.out.println("RETRIEVING INFORMATION ABOUT A PATH\n");

        retrievePathInfo();

        iterateThroughAPath();

        addSeparator();
    }

    private void iterateThroughAPath() {
        System.out.println("\nIterate through a path...\n");
        int spaces = 1;
        final Path myPath = Paths.get("PathToIterate", "dir1", "dir2", "dir3");
        final Path myFile = Paths.get(myPath.toString(), "MyFile.txt");
        try {
            Files.createDirectories(myPath);
            Files.createFile(myFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // PathToIterate/dir1/dir2/dir3/MyFile.txt
        System.out.println("Show hierarchy\t:\t" + myFile.getParent() + "\\" + myFile.getFileName());

        for (Path subPath : myPath) {
            System.out.format("%" + spaces + "s%s%n", "", subPath);
            spaces += 2;
        }
        System.out.format("%" + spaces + "s%s%n", "", myFile.getFileName());
    }

    private void retrievePathInfo() {
        final Path path = Paths.get("IO and NIO", "src", "ConsoleTest.java");
        System.out.println("getFileName\t:\t" + path.getFileName());
        System.out.println("getName(1)\t:\t" + path.getName(1));
        System.out.println("getNameCount\t:\t" + path.getNameCount());
        System.out.println("getParent\t:\t" + path.getParent());
        System.out.println("getRoot\t\t:\t" + path.getRoot());
        System.out.println("subpath(0, 2)\t:\t" + path.subpath(0, 2));
        System.out.println("toString()\t:\t" + path.toString());
    }

    private void copyMoveAndDeleteFilesExample() {

        System.out.println("COPY MOVE AND DELETE FILES EXAMPLE\n");

        copyDeleteAndMove();

        move2SameFilesToSameDirectory();

        addSeparator();
    }

    private void move2SameFilesToSameDirectory() {
        System.out.println("\nTrying to move 2 same files to the same directory...\n");

        final Path path = Paths.get("move2SameFilesToSameDirectory/test1");
        final Path file = Paths.get("move2SameFilesToSameDirectory/test2.txt");
        final Path targ = Paths.get("move2SameFilesToSameDirectory/test23.txt");
        try {
            Files.createDirectories(path);
            System.out.println("Created directory\t:\t" + path.getName(0));
            Files.createFile(file);
            System.out.println("Created file\t\t:\t" + file.getName(1));
            Files.copy(file, targ);
            System.out.println("Copy file to\t\t:\t" + targ.getName(1));
            Files.copy(file, targ, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Replace file\t\t:\t" + targ.getName(1));
        } catch (final IOException e) {
            System.out
                .println("Exception occured\t:\tCannot copy file, the file already exist in this directory");
            System.out.println("Exception type\t\t:\t" + e.getClass().getName());
        } finally {
            try {
                System.out.println("\nDelete files if exist using the Files.deleteIfExists() method...\n");
                Files.deleteIfExists(targ);
                Files.deleteIfExists(file);
                Files.deleteIfExists(path);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDeleteAndMove() {
        final Path path = Paths.get("CopyDeleteAndMoveDirectory/test1/");
        try {
            Files.createDirectories(path);
            System.out.println("Created directory\t:\t" + path.getName(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Path source = Paths.get("CopyDeleteAndMoveDirectory/test1"); // exists
        final Path target = Paths.get("CopyDeleteAndMoveDirectory/test2"); // doesn't yet exist
        try {
            Files.copy(source, target);
            System.out.println("Using\t\t\t:\tFiles.copy(source, target) to copy file");
            Files.delete(target); // back to one copy
            System.out.println("Using\t\t\t:\tFiles.delete(target) to delete file");
            Files.move(source, target); // still one copy
            System.out.println("Using\t\t\t:\tFiles.move(source, target) to move file");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void creatingFilesAndDirectoriesNIO2Example() {

        System.out.println("CREATING FILES AND DIRECTORIES NIO2 EXAMPLE\n");

        createFileUsingFilesStaticMethods();

        createDirectoryUsingFilesStaticMethods();

        addSeparator();
    }

    private void createDirectoryUsingFilesStaticMethods() {
        System.out.println("\nCreating Directory Using Files Static Methods...");
        final Path path1 = Paths.get("FilesDirectory");
        final Path path2 = Paths.get("FilesDirectory/Directory/");
        final Path file = Paths.get("FilesDirectory/Directory/Program.java");

        try {
            Files.createDirectory(path1);
            System.out.println("Created directory\t:\t" + path1.getFileName());
            Files.createDirectory(path2); // createDirectories(path2) can be used to create all directories
                                          // needed at once
            System.out.println("Created subdirectory\t:\t" + path2.getFileName());
            Files.createFile(file);
            System.out.println("Created file\t\t:\t" + file.getFileName());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileUsingFilesStaticMethods() {
        final Path path = Paths.get("fileWriteWithPath.txt");
        System.out.println("File 'fileWriteWithPath.txt' exists in path\t:\t" + Files.exists(path));

        // FileAlreadyExistsException is thrown if the file already exists in the directory
        try {
            Files.createFile(path);
            System.out.println("\nFile created\t:\t" + path.getName(0));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nFile 'fileWriteWithPath.txt' exists in path\t:\t" + Files.exists(path));
    }

    private void createPathExample() {

        System.out.println("CREATE PATH EXAMPLE\n");

        // getting absolute path
        final Path path = Paths.get("D:\\Projects\\OCP-Workspace\\IO and NIO\\src", "ConsoleTest.java");
        System.out.println("Getting absolute path\t:\t" + path);

        // getting relative path
        final Path path1 = Paths.get("IO and NIO\\src", "ConsoleTest.java");
        System.out.println("Getting relative path\t:\t" + path1);

        // getting relative path, by getting rid of backslashes and escape characters
        final Path path2 = Paths.get("IO and NIO", "src", "ConsoleTest.java");
        System.out.println("Without '\\' in the code\t:\t" + path2);
        final String fileName = path2.getFileName().toString();
        System.out.println("Getting file name\t:\t" + fileName);

        // getting path using Uniform Resource Identifier (URI) reference
        final Path path3 = Paths.get(URI.create("file:///D:/Projects/OCP-Workspace"));
        System.out.println("URL path\t\t:\t" + path3);

        addSeparator();
    }

    private void workingWithFilesAndDirectoriesExample() {

        System.out.println("WORKING WITH FILES AND DIRECTORIES\n");

        createADirectoryWithAFile();

        try {
            readFileFromExistingDirectory();
            createDeleteAndRenameAFileAndDirectory();
        } catch (final IOException e) {
            System.out.println("Exception caught");
        }

        loopThroughADirectory();

        addSeparator();

    }

    private void loopThroughADirectory() {
        final File search = new File("searchDirectory");
        System.out.println("\nLooping through directory\t:\t" + search.getName());
        String[] files = search.list();

        System.out.println("\nScanning the 'searchDirectory' directory for content...\n");
        for (String fn : files) {
            System.out.println("Found\t:\t" + fn);
        }
    }

    private void createDeleteAndRenameAFileAndDirectory() throws IOException {
        System.out.println("CREATE, DELETE AND RENAME A FILE AND A DIRECTORY\n");

        final File dirToDelete = new File("dirToDelete");
        dirToDelete.mkdir();
        System.out.println("New directory created\t\t\t:\t" + dirToDelete.getName());

        final File fileToDelete1 = new File(dirToDelete, "fileToDelete1.txt");
        fileToDelete1.createNewFile();
        System.out.println("New file created in the directory\t:\t" + fileToDelete1.getName());

        final File fileToDelete2 = new File(dirToDelete, "fileToDelete2.txt");
        fileToDelete2.createNewFile();
        System.out.println("New file created in the directory\t:\t" + fileToDelete2.getName());

        System.out.println("\nDeleting fileToDelete1.txt");
        System.out.println("File deleted\t\t\t:\t" + fileToDelete1.delete());

        System.out.println("\nTrying to delete 'dirToDelete' directory");
        System.out.println("\nDirectory can be deleted\t:\t" + dirToDelete.delete());
        if (!dirToDelete.delete()) {
            System.out.println("WarningMessage\t\t\t:\tCannot delete the directory because it is not empty!");
        }

        final File newFileName = new File(dirToDelete, "newFileName.txt");
        System.out.println("\nRenamed 'fileToDelete2txt' inside 'dirToDelete' directory to 'newFileName.txt'");
        fileToDelete2.renameTo(newFileName);

        final File newDirectory = new File("newDirectory");
        System.out.println("\nRenamed 'dirToDelete' directory to 'newDirectory'");
        dirToDelete.renameTo(newDirectory);
        
        addSeparator();
    }

    private void readFileFromExistingDirectory() throws IOException {
        final File existingDir = new File("existingDir");
        System.out.println("\nIs 'existingDir' a directory\t\t:\t" + existingDir.isDirectory());

        final File existingDirFile = new File(existingDir, "existingDirFile.txt");
        System.out.println("\nIs file 'existingDirFile' in this directory\t:\t" + existingDirFile.isFile());

        final FileReader fr = new FileReader(existingDirFile);
        final BufferedReader br = new BufferedReader(fr);
        System.out.println("\nScanning file for content...");
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println("Found\t:\t" + s);
        }
        br.close();
        addSeparator();
    }

    private void createAFileUsingPrintWriter() {
        final File file = new File("PrintWriterFile.txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            System.out.println("\nCreated a file using PrintWriter Class\t:\t" + file.getName());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void createADirectoryWithAFile() {
        final File myDir = new File("mydir");
        myDir.mkdir();
        System.out.println("Created a directory called\t\t:\t" + myDir.getName());
        final File myFile = new File(myDir, "myFile.txt");
        PrintWriter pw = null;
        try {
            myFile.createNewFile();
            System.out.println("Create a file in this directory called\t:\t" + myFile.getName());
            pw = new PrintWriter(myFile);
            pw.println("content inside the file");
            pw.flush();
            pw.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void createAFileExample() {

        System.out.println("CREATE A FILE EXAMPLES\n");

        createFileUsingCreateNewFileMethod();

        createAFileUsingPrintWriter();

        addSeparator();
    }

    private void createFileUsingCreateNewFileMethod() {
        System.out.println("Creating file using File's createNewFile() method\n");
        try {
            final File file = new File("fileExists.txt");
            System.out.println("File '" + file.getName() + "' exists\t:\t" + file.exists());
            file.createNewFile();
            System.out.println("\nFile created...");
            System.out.println("\nCheck again\t:\t" + file.exists());
        } catch (final IOException e) {
            System.out.println("Creating file caugth an Exception");
        }
    }

    private void fileWriterAndFileReaderExample() {
        System.out.println("FileWriter AND FileReader EXAMPLE\n");
        final char[] in = new char[20];
        int size = 0;

        final File file = new File("FileWithContent.txt");

        try (final FileWriter fw = new FileWriter(file); final FileReader fr = new FileReader(file)) {
            System.out.println("Created a file called\t:\t" + file.getName());
            fw.write("howdy folks");
            System.out.println("\nFilling the file with data...\n");
            fw.flush();
            size = fr.read(in);
            System.out.println("Character count in file content\t:\t" + size);
            System.out.print("Content value\t:\t");
            for (char c : in) {
                System.out.print(c);
            }
            System.out.println();
        } catch (final IOException e) {
            System.out.println("Reading and writing caught an Exception");
        }

        addSeparator();
    }

    private void combineIOClassesExample() {
        System.out.println("COMBINING IO CLASSES\n");
        
        final File file = new File("Combining IO Classes.txt");
        
        try (final FileWriter fw = new FileWriter(file); 
                final PrintWriter pw = new PrintWriter(fw)) {
            file.createNewFile();
            System.out.println("File created\t\t:\t" + file.getName());
            System.out.println("File wrapped inside FileWriter...");
            System.out.println("FileWriter wrapped inside PrintWriter...");
            pw.println("quasar");
            pw.print("2016");
            System.out.println("File filled in with some data...");
            pw.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        addSeparator();
    }

    private void anotherCombineIOClassesExample() {
        System.out.println("Combining IO Classes : Reading from a file\n");
        
        final File file = new File("Combining IO Classes Read from file.txt");
        
        try (final FileReader fr = new FileReader(file); final BufferedReader br = new BufferedReader(fr)) {
            System.out.println("Existing file\t:\t" + file.getName());
            System.out.println("File wrapped inside FileWriter...");
            System.out.println("FileWriter wrapped inside BufferedReader...");
            String data;
            while ((data = br.readLine()) != null) {
                System.out.println("Found\t:\t" + data);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        addSeparator();
    }

    private void addSeparator() {
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------");
    }

    private void cleanDirectories() {
        System.out.println("CLEANING DIRECTORIES...");

        // get fileWriteWithPath.txt
        final Path fileWriteWithPath = Paths.get("fileWriteWithPath.txt");

        // get FilesDirectory with content
        final Path program = Paths.get("FilesDirectory", "Directory", "Program.java");
        final Path directory = Paths.get("FilesDirectory", "Directory");
        final Path filesDirectory = Paths.get("FilesDirectory");

        // temp Directory with content
        final Path tempTest1 = Paths.get("temp", "test1");
        final Path tempTest2Txt = Paths.get("temp", "test2.txt");
        final Path temp = Paths.get("temp");

        // move2SameFilesToSameDirectory Directory
        final Path move2SameFilesToSameDirectory = Paths.get("move2SameFilesToSameDirectory");

        // copyDeleteAndMoveDirectory Directory
        final Path test2TxtInCopyDeleteAndMoveDirectory = Paths.get("CopyDeleteAndMoveDirectory", "test2");
        final Path copyDeleteAndMoveDirectory = Paths.get("CopyDeleteAndMoveDirectory");

        // PathToIterate Directory
        final Path myFileInPathToIterateDir = Paths.get("PathToIterate", "dir1", "dir2", "dir3", "MyFile.txt");
        final Path dir3InPathToIterateDir = Paths.get("PathToIterate", "dir1", "dir2", "dir3");
        final Path dir2InPathToIterateDir = Paths.get("PathToIterate", "dir1", "dir2");
        final Path dir1InPathToIterateDir = Paths.get("PathToIterate", "dir1");
        final Path pathToIterate = Paths.get("PathToIterate");

        // myDir Directory
        final Path myFileInMyDir = Paths.get("mydir", "myFile.txt");
        final Path myDir = Paths.get("mydir");

        // PrintWriterFile.txt File
        final Path printWriterFile = Paths.get("PrintWriterFile.txt");

        // dirToDelete directory with its content
        final Path fileToDelete2 = Paths.get("dirToDelete", "fileToDelete2.txt");
        final Path newFileName = Paths.get("dirToDelete", "newFileName.txt");
        final Path dirToDelete = Paths.get("dirToDelete");

        // newDirectory with its content
        final Path newFileNameFile = Paths.get("newDirectory", "newFileName.txt");
        final Path newDirectory = Paths.get("newDirectory");
        
        // FileWithContent.txt File
        final Path fileWithContent = Paths.get("FileWithContent.txt");

        // fileExists.txt File
        final Path fileExists = Paths.get("fileExists.txt");
        
        // Combining IO Classes.txt File
        final Path combiningIOClasses = Paths.get("Combining IO Classes.txt");
        
        try {
            // Delete fileWriteWithPath.txt
            Files.deleteIfExists(fileWriteWithPath);

            // Delete FilesDirectory with content
            Files.deleteIfExists(program);
            Files.deleteIfExists(directory);
            Files.deleteIfExists(filesDirectory);

            // Delete temp directory with content
            Files.deleteIfExists(tempTest1);
            Files.deleteIfExists(tempTest2Txt);
            Files.deleteIfExists(temp);

            // Delete move2SameFilesToSameDirectory directory with content
            Files.deleteIfExists(move2SameFilesToSameDirectory);

            // Delete copyDeleteAndMoveDirectory directory with content
            Files.deleteIfExists(test2TxtInCopyDeleteAndMoveDirectory);
            Files.deleteIfExists(copyDeleteAndMoveDirectory);

            // Delete PathToIterate directory with content
            Files.deleteIfExists(myFileInPathToIterateDir);
            Files.deleteIfExists(dir3InPathToIterateDir);
            Files.deleteIfExists(dir2InPathToIterateDir);
            Files.deleteIfExists(dir1InPathToIterateDir);
            Files.deleteIfExists(pathToIterate);

            // Delete myDir directory with content
            Files.deleteIfExists(myFileInMyDir);
            Files.deleteIfExists(myDir);
            
            // Delete dirToDelete directory with content
            Files.deleteIfExists(fileToDelete2);
            Files.deleteIfExists(newFileName);
            Files.deleteIfExists(dirToDelete);
            
            // Delete newDirectory directory with content
            Files.deleteIfExists(newFileNameFile);
            Files.deleteIfExists(newDirectory);

            // Delete PrintWriterFile.txt file
            Files.deleteIfExists(printWriterFile);

            // Delete FileWithContent.txt file
            Files.deleteIfExists(fileWithContent);

            // Delete fileExist.txt file
            Files.deleteIfExists(fileExists);
            
            // Delete Combining IO Classes.txt file
            Files.deleteIfExists(combiningIOClasses);

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
