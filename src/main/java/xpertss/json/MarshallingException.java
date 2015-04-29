package xpertss.json;

/**
 * Thrown by the marshalling and unmarshalling process to indicate an
 * error has occurred.
 */
public class MarshallingException extends RuntimeException {


    public MarshallingException()
    {
        super();
    }

    public MarshallingException(String msg)
    {
        super(msg);
    }


    public MarshallingException(Throwable cause)
    {
        super(cause);
    }


    public MarshallingException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

}
