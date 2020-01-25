package ru.niiar.oracleSpringTest.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message){
        super(message);
    }

    public UserAlreadyExistException(){}
}
