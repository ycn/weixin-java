package cc.ycn.common.util;

/**
 * Created by andy on 7/25/15.
 */
public enum TransactionStatus {

    SUCCESS("事务执行成功"),
    WATCH_FAILED("方法重入导致失败"),
    FAILED("事务执行失败");

    private String info;

    private TransactionStatus(String info) {
        this.info = info;
    }

    public String info() {
        return info;
    }

}
