package br.com.monolithic.ecommerce.config.dbmigrations;

import br.com.monolithic.ecommerce.domain.Product;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog
public class InitialSetupMigration {

    @ChangeSet(order = "01", id = "AddProducts", author = "Davi")
    public void addProducts(MongoTemplate mongoTemplate) {
        mongoTemplate.save(new Product("Televisão Samsumg 58 polegadas", 2400.00));
        mongoTemplate.save(new Product("Geladeira Consul Inox 405 Lt", 3100.00));
    }

}
