package com.brogs.crm.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomQuestionControllerNotRest {

    @GetMapping
    public String customerInfo() {
        return "temp-login";
    }

    @PostMapping
    public String customerInfo(TempCustomer tempCustomer, RedirectAttributes attributes) {
        attributes.addFlashAttribute("customer", tempCustomer);
        return "redirect:/customer/question";
    }

    @GetMapping("/question")
    public String question() {
        return "question";
    }
}
