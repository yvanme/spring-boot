package org.matrixstudio.boot.resource;

import org.matrixstudio.boot.model.document.Audit;
import org.springframework.hateoas.ResourceSupport;

public class AuditResource extends ResourceSupport {

    private Audit audit;

    public AuditResource(Audit audit) {
        this.audit = audit;
    }

    public Audit getAudit() {
        return audit;
    }
}
