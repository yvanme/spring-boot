package org.matrixstudio.boot.resource;

import org.matrixstudio.boot.model.entity.Enterprise;
import org.springframework.hateoas.ResourceSupport;

public class EnterpriseResource extends ResourceSupport {
    private Enterprise enterprise;

    public EnterpriseResource(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }
}
