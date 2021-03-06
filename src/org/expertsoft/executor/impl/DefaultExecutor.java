package org.expertsoft.executor.impl;

import org.expertsoft.executor.Executor;
import org.expertsoft.executor.TaskAccess;
import org.expertsoft.executor.thread.ExecuteThread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by wladek on 1/13/17.
 */
public class DefaultExecutor implements Executor, TaskAccess{

    private final Queue<Runnable> queueOfTasks;
    private final ExecuteThread executeThread;

    public DefaultExecutor(){
        queueOfTasks = new LinkedList<Runnable>();
        executeThread = new ExecuteThread(this);
        executeThread.start();
    }

    public Runnable getNextTask(){
        synchronized (queueOfTasks){
            return queueOfTasks.poll();
        }
    }

    public void execute(Runnable task){
        synchronized (queueOfTasks){
            queueOfTasks.add(task);
            executeThread.myNotify();
        }
    }

}
