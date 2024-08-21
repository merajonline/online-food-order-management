package org.example.ordermanagement.exception;

import lombok.Data;
import lombok.Getter;
import org.example.ordermanagement.model.ResultCode;

import java.io.Serial;

@Data
public class BixException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1724137844000L;

    private ResultCode resultCode;

    private Object[] args;

    private Object data;

    public BixException(ResultCode resultCode, Object[] args, Object data) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.args = args;
        this.data = data;
    }

    public BixException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
    }

    public BixException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public static BixException of(ResultCode resultCode) {
        return new BixException(resultCode);
    }

}
