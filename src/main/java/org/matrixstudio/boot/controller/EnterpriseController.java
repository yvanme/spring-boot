package org.matrixstudio.boot.controller;

import org.matrixstudio.boot.model.entity.Enterprise;
import org.matrixstudio.boot.repository.jpa.EnterpriseRepository;
import org.matrixstudio.boot.resource.EnterpriseResource;
import org.matrixstudio.boot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprises")
@ExposesResourceFor(Enterprise.class)
public class EnterpriseController {

    private final Assembler enterpriseAssembler = new Assembler();

    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping
    public PagedResources<EnterpriseResource> list(Pageable pageable, @RequestParam(required = false) String search,
                                                   PagedResourcesAssembler<Enterprise> assembler) {
        return assembler.toResource(enterpriseService.list(pageable, search), enterpriseAssembler);
    }

    @GetMapping("/{oid}")
    public EnterpriseResource get(@PathVariable Long oid) {
        Enterprise enterprise = enterpriseRepository.findOne(oid);
        if (enterprise == null) {
            throw new RuntimeException("Enterprise[" + oid + "] not found.");
        }
        return enterpriseAssembler.toResource(enterprise);
    }

    @PostMapping
    public EnterpriseResource save(@RequestBody Enterprise enterprise) {
        Enterprise newObject = enterpriseRepository.save(enterprise);
        return enterpriseAssembler.toResource(newObject);
    }

    class Assembler extends ResourceAssemblerSupport<Enterprise, EnterpriseResource> {

        public Assembler() {
            super(EnterpriseController.class, EnterpriseResource.class);
        }

        @Override
        public EnterpriseResource toResource(Enterprise enterprise) {
            return createResourceWithId(enterprise.getOid(), enterprise);
        }

        @Override
        protected EnterpriseResource instantiateResource(Enterprise entity) {
            return new EnterpriseResource(entity);
        }
    }
}
