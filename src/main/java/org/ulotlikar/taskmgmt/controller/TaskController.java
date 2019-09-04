package org.ulotlikar.taskmgmt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.ulotlikar.taskmgmt.bean.ResponseCode;
import org.ulotlikar.taskmgmt.bean.RestResponse;
import org.ulotlikar.taskmgmt.entity.Task;
import org.ulotlikar.taskmgmt.repository.TaskRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/tasks")
    @Transactional
    public RestResponse getTasks(Pageable pageable) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request for GET tasks");

        try {
            Page<Task> task = taskRepository.findAll(pageable);
            restResponse.setData(task);
            restResponse.setTotalRecords(task.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to load tasks");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @PostMapping("/tasks")
    @Transactional
    public RestResponse createTask(@Valid @RequestBody Task task) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to create task");

        try {
            Task taskInstance = taskRepository.save(task);
            restResponse.setData(taskInstance);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to create task");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @PutMapping("/tasks/{taskId}")
    @Transactional
    public RestResponse updateTask(@PathVariable Long taskId, @Valid @RequestBody Task taskRequest) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to update task");

        try {
            Task taskInstance = taskRepository.findById(taskId).map(task -> {
                task.setName(taskRequest.getName());
                task.setDescription(taskRequest.getDescription());
                return taskRepository.save(task);
            }).orElseThrow(() -> new Exception("TaskId " + taskId + " not found"));

            restResponse.setData(taskInstance);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to update task");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @DeleteMapping("/tasks/{taskId}")
    @Transactional
    public RestResponse deleteTask(@PathVariable Long taskId) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to delete task");

        try {
            taskRepository.findById(taskId).map(task -> {
                taskRepository.delete(task);
                return null;
            }).orElseThrow(() -> new Exception("TaskId " + taskId + " not found"));
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to update task");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }
}
