package com.pos.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Created by rrampall on 15/03/18.
 */
public class PosApplicationException extends Exception {

    @Getter @Setter
    HttpStatus httpStatus;

    @Getter @Setter
    String message;

    @Getter @Setter
    Object[] msgArguments;


    public PosApplicationException() {
        super();
    }

    public PosApplicationException( HttpStatus httpStatus, String message, Object[] errMsgArguments, Throwable cause) {
        super(cause);
        initialize(message, httpStatus, errMsgArguments);

    }

    public PosApplicationException( HttpStatus httpStatus, String message , Object[] errMsgArguments) {
        super(message);
        initialize(message, httpStatus, errMsgArguments);
    }

    public PosApplicationException(Throwable cause) {
        super(cause);

        //add this, because the framework will create a new wrapper exception on our cliqrApplicationExcetion
        //and this new warrper is used in ErrorResolverImpl, so need to init the wrapper also
        //otherwise will have NPE, and all exception becomes 500
        if (cause instanceof PosApplicationException) {
            PosApplicationException cqe = (PosApplicationException) cause;
            initialize( cqe.getMessage(), cqe.getHttpStatus(), cqe.getMsgArguments());
        }
    }

    public PosApplicationException(String message) {
        super(message);
    }

    public PosApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    private void initialize(String message, HttpStatus status, Object[] args){
        this.message = message;
        this.httpStatus = status;
        this.msgArguments = args;
    }



}
