package com.agh.gopr.app.exception;

public class MethodException extends GoprException {
    public MethodException(String detailMessage) {
        super(detailMessage);
    }

    public static class EmptyMethodDefinitionException extends MethodException {

        public EmptyMethodDefinitionException(String detailMessage) {
            super(detailMessage);
        }
    }

    public static class WrongParameterException extends MethodException {

        public WrongParameterException(String detailMessage) {
            super(detailMessage);
        }
    }

    public static class WrongNumberOfParametersException extends MethodException {

        public WrongNumberOfParametersException(String detailMessage) {
            super(detailMessage);
        }
    }
}
