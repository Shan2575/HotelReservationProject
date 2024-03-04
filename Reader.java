 
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
public class Reader{
  private String csvFile;
  private ArrayList<Hotel> hotels;
  private ArrayList<Reservation> reservations;
  private ArrayList<Stay> stays;
  private ArrayList<Cancellation> cancellations;
/**
 * 
 * @param file Takes in a file and sets csv to this file string. Depending on the contents of the file string runs different methods.
 */
  public Reader(String file){
    this.csvFile = file;
    if(file.equals("l4Hotels.csv")){
    hotels = new ArrayList<Hotel>();    
    readHotelFile();
    }
    if(file.equals("reservations.csv")){
    readInReservations();
    }
    if(file.equals("stays.csv")){
    stays = new ArrayList<Stay>();
    readInStays();
    }
    if(file.equals("cancellations.csv")){
    cancellations = new ArrayList<Cancellation>();
    readInCancellations();
    }
    
  }
  
  /**
   * @param void readHotelFile() method reads in hotels from the hotels file creating them as hotel objects 
   * 						     as well as filling up each hotel object's RoomType ArrayList.
   */
  
  private void readHotelFile(){
    try{
       File hotelFile = new File(csvFile);
       Scanner readInFile = new Scanner(hotelFile);
       String hotelsString;
       String [] commaSeparated;
       
       while(readInFile.hasNext()){
           hotelsString = readInFile.nextLine();
           commaSeparated = hotelsString.split(",");
           String [] roomCosts = Arrays.copyOfRange(commaSeparated, 5, 12);
           double[] rates = Arrays.stream(roomCosts).mapToDouble(Double::parseDouble).toArray();
           
           
           if(!commaSeparated[0].equals("")){
           
           Hotel s = new Hotel(commaSeparated[0]);
           RoomType t = new RoomType(commaSeparated[1], Integer.parseInt(commaSeparated[2]), commaSeparated[3], commaSeparated[4], rates);
           s.addRoomType(t);
           hotels.add(s);
           
           }
           
           
           if(commaSeparated[0].equals("")){
               
           RoomType t = new RoomType(commaSeparated[1], Integer.parseInt(commaSeparated[2]), commaSeparated[3], commaSeparated[4], rates);
           hotels.get(hotels.size() - 1).addRoomType(t);
           }
        }
    }
    catch(IOException e){
        System.out.println("Could not read from file.");
    }
  }
   /**
   * @param void readInReservations() method reads in reservations from the reservations file.
   */
   protected void readInReservations(){
    reservations = new ArrayList<Reservation>();
    try{
     File userFile = new File(csvFile);
     Scanner readInFile = new Scanner(userFile);
     Reservation reservation;

     String [] commaSeparated;
     String [] dateSplit;
     Date checkInDate;
     

     while(readInFile.hasNext()){
       commaSeparated = readInFile.nextLine().split(",");
       dateSplit = commaSeparated[4].split("/");
       int[] dateSplitIntegers = Arrays.stream(dateSplit).mapToInt(Integer::parseInt).toArray();
       LocalDate localDate = LocalDate.of(dateSplitIntegers[2], dateSplitIntegers[1], dateSplitIntegers[0]);
       checkInDate = java.sql.Date.valueOf(localDate);
       
       
       String [] getRooms = commaSeparated[7].split("/");
       Room [] rooms = new Room[getRooms.length];
       String [] getParts;
       
        for(int i = 0; i < getRooms.length; i++){
        getParts = getRooms[i].split("-");
        boolean breakfastIncluded = false;
        if(getParts[2].equals("TRUE")){ breakfastIncluded = true;}
        Room room = new Room(getParts[0], getParts[1], breakfastIncluded);
        rooms[i] = room;
        }
       reservation = new Reservation(Integer.parseInt(commaSeparated[0]), commaSeparated[1], 
                                     commaSeparated[2], commaSeparated[3], checkInDate,Integer.parseInt(commaSeparated[5]), 
                                     Integer.parseInt(commaSeparated[6]), rooms,
                                     Double.parseDouble(commaSeparated[8]), Double.parseDouble(commaSeparated[9]));

       reservations.add(reservation);
     }
    }
    catch(IOException e){
     System.out.println("Could not read from file.");
     e.printStackTrace();
    }
   }
   
   /**
    * @param void readInStays() method reads in stays from the stays file.
    */
   public void readInStays(){
    stays = new ArrayList<Stay>();
    
    try{
     File userFile = new File(csvFile);
     Scanner readInFile = new Scanner(userFile);
     Stay stay;
     Reservation reservation;

     String [] commaSeparated;
     String [] dateSplit;
     Date checkInDate;
     
     

     while(readInFile.hasNext()){
       commaSeparated = readInFile.nextLine().split(",");
       dateSplit = commaSeparated[4].split("/");
       int[] dateSplitIntegers = Arrays.stream(dateSplit).mapToInt(Integer::parseInt).toArray();
       LocalDate localDate = LocalDate.of(dateSplitIntegers[2], dateSplitIntegers[1], dateSplitIntegers[0]);
       checkInDate = java.sql.Date.valueOf(localDate);
       
       String [] getRooms = commaSeparated[7].split("/");
       Room [] rooms = new Room[getRooms.length];
       String [] getParts;
       
        for(int i = 0; i < getRooms.length; i++){
        getParts = getRooms[i].split("-");
        boolean breakfastIncluded = false;
        if(getParts[2].equals("TRUE")){ breakfastIncluded = true;}
        Room room = new Room(getParts[0], getParts[1], breakfastIncluded);
        rooms[i] = room;
        }
       reservation = new Reservation(Integer.parseInt(commaSeparated[0]), commaSeparated[1], 
                                     commaSeparated[2], commaSeparated[3], checkInDate,Integer.parseInt(commaSeparated[5]), 
                                     Integer.parseInt(commaSeparated[6]), rooms,
                                     Double.parseDouble(commaSeparated[8]), Double.parseDouble(commaSeparated[9]));

       stay = new Stay(reservation);
       if(commaSeparated.length == 12){
       Date actualCheckOut;
       dateSplit = commaSeparated[11].split("/");
       dateSplitIntegers = Arrays.stream(dateSplit).mapToInt(Integer::parseInt).toArray();
       localDate = LocalDate.of(dateSplitIntegers[2], dateSplitIntegers[1], dateSplitIntegers[0]);
       actualCheckOut = java.sql.Date.valueOf(localDate);
       stay.setActualCheckOut(actualCheckOut);
       }
       stays.add(stay);
       
     }
    }
    catch(IOException e){
     System.out.println("Could not read from file.");
     e.printStackTrace();
    }
   }
   /**
    * @param void readInCancellations() method reads in cancellations from the cancellations file.
    */
   public void readInCancellations(){
       
       
    cancellations = new ArrayList<Cancellation>();
    try{
     File userFile = new File(csvFile);
     Scanner readInFile = new Scanner(userFile);
     Cancellation cancellation;
     Reservation reservation;

     String [] commaSeparated;
     String [] dateSplit;
     Date checkInDate;
     Date cancellationDate;
     

     while(readInFile.hasNext()){
       commaSeparated = readInFile.nextLine().split(",");
       dateSplit = commaSeparated[4].split("/");
       int[] dateSplitIntegers = Arrays.stream(dateSplit).mapToInt(Integer::parseInt).toArray();
       LocalDate localDate = LocalDate.of(dateSplitIntegers[2], dateSplitIntegers[1], dateSplitIntegers[0]);
       checkInDate = java.sql.Date.valueOf(localDate);
       
       
       String [] getRooms = commaSeparated[7].split("/");
       Room [] rooms = new Room[getRooms.length];
       String [] getParts;
       
        for(int i = 0; i < getRooms.length; i++){
        getParts = getRooms[i].split("-");
        boolean breakfastIncluded = false;
        if(getParts[2].equals("TRUE")){ breakfastIncluded = true;}
        Room room = new Room(getParts[0], getParts[1],breakfastIncluded);
        rooms[i] = room;
        }
       reservation = new Reservation(Integer.parseInt(commaSeparated[0]), commaSeparated[1], 
                                     commaSeparated[2], commaSeparated[3], checkInDate,Integer.parseInt(commaSeparated[5]), 
                                     Integer.parseInt(commaSeparated[6]), rooms,
                                     Double.parseDouble(commaSeparated[8]), Double.parseDouble(commaSeparated[9]));
                                     

       dateSplit = commaSeparated[10].split("/");
       dateSplitIntegers = Arrays.stream(dateSplit).mapToInt(Integer::parseInt).toArray();
       localDate = LocalDate.of(dateSplitIntegers[2], dateSplitIntegers[1], dateSplitIntegers[0]);
       cancellationDate = java.sql.Date.valueOf(localDate);
       
       Boolean bool = false;
       if(commaSeparated[10].equals("TRUE")){bool = true;}
       if(commaSeparated[10].equals("FALSE")){bool = false;}
                                     
       cancellation = new Cancellation(reservation,cancellationDate,bool);
       cancellations.add(cancellation);
     }
    }
    catch(IOException e){
     System.out.println("Could not read from file.");
     e.printStackTrace();
    }
    
    
    
    }
   
   /**
    * 
    * @return getReservations() method returns a list of reservations. Used in the generation of cancellations and stays.
    */
  
  public ArrayList<Reservation> getReservations(){
    return reservations;
    }
  
  /**
   * 
   * @return getHotels() returns a list of hotels. Used in making reservations and also analytics.
   */
  public ArrayList<Hotel> getHotels(){
    return hotels;
  }
  /**
   * 
   * @return getStays() method returns a list of stays. Used in analytics and also in setting actual check out time of guest.
   */
  public ArrayList<Stay> getStays(){
    return stays;
    }
  /**
   * 
   * @return getCancellations() returns a list of cancellations. Used in analytics.
   */
  public ArrayList<Cancellation> getCancellations(){
    return cancellations;
    }
  /**
   * 
   * @return getAllRoomTypes() method returns a list of all RoomTypes. Needed by analytics as unfortunately we didn't think to store price per room 
   * 																   when generating total costs for reservations. (Quick fix.)
   */
  public ArrayList<RoomType> getAllRoomTypes(){
    ArrayList<RoomType> roomTypes = new ArrayList<RoomType>();
    for(int i = 0; i < hotels.size(); i++){
        ArrayList<RoomType> hotelRoomTypes = hotels.get(i).getRoomTypes();
        for(int j = 0; j < hotelRoomTypes.size(); j++){
        roomTypes.add(hotelRoomTypes.get(j));
        }
    }
    return roomTypes;
   }


 }



