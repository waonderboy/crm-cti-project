package com.brogs.crm.interfaces.controller.ticket;

import com.brogs.crm.application.TicketFacade;
import com.brogs.crm.common.AccountPrincipal;
import com.brogs.crm.common.response.CommonResponse;
import com.brogs.crm.common.security.CurrentAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TicketController {

    private final TicketFacade ticketFacade;
    private final TicketDtoMapper ticketDtoMapper;

    /**
     * 고객까지 추가해서 만들기
     * 고객까지 추가해서 검색 조건에 맞는 검색기능을 개발하자
     */
//    @GetMapping("/ticket")
//    public CommonResponse searchTickets(@CurrentAccount AccountPrincipal accountPrincipal,
//                                        @RequestBody TicketDto.SearchCondition searchCondition,
//                                        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        ticketFacade.searchTickets(searchCondition);
//        return CommonResponse.success(response);
//    }

    @PostMapping("/ticket")
    public CommonResponse registerWithCustomer(@CurrentAccount AccountPrincipal accountPrincipal,
                                               @RequestBody TicketDto.ManualCreationReq request) {
        ticketFacade.register(accountPrincipal.getEmail(), ticketDtoMapper.of(request));
        return CommonResponse.success("티켓 등록이 완료되었습니다.");
    }

    @GetMapping("/ticket/{ticketId}")
    public CommonResponse getTicket(@CurrentAccount AccountPrincipal accountPrincipal,
                                    @PathVariable Long ticketId) {
        var response = ticketDtoMapper.of(ticketFacade.getTicket(ticketId));
        return CommonResponse.success(response);
    }

    @PostMapping("/ticket/{ticketId}/customer")
    public CommonResponse registerCustomerAtTicket(@CurrentAccount AccountPrincipal accountPrincipal,
                                                   @PathVariable Long ticketId,
                                                   @RequestBody TicketDto.UpdateCustomerReq request) {
        ticketFacade.registerCustomer(ticketId, ticketDtoMapper.of(request));
        return CommonResponse.success("티켓 [" + ticketId + "] 에 고객 정보 등록이 완료되었습니다.");
    }

    @PutMapping("/ticket/{ticketId}/customer")
    public CommonResponse updateCustomerAtTicket(@CurrentAccount AccountPrincipal accountPrincipal,
                                                   @PathVariable Long ticketId,
                                                   @RequestBody TicketDto.UpdateCustomerReq request) {
        ticketFacade.updateCustomer(ticketId, ticketDtoMapper.of(request));
        return CommonResponse.success("티켓 [" + ticketId + "] 에 고객 정보 업데이트가 완료되었습니다.");
    }


}
