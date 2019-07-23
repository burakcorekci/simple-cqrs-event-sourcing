package com.corekcioglu.elinvar.bankAccountWrite.Service;

import com.corekcioglu.elinvar.bankAccountWrite.Config.ConfigurationFactory;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.AccountCreationDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.DepositDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.TransferDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.WithdrawalDto;
import com.corekcioglu.elinvar.bankAccountWrite.Entity.RestResponse;
import com.corekcioglu.elinvar.commons.Entity.Event.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {
    private KafkaProducer<UUID, AbstractEvent> kafkaProducer;
    private ConfigurationFactory configFactory;

    @Inject
    public AccountServiceImpl(
            KafkaProducer<UUID, AbstractEvent> kafkaProducer,
            ConfigurationFactory configFactory) {
        this.kafkaProducer = kafkaProducer;
        this.configFactory = configFactory;
    }

    public Optional<BankAccountCreatedEvent> createAccount(AccountCreationDto accountCreationDto) throws Exception {
        // I chose to call the read web service instead of creating a common repository/service layer. This way write
        // and read operations are completely separated into their own domains and can scale independently.
        // E-mail should be unique.
        RestResponse rr = returnGetResponse("http://localhost:8001/account/email/" + accountCreationDto.getEmail());
        if (rr.getStatus() != 200) {
            throw new Exception();
        }

        BankAccountCreatedEvent event = new BankAccountCreatedEvent();

        event.setAccountId(UUID.randomUUID());
        event.setName(accountCreationDto.getName());
        event.setEmail(accountCreationDto.getEmail());

        return publishToKafka(event);
    }

    public Optional<DepositPerformedEvent> depositToAccount(DepositDto depositDto) throws Exception {
        // Account should be available.
        RestResponse rr = returnGetResponse("http://localhost:8001/account/" + depositDto.getAccountId());
        if (rr.getStatus() != 200) {
            throw new Exception();
        }

        DepositPerformedEvent event = new DepositPerformedEvent();

        event.setAccountId(depositDto.getAccountId());
        event.setAmount(depositDto.getAmount());

        return publishToKafka(event);
    }

    public Optional<TransferPerformedEvent> transferToAnotherAccount(TransferDto transferDto) throws Exception {
        // Account should be available.
        RestResponse rr = returnGetResponse("http://localhost:8001/account/" + transferDto.getAccountId());
        if (rr.getStatus() != 200) {
            throw new Exception();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(rr.getBody(), Map.class);
        if ((Double) map.get("amount") < transferDto.getAmount()) {
            throw new Exception();
        }

        rr = returnGetResponse("http://localhost:8001/account/" + transferDto.getTargetId());
        if (rr.getStatus() != 200) {
            throw new Exception();
        }

        TransferPerformedEvent event = new TransferPerformedEvent();

        event.setAccountId(transferDto.getAccountId());
        event.setTargetId(transferDto.getTargetId());
        event.setAmount(transferDto.getAmount());

        return publishToKafka(event);
    }

    public Optional<WithdrawalPerformedEvent> withdrawFromAccount(WithdrawalDto withdrawalDto) throws Exception {
        // Account should be available.
        RestResponse rr = returnGetResponse("http://localhost:8001/account/" + withdrawalDto.getAccountId());
        if (rr.getStatus() != 200) {
            throw new Exception();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(rr.getBody(), Map.class);
        if ((Double) map.get("amount") < withdrawalDto.getAmount()) {
            throw new Exception();
        }

        WithdrawalPerformedEvent event = new WithdrawalPerformedEvent();

        event.setAccountId(withdrawalDto.getAccountId());
        event.setAmount(withdrawalDto.getAmount());

        return publishToKafka(event);
    }

    private <T extends AbstractEvent> Optional<T> publishToKafka(T event) {
        // Send it to Kafka
        Future<RecordMetadata> futureRecord = kafkaProducer.send(
                new ProducerRecord<>(
                        configFactory.getConfigurationParameters().getKafka().getTopic(),
                        event.getAccountId(),
                        event
                )
        );

        try {
            // If event is sent succesfully
            RecordMetadata record = futureRecord.get();
            return Optional.of(event);
        }
        catch (Exception ex) {
            return Optional.empty();
        }
    }

    private RestResponse returnGetResponse(String uri) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(uri).openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();

        BufferedReader br;
        if (200 <= status && status <= 299) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String message = br.lines().collect(Collectors.joining());
        con.disconnect();

        RestResponse rr = new RestResponse();
        rr.setStatus(status);
        rr.setBody(message);

        return rr;
    }
}
