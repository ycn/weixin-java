package cc.ycn.common.exception;

import cc.ycn.common.bean.WxError;

/**
 * 微信异常
 *
 * @author andy
 */
public class WxErrorException extends RuntimeException {

    private WxError error;

    public WxErrorException(WxError error) {
        super(error.toString());
        this.error = error;
    }

    public WxError getError() {
        return error;
    }
}
