package com.brogs.crm.interfaces.socket;

import com.brogs.crm.domain.ticket.TicketCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MessageDtoMapper {

    TicketCommand.MatchMessage of(MessageDto.Main request);

    TicketCommand.MatchMessage of(MessageDto.Question request);
}
