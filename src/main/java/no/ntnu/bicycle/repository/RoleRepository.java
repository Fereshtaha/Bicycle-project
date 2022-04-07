package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer>  {
}
