package com.corekcioglu.elinvar.bankAccountWrite.Config;

import com.corekcioglu.elinvar.bankAccountWrite.Config.Parameter.ConfigurationParameters;
import com.corekcioglu.elinvar.commons.Serialization.KafkaJsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFactory {
    private ConfigurationParameters configurationParameters;

    public ConfigurationFactory() {
        this.initializeConfig();
    }

    private void initializeConfig() {
        Yaml yaml = new Yaml();

        try( InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml")) {
            configurationParameters = yaml.loadAs(in, ConfigurationParameters.class);
        }
        catch (Exception e) {

        }
    }

    public ConfigurationParameters getConfigurationParameters() {
        return configurationParameters;
    }

    public Properties getKafkaProperties() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationParameters.getKafka().getUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);

        return props;
    }
}
