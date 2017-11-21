package org.matrixstudio.boot.repository.mongodb;

import org.matrixstudio.boot.model.document.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<Audit, java.lang.String> {
}
