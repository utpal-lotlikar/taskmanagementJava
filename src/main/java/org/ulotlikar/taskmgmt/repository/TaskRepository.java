package org.ulotlikar.taskmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulotlikar.taskmgmt.entity.Task;

import java.util.Optional;

/**
 * @author Utpal Lotlikar
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);
    Optional<Task> findByName(String name);

}
