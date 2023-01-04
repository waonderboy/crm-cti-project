package com.brogs.crm.domain.ticket;

import com.brogs.crm.domain.agentaccount.AccountInfo;
import com.brogs.crm.domain.agentaccount.agentprofile.AgentProfile;
import com.brogs.crm.domain.customer.Customer;
import com.brogs.crm.domain.ticket.message.MessageInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class TicketInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private String title;
        private String memo;
        private String referenceUrl;
        private Ticket.TicketPriorityType priority;
        private Ticket.TicketStatusType status;
        private TicketInfo.CustomerInfo customerInfo;
        private AccountInfo.AgentInfo agentInfo;
        private List<MessageInfo.Main> messageInfos;
        //TODO: custom과 message도 추후에 추가해서 전송
        public static Main from(Ticket ticket, AgentProfile profile, Customer customer) {
            return Main.builder()
                    .title(ticket.getTitle())
                    .memo(ticket.getMemo())
                    .referenceUrl(ticket.getReferenceUrl())
                    .priority(ticket.getPriority())
                    .status(ticket.getStatus())
                    .agentInfo(AccountInfo.AgentInfo.from(profile))
                    .customerInfo(CustomerInfo.from(customer))
                            .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class CustomerInfo {
        private Long customerId;
        private String name;
        private String address;
        private String memo;
        private String email;
        private String phoneNumber;
        private String snsId;
        private Customer.SnsType snsType;

        public static CustomerInfo from(Customer entity){
            return CustomerInfo.builder()
                    .customerId(entity.getId())
                    .name(entity.getName())
                    .address(entity.getAddress())
                    .memo(entity.getMemo())
                    .email(entity.getEmail())
                    .phoneNumber(entity.getPhoneNumber())
                    .snsId(entity.getSnsId())
                    .snsType(entity.getSnsType())
                    .build();
        }
    }
}