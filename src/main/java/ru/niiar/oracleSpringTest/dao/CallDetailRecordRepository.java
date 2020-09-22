package ru.niiar.oracleSpringTest.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.oracleSpringTest.model.CallDetailRecord;
import ru.niiar.oracleSpringTest.model.Division;
import ru.niiar.oracleSpringTest.model.Subscriber;

import java.util.List;


@Repository
public interface CallDetailRecordRepository extends CrudRepository<CallDetailRecord, Integer> {
    Iterable<CallDetailRecord> findAllByOrderByStartTimeDesc();
    Iterable<CallDetailRecord> findAllBySubscriberA(Subscriber subscriber);
    Iterable<CallDetailRecord> findAllBySubscriberB(Subscriber subscriber);
    Iterable<CallDetailRecord> findAllByNumberAOrNumberB(Long numberA, Long numberB);
    Iterable<CallDetailRecord> findAllBySubscriberAFullNameContains(String fullName);
    Iterable<CallDetailRecord> findAllBySubscriberBFullNameContains(String fullName);
    List<CallDetailRecord> findAllBySubscriberADivision(Division division);
    List<CallDetailRecord> findAllBySubscriberBDivision(Division division);
    List<CallDetailRecord> findAllBySubscriberADivisionOrderByStartTime(Division division);
    List<CallDetailRecord> findAllBySubscriberBDivisionOrderByStartTime(Division division);


}
