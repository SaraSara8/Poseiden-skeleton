package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TradeService {

    public List<Trade> findAllTrade() ;

    public Trade insert(Trade trade) ;

    @Transactional
    public void delete(Trade trade);

    public Trade findTrade(Integer id);

    public boolean existsById(int id);

}
