package com.petia.dollhouse.repositories;

import com.petia.dollhouse.domain.entities.PromoOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoOfferRepository extends JpaRepository<PromoOffer,String> {
}
