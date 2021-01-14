package by.bsu.mmf.fan.iso.utils.dfs;

import java.util.Stack;

/**
 * A utility class for implementing depth-first search non-recursively.
 *
 * @param <TReturn> the return type of the visit method.
 * @param <TParam>  the type of the parameter that will be passed into the
 *                  {@link #visit(int, Object, EnqueueConsumer) visit} method.
 */
public abstract class NonRecursiveDFS<TReturn, TParam> {
    /**
     * Executes the depth-first search starting from the specified vertex, passing param into the
     * {@link #visit(int, Object, EnqueueConsumer) visit} method the first time it is called.
     *
     * @param startVertex the starting vertex for the depth-first search.
     * @param param       the parameter that will be passed into the
     *                    {@link #visit(int, Object, EnqueueConsumer) visit} method.
     */
    public void performDfs(int startVertex, TParam param) {
        Stack<Message> stack = new Stack<>();
        var exit = new Exit<TReturn>(startVertex, null);
        stack.push(exit);
        stack.push(new Visit<TReturn, TParam>(startVertex, param, null, exit));
        while (!stack.empty()) {
            var message = stack.pop();
            if (message instanceof Visit) {
                Visit<TReturn, TParam> visitMessage = (Visit<TReturn, TParam>) message;
                int node = visitMessage.getNode();
                TReturn result = this.visit(node, visitMessage.getParmeter(), (x, newParam) -> {
                    var exit2 = new Exit<TReturn>(x, node);
                    stack.push(exit2);
                    stack.push(new Visit<>(x, newParam, node, exit2));
                });
                Exit<TReturn> exitMessage = visitMessage.getExitMessage();
                if (exitMessage != null)
                    exitMessage.setResult(result);
            } else if (message instanceof Exit) {
                Exit<TReturn> exitMesssage = (Exit<TReturn>) message;
                this.exit(exitMesssage.getNode());

                Integer parentNode = exitMesssage.getParentNode();
                if (parentNode != null) {
                    this.exitNode(exitMesssage.getNode(), parentNode, exitMesssage.getResult());
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /**
     * Visits the node and enqueues children for visiting.
     *
     * @param node    the current node being visited.
     * @param param   the parameter passed from the parent node.
     * @param enqueue the callback to call for each child node to be visited by the depth-first search.
     * @return the value to return and pass into {@link #exitNode(int, int, Object) exitNode}.
     */
    public abstract TReturn visit(int node, TParam param, EnqueueConsumer<TParam> enqueue);

    /**
     * Called after a child node is visited.
     *
     * @param childNode  the child node that was visited.
     * @param parentNode the node that enqueued the child to be visited.
     * @param result     the result returned by {@link #visit(int, Object, EnqueueConsumer) visit} when visiting the
     *                   {@code childNode}.
     */
    public abstract void exitNode(int childNode, int parentNode, TReturn result);

    /**
     * Called after all children of the {@code node} have been visited.
     *
     * @param node the node from which the depth first search procedure is exiting.
     */
    public abstract void exit(int node);
}

interface Message {

}

class Visit<T, TParam> implements Message {
    private final int node;
    private final TParam parmeter;
    private final Integer parentNode;
    private final Exit<T> exitMessage;

    public Visit(int node, TParam parmeter, Integer parentNode, Exit<T> exitMessage) {
        this.node = node;
        this.parmeter = parmeter;
        this.parentNode = parentNode;
        this.exitMessage = exitMessage;
    }

    public int getNode() {
        return this.node;
    }

    public Integer getParentNode() {
        return this.parentNode;
    }

    public Exit<T> getExitMessage() {
        return this.exitMessage;
    }

    public TParam getParmeter() {
        return this.parmeter;
    }
}

class Exit<T> implements Message {
    private final int node;
    private final Integer parentNode;
    private T result = null;

    public Exit(int node, Integer parentNode) {
        this.node = node;
        this.parentNode = parentNode;
    }

    public int getNode() {
        return this.node;
    }

    public Integer getParentNode() {
        return this.parentNode;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

