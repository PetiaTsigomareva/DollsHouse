package com.petia.dollhouse.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

	@Query("SELECT r FROM Reservation r WHERE r.service.id =:serviceId and r.employee.id = :employeeId and r.reservationDateTime > DATE_FORMAT(:fromDate, '%Y-%m-%d 00:00:00') and r.reservationDateTime < DATE_FORMAT(:toDate, '%Y-%m-%d 00:00:00')")
	List<Reservation> getAllReservationsForTimePeriodOfficeServiceEmployee(String serviceId, String employeeId, String fromDate, String toDate);


	Optional<Reservation> findByReservationDateTime(LocalDateTime reservationDateTime);
}
