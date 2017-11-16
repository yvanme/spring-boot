package org.matrixstudio.boot.service;

import org.matrixstudio.boot.model.entity.Enterprise;
import org.matrixstudio.boot.repository.jpa.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "#root.targetClass.name", value = "enterprises")
    public Page<Enterprise> list(Pageable pageable, String search) {
        return enterpriseRepository.findAll(pageable);
    }
}
