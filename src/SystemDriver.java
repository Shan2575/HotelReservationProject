

import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.*; 
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * @author 
 * Sean Barrett 15124126
 * Daniel Clarke 18249736
 * Martin Vaughan 16158431
 * Brian Shanahan 17218829
 */
public class SystemDriver{
  private Reader readInReservations;
   
  
  public SystemDriver(){
    readInReservations = new Reader("reservations.csv");
    removeExpired();
   }
  /**
   * 
   */
   public void run(){
    Scanner sc = new Scanner(System.in);
    int choice;
    
    System.out.println("1.Customer login\n2.Hotel desk personnel login\n3.Supervisor login\n4.Exit");
    choice = sc.nextInt();
    if(choice == 4) System.exit(0);
    //CUSTOMER'S MENU========================================================================================
    if(choice == 1){
        
        System.out.println("1.Make reservation\n2.Make cancellation\n3.Log out");
        int customerChoice = 0;
        while(customerChoice > 3 || customerChoice < 1){
        customerChoice = sc.nextInt();
        }
    if(customerChoice == 3) System.exit(1);
    if(customerChoice == 1){
    makeReservation();
    run();
    
    }
    if(customerChoice == 2){
    makeCancellation();
    run();
    
    }
    }
    
    
    //DESK MENU
    if(choice == 2){
        
        System.out.println("1.Make reservation\n2.Make cancellation\n3.Check in customer\n4.Check out customer\n5.Log out");
        int deskChoice = 0;
        while(deskChoice > 5 || deskChoice < 1){
        deskChoice = sc.nextInt();
        }
        if(deskChoice == 5) System.exit(2);
        if(deskChoice == 1){
        makeReservation();
        run();
        }
        if(deskChoice == 2){
        makeCancellation();
        run();
        }
        if(deskChoice == 3){
            registerStay();
            run();
        
         
        }
        if(deskChoice == 4){
        registerCheckOut();
        run();
        }
        
      }
    
      
      //SUPERVISOR
      if(choice == 3){
        
        
        System.out.println("1.Make reservation\n2.Make cancellation\n3.Check in customer\n4.Check out customer\n5.Apply discount\n6.Analytics\n7.Log out");
        int supervisorChoice = 0;
        while(supervisorChoice > 7 || supervisorChoice < 1){
        supervisorChoice = sc.nextInt();
        }
        if(supervisorChoice == 7) System.exit(3);
        if(supervisorChoice == 1){
        makeReservation();
        run();
        }
        if(supervisorChoice == 2){
        makeCancellation();
        run();
        }
        if(supervisorChoice == 3){
        registerStay();
        run();
        }
        if(supervisorChoice == 4){
        registerCheckOut();
        run();
        }
        
        if(supervisorChoice == 5){
        applyDiscountToStay();
        run();
        }
        if(supervisorChoice == 6){
        analyticsEngine();
        run();
        }
  }}
/**
 * 
 * @return getDate() method gets a check-in date for a reservation.
 */
public Date getDate(){
    
    int [] days = {31,28,31,30,31,30,31,31,30,31,30,31};
    int day;
    int month;
    int year;
    Date today = new Date();
    
    int thisYear;
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    thisYear = cal.get(Calendar.YEAR);
    
    Scanner sc = new Scanner(System.in);
    System.out.println("Reservations only offered up until a year from current date.");
    System.out.println("Enter the check in date: ");
    System.out.println("Year:");
    year = sc.nextInt();
    while(year < thisYear || year > thisYear + 5){
    System.out.println("Invalid year.");
    System.out.println("Year:");
    year = sc.nextInt();
    }
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        days[1] = 29;
    System.out.print("Month:");
    month = sc.nextInt();
    while(month < 1 || month > 12){
    System.out.println("Invalid date.");
    System.out.println("Month:");
    month = sc.nextInt();
    }
    System.out.print("Day:");
    day = sc.nextInt();
    while(day < 1 || day > days[month -1]){
    System.out.println("Invalid date.");
    System.out.println("Day:");
    day = sc.nextInt();
    }
    LocalDate localDate = LocalDate.of(year, month, day);
    Date reservationDate = java.sql.Date.valueOf(localDate);
    return reservationDate;
}

/**
 * @param void makeReservation() method builds a reservation object.
 */
    public void makeReservation(){
    Scanner sc = new Scanner(System.in);
    Reader reader = new Reader("l4Hotels.csv");
    Reservation reservation;
    int reservationNumber;
    String reservationName;
    String reservationType;
    Date checkInDate;
    int numberOfNights;
    int numberOfRooms;
    Room[] rooms;
    double totalCost = 0;
    double deposit;
    
    
    System.out.println("Please enter a reservation number:");
    reservationNumber = sc.nextInt();
    System.out.println("Enter the reservation name:");
    reservationName = sc.next();
    System.out.println("Choose hotel:");
    
    ArrayList<Hotel> hotelsList = reader.getHotels();
    
    
    int k = 0;
    String hotelString = "";
    char c = 'A';
      
      while(hotelString == ""){
      for (Hotel hotelChoice : hotelsList){
            System.out.println(c + ") " + hotelChoice.getHotelType()); 
            c++;
         }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < hotelsList.size()){
            hotelString = hotelsList.get(n).getHotelType();
      }
      c = 'A';
      }
      
      for(; k <hotelsList.size();k++){
        if(hotelsList.get(k).getHotelType().equals(hotelString)) break;
        
        }
    System.out.print("Reservation type - S/AP:");
    reservationType = sc.next();
    while(!(reservationType.equals("S")||reservationType.equals("AP")))
    {
        System.out.println("Invalid reservation type.");
        System.out.print("Reservation type - S/AP:");
        reservationType = sc.next();
    }
    checkInDate = getDate();
    Date today = new Date();
    Date dateLimit = today;
    Calendar cal = Calendar.getInstance();
        cal.setTime(dateLimit);
        cal.add(Calendar.DATE, 365);
        dateLimit = cal.getTime();
    while(checkInDate.before(today) || checkInDate.after(dateLimit)){
        System.out.println("Invalid date. Cannot be past date or date beyond 365 days from current date");
        checkInDate = getDate();
    }
    System.out.println(checkInDate.toString());
    
    System.out.print("Number of nights:");
    numberOfNights = sc.nextInt();
    while(numberOfNights > 28){
    System.out.println("Reservations only tendered for 28 day periods.");
    numberOfNights = sc.nextInt();
    }
    
    Date expectedCheckOut;
    cal = Calendar.getInstance();
    cal.setTime(checkInDate);
    cal.add(Calendar.DATE, numberOfNights);
    expectedCheckOut = cal.getTime();
    
    System.out.print("Number of rooms:");
    numberOfRooms = sc.nextInt();
    while(numberOfRooms > 50){
    System.out.println("Bookings of over 50 rooms not permitted.");
    numberOfRooms = sc.nextInt();}
    System.out.print("Enter the deposit: ");
    deposit = sc.nextDouble();
    rooms = new Room[numberOfRooms];
    String occupancy;
    ArrayList<RoomType> roomTypeList = hotelsList.get(k).getRoomTypes();

    
    for(int i = 0; i < rooms.length; i++){
      RoomType roomtype = hotelsList.get(k).getRoomTypes().get(0);
      String typeOfRoom = "";
      int adults;
      int children;
      String breakfast;
      boolean breakfastIncluded = false;
      
      
       c = 'A';
      
      while(typeOfRoom == ""){
      for (RoomType typeChoice : roomTypeList){
            System.out.println(c + ") " + typeChoice.getType()); 
            c++;
         }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < roomTypeList.size()){
            
            if(checkIfRoomAvailable(roomTypeList.get(n), checkInDate, expectedCheckOut)) {
            	typeOfRoom = roomTypeList.get(n).getType();
            	roomtype = roomTypeList.get(n);
            }
            else {
            	System.out.println("Sorry, room unavailable for these dates");
            	
            }
          }
      c = 'A';
      }
      System.out.println("Occupancy:");
      System.out.println("Enter adults:");
      adults = sc.nextInt();
      while(adults < roomtype.getMinimumAdults() || adults > roomtype.getMaximumAdults()) {
    	  System.out.println("Over capacity");
    	  System.out.println("Enter adults:");
          adults = sc.nextInt();
    	}
      System.out.println("Enter children:");
      children = sc.nextInt();
      while(children > roomtype.getMaximumChildren() - adults) {
    	  System.out.println("Over capacity");
    	  System.out.println("Enter children:");
          children = sc.nextInt();
      }
      occupancy = Integer.toString(adults) + " + " + Integer.toString(children);
      System.out.println("Breakfast included - Y/N:");
      breakfast = sc.next();
      while(!(breakfast.equals("Y") || breakfast.equals("N"))){
        breakfast = sc.next();
      }
      if(breakfast.equals("Y")) breakfastIncluded = true;
      

      rooms[i] = new Room(typeOfRoom,occupancy,breakfastIncluded);

    }
    ArrayList<RoomType> rates = reader.getAllRoomTypes();
    for(int e = 0; e < rooms.length; e++){
    for(int f = 0; f < rates.size(); f++){
        if(rates.get(f).getType().equals(rooms[e].getRoomType())){
          double[] roomRates = rates.get(f).getRates();
          for(int g = 0; g < numberOfNights; g++){
            totalCost += roomRates[((checkInDate.getDay() + g) % 7)];
          }
        }
      }
    }
    
    
    System.out.println("Total cost is:" + Double.toString(totalCost));
    reservation = new Reservation(reservationNumber, reservationName, hotelString, 
                                  reservationType,checkInDate, 
                                  numberOfNights, numberOfRooms, 
                                  rooms, deposit, totalCost);
    
    try{
    
     File file = new File("reservations.csv");
     Scanner fileIn = new Scanner(file);
     
    try {
        if(fileIn.hasNext()){
        PrintWriter printWriter = new PrintWriter(new FileWriter("reservations.csv", true));
        printWriter.write(reservation.toString() + "\n");
        printWriter.close();
        }
        
        if(!fileIn.hasNext()){
    
        
            PrintWriter printWriter = new PrintWriter(new FileWriter("reservations.csv"));
            printWriter.write(reservation.toString() + "\n");
            printWriter.close();
        } 
        } catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
   
    
    }
    catch(IOException e){
     System.out.println("Could not read from file.");
     e.printStackTrace();
    }
    System.out.println("Reservation successfully processed.");
    
    }
    /**
     * @param void makeCancellation() method builds a cancellation object.
     */
    
    public void makeCancellation(){
    Scanner sc = new Scanner(System.in);
    readInReservations.readInReservations();
    ArrayList<Reservation> validReservations = readInReservations.getReservations();
    Reservation userReservation = validReservations.get(validReservations.size()-1);
    int i = 0;
    int k = 0;
    for(Reservation r: validReservations){System.out.println(r.toString());}
    int userInput;
    System.out.println("Please enter reservation number:");
    userInput = sc.nextInt();
    while(i < 5 && k < 1 ){
    for(int j = 0; j < validReservations.size(); j++){
        
        
        if(validReservations.get(j).getReservationNumber() == userInput){
        userReservation = validReservations.get(j);
        k++;
        break;
            
        }}
       
        
        if(k < 1){
        System.out.println("Invalid registration number.");
        System.out.println("Please enter reservation number:");
        userInput = sc.nextInt();}
        i++;
    }
    
    if(i == 5) run();
    System.out.println(userReservation);
    Cancellation userCancellation = new Cancellation(userReservation);
    try{
    File file = new File("cancellations.csv");
    Scanner fileIn = new Scanner(file);
    try {
        if(fileIn.hasNext()){
        PrintWriter printWriter = new PrintWriter(new FileWriter("cancellations.csv", true));
        printWriter.write(userCancellation.toString() + "\n");
        printWriter.close();
        }
        if(!fileIn.hasNext()){
    
        
            PrintWriter printWriter = new PrintWriter(new FileWriter("cancellations.csv"));
            printWriter.write(userCancellation.toString() + "\n");
            printWriter.close();
        } 
        } catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
   }
    catch(IOException e){
     System.out.println("Could not read from file. " + "cancellations.csv");
     e.printStackTrace();
    }
    
    }
    /**
     * @param void registerStay() method builds a stay object.
     */
    public void registerStay(){
    Date today = new Date();     
    int day, month, year;
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH) + 1;
    day = cal.get(Calendar.DAY_OF_MONTH);
    String todayDate = year + "-" + month + "-" + day;
    Scanner sc = new Scanner(System.in);
    readInReservations.readInReservations();
    ArrayList<Reservation> validReservations = readInReservations.getReservations();
    Reservation userReservation = validReservations.get(validReservations.size()-1);
    int i = 0;
    int k = 0;
    for(Reservation r: validReservations){System.out.println(r.toString());}
    int userInput;
    System.out.println("Please enter reservation number:");
    userInput = sc.nextInt();
    while(i < 5 && k < 1 ){
    for(int j = 0; j < validReservations.size(); j++){
        
        
        if(validReservations.get(j).getReservationNumber() == userInput){
        if(validReservations.get(j).getCheckInDate().toString().equals(todayDate)){
            
        userReservation = validReservations.get(j);
        k++;
        break;}
        else{
            System.out.println("Cannot check in before or after prearranged check-in date.");
        
        }
            
        }}
       
        
        if(k < 1){
        System.out.println("Invalid registration number.");
        System.out.println("Please enter reservation number:");
        userInput = sc.nextInt();}
        i++;
    }
    
    if(i == 5) run();
    System.out.println(userReservation);
    System.out.println(userReservation.getCheckInDate().toString());
    System.out.println(todayDate);
    
    Stay stay = new Stay(userReservation);
    
    try{
    File file = new File("stays.csv");
    Scanner fileIn = new Scanner(file);
    try {
        if(fileIn.hasNext()){
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv", true));
        printWriter.write(stay.toString() + "\n");
        printWriter.close();
        }
        if(!fileIn.hasNext()){
    
        
            PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv"));
            printWriter.write(stay.toString() + "\n");
            printWriter.close();
        } 
        } catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    catch(IOException e){
     System.out.println("Could not read from file.");
     e.printStackTrace();
    }
    }
    public void registerCheckOut(){
        
    Date actualCheckOut = new Date();
    
    
    Scanner sc = new Scanner(System.in);
    int i = 0;
    int k = 0;
    int index = 0;
       
    
        
    ArrayList<Stay> stays;    
    Reader readInStays = new Reader("stays.csv");
    stays = readInStays.getStays();
    Stay userStay = stays.get(stays.size()-1);
    for(Stay s: stays){
    System.out.println(s.toString());
    }
    
    int userInput;
    System.out.println("Please enter reservation number:");
    userInput = sc.nextInt();
    
    while(i < 5 && k < 1 ){
    for(int j = 0; j < stays.size(); j++){
        
        
        if(stays.get(j).getReservation().getReservationNumber() == userInput){
        
            
        userStay = stays.get(j);
        index = j;
        k++;
        break;
        
        
            
        }
       
        }
        if(k < 1){
        System.out.println("Invalid registration number.");
        System.out.println("Please enter reservation number:");
        userInput = sc.nextInt();
        i++;
    }}
    System.out.println(userStay.toString());
    stays.get(index).setActualCheckOut(actualCheckOut);
    
    
    
    for(i = 0; i < stays.size();i++){
        if(i == 0){
        try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv"));
        printWriter.write(stays.get(0).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    else{
    try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv", true));
        printWriter.write(stays.get(i).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    
    }
    
}
}
/**
 * @param void applyDiscountToStay() method applies a discount to a stay.
 */
public void applyDiscountToStay(){
          
    Scanner sc = new Scanner(System.in);
    int i = 0;
    int k = 0;
    int index = 0;
       
    
        
    ArrayList<Stay> stays;    
    Reader readInStays = new Reader("stays.csv");
    stays = readInStays.getStays();
    Stay userStay = stays.get(stays.size()-1);
    for(Stay s: stays){
    System.out.println(s.toString());
    }
    
    int userInput;
    System.out.println("Please enter reservation number:");
    userInput = sc.nextInt();
    
    while(i < 5 && k < 1 ){
    for(int j = 0; j < stays.size(); j++){
        
        
        if(stays.get(j).getReservation().getReservationNumber() == userInput){
        
            
        userStay = stays.get(j);
        index = j;
        k++;
        break;
        
        
            
        }
       
        }
        if(k < 1){
        System.out.println("Invalid registration number.");
        System.out.println("Please enter reservation number:");
        userInput = sc.nextInt();
        i++;
    }}
    System.out.println(userStay.toString());
    stays.get(index).getReservation().setTotalCost((stays.get(index).getReservation().getTotalCost()/5)*4);
    
    
    
    for(i = 0; i < stays.size();i++){
        if(i == 0){
        try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv"));
        printWriter.write(stays.get(0).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    else{
    try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv", true));
        printWriter.write(stays.get(i).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    }
    }
/**
 * @param void removeExpired() method removes all stays and cancellationis older than 7 years old.
 */
    public void removeExpired(){
    ArrayList<Stay> stays;    
    Reader readInStays = new Reader("stays.csv");
    stays = readInStays.getStays();
    ArrayList<Cancellation> cancellations;    
    Reader readInCancellations = new Reader("cancellations.csv");
    cancellations = readInCancellations.getCancellations();
    Date today = new Date();
    Date dateLimit = today;
    Calendar cal = Calendar.getInstance();
        cal.setTime(dateLimit);
        cal.add(Calendar.DATE, -2555);
        dateLimit = cal.getTime();
    ArrayList<Cancellation> cancellationsLessThan7YearsOld = new ArrayList<Cancellation>();
    ArrayList<Stay> staysLessThan7YearsOld = new ArrayList<Stay>();
    for(Cancellation s: cancellations){
    if(s.getDate().after(dateLimit)){cancellationsLessThan7YearsOld.add(s);}
    }
    for(Stay s: stays){
    if(s.getCheckIn().after(dateLimit)){staysLessThan7YearsOld.add(s);}
    }
    cancellations = cancellationsLessThan7YearsOld;
    stays = staysLessThan7YearsOld;
    for(int i = 0; i < stays.size();i++){
        if(i == 0){
        try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv"));
        printWriter.write(stays.get(0).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    else{
    try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("stays.csv", true));
        printWriter.write(stays.get(i).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    }
    for(int i = 0; i < cancellations.size();i++){
        if(i == 0){
        try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("cancellations.csv"));
        printWriter.write(cancellations.get(0).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    else{
    try{
        PrintWriter printWriter = new PrintWriter(new FileWriter("cancellations.csv", true));
        printWriter.write(cancellations.get(i).toString() + "\n");
        printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Could not open BufferedWriter.");
            e.printStackTrace();
        }
    }
    }
    }
    /**
     *@param analyticsEngine() method runs analytics on the csv files given user inputs. 
     */
    public void analyticsEngine(){
    ArrayList<Stay> stays;    
    Reader readInStays = new Reader("stays.csv");
    stays = readInStays.getStays();
    ArrayList<Cancellation> cancellations;    
    Reader readInCancellations = new Reader("cancellations.csv");
    cancellations = readInCancellations.getCancellations();
    Scanner sc = new Scanner(System.in);
    int choice;
    System.out.println("Enter start date:");
    Date startDate = getDates();
    Date today = new Date();
    Date dateLimit = today;
    Calendar cal = Calendar.getInstance();
        cal.setTime(dateLimit);
        cal.add(Calendar.DATE, -2555);
        dateLimit = cal.getTime();
    while(startDate.after(today) || startDate.before(dateLimit)){
        System.out.println("Invalid date. Cannot be future date or date before 7 years from current date");
        startDate = getDates();
    }
    System.out.println("Enter finish date:");
    Date finishDate = getDates();
    while(finishDate.after(today) || finishDate.before(dateLimit) || finishDate.before(startDate)){
        System.out.println("Invalid date. Cannot be future date or date before start date");
        finishDate = getDates();
    }
    System.out.println("1.Hotel Occupancy\n2.Specific Room Occupancy\n3.Hotel Earnings\n4.Room Earnings\n5.Exit");
    choice = sc.nextInt();
    while(choice > 5 || choice < 0){choice = sc.nextInt();}
    if(choice == 5){
    run();
    }
    if(choice == 1){
    int occupants = 0;
    Reader reader = new Reader("l4Hotels.csv");
    ArrayList<Hotel> hotelsList = reader.getHotels();
    int k = 0;
    String hotelString = "";
    char c = 'A';
      
      while(hotelString == ""){
      for (Hotel hotelChoice : hotelsList){
            System.out.println(c + ") " + hotelChoice.getHotelType()); 
            c++;
      }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < hotelsList.size()){
            hotelString = hotelsList.get(n).getHotelType();
            
      }
      c = 'A';
      }
       
    for(Stay s: stays){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getExpectedCheckOut()))){
    if(s.getReservation().getHotelType().equals(hotelString)){
    String occupancy = "";
    Room[]rooms = s.getReservation().getRooms();
    for(int i = 0; i < rooms.length; i++){
    if(i == rooms.length - 1){
        occupancy += rooms[i].getOccupancy();
      }
      else{
        occupancy += rooms[i].getOccupancy()  + " + ";
      }}
    String[] split = occupancy.split(" ");
    String[] splitAgain = new String[(split.length/2)+1];
    int j = 0;
    for(int i = 0; i < split.length; i+=2){
    splitAgain[j] = split[i];
    j++;
    }
    int[] occupancies = Arrays.stream(splitAgain).mapToInt(Integer::parseInt).toArray();
    for(int i =0; i < occupancies.length; i++){occupants+= occupancies[i];}
    }
    }
    }
    System.out.println("Hotel Occupancy:" + occupants);
    }
    if(choice == 2){
    int occupants = 0;
    Reader reader = new Reader("l4Hotels.csv");
    ArrayList<Hotel> hotelsList = reader.getHotels();
    int k = 0;
    String hotelString = "";
    char c = 'A';
      
      while(hotelString == ""){
      for (Hotel hotelChoice : hotelsList){
            System.out.println(c + ") " + hotelChoice.getHotelType()); 
            c++;
      }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < hotelsList.size()){
            hotelString = hotelsList.get(n).getHotelType();
            
      }
      c = 'A';
      }
      for(; k <hotelsList.size();k++){
        if(hotelsList.get(k).getHotelType().equals(hotelString)) break;
      }
      String typeOfRoom = "";
      c = 'A';
      ArrayList<RoomType> roomTypeList = hotelsList.get(k).getRoomTypes();
      while(typeOfRoom == ""){
      for (RoomType typeChoice : roomTypeList){
            System.out.println(c + ") " + typeChoice.getType()); 
            c++;
         }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < roomTypeList.size()){
            typeOfRoom = roomTypeList.get(n).getType();
      }
      c = 'A';
      }
      
    for(Stay s: stays){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getExpectedCheckOut()))){
    if(s.getReservation().getHotelType().equals(hotelString)){
    String occupancy = "";
    Room[]rooms = s.getReservation().getRooms();
    for(int i = 0; i < rooms.length; i++){
    if(rooms[i].getRoomType().equals(typeOfRoom)){
      if(i == rooms.length - 1){
        occupancy += rooms[i].getOccupancy();
      }
      else{
        occupancy += rooms[i].getOccupancy()  + " + ";
      }}}
    String[] split = occupancy.split(" ");
    String[] splitAgain = new String[(split.length/2)+1];
    int j = 0;
    for(int i = 0; i < split.length; i+=2){
    splitAgain[j] = split[i];
    j++;
    }
    int[] occupancies = Arrays.stream(splitAgain).mapToInt(Integer::parseInt).toArray();
    for(int i =0; i < occupancies.length; i++){occupants+= occupancies[i];}
    }
    }
    }
    System.out.println("Room occupancy:" + occupants);
    }
    
    if(choice == 3){
    double earnings = 0;
    Reader reader = new Reader("l4Hotels.csv");
    ArrayList<Hotel> hotelsList = reader.getHotels();
    int k = 0;
    String hotelString = "";
    char c = 'A';
      
      while(hotelString == ""){
      for (Hotel hotelChoice : hotelsList){
            System.out.println(c + ") " + hotelChoice.getHotelType()); 
            c++;
      }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < hotelsList.size()){
            hotelString = hotelsList.get(n).getHotelType();
      }
      c = 'A';
      }
       
    for(Stay s: stays){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getExpectedCheckOut()))){
    if(s.getReservation().getHotelType().equals(hotelString)){
    
    earnings += s.getReservation().getTotalCost();
    }
    }
    }
    for(Cancellation s: cancellations){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getDate()))){
    if(s.getReservation().getHotelType().equals(hotelString)&& s.getRefunded() == false){
    
    earnings += s.getReservation().getTotalCost();
    }
    }
    }
    
    
    System.out.println("Hotel Earnings:" + earnings);
    
    }
    if(choice == 4){
        
        
    double totalCost = 0;
    Reader reader = new Reader("l4Hotels.csv");
    ArrayList<Hotel> hotelsList = reader.getHotels();
    int k = 0;
    String hotelString = "";
    char c = 'A';
      
      while(hotelString == ""){
      for (Hotel hotelChoice : hotelsList){
            System.out.println(c + ") " + hotelChoice.getHotelType()); 
            c++;
      }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < hotelsList.size()){
            hotelString = hotelsList.get(n).getHotelType();
      }
      c = 'A';
      }
      for(; k <hotelsList.size();k++){
        if(hotelsList.get(k).getHotelType().equals(hotelString)) break;
      }
      String typeOfRoom = "";
      c = 'A';
      ArrayList<RoomType> roomTypeList = hotelsList.get(k).getRoomTypes();
      while(typeOfRoom == ""){
      for (RoomType typeChoice : roomTypeList){
            System.out.println(c + ") " + typeChoice.getType()); 
            c++;
         }
         String input = sc.next();
         int n = input.toUpperCase().charAt(0) - 'A';
      if (0 <= n && n < roomTypeList.size()){
            typeOfRoom = roomTypeList.get(n).getType();
      }
      c = 'A';
      }
      
    for(Stay s: stays){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getExpectedCheckOut()))){
    if(s.getReservation().getHotelType().equals(hotelString)){
    
    Room[]rooms = s.getReservation().getRooms();
    for(int i = 0; i < rooms.length; i++){
    if(rooms[i].getRoomType().equals(typeOfRoom)){
    ArrayList<RoomType> rates = reader.getAllRoomTypes();
   
    for(int f = 0; f < rates.size(); f++){
        if(rates.get(f).getType().equals(rooms[i].getRoomType())){
          double[] roomRates = rates.get(f).getRates();
          for(int g = 0; g < s.getReservation().getNumberOfNights(); g++){
            totalCost += roomRates[((s.getReservation().getCheckInDate().getDay() + g) % 7)];
            
          }
        
      }
    }}}}}}
    
    for(Cancellation s: cancellations){
    if(!(finishDate.before(s.getReservation().getCheckInDate())||startDate.after(s.getDate()))){
    if(s.getReservation().getHotelType().equals(hotelString)&& s.getRefunded() == false){
    
    Room[]rooms = s.getReservation().getRooms();
    for(int i = 0; i < rooms.length; i++){
    if(rooms[i].getRoomType().equals(typeOfRoom)){
    ArrayList<RoomType> rates = reader.getAllRoomTypes();
    for(int f = 0; f < rates.size(); f++){
        if(rates.get(f).getType().equals(rooms[i].getRoomType())){
          double[] roomRates = rates.get(f).getRates();
          for(int g = 0; g < s.getReservation().getNumberOfNights(); g++){
            totalCost += roomRates[((s.getReservation().getCheckInDate().getDay() + g) % 7)];
          }
        
      }
    }}}}}}
    
    System.out.println("Room Earnings (before discount):" + totalCost);
    }}
    /**
     * 
     * @return getDates() method gets dates for the analytics engine.
     */
    
    public Date getDates(){
    int [] days = {31,28,31,30,31,30,31,31,30,31,30,31};
    int day;
    int month;
    int year;
    Date today = new Date();
    
    int thisYear;
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    thisYear = cal.get(Calendar.YEAR);
    
    Scanner sc = new Scanner(System.in);
    
    
    System.out.println("Year:");
    year = sc.nextInt();
    while(year > thisYear || year < thisYear - 6){
    System.out.println("Invalid year.");
    System.out.println("Year:");
    year = sc.nextInt();
    }
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        days[1] = 29;
    System.out.print("Month:");
    month = sc.nextInt();
    while(month < 1 || month > 12){
    System.out.println("Invalid date.");
    System.out.println("Month:");
    month = sc.nextInt();
    }
    System.out.print("Day:");
    day = sc.nextInt();
    while(day < 1 || day > days[month -1]){
    System.out.println("Invalid date.");
    System.out.println("Day:");
    day = sc.nextInt();
    }
    LocalDate localDate = LocalDate.of(year, month, day);
    Date reservationDate = java.sql.Date.valueOf(localDate);
    return reservationDate;
    
    
    }
    
    /**
     * 
     * @param type
     * @param startDate
     * @param endDate
     * @return checkIfRoomAvailable() method checks if a room is available during makeReservation().
     */
    
    
    
    public boolean checkIfRoomAvailable(RoomType type, Date startDate, Date endDate) {
    	int count = 0;
    	ArrayList<Reservation> reservations = readInReservations.getReservations();
    	ArrayList<Cancellation> cancellations;    
        Reader readInCancellations = new Reader("cancellations.csv");
        cancellations = readInCancellations.getCancellations();
        ArrayList<Reservation> relevantReservations = reservations;
    	for(int i = 0; i < reservations.size(); i++) {
    		for(int j = 0; j < cancellations.size(); j++) {
    			if(reservations.get(i).getReservationNumber() == cancellations.get(j).getReservation().getReservationNumber()) {
    				relevantReservations.remove(i);
    			}
    			
    		}
    	}
    	ArrayList<Reservation> evenMoreRelevantReservations = relevantReservations;
    	for(int i = 0; i < relevantReservations.size(); i++) {
    		    Date expectedCheckOut;
    		    Calendar cal = Calendar.getInstance();
    		    cal.setTime(relevantReservations.get(i).getCheckInDate());
    		    cal.add(Calendar.DATE, relevantReservations.get(i).getNumberOfNights());
    		    expectedCheckOut = cal.getTime();
    		    
    		    if(!(endDate.before(relevantReservations.get(i).getCheckInDate())||startDate.after(expectedCheckOut))) {
    		    	evenMoreRelevantReservations.remove(i);
    		    }
    		}
    	for(Reservation r: evenMoreRelevantReservations) {
    		Room[] rooms = r.getRooms();
    		for(Room s: rooms) {
    			if(s.getRoomType().equals(type.getType())) {
    				count++;
    			}
    		}
    	}
    	if(count >= type.getNumberOfRooms()) {
    		return false;
    	}
    	return true;
    	}
    
    
}