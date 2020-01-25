package ru.niiar.oracleSpringTest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.oracleSpringTest.model.Division;
import ru.niiar.oracleSpringTest.model.RosDetalization;

import java.util.List;

@Repository
public interface RosDetalizationRepository extends CrudRepository<RosDetalization, Integer> {
    RosDetalization findByDateTimeIsContaining(String dateTime);
    List<RosDetalization> findAllBySubscriberDivision(Division division);
}
