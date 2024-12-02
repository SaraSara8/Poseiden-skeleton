package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingService {

    private final RatingRepository ratingRepository;


    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> findAllRating(){

        return ratingRepository.findAll();

    }

    public Rating insert(Rating rating){

        return ratingRepository.save(rating);
    }

    public void delete(Rating rating){

        ratingRepository.deleteById(rating.getId());

    }

    public Rating findRating(Integer id){

        return ratingRepository.findById(id).orElse(null);


    }
}
