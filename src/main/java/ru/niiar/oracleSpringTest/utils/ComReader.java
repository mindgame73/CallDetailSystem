package ru.niiar.oracleSpringTest.utils;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class ComReader {

    //@Autowired
    private EventListener eventListener;

    //@Autowired
    private SerialPort serialPort;

    @Value("${serialPort.baudRate}")
    private int baudRate;
    @Value("${serialPort.dataBits}")
    private int dataBits;
    @Value("${serialPort.stopBits}")
    private int stopBits;
    @Value("${serialPort.parity}")
    private int parity;

    @PostConstruct
    public void init(){
        try {
            System.out.println("Opening port");
            serialPort.openPort();
            serialPort.setParams(baudRate,dataBits,stopBits,parity);
            serialPort.addEventListener(eventListener, 1);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
