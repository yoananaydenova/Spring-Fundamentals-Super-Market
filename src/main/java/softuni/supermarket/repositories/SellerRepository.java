package softuni.supermarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.supermarket.models.entities.Seller;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    Optional<Seller> findByFirstNameAndLastName(String firstName, String lastName);

    List<Seller> findAllByShopName(String shopName);
}
