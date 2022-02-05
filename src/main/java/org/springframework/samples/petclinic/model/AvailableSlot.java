package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "parking")
public class AvailableSlot {
	@Id
	@Column(name = "id", nullable = false)
	private int id;

	@JsonProperty("slot")
	@Column(name = "slot", nullable = false)
	String slot;

	@JsonProperty("position")
	@Column(name = "position", nullable = false)
	String position;
}
