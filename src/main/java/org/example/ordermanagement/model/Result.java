package org.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.example.ordermanagement.exception.CommonApiResultCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Result<K> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1724081899000L;

    private String resultCode;
    private String resultMessage;
    private boolean success;
    private Long timeStamp;
    private K data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public Result() {
        setResultCode(CommonApiResultCode.SUCCESS);
    }

    public Result(K data) {
        this();
        this.timeStamp = System.currentTimeMillis();
        success = true;
        this.data = data;
    }

    public Result(ResultCode resultCode, K data, Object... args) {
        this.resultCode = resultCode.getCode();
        this.timeStamp = System.currentTimeMillis();
        this.data = data;
        if (args != null && args.length > 0) {
            this.resultMessage = String.format(resultCode.getMessage(), args);
        } else {
            this.resultMessage = resultCode.getMessage();
        }
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode.getCode();
        this.resultMessage = resultCode.getMessage();
    }


}
