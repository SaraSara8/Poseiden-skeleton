package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class BidListService {


        private final BidListRepository bidListRepository;

        public BidListService(BidListRepository bidListRepository) {
            this.bidListRepository = bidListRepository;
        }

        public List<BidList> findAll() {
            return bidListRepository.findAll();
        }

        public BidList findById(Integer id) {
            return bidListRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
        }

        public BidList save(BidList bidList) {
            return bidListRepository.save(bidList);
        }

        public void delete(BidList bidList) {
            bidListRepository.deleteById(bidList.getId());
        }

        public boolean existsById(int id) {
            return bidListRepository.existsById(id);
        }
    }

