package org.ulotlikar.taskmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulotlikar.taskmgmt.entity.TaskComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Utpal Lotlikar
 *
 */
@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    Page<TaskComment> findByTaskId(Long taskId, Pageable pageable);
    Optional<TaskComment> findByIdAndTaskId(Long id, Long taskId);
}
