package org.matrixstudio.boot.repository.jpa;

import org.matrixstudio.boot.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, java.lang.String> {
}
