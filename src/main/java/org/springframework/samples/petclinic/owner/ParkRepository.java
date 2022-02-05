package org.springframework.samples.petclinic.owner;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.AvailableSlot;
import org.springframework.samples.petclinic.model.AvailableSlots;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ParkRepository extends Repository<AvailableSlot, Integer> {

	@Transactional(readOnly = true)
	@Cacheable("parking")
	Collection<AvailableSlot> findAll() throws DataAccessException;

	@Transactional(readOnly = true)
	@Cacheable("parking")
	Page<AvailableSlot> findAll(Pageable pageable) throws DataAccessException;

	void save(AvailableSlot availableSlot);
}
