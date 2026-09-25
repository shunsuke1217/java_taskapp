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
import com.example.taskapp.service.TaskService;

@Controller
public class TaskController {
    
    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }
    // /tasksにアクセスしたら全てのタスクを表示する
    @GetMapping("/tasks")
    public String list(Model model){
        model.addAttribute("tasks",taskService.findAllTasks());
        return "tasks/list";
    }
    // タスクの登録
    @PostMapping("/tasks")
    public String create(@Valid Task task, BindingResult result){
        if(result.hasErrors()){
            return "tasks/new";
        }
        taskService.saveTask(task);
        return "redirect:/tasks";
    }
    //タスク作成画面
    @GetMapping("/tasks/new")
    public String newTask(Model model){
       model.addAttribute("task",new Task());
       return "tasks/new";
    }
    //タスクの削除
    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
    //タスクの編集画面(タスク取得→それをHTMLに埋め込み)
    @GetMapping("/tasks/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Task task= taskService.findTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("id", id);
        return "tasks/edit";
    }
    //タスクの編集(idの取得、そのidのタスクをHTMLから受け取ったnewTaskで上書き)
    @PostMapping("/tasks/{id}/edit")
    public String edit(@PathVariable Long id, @Valid Task newTask, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", newTask);
            model.addAttribute("id", id);
            return "tasks/edit";
        }
        taskService.edit(id, newTask);
        return "redirect:/tasks";
    }
    //タスクの完了状態の切り替え(タスクの取得→状態の切り替え→それをsave→tasksへredirect)
    @PostMapping("/tasks/{id}/done")
    public String toggleDone(@PathVariable Long id){
        taskService.toggleDone(id);
        return "redirect:/tasks";
    }

}
