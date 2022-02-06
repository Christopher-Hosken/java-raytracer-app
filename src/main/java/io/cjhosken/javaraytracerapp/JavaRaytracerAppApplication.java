package io.cjhosken.javaraytracerapp;

import io.cjhosken.javaraytracerapp.jrs.JRSFile;

public class JavaRaytracerAppApplication {
    public static void main(String args[]) {
        JRSFile file = new JRSFile();

        file.toJSON();
    }
}