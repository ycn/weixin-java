package cc.ycn.common.cache.base;

import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andy on 12/24/15.
 */
public abstract class WxCacheLoader<T> extends CacheLoader<String, T> {

    private ExecutorService executor;

    public WxCacheLoader(int executorSize) {
        this.executor = Executors.newFixedThreadPool(executorSize);
    }

    @Override
    public T load(String appId) throws Exception {
        return innerLoadOne(appId, null, true);
    }

    @Override
    public ListenableFuture<T> reload(final String appId, final T oldValue) throws Exception {
        checkNotNull(appId);
        checkNotNull(oldValue);

        ListenableFutureTask<T> task = ListenableFutureTask.create(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return innerLoadOne(appId, oldValue, false);
            }
        });

        executor.execute(task);
        return task;
    }

    private T innerLoadOne(String appId, T oldValue, boolean sync) {
        return loadOne(appId, oldValue, sync);
    }

    abstract protected T loadOne(String appId, T oldValue, boolean sync);
}
