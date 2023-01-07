package com.brogs.crm.interfaces.controller.ticket;

import com.brogs.crm.application.TicketFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class TempChatControllerNotRest {
    private final TicketFacade ticketFacade;
    private final TicketDtoMapper ticketDtoMapper;

    @GetMapping("/ticket/{ticketId}")
    public String getTicket(@PathVariable Long ticketId, Model model) {
        var response = ticketDtoMapper.of(ticketFacade.getTicket(ticketId));
        model.addAttribute("ticketId", ticketId);
        model.addAttribute("title", response.getTitle());
        model.addAttribute("agent", response.getAgentInfo());
        model.addAttribute("customer", response.getCustomerInfo());
        return "ticket";
    }

}
