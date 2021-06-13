package soakafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.common.errors.WakeupException;

public class KafkaExample {
    private final String topic;
    private final Properties props;

    public KafkaExample(String brokers, String username, String password) {
        this.topic = "kafka_like";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        //Propriedades
        props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", "kafka-hoshikawa");
        props.put("enable.auto.commit", "false");
        props.put("max.poll.records", "10");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", jaasCfg);
        //props.put("ssl.client.auth", "requested");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");  
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    }

    public String consume() {
        String ret = "";
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
          while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
            {
              System.out.println(record.offset() + ": " + record.value());
              ret = ret + record.value();
            }
              if (ret != "")
                break;
          }
        } catch (Exception e) {
          // ignore for shutdown
        } finally {
          consumer.commitAsync();
          consumer.close();
        }
        return ret;
    };

    public void produce(String message) {
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord record = new ProducerRecord<String, String>(topic, "msg", message);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println(
                "Mensagem enviada com sucesso para: " + data.topic() + " | partition " + data.partition() + "| offset " + data.offset() + "| tempo " + data
                    .timestamp());
        };
        try {
            producer.send(record, callback).get();
        } catch (ExecutionException | InterruptedException e) {
        }
        finally {
            producer.close();
        }
    }

    public static void main(String[] args) {
                /*
		String brokers = System.getenv("CLOUDKARAFKA_BROKERS");
		String username = System.getenv("CLOUDKARAFKA_USERNAME");
		String password = System.getenv("CLOUDKARAFKA_PASSWORD");
                */
                String brokers = "cell-1.streaming.us-ashburn-1.oci.oraclecloud.com:9092";
                String username = "hoshikawaoraclecloud/oracleidentitycloudservice/hoshikawa2@hotmail.com/ocid1.streampool.oc1.iad.amaaaaaaihuwreyagjtsqtsamegi4nerjxxwspkckiokrvoukgztajzakb5a";
                String password = "W(22}s.C[)Ya9Uc)#kLk";
		KafkaExample c = new KafkaExample(brokers, username, password);
        c.consume();
    }
}
