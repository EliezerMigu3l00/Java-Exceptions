package model.entities;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Reservation {

	private Integer roomNumber;
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	private static DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Reservation() {
	}
	
	public Reservation(Integer roomNumber, LocalDate checkIn, LocalDate checkOut) {
		this.roomNumber = roomNumber;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public long duration() {
	    long diff = checkOut
	            .atStartOfDay(ZoneId.systemDefault())
	            .toInstant()
	            .toEpochMilli() -
	            checkIn
	            .atStartOfDay(ZoneId.systemDefault())
	            .toInstant()
	            .toEpochMilli();

	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public String updatesDates(LocalDate checkIn, LocalDate checkOut) {
		LocalDate now = LocalDate.now();
		
		if(checkIn.isBefore(now) || checkOut.isBefore(now)) {
			return "Error in reservation: Reservation dates for update must be future dates";
		}  
		if (!checkOut.isAfter(checkIn)) {
			return "Error in reservation: Check-out date must be after check-in date";
		}
		
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		
		return null;
	}

	@Override
	public String toString() {
		return "Reservation: Room " + roomNumber + ", check-in: " + checkIn.format(formatador) + ", check-out: " + checkOut.format(formatador) + ", " + duration() + " nights";
	}
	
}
