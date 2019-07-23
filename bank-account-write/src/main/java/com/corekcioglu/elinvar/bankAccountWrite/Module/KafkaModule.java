package com.corekcioglu.elinvar.bankAccountWrite.Module;

import com.corekcioglu.elinvar.bankAccountWrite.Config.ConfigurationFactory;
import com.corekcioglu.elinvar.commons.Entity.Event.AbstractEvent;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.UUID;

public class KafkaModule extends AbstractModule {
    private ConfigurationFactory configFactory;

    public KafkaModule(ConfigurationFactory configFactory) {
        this.configFactory = configFactory;
    }

    @Override
    public void configure() { }

    @Provides
    public KafkaProducer<UUID, AbstractEvent> createKafkaProducer() {
        return new KafkaProducer<UUID, AbstractEvent>(configFactory.getKafkaProperties());
    }
}
