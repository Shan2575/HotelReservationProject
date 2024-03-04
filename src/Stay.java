import java.util.Date;
import java.util.Calendar;

/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */

public class Stay{
  private Reservation reservation;
  private Date checkIn;
  private Date expectedCheckOut;
  private Date actualCheckOut;
/**
 * 
 * @param reservation
 */
  public Stay(Reservation reservation){
    this.reservation = reservation;
    this.checkIn = new Date();
    setExpectedCheckOut();
  }

  /**
   * 
   * @return getReservation() method returns the reservation.
   */
  public Reservation getReservation(){
     return reservation;
  }
/**
 * @param void setExpectedCheckOut() methods sets the expected check-out of a stay.
 */
  public void setExpectedCheckOut(){
    Date expectedCheckOut;
    Calendar cal = Calendar.getInstance();
    cal.setTime(checkIn);
    cal.add(Calendar.DATE, reservation.getNumberOfNights());
    expectedCheckOut = cal.getTime();
    
    
    this.expectedCheckOut = expectedCheckOut;
        
  }
  /**
   * 
   * @param date setActualCheckOut() method sets the actual check-out of a stay.
   */
  
  public void setActualCheckOut(Date date){
    this.actualCheckOut = date;
  }
/**
 *   
 * @return getActualCheckOut() method returns the actual check-out of a stay.
 */
  public Date getActualCheckOut(){
      return actualCheckOut;
  }
  /**
   * 
   * @return getCheckIn() method returns the check-in of a stay.
   */

  public Date getCheckIn(){
    return getReservation().getCheckInDate();
  }
  /**
   * 
   * @return getExpectedCheckOut() method returns the expected check-out of a stay.
   */
  
  public Date getExpectedCheckOut(){
    return expectedCheckOut;
  }
  /**
   * 
   * @return getCost() method returns the total cost of a stay.
   */

  public double getCost(){
    return reservation.getTotalCost();
  }
  /**
   * @param toString() method prints stays. If actual check-out has been set, prints an extra field.
   */
  public String toString(){
    int day, month, year;
    int d, m, y;
    Calendar cal = Calendar.getInstance();
    cal.setTime(getExpectedCheckOut());
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH) + 1;
    day = cal.get(Calendar.DAY_OF_MONTH);
    if(actualCheckOut != null){
    cal = Calendar.getInstance();
    cal.setTime(getActualCheckOut());
    y = cal.get(Calendar.YEAR);
    m = cal.get(Calendar.MONTH) + 1;
    d = cal.get(Calendar.DAY_OF_MONTH);
    
    return reservation.toString() + "," + day + "/" + month + "/" + year + "," + d + "/" + m + "/" + y;}
    
    return reservation.toString() + "," + day + "/" + month + "/" + year;
    
    }
}
