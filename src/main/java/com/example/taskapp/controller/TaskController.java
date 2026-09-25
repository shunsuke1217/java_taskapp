package com.example.taskapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.taskapp.entity.Task;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskController {
    
    private final TaskRepository taskRepository;
    public TaskController(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    // /tasksにアクセスしたら全てのタスクを表示する
    @GetMapping("/tasks")
    public String list(Model model){
        // taskRepositoryに保存されているタスクを全て取得してHTMLに渡すためにmodel.tasksに追加する
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks/list";
    }
    // タスクの作成
    @PostMapping("/tasks")
    public String create(@Valid Task task, BindingResult result){
        if(result.hasErrors()){
            return "tasks/new";
        }
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    //タスクの登録
    @GetMapping("/tasks/new")
    public String newTask(Model model){
        model.addAttribute("task",new Task());
        return "tasks/new";
    }
    //タスクの削除
    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable Long id){
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
    

}
