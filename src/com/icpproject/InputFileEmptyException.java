package com.icpproject;

import java.io.FileNotFoundException;

public class InputFileEmptyException extends Exception {
    public InputFileEmptyException() {
        this ("Input file is empty");
    }

    public InputFileEmptyException (String message) {
        super(message);
    }
}
