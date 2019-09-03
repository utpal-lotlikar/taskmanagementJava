package org.ulotlikar.taskmgmt.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.ulotlikar.taskmgmt.entity.Task;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Before
    public void setUp(){
        // given
        Task task = new Task();
        task.setName("Sample task");
        task.setDescription("This is a first task");
        task.setDateCreated(new Date());
        task.setLastUpdated(new Date());

        testEntityManager.persist(task);
    }

    @Test
    public void whenFindByName_thenReturnTask() {
        // when
        Task task = taskRepository.findByName("Sample task").get();

        // then
        assertThat(task.getDescription()).isEqualTo("This is a first task");
    }

    @Test
    public void whenFindAll_thenReturnTaskList() {
        // when
        List<Task> tasks = taskRepository.findAll();

        // then
        assertThat(tasks).hasSize(1);
    }
}
