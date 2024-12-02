package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    private final RuleNameRepository  ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {

        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAllRuleNames() {

        return ruleNameRepository.findAll();
    }

    public RuleName insert(RuleName ruleName) {


        return ruleNameRepository.save(ruleName);
    }

    public void delete(RuleName ruleName) {

        ruleNameRepository.deleteById(ruleName.getId());

    }

    public RuleName findRating(Integer id){

        return ruleNameRepository.findById(id).orElse(null);


    }
}






