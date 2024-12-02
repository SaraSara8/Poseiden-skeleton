package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public List<CurvePoint> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }


    public CurvePoint insert(CurvePoint curvePoint) {


        return curvePointRepository.save(curvePoint);
    }

    public void delete(CurvePoint curvePoint){

         curvePointRepository.deleteById(curvePoint.getId());

    }

    public CurvePoint findCurvePoint(Integer id){

        return curvePointRepository.findById(id).orElse(null);


    }



}
