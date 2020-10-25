package ru.niiar.cdr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.cdr.model.Division;
import ru.niiar.cdr.model.Subscriber;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Integer> {
    Optional<Subscriber> getSubscriberByFullName(String string);
    Optional<Subscriber> getSubscriberByInternalNum(int num);
    List<Subscriber> findAllByOrderByInternalNumAsc();
    Iterable<Subscriber> findTop100ByOrderByInternalNumAsc();
    Iterable<Subscriber> findByInternalNumOrExternalNum(int int_num, long ext_num);
    Iterable<Subscriber> findByFullNameContains(String string);
    Optional<Subscriber> findByExternalNum(Long ext_num);
    List<Subscriber> findAllByDivision(Division division);

}
