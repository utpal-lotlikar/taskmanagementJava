package org.ulotlikar.taskmgmt.bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.NDC;
import org.springframework.http.HttpStatus;

/**
 * @author Utpal Lotlikar
 *
 */

public class RestResponse {

	String requestId;
	long timestamp;
	ResponseCode status = ResponseCode.RC_200;
	String message = "Success";
	Object data;
	Long totalRecords;

	public RestResponse() {
		requestId = UUID.randomUUID().toString();
		timestamp = new Date().getTime();
	}

	public void setErrorMessage(ResponseCode code, String message) {
		this.status = code;
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRequestId() {
		return requestId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public ResponseCode getStatus() {
		return status;
	}

	public void setStatus(ResponseCode status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
