package com.mmbackendassignment.mmbackendassignment.exception;

public class FileExtensionNotSupportedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileExtensionNotSupportedException( String extension ) {
        super("File extension not supported: ." + extension);
    }
}
