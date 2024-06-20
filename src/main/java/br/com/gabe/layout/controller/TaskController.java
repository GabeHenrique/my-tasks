package br.com.gabe.layout.controller;

import br.com.gabe.layout.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

  List<Task> tasks = new ArrayList<>();

  @GetMapping("/home")
  public ModelAndView home() {
    return new ModelAndView("home");
  }

  @GetMapping("/create")
  public ModelAndView viewCreate() {
    ModelAndView mv = new ModelAndView("create");
    mv.addObject("task", new Task());
    return mv;
  }

  @PostMapping("/create")
  public String create(Task task) {
    Long id = tasks.size() + 1L;
    tasks.add(new Task(id, task.getName(), task.getDate()));
    return "redirect:/";
  }

  @GetMapping("/")
  public ModelAndView home(String name) {
    ModelAndView mv = new ModelAndView("list");
    mv.addObject("name", name);
    mv.addObject("tasks", tasks);
    return mv;
  }


  @GetMapping("/search")
  public ModelAndView search(@RequestParam(name = "name", required = false) String name) {
    ModelAndView mv = new ModelAndView("list");
    if (name == null || name.isBlank()) {
      mv.addObject("tasks", tasks);
    } else {
      List<Task> filteredTasks = tasks.stream()
          .filter(task ->
              task.getName().toUpperCase().contains(name.toUpperCase()))
          .collect(Collectors.toList());
      mv.addObject("tasks", filteredTasks);
    }
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
    return "redirect:/";
  }
}
