package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
public class EntryConfig {

    @NonNull
    private String key;
    @NonNull
    private String value;


}
