package com.corekcioglu.elinvar.bankAccountWrite.Controller;

import com.corekcioglu.elinvar.bankAccountWrite.Dto.AccountCreationDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.DepositDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.TransferDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.WithdrawalDto;
import com.corekcioglu.elinvar.commons.Entity.Event.BankAccountCreatedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.DepositPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.TransferPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.WithdrawalPerformedEvent;
import com.corekcioglu.elinvar.bankAccountWrite.Service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("")
@Produces("application/json")
public class AccountController {
    @Inject
    private AccountService accountService;

    @POST
    @Path("/account")
    @Consumes("application/json")
    public Response createAccount(AccountCreationDto accountCreationDto) {
        if (accountCreationDto.isNotValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<BankAccountCreatedEvent> event = Optional.empty();
        try {
            event = accountService.createAccount(accountCreationDto);
        }
        catch (Exception e) {

        }


        if (event.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(event.get()).build();
        }
        else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    @POST
    @Path("/deposit")
    @Consumes("application/json")
    public Response depositToAccount(DepositDto depositDto) {
        if (depositDto.isNotValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<DepositPerformedEvent> event = Optional.empty();
        try {
            event = accountService.depositToAccount(depositDto);
        }
        catch (Exception e) {

        }

        if (event.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(event.get()).build();
        }
        else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    @POST
    @Path("/transfer")
    @Consumes("application/json")
    public Response transferToAnotherAccount(TransferDto transferDto) {
        if (transferDto.isNotValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<TransferPerformedEvent> event = Optional.empty();
        try {
            event = accountService.transferToAnotherAccount(transferDto);
        }
        catch (Exception e) {

        }

        if (event.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(event.get()).build();
        }
        else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    @POST
    @Path("/withdraw")
    @Consumes("application/json")
    public Response withdrawFromAccount(WithdrawalDto withdrawalDto) {
        if (withdrawalDto.isNotValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<WithdrawalPerformedEvent> event = Optional.empty();
        try {
            event = accountService.withdrawFromAccount(withdrawalDto);
        }
        catch (Exception e) {

        }

        if (event.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(event.get()).build();
        }
        else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }
}
