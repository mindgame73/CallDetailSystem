package ru.niiar.oracleSpringTest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.oracleSpringTest.model.ResultCode;
import java.util.Optional;

@Repository
public interface ResultCodeRepository extends CrudRepository<ResultCode, Integer> {
    Optional<ResultCode> getResultCodeByResultCode(String string);
}
