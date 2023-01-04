package com.brogs.crm.domain.ticket;

import com.brogs.crm.domain.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class TicketCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterTicket {
        private String title;
        private String memo;
        private String referenceUrl;
        private Ticket.TicketPriorityType priority;
        private Ticket.TicketStatusType status;

        public Ticket toEntity() {
            return Ticket.builder()
                    .title(title)
                    .memo(memo)
                    .priority(priority)
                    .status(status)
                    .build();
        }

    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCustomer {
        private String name;
        private String address;
        private String memo;
        private String email;
        private String phoneNumber;
        private String snsId;
        private Customer.SnsType snsType;
        public Customer toEntity() {
            return Customer.builder()
                    .name(name)
                    .address(address)
                    .memo(memo)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .snsId(snsId)
                    .snsType(snsType)
                    .build();
        }
    }

}