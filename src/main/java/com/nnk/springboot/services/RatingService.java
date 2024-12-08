package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {


    public List<Rating> findAllRating();

    public Rating insert(Rating rating);



    public void delete(Rating rating);



    public Rating findRating(Integer id);


    public boolean existsById(int id);
}
