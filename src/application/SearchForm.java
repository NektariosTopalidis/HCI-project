package application;

import java.time.LocalDate;

public class SearchForm {
	private String destination;
	private Integer peopleAmount;
	private Boolean petCheck;
	private LocalDate fromDate;
	private LocalDate toDate;

	public SearchForm(String destination,Integer peopleAmount,Boolean petCheck,LocalDate fromDate,LocalDate toDate) {
		this.destination = destination;
		this.peopleAmount = peopleAmount;
		this.petCheck = petCheck;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getDestination() {
		return destination;
	}

	public Integer getPeopleAmount() {
		return peopleAmount;
	}

	public Boolean getPetCheck() {
		return petCheck;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setPeopleAmount(Integer peopleAmount) {
		this.peopleAmount = peopleAmount;
	}

	public void setPetCheck(Boolean petCheck) {
		this.petCheck = petCheck;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
}
