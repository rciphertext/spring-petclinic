package org.springframework.samples.petclinic.parking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.AvailableSlot;
import org.springframework.samples.petclinic.model.AvailableSlots;
import org.springframework.samples.petclinic.model.RequestSlot;
import org.springframework.samples.petclinic.owner.ParkRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ParkingController {
	static int counter = 4;
	private final ParkRepository parkRepository;

	Logger logger = LoggerFactory.getLogger(ParkingController.class);

	public ParkingController(ParkRepository parkRepository) {
		this.parkRepository = parkRepository;
	}

	@GetMapping("/slots.html")
	public String getData(@RequestParam(defaultValue = "1") int page, Model model) {
		logger.info("Request received to get available slots and location.");
		AvailableSlots availableSlots = new AvailableSlots();
		Page<AvailableSlot> paginated = findPaginated(page);
		availableSlots.getAvaliableSlotList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);
	}

	private String addPaginationModel(int page, Page<AvailableSlot> paginated, Model model) {
		List<AvailableSlot> listSlots = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listSlots", listSlots);
		return "slots/slotList";
	}

	private Page<AvailableSlot> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		try{
			return parkRepository.findAll(pageable);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/post", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> postData(@RequestBody String body) throws JsonProcessingException {
		RequestSlot requestBody = new ObjectMapper().readValue(body, RequestSlot.class);
		AvailableSlot availableSlot = new AvailableSlot();
		availableSlot.setId(counter++);
		availableSlot.setSlot(requestBody.getSlot());
		availableSlot.setPosition(requestBody.getPosition());
		parkRepository.save(availableSlot);
		return new ResponseEntity<>("Data inserted", HttpStatus.OK);
	}
}
