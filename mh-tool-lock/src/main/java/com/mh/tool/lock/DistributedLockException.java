package com.mh.tool.lock;

/**
 * @author: yjx
 * @createTime: 2021/1/5
 * @description:
 */
public class DistributedLockException extends Exception{

    private static final long serialVersionUID = -4422354332440610539L;

    public DistributedLockException() {
        super();
    }

    public DistributedLockException(String message) {
        super(message);
    }

    public DistributedLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributedLockException(Throwable cause) {
        super(cause);
    }

    protected DistributedLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
