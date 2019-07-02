package ru.ishbuldin.andrei.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Note note) {
        return "add";
    }

    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String checkNote(@Valid Note note, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        noteRepository.save(note);
        return "redirect:/";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        Note note = noteRepository.findById(id).get();
        model.addAttribute("note", note);
        return "view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        noteRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String expression, Model model) {
        model.addAttribute("notes", noteRepository.findLikeTitleAndBody(expression));
        return "search";
    }

}