package be.vdab.training.exceptions;

/**
 * Created by admin on 24/10/2017.
 */
public class MyDomainException extends Throwable {
    public MyDomainException(String s, String employee) {
        super(s);
    }
}
