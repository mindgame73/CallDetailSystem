package ru.niiar.oracleSpringTest.utils;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.niiar.oracleSpringTest.dao.CallDetailRecordRepository;
import ru.niiar.oracleSpringTest.dao.ResultCodeRepository;
import ru.niiar.oracleSpringTest.dao.SubscriberRepository;
import ru.niiar.oracleSpringTest.model.CallDetailRecord;

@Service
public class EventListener implements SerialPortEventListener {


    private SerialPort serialPort;
    private CallDetailRecordRepository callDetailRecordRepository;
    private ResultCodeRepository resultCodeRepository;
    private SubscriberRepository subscriberRepository;

    //@Autowired
    public void setSerialPort(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    @Autowired
    public void setCallDetailRecordRepository(CallDetailRecordRepository callDetailRecordRepository){
        this.callDetailRecordRepository = callDetailRecordRepository;
    }
    @Autowired
    public void setResultCodeRepository(ResultCodeRepository resultCodeRepository){
        this.resultCodeRepository = resultCodeRepository;
    }

    @Autowired
    public void setSubscriberRepository(SubscriberRepository subscriberRepository){
        this.subscriberRepository = subscriberRepository;
    }


        @Override
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR()){
            try {
                String call = serialPort.readString(event.getEventValue());
                System.out.println(call);
                parseAndPersist(call);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseAndPersist(String str){
            CallDetailRecord cdr = new CallDetailRecord();
            String[] parsedString = str.split(";");
            cdr.setBoard_cdr_id(Integer.parseInt(parsedString[0]));
            cdr.setStartTime(parsedString[1] + " " + parsedString[2]);
            if (!parsedString[3].isEmpty()) cdr.setStopTime(parsedString[1] + " " +
                    parsedString[3]);
            cdr.setFullTime(Integer.parseInt(parsedString[4]));
            cdr.setVoiceTime(Integer.parseInt(parsedString[5]));
            cdr.setNumberB(Long.parseLong(parsedString[6]));
            if (!parsedString[7].isEmpty()) {
                cdr.setNumberA(Long.parseLong(parsedString[7]));
                boolean subFound = subscriberRepository.findByExternalNum(Long.parseLong(parsedString[7])).isPresent();
                if (subFound) cdr.setSubscriber(subscriberRepository.findByExternalNum(Long.parseLong(parsedString[7])).get());
            }
            cdr.setMount_a(Integer.parseInt(parsedString[8]));
            cdr.setMount_b(Integer.parseInt(parsedString[9]));
            cdr.setResultCode(parsedString[10]);
            boolean rsFound = resultCodeRepository.getResultCodeByResultCode(parsedString[10]).isPresent();
            if(rsFound) cdr.setResultCodeObj(resultCodeRepository.getResultCodeByResultCode(parsedString[10]).get());
            cdr.setRoutingTable(Integer.parseInt(parsedString[11]));
            cdr.setFlowPort(Integer.parseInt(parsedString[12].trim()));
            callDetailRecordRepository.save(cdr);
    }
}
