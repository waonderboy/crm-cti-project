package com.brogs.crm.interfaces.controller.ticket;

import com.brogs.crm.interfaces.controller.agentaccount.AccountDto;
import lombok.Getter;
import lombok.Setter;

public class TicketDto {

    @Getter
    @Setter
    public static class ManualCreationReq {
        private String title;
        private String memo;
        private String referenceUrl;
        private String priority;
        private String status;
        private UpdateCustomerReq customer;
    }

    @Getter
    @Setter
    public static class TicketDetailsRes {
        private String title;
        private String memo;
        private String referenceUrl;
        private String priority;
        private String status;
        private AccountDto.ProfileRes agentInfo;
        private CustomerRes customerInfo;
    }

    @Getter
    @Setter
    public static class SearchCondition {
        private SearchType searchType;
        private String searchKeyword;
    }

    @Getter
    @Setter
    public static class UpdateCustomerReq {
        private String name;
        private String address;
        private String memo;
        private String email;
        private String phoneNumber;
        private String snsId;
        private String snsType;
    }


    @Getter
    @Setter
    public static class CustomerRes {
        private Long customerId;
        private String name;
        private String address;
        private String memo;
        private String email;
        private String phoneNumber;
        private String snsId;
        private String snsType;
    }
}
