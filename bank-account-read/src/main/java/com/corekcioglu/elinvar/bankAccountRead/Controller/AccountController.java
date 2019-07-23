package com.corekcioglu.elinvar.bankAccountRead.Controller;

import com.corekcioglu.elinvar.bankAccountRead.Entity.Account;
import com.corekcioglu.elinvar.bankAccountRead.Service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/account")
@Produces("application/json")
public class AccountController {
    private AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountId}")
    public Response getAccountById(@PathParam("accountId") UUID accountId) {
        if (accountId.toString().isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        Optional<Account> oAccount = accountService.getAccountById(accountId);
        if (oAccount.isPresent()) {
            return Response.ok().entity(oAccount.get()).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response getAccountByEmail(@PathParam("email") String email) {
        if (email.isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        Optional<Account> oAccount = accountService.getAccountByEmail(email);
        if (oAccount.isPresent()) {
            return Response.ok().entity(oAccount.get()).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/name/{name}")
    public Response getAccountsByName(@PathParam("name") String name) {
        if (name.isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        List<Account> list = accountService.getAccountsByName(name);
        if (list.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok().entity(list).build();
        }
    }
}
