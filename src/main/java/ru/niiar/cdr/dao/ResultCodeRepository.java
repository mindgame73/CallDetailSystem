package ru.niiar.cdr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.cdr.model.ResultCode;
import java.util.Optional;

@Repository
public interface ResultCodeRepository extends CrudRepository<ResultCode, Integer> {
    Optional<ResultCode> getResultCodeByResultCode(String string);
}
