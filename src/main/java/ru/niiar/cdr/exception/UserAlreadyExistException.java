package ru.niiar.cdr.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message){
        super(message);
    }

    public UserAlreadyExistException(){}
}
