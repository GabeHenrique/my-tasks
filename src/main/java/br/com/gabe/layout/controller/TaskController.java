package br.com.gabe.layout.controller;

import br.com.gabe.layout.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

  List<Task> tasks = new ArrayList<>();

  @GetMapping("/create")
  public ModelAndView home() {
    ModelAndView mv = new ModelAndView("create");
    mv.addObject("task", new Task());
    return mv;
  }

  @PostMapping("/create")
  public String create(Task task) {
    Long id = tasks.size() + 1L;
    tasks.add(new Task(id, task.getName(), task.getDate()));
    return "redirect:/list";
  }

  @GetMapping("/list")
  public ModelAndView list() {
    ModelAndView mv = new ModelAndView("list");
    mv.addObject("tasks", tasks);
    return mv;
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("create");
    var taskFound = tasks.stream().filter(task -> task.getId().equals(id)).findFirst().get();
    mv.addObject("task", taskFound);
    return mv;
  }

  @PostMapping("update/{id}")
  public String update(Task task) {
    var exists = tasks.stream().filter(t -> t.getId().equals(task.getId())).findAny();
    if (exists.isPresent()) {
      exists.get().setName(task.getName());
      exists.get().setDate(task.getDate());
    }
    return "redirect:/list";
  }
}
