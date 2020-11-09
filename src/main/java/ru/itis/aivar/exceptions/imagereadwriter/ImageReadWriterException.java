package ru.itis.aivar.exceptions.imagereadwriter;

public class ImageReadWriterException extends RuntimeException{
    public ImageReadWriterException() {
        super();
    }

    public ImageReadWriterException(String message) {
        super(message);
    }

    public ImageReadWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageReadWriterException(Throwable cause) {
        super(cause);
    }

    protected ImageReadWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
