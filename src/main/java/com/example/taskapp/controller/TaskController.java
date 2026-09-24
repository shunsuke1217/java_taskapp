package com.example.taskapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;


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

}
