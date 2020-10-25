package ru.niiar.cdr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.cdr.model.Division;
import ru.niiar.cdr.model.RosDetalization;

import java.util.List;

@Repository
public interface RosDetalizationRepository extends CrudRepository<RosDetalization, Integer> {
    RosDetalization findByDateTimeIsContaining(String dateTime);
    List<RosDetalization> findAllBySubscriberDivision(Division division);
}
