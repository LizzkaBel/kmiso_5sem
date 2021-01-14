package by.bsu.mmf.fan.iso.utils.dfs;

@FunctionalInterface
public interface EnqueueConsumer<TParam> {
    void enqueue(int node, TParam param);
}
