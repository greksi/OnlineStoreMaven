package itstep.grek.OnlineStore.repository;

import itstep.grek.OnlineStore.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
