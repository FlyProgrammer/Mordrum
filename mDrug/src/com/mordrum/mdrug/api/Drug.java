package com.mordrum.mdrug.api;

import org.bukkit.Location;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 6:33 PM
 */
public class Drug extends DrugTemplate {
	private int currentStep;
	private Location locationData;
	private String producerName;

	public Drug(DrugTemplate dt) {
		super(dt);
	}

	public Location getLocationData() {
		return locationData;
	}

	public void setLocationData(Location locationData) {
		this.locationData = locationData;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public void NextStep() {
		this.currentStep++;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
}
