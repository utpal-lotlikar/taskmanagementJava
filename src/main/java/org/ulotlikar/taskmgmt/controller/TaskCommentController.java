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
import org.ulotlikar.taskmgmt.entity.TaskComment;
import org.ulotlikar.taskmgmt.repository.TaskCommentRepository;
import org.ulotlikar.taskmgmt.repository.TaskRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class TaskCommentController {
    @Autowired
    private TaskCommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;

    private static Logger logger = LoggerFactory.getLogger(TaskCommentController.class);

    @GetMapping("/tasks/{taskId}/comments")
    @Transactional
    public RestResponse getAllCommentsByTaskId(@PathVariable(value = "taskId") Long taskId,
                                               Pageable pageable) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request for GET task comments");

        try {
            Page<TaskComment> taskComments = commentRepository.findByTaskId(taskId, pageable);
            restResponse.setData(taskComments);
            restResponse.setTotalRecords(taskComments.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to load task comments");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @PostMapping("/tasks/{taskId}/comments")
    @Transactional
    public RestResponse createComment(@PathVariable (value = "taskId") Long taskId,
                                 @Valid @RequestBody TaskComment comment) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to create task comments");

        try {
            TaskComment commentInstance = taskRepository.findById(taskId).map(task -> {
                comment.setTask(task);
                return commentRepository.save(comment);
            }).orElseThrow(() -> new Exception("TaskId " + taskId + " not found"));
            restResponse.setData(commentInstance);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to load task comments");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @PutMapping("/tasks/{taskId}/comments/{commentId}")
    @Transactional
    public RestResponse updateComment(@PathVariable (value = "taskId") Long taskId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody TaskComment commentRequest) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to update task comments");

        try {
            if(taskRepository.findById(taskId) == null) {
                throw new Exception("TaskId " + taskId + " not found");
            }

            TaskComment commentInstance = commentRepository.findById(commentId).map(comment -> {
                comment.setDescription(commentRequest.getDescription());
                return commentRepository.save(comment);
            }).orElseThrow(() -> new Exception("CommentId " + commentId + "not found"));

            restResponse.setData(commentInstance);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to load task comments");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }

    @DeleteMapping("/tasks/{taskId}/comments/{commentId}")
    @Transactional
    public RestResponse deleteComment(@PathVariable (value = "taskId") Long taskId,
                                           @PathVariable (value = "commentId") Long commentId) {
        RestResponse restResponse = new RestResponse();

        MDC.put("uid", restResponse.getRequestId());
        logger.info("Received request to delete task comments");

        try {
            commentRepository.findByIdAndTaskId(commentId, taskId).map(comment -> {
                commentRepository.delete(comment);
                return null;
            }).orElseThrow(() -> new Exception("Comment not found with id " + commentId + " and taskId " + taskId));
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setErrorMessage(ResponseCode.RC_200.RC_500, "Failed to load task comments");
        } finally {
            MDC.remove("uid");
        }

        return restResponse;
    }
}
