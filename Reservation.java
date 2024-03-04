 

import java.util.Date;
import java.util.Calendar;
/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
public class Reservation{
  private int reservationNumber;
  private String reservationName;
  private String hotelType;
  private String reservationType;
  private Date checkInDate;
  private int numberOfNights;
  private int numberOfRooms;
  private Room[] rooms;
  private double deposit;
  private double totalCost;
  /**
   * 
   * @param reservationNumber
   * @param reservationName
   * @param hotelType
   * @param reservationType
   * @param checkInDate
   * @param numberOfNights
   * @param numberOfRooms
   * @param rooms
   * @param deposit
   * @param totalCost
   */
  public Reservation(int reservationNumber, String reservationName, String hotelType,
                     String reservationType, Date checkInDate,
                     int numberOfNights, int numberOfRooms, Room[] rooms, double deposit, double totalCost){

    this.reservationNumber = reservationNumber;
    this.reservationName = reservationName;
    this.hotelType = hotelType;
    this.reservationType = reservationType;
    this.checkInDate = checkInDate;
    this.numberOfNights = numberOfNights;
    this.numberOfRooms = numberOfRooms;
    this.rooms = rooms;
    this.deposit = deposit;
    this.totalCost = totalCost;
    
  }
/**
 * 
 * @return getReservationNumber() returns reservation number of a reservation. Used in generating stays and cancellations. 
 */
  public int getReservationNumber(){
    return reservationNumber;
  }
  /**
   * 
   * @return getReservationName() returns name on reservation. Unused.
   */

  public String getReservationName(){
    return reservationName;
  }
  /**
   * 
   * @return getHotelType() method returns the hotel type of a reservation. Used by analytics.
   */
  
  public String getHotelType(){
    return hotelType;
  }
  /**
   * 
   * @return getReservationType() method returns the reservation type (S/AP). Used in generation cancellations.
   */

  public String getReservationType(){
    return reservationType;
  }
  /**
   * 
   * @return getCheckInDate() method returns the check-in date of a reservation. Used in generating stays and also in analytics.
   */

  public Date getCheckInDate(){
    return checkInDate;
  }
  /**
   * 
   * @return getNumberOfNights() method returns the number of nights. Used in generating stays and also in analytics. 
   */

  public int getNumberOfNights(){
    return numberOfNights;
  }
  
  /**
   * 
   * @return getTotalCost() method returns total cost of reservation.
   */

  public double getTotalCost(){
    return totalCost;
  }
  /**
   * 
   * @param newCost setTotalCost() method sets the total cost of a reservation. Used in applying discounts to stays.
   */
  
  public void setTotalCost(double newCost){
      this.totalCost = newCost;
  }
  /**
   * 
   * @return getRooms() method returns the rooms of a method. Used in analytics.
   */
  
  public Room[] getRooms(){
    return rooms;
  }
  /**
   * 
   * @return getDeposit() method returns the deposit of a reservation.
   */
  
  public double getDeposit(){
     return deposit;
  }
  /**
   * 
   */

  public String toString(){
    int day, month, year;
    Calendar cal = Calendar.getInstance();
    cal.setTime(checkInDate);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH) + 1;
    day = cal.get(Calendar.DAY_OF_MONTH);
    
    String roomsDetails = "";
    for(int i = 0; i < rooms.length; i++){
      if(i == rooms.length - 1){
        roomsDetails += rooms[i].getRoomType() + "-" + rooms[i].getOccupancy() + "-" + rooms[i].getBreakfastIncluded();
      }
      else{
        roomsDetails += rooms[i].getRoomType() + "-" + rooms[i].getOccupancy() + "-" + rooms[i].getBreakfastIncluded() + "/";
      }
    }
    
    return reservationNumber + "," + reservationName + "," + hotelType + "," + reservationType + "," + day + "/" + month + "/" + year +
                                      "," + numberOfNights + "," + numberOfRooms + "," + roomsDetails + "," + deposit + "," + totalCost;
}
}


