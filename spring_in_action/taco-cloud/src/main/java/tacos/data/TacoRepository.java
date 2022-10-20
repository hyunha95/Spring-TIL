package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
//	Taco save(Taco design);
}
