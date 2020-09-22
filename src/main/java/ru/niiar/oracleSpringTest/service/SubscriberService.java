package ru.niiar.oracleSpringTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.niiar.oracleSpringTest.dao.SubscriberRepository;
import ru.niiar.oracleSpringTest.model.Subscriber;

import java.util.Collections;
import java.util.List;

@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;

    @Autowired
    public void setSubscriberRepository(SubscriberRepository subscriberRepository){
        this.subscriberRepository = subscriberRepository;
    }



    public Page<Subscriber> findPaginated(Pageable pageable){
      List<Subscriber> subscribers = subscriberRepository.findAllByOrderByInternalNumAsc();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Subscriber> list;


        if (subscribers.size() < startItem){
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, subscribers.size());
            list = subscribers.subList(startItem, toIndex);
        }

        Page<Subscriber> subPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), subscribers.size());
        return subPage;
    }


}
