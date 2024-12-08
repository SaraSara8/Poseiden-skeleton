package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RuleNameService {



    public List<RuleName> findAllRuleNames() ;

    public RuleName insert(RuleName ruleName) ;



    public void delete(RuleName ruleName) ;


    public RuleName findRuleName(Integer id);

    public boolean existsById(int id);
}






