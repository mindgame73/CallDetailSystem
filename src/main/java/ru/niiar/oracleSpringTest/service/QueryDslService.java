package ru.niiar.oracleSpringTest.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.niiar.oracleSpringTest.model.QCallDetailRecord;
import ru.niiar.oracleSpringTest.model.QSubscriber;
import ru.niiar.oracleSpringTest.model.QUser;
import ru.niiar.oracleSpringTest.model.Subscriber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueryDslService{

    @Autowired
    private EntityManager entityManager;

    public List<Subscriber> getSubscriberByFilter(Subscriber subscriber){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QSubscriber sub = QSubscriber.subscriber;
        JPAQuery<?> query = queryFactory.from(sub);
                if (!subscriber.getFullName().equals(""))
                    query.where(sub.fullName.containsIgnoreCase(subscriber.getFullName()));
                if (subscriber.getInternalNum() != null)
                    query.where(sub.internalNum.eq(subscriber.getInternalNum()));
                if (subscriber.getExternalNum() != null)
                    if (subscriber.getExternalNum().toString().length() == 5)
                        query.where(sub.externalNum.eq(8423500000L + subscriber.getExternalNum()));
                    else
                        query.where(sub.externalNum.eq(subscriber.getExternalNum()));
                if (!subscriber.getBuilding().equals(""))
                    query.where(sub.building.containsIgnoreCase(subscriber.getBuilding()));
                if (!subscriber.getRoom().equals(""))
                    query.where(sub.room.containsIgnoreCase(subscriber.getRoom()));
                if (subscriber.getDivision().getDivision_id() != 0)
                    query.where(sub.division.division_id.eq(subscriber.getDivision().getDivision_id()));
                if (!subscriber.getSubDescr().equals(""))
                    query.where(sub.subDescr.containsIgnoreCase(subscriber.getSubDescr()));
                if (!subscriber.getGpStrip().equals(""))
                    query.where(sub.gpStrip.containsIgnoreCase(subscriber.getGpStrip()));
                if (!subscriber.getAllocation().equals(""))
                    query.where(sub.allocation.containsIgnoreCase(subscriber.getAllocation()));
                if (subscriber.isDigital())
                    query.where(sub.isDigital.eq(true));
                if (!subscriber.getCos().equals(""))
                    query.where(sub.cos.containsIgnoreCase(subscriber.getCos()));
                if (subscriber.isFax())
                    query.where(sub.isFax.eq(true));
                if (subscriber.isSip())
                    query.where(sub.isSip.eq(true));

        return (List<Subscriber>) query.fetch();
    }

    public List<Object[]> getDivisionCountCalls(String monthId, String year){
        if (monthId == null && year == null) {
            List<Object[]> result = entityManager
                    .createNativeQuery("select DIVISION_NAME, count(d.DIVISION_NAME) as num " +
                            " from CALL_DETAIL_RECORDS inner join SUBSCRIBERS S on CALL_DETAIL_RECORDS.SUBA_ID = S.SUB_ID " +
                            " inner join DIVISIONS D on  S.DIVISION_ID = D.DIVISION_ID " +
                            " where to_char(start_time, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            " group by d.DIVISION_NAME order by num DESC ").getResultList();
            return result;
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select DIVISION_NAME, count(d.DIVISION_NAME) as num from CALL_DETAIL_RECORDS " +
                            " inner join SUBSCRIBERS S on CALL_DETAIL_RECORDS.SUBA_ID = S.SUB_ID " +
                            " inner join DIVISIONS D on  S.DIVISION_ID = D.DIVISION_ID  " +
                            " where to_char(start_time, 'MM-YYYY') = ?1  " +
                            " group by d.DIVISION_NAME order by num DESC");
            query.setParameter(1,monthyear);
            return query.getResultList();
        }
    }

    public List<Object[]> getPeopleDivisionCountCalls(Integer id, String monthId, String year){
        if (monthId == null) {
            Query query = entityManager
                    .createNativeQuery("select CONCAT(FULL_NAME,INTERNAL_NUM) as info, count(s.INTERNAL_NUM) as counter " +
                            "    from CALL_DETAIL_RECORDS " +
                            "    inner join SUBSCRIBERS S on CALL_DETAIL_RECORDS.SUBA_ID = S.SUB_ID " +
                            "    inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "    where d.DIVISION_ID = ?1 AND to_char(start_time, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            "    group by s.INTERNAL_NUM, FULL_NAME\n" +
                            "    order by counter DESC");
            query.setParameter(1, id);
            return query.getResultList();
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select CONCAT(FULL_NAME,INTERNAL_NUM) as info, count(s.INTERNAL_NUM) as counter " +
                            "    from CALL_DETAIL_RECORDS " +
                            "    inner join SUBSCRIBERS S on CALL_DETAIL_RECORDS.SUBA_ID = S.SUB_ID " +
                            "    inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "    where d.DIVISION_ID = ?1 AND to_char(start_time, 'MM-YYYY') = ?2 " +
                            "    group by s.INTERNAL_NUM, FULL_NAME\n" +
                            "    order by counter DESC");
            query.setParameter(1, id);
            query.setParameter(2, monthyear);
            return query.getResultList();
        }
    }

    public List<Object[]> getRosDetalizationCountCalls(String monthId, String year) {
        if (monthId == null && year == null) {
            List<Object[]> result = entityManager
                    .createNativeQuery("select D.DIVISION_NAME, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            "group by DIVISION_NAME " +
                            "order by sum_cost desc").getResultList();
            return result;
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select D.DIVISION_NAME, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = ?1 " +
                            "group by DIVISION_NAME " +
                            "order by sum_cost desc");
            query.setParameter(1,monthyear);
            return query.getResultList();
        }
    }

    public List<Object[]> getPeopleRosDetalizationCountCalls(Integer id, String monthId, String year) {
        if (monthId == null) {
            Query query = entityManager
                    .createNativeQuery("select CONCAT(FULL_NAME, EXTERNAL_NUM) as info, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where d.DIVISION_ID = ?1 AND to_char(DATE_TIME, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            "group by S.EXTERNAL_NUM, FULL_NAME " +
                            "order by sum_cost desc");
            query.setParameter(1, id);
            return query.getResultList();
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select CONCAT(FULL_NAME, EXTERNAL_NUM) as info, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where d.DIVISION_ID = ?1 AND to_char(DATE_TIME, 'MM-YYYY') = ?2 " +
                            "group by S.EXTERNAL_NUM, FULL_NAME " +
                            "order by sum_cost desc");
            query.setParameter(1, id);
            query.setParameter(2, monthyear);
            return query.getResultList();
        }
    }

    public List<Object[]> getAllRouteCount(String monthId, String year) {
        if (monthId == null && year == null) {
            List<Object[]> result = entityManager
                    .createNativeQuery("select ROUTE, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            "group by ROUTE " +
                            "order by sum_cost desc").getResultList();
            return result;
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select ROUTE, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = ?1 " +
                            "group by ROUTE " +
                            "order by sum_cost desc fetch next 50 rows only");
            query.setParameter(1,monthyear);
            return query.getResultList();
        }
    }

    public List<Object[]> getCountByRouteName(String route, String monthId, String year) {
        if (monthId == null) {
            Query query = entityManager
                    .createNativeQuery("select DIVISION_NAME, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = (select to_char(current_date, 'MM-YYYY') from dual) " +
                            "AND ROUTE = ?1 " +
                            "group by DIVISION_NAME " +
                            "order by sum_cost desc " +
                            "FETCH NEXT 15 ROWS ONLY");
            query.setParameter(1, route);
            return query.getResultList();
        }
        else
        {
            String monthyear = monthId.concat("-").concat(year);
            Query query = entityManager
                    .createNativeQuery("select DIVISION_NAME, SUM(R.COST) as sum_cost from ROS_DETALIZATION R " +
                            "inner join SUBSCRIBERS S on R.SUB_ID = S.SUB_ID " +
                            "inner join DIVISIONS D on S.DIVISION_ID = D.DIVISION_ID " +
                            "where to_char(DATE_TIME, 'MM-YYYY') = ?1 AND ROUTE = ?2 " +
                            "group by DIVISION_NAME " +
                            "order by sum_cost desc " +
                            "FETCH NEXT 15 ROWS ONLY");
            query.setParameter(1, monthyear);
            query.setParameter(2, route);
            return query.getResultList();
        }
    }
}