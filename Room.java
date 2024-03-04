 
/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */

public class Room{
	  private String roomType;
	  private String occupancy;
	  private boolean breakfastIncluded;
	  /**
	   * 
	   * @param roomType
	   * @param occupancy
	   * @param breakfastIncluded
	   */
	  
	  public Room(String roomType, String occupancy, boolean breakfastIncluded){
	    this.roomType = roomType;
	    this.occupancy = occupancy;
	    this.breakfastIncluded = breakfastIncluded;
	  }
	  /**
	   * 
	   * @return getRoomType() method returns the room type of a room.
	   */
	  public String getRoomType(){
	    return roomType;
	  }
	  /**
	   * 
	   * @return getOccupancy() method returns the occupancy of a room.
	   */
	  public String getOccupancy(){
	    return occupancy;
	  }
	  /**
	   * 
	   * @return getBreakfastIncluded() method returns the breakfast included status of a room.
	   */
	  public boolean getBreakfastIncluded(){
	    return breakfastIncluded;
	  }
	}
