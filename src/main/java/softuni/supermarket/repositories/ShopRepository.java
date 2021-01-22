package softuni.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.supermarket.models.entities.Shop;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
    Optional<Shop> findByAddress(String shopAddress);
    Optional<Shop> findByName(String shopName);
}
