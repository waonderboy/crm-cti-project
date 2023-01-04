package com.brogs.crm.interfaces.controller.ticket;


import com.brogs.crm.domain.ticket.TicketCommand;
import com.brogs.crm.domain.ticket.TicketInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TicketDtoMapper {

    @Mapping(source = "snsType", target = "snsType")
    TicketCommand.UpdateCustomer of(TicketDto.UpdateCustomerReq request);

    @Mappings({@Mapping(target = "priority", source = "priority"), @Mapping(target = "status", source = "status")})
    TicketCommand.RegisterTicket of(TicketDto.ManualCreationReq request);

    @Mappings({@Mapping(target = "priority", source = "priority"), @Mapping(target = "status", source = "status")})
    TicketDto.TicketDetailsRes of(TicketInfo.Main result);
}
