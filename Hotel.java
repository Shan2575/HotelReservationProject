
import java.util.ArrayList;
/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
public class Hotel{
  private String hotelType;
  private ArrayList<RoomType> roomTypes;
/**
 * 
 * @param hotelType
 */
  public Hotel(String hotelType){
    this.hotelType = hotelType;
    roomTypes = new ArrayList<RoomType>();
  }
  /**
   *
   * @return getHotelType() method returns the hotel type.
   *
   */

  public String getHotelType(){
    return hotelType;
  }
  /**
   * 
   * @return getRoomTypes() method returns the rooms types of a hotel.
   */
  
  public ArrayList<RoomType> getRoomTypes(){
    return roomTypes;
  }
  /**
   * 
   * @param room addRoomType() method adds a RoomType to a hotel.
   */

  public void addRoomType(RoomType room){
    roomTypes.add(room);
  }

}
