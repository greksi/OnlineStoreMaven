package itstep.grek.OnlineStore.controllers;

import itstep.grek.OnlineStore.Models.Category;
import itstep.grek.OnlineStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categoryList")
    public String categoryList(Model model){
        List<Category> categories = new ArrayList<>();
        categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    @GetMapping("/categoryAdd")
    public String addCategory(@ModelAttribute Category category,  Model model) {
        return "categoryAdd";
    }

    @PostMapping("/categoryAdd")
    public String addCategory(Category category) {
        categoryRepository.save(category);
        return  "redirect:/admin";
    }

    @GetMapping("/categoryList/{category}/edit")
    public String categoryEdit(@ModelAttribute Category category, BindingResult errors, Model model) {
        return "categoryEdit";
    }

    @PostMapping("/categoryEdit")
    public String categoryEdit(Category category) throws IOException
    {

        categoryRepository.save(category);
        return  "redirect:/categoryList";
    }

    @RequestMapping(value = "categoryList/{id}/remove", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categoryList";
    }

}
