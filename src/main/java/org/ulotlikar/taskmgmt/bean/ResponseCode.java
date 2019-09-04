package org.ulotlikar.taskmgmt.bean;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseCode {
    RC_200(200), RC_400(400), RC_401(401), RC_402(402), RC_403(403), RC_404(404), RC_412(412), RC_500(500), RC_501(501), RC_502(502), RC_204(204);

    int code;

    ResponseCode(int state){
        this.code = state;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String toString(){
        return String.valueOf(code);
    }
}
