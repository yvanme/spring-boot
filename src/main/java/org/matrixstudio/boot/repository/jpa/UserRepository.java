package org.matrixstudio.boot.repository.jpa;

import org.matrixstudio.boot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, java.lang.String> {

}
