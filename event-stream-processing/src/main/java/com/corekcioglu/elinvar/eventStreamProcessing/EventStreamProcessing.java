package com.corekcioglu.elinvar.eventStreamProcessing;

import com.corekcioglu.elinvar.commons.Entity.Event.*;
import com.corekcioglu.elinvar.commons.Serialization.KafkaJsonDeserializer;
import com.corekcioglu.elinvar.eventStreamProcessing.Module.PersistenceModule;
import com.corekcioglu.elinvar.eventStreamProcessing.Module.ServiceModule;
import com.corekcioglu.elinvar.eventStreamProcessing.Service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.util.*;

public class EventStreamProcessing {
    @Inject
    private AccountService accountService;

    public static void main(String[] args) {
        EventStreamProcessing eventStreamProcessing = new EventStreamProcessing();
        Injector injector = Guice.createInjector(
                new PersistenceModule(eventStreamProcessing.getClass().getSimpleName()),
                new ServiceModule()
        );
        injector.injectMembers(eventStreamProcessing);

        eventStreamProcessing.startProcessing();
    }

    private void startProcessing() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "events-processing");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<UUID, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("events"));

        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            final ConsumerRecords<UUID, String> consumerRecords = consumer.poll(10);
            Iterator<ConsumerRecord<UUID, String>> it = consumerRecords.iterator();

            while (it.hasNext()) {
                ConsumerRecord<UUID, String> row = it.next();
                Map<String, Object> map = null;
                try {
                    map = mapper.readValue(row.value(), Map.class);
                }
                catch (Exception e) {

                }

                if (map == null) {
                    break;
                }

                // I do not like this part at all. There should be a better approach to handle different types of events.
                String value = (String) map.get("eventType");
                System.out.println(map);
                try {
                    if (value.compareTo(BankAccountCreatedEvent.class.getSimpleName()) == 0) {
                        BankAccountCreatedEvent event = (BankAccountCreatedEvent) new KafkaJsonDeserializer<BankAccountCreatedEvent>
                                (BankAccountCreatedEvent.class).deserialize("", row.value().getBytes());
                        accountService.processEvent(event);
                    } else if (value.compareTo(DepositPerformedEvent.class.getSimpleName()) == 0) {
                        DepositPerformedEvent event = (DepositPerformedEvent) new KafkaJsonDeserializer<DepositPerformedEvent>
                                (DepositPerformedEvent.class).deserialize("", row.value().getBytes());
                        accountService.processEvent(event);
                    } else if (value.compareTo(TransferPerformedEvent.class.getSimpleName()) == 0) {
                        TransferPerformedEvent event = (TransferPerformedEvent) new KafkaJsonDeserializer<TransferPerformedEvent>
                                (TransferPerformedEvent.class).deserialize("", row.value().getBytes());
                        accountService.processEvent(event);
                    } else if (value.compareTo(WithdrawalPerformedEvent.class.getSimpleName()) == 0) {
                        WithdrawalPerformedEvent event = (WithdrawalPerformedEvent) new KafkaJsonDeserializer<WithdrawalPerformedEvent>
                                (WithdrawalPerformedEvent.class).deserialize("", row.value().getBytes());
                        accountService.processEvent(event);
                    }
                }
                catch (Exception e) {
                    break;
                }
            }

            consumer.commitAsync();
        }
    }
}
