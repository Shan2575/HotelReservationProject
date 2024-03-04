/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
public class RoomType{
  private String type;
  private int numberOfRooms;
  private String occupancyMinimum;
  private String occupancyMaximum;
  private double [] rates = new double[7];
  private int minimumAdults;
  private int adults;
  private int children;
  	/**
  	 * 
  	 * @param type
  	 * @param numberOfRooms
  	 * @param occupancyMinimum
  	 * @param occupancyMaximum
  	 * @param rates
  	 */
    public RoomType(String type, int numberOfRooms, String occupancyMinimum, String occupancyMaximum, double[] rates){
    this.type = type;
    this.numberOfRooms = numberOfRooms;
    this.occupancyMinimum = occupancyMinimum;
    this.occupancyMaximum = occupancyMaximum;
    this.rates = rates;
    setOccupancies();
  }
    
    /**
     * Sets the int value occupancies of a room from the occupancy strings.
     */
    
   public void setOccupancies() {
	   	String[] split = occupancyMinimum.split("\\+");
	    minimumAdults = Integer.parseInt(split[0]);
	    split = occupancyMaximum.split("\\+");
	    adults = Integer.parseInt(split[0]);
	    children = Integer.parseInt(split[1]);
	    
	 }
   /**
    * 
    * @return getMinimumAdults() method returns the minimum adults. Used in making a reservation.
    */
   
   public int getMinimumAdults() {
	   
	   return minimumAdults;
   }
   /**
    * 
    * @return getMaximumAdults() method returns the maximum adults. Used in making a reservation.
    */
   public int getMaximumAdults() {
	   
	   return adults;
   }
   /**
    * 
    * @return getMaximumChildren() method returns the maximum children. Maximum value as total occupancy is worked on in making a reservation.
    * 																	True maximum children works out as total occupancy - 1 as adults can never be 0
    * 																	and the two figures play off each other. Method assumes that where 2+1 is maximum occupancy
    * 																	this can be read also as 1+2.
    * 																	
    */
   public int getMaximumChildren() {
	   
	   return adults + children;
   }
    /**
     * 
     * @return getType() method returns the name of a room type.
     */
  public String getType(){
    return type;
  }
    /**
     * 
     * @return getNumberOfRooms() method returns the number of rooms of a given room type. Used in the checkIfRoomAvailable() method.
     * 															
     */
  public int getNumberOfRooms(){
    return numberOfRooms;
  }
  	/**
  	 * 
  	 * @return getRates[] returns the rates of a room type.
  	 */
 
  public double[] getRates(){
     return rates;
  }
  
  /**
   * 
   * @param i setNumberOfRooms() method sets the number of rooms for a RoomType. 
   * 		  Used only for testing the checkIfRoomAvailable() method in SystemDriver Class, as, of course, the hotels csv files defines these numbers.
   */
  
  public void setNumberOfRooms(int i) {
	 this.numberOfRooms = i;
	  
  }
}
