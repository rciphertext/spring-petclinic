package org.springframework.samples.petclinic.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class AvailableSlots {
	private List<AvailableSlot> availableSlots;

	@XmlElement
	public List<AvailableSlot> getAvaliableSlotList() {
		if (availableSlots == null) {
			availableSlots = new ArrayList<>();
		}
		return availableSlots;
	}
}
