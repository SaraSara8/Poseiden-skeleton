package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


public interface CurvePointService {


    public List<CurvePoint> findAllCurvePoints();


    public CurvePoint insert(CurvePoint curvePoint) ;

    public void delete(CurvePoint curvePoint);

    public CurvePoint findCurvePoint(Integer id);

    public boolean existsById(int id);

}
