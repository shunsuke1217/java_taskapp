package com.example.taskapp.service;

import org.springframework.stereotype.Service;

import com.example.taskapp.entity.Task;
import java.util.List;
import jakarta.transaction.Transactional;
import com.example.taskapp.repository.TaskRepository;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }
    public Task findTaskById(Long id){
        return taskRepository.findById(id).orElseThrow();
    }
    // タスクの登録
    public void saveTask(Task task){
        taskRepository.save(task);
    }

    //タスクの削除
    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }
    //タスクの編集(idの取得、そのidのタスクを受け取ったnewTaskで上書き)
    @Transactional
    public void edit(Long id, Task newTask){
        Task task=findTaskById(id);
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        saveTask(task);
    }
    //タスクの完了状態の切り替え(タスクの取得→状態の切り替え→それをsave→tasksへredirect)
    @Transactional 
    public void toggleDone(Long id){
        Task task=findTaskById(id);
        task.toggleDone();
        saveTask(task);
    }

}

