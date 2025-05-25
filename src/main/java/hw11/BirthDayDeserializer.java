package hw11;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BirthDayDeserializer extends JsonDeserializer<String> {

    private static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        long epochMilli = p.getLongValue();
        return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.of("UTC")).toLocalDate().format(formater);
    }
}
