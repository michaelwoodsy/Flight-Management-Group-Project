package project.model;

import java.io.File;

public class Loader {

    /**
     * Ensures loaded file is correct file format.
     */
    public boolean errorHandler(File file) {
        String extension = "";

        int i = file.getAbsolutePath().lastIndexOf('.');
        int p = Math.max(file.getAbsolutePath().lastIndexOf('/'), file.getAbsolutePath().lastIndexOf('\\'));

        if (i > p) {
            extension = file.getAbsolutePath().substring(i+1);
        }
        if (!extension.equals("dat") && !extension.equals("csv")) {
            return false;
        } else {
            return true;
        }
    }
}
