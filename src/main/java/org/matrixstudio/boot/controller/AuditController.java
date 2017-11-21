package org.matrixstudio.boot.controller;

import org.matrixstudio.boot.model.document.Audit;
import org.matrixstudio.boot.repository.mongodb.AuditRepository;
import org.matrixstudio.boot.resource.AuditResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audits")
@ExposesResourceFor(Audit.class)
public class AuditController {

    private final Assembler auditAssembler = new Assembler();

    @Autowired
    private AuditRepository auditRepository;

    @GetMapping
    public PagedResources<AuditResource> list(Pageable pageable, @RequestParam(required = false) String search,
                                              PagedResourcesAssembler<Audit> assembler) {
        return assembler.toResource(auditRepository.findAll(pageable), auditAssembler);
    }

    @GetMapping("/{oid}")
    public AuditResource get(@PathVariable String oid) {
        Audit audit = auditRepository.findOne(oid);
        if (audit == null) {
            throw new RuntimeException("Audit[" + oid + "] not found.");
        }

        return auditAssembler.toResource(audit);
    }

    @PostMapping
    public AuditResource save(@RequestBody Audit audit) {
        return auditAssembler.toResource(auditRepository.save(audit));
    }

    class Assembler extends ResourceAssemblerSupport<Audit, AuditResource> {

        public Assembler() {
            super(AuditController.class, AuditResource.class);
        }

        @Override
        public AuditResource toResource(Audit audit) {
            return createResourceWithId(audit.getOid(), audit);
        }

        @Override
        protected AuditResource instantiateResource(Audit entity) {
            return new AuditResource(entity);
        }
    }
}
