package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;


    public interface BidListService {
        public List<BidList> findAll() ;

        public BidList findBidList(Integer id) ;

        public BidList insert(BidList bidList) ;

        public void delete(BidList bidList);

        public boolean existsById(int id) ;
    }

