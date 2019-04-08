package com.petia.dollhouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

//	@Query("SELECT r FROM Reservation r WHERE r.office.id =:officeId and r.services.id =:serviceId and reservationDateTime > trunc(:fromDate) and reservationDateTime > trunc(:toDate)+1")
//	List<Reservation> getAllReservationsForTimePeriodForOfficeService(String officeId, String serviceId, String fromDate, String toDate);
}
