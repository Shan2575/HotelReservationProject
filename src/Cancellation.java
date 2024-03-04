 
/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
import java.util.Date;
import java.util.Calendar;

/**
 * 
 * This class takes it a reservation and converts it to a cancellation for the cancellations.csv
 *
 */

public class Cancellation{
  private Reservation reservation;
  private Date cancellationDate;
  private boolean refunded;
/**
 * 
 * @param reservation Takes in a reservation, converts it to a cancellation. Adds a cancellation date, which is the date it is cancelled. 
 * 																			 Adds a refunded status.
 * 
 */
  public Cancellation(Reservation reservation){
    this.reservation = reservation;
    cancellationDate = new Date();
    refunded = checkRefund();
  }
  /**
   * 
   * @param reservation
   * @param cancellationDate
   * @param refunded
   */
  public Cancellation(Reservation reservation, Date cancellationDate, boolean refunded){
    this.reservation = reservation;
    this.cancellationDate = cancellationDate;
    this.refunded = refunded;
  }
  /**
   * 
   * @return getDate() method returns the cancellation date.
   */
  public Date getDate(){
    return cancellationDate;
  }
  /**
   * 
   * @return getRefunded() method returns the refunded status.
   */
  public boolean getRefunded(){
    return refunded;
  }
    /**
     * 
     * @return getReservations() method returns the reservation the cancellation is based on.
     */
  public Reservation getReservation(){
    return reservation;
  }
  /**
   * 
   */
  public String toString(){
    int day, month, year;
    Calendar cal = Calendar.getInstance();
    cal.setTime(cancellationDate);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH) + 1;
    day = cal.get(Calendar.DAY_OF_MONTH);
    return reservation.toString() + "," + day + "/" + month + "/" + year + "," + refunded;
    
   }
  /**
   * 
   * @return checkRefund() method returns the refund status. Used by analytics.
   */

  private boolean checkRefund(){
    
    Date checkInDate = reservation.getCheckInDate();
    Date payBefore = checkInDate;
    
    Calendar cal = Calendar.getInstance();
    cal.setTime(payBefore);
    cal.add(Calendar.DATE, -1);
    payBefore = cal.getTime();  
      
    if(reservation.getReservationType().equals("S") && cancellationDate.before(payBefore)){
    return true;
      
    }
    return false;
  }
}
