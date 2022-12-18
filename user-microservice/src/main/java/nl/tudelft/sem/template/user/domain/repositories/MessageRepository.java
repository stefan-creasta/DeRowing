package nl.tudelft.sem.template.user.domain.repositories;

import nl.tudelft.sem.template.user.domain.NetId;import nl.tudelft.sem.template.user.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

	@Query(value = "SELECT * FROM MESSAGE m WHERE m.receiver like ?1", nativeQuery = true)
	List<Message> findMessagesByNetId(String netId);
}
