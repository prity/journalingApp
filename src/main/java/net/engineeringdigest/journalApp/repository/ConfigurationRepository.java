package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.EntryConfig;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<EntryConfig, ObjectId>  {

}
