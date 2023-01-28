package com.artzhelt.sweater.controller;

import com.artzhelt.sweater.domain.Message;
import com.artzhelt.sweater.domain.SweaterUser;
import com.artzhelt.sweater.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("messages", messageRepository.findByTagContaining(filter));
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal SweaterUser user,
                      @RequestParam String text,
                      @RequestParam String tag, Model model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        Iterable<Message> allMessages = messageRepository.findAll();
        model.addAttribute("messages", allMessages);
        return "main";
    }

}