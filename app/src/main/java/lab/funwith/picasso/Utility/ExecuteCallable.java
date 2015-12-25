package lab.funwith.picasso.Utility;

import java.util.concurrent.Callable;

public class ExecuteCallable<T> implements Callable<T> {
    ExecuteCallableInterface listener;

    public ExecuteCallable(ExecuteCallableInterface listener) {
        this.listener = listener;
    }

    @Override
    public T call() throws Exception {
        if(listener != null){
            return (T)listener.executeCall();
        }
        return null;
    }
}
