// Lab 1: Understanding OOPs


// Your task is to take the race track program presented in class and add several parts to it.
// •	Add a new class called Motorcycle. It should extend vehicle which will allow it to be a part of the array with other vehicles. Motorcycles have great speed (the speed you should set for your motorcycle instance should be 95) but are dangerous. Redefine ModifySpeed method so that the motorcycle will travel a random amount between 0 and their max speed. Additionally, since motorcycle extends vehicle you must redefine Go. Do so like Car, and Truck but add a conditional statement that checks for the motorcycles chance to crash which is 2% per loop. If the motorcycle crashes then it should not progress in the race any further (but it will restart in any future races).



// •	Next we need to redefine the way passengers are kept track of in all vehicles. First passengers should be a private variable which can only be accessed by appropriate accessor and mutator methods. Finally we should add a new final variable for the max occupancy of the vehicle. The max occupancy should be checked in the mutator method for passengers. The max occupancy should be defined as:



// o	Car = 5
// o	Truck = 3
// o	Motorcycle = 1
// If more passengers than are allowed attempt to pile into a vehicle the program should indicated this to the user and continue with the max passenger amount.
// •	We want to change the race program from being 1 race to being a whole season. Do so by adding a looping structure to the main method. The season is defined as 25 races, and at the completion of all races the vehicle with the most wins should be printed (if 2 vehicles tie some tie-breaker should be conducted).  For this section you may want to change when the vehicles get printed out so that they only print after races.


public class RaceTrack //driver
{
    public final static int raceDuration = 1000; //store the length of the race (can be accessed anywhere in code)

    public static void main(String arg[])
    {
        for (int raceNum = 1; raceNum <= 25; raceNum++)
        {
            //Instantiating my object instances
            Vehicle c1 = new Car(1,3,85); //this one I'm storing as the base class (vehicle)
            Car c2 = new Car(2,4,100);    //this one I store as the sub class (car). There are differences but they do not come into play here
            Motorcycle m1 = new Motorcycle(4,1,95); //Motorcycle
            Truck t1 = new Truck(3,2,90,250);

            //This is an array
            Vehicle[] allVehicles = new Vehicle[4];
            //placing the vehicles into an array
            allVehicles[0] = c1;
            allVehicles[1] = c2;   //remember just because I store these as vehicles doesn't mean that the
            allVehicles[2] = t1;   //methods for them has changed. Each still stores its own go method.
            allVehicles[3] = m1; //motorcycle
            //infinite loop (well without the base case it is)

            while (true) //this will run until a race participant crosses the finish line (passes raceDuration)
            {
                int max=0;
                //tell the cars to "go" one by one
                for (int i=0; i<allVehicles.length;i++) //3 times
                {
                    Vehicle v = allVehicles[i];
                    v.Go();//polymorphism
                    System.out.println(v);
                    max = Math.max(max,v.RaceProgress);
                }
                System.out.println();
                //check to see if someone has won the race
                if (max > raceDuration)
                {
                    break;
                }
            }
            System.out.println("*** Race "+raceNum+" ***");
            System.out.println("We have a winner!!! \n*** Vehicle "+RaceTrack.GetFurthestVehicle(allVehicles)+" ***");
            System.out.println();
        }

    }
    //just a helper method to find out which vehicle won the race
    public static int GetFurthestVehicle(Vehicle[] allVehicles)
    {
        int max=0;
        int VIN=0;
        for (int i=0; i<allVehicles.length;i++)
        {
            if (max < allVehicles[i].RaceProgress)
            {
                max = allVehicles[i].RaceProgress;
                VIN = allVehicles[i].VIN;
            }
        }
        return VIN;
    }
}

//Base class (abstract means that we can't make Vehicles)
//class' responsibility to protect its data/attributes
abstract class Vehicle extends Object
{
    //attributes (fields
    int passengers;
    int speed;
    int VIN;
    int RaceProgress;

//        public void setPassengers(int passengers)
//        {
//            this.passengers = passengers;
//        }
//        public int getPassengers()
//        {
//            return passengers;
//        }


    //Constructors (used to create the objects):
    Vehicle()
    {
        //super();
        passengers = 1;
        speed = 0;
        RaceProgress = 0;

    }
    /**
     * Main constructor
     * @param vin = this is the vin number identification for the vehicle
     */
    //overloaded constructor
    Vehicle(int vin)
    {
        this();
        VIN = vin;
    }

    /**
     * This is the main function that progresses the vehicles through the race
     * this should be called each loop of the program (this must be redefined in each
     * subclass of vehicle)
     */
    abstract public void Go();

    /**
     * Part of object. This is invoked when an instance of this class is attempted to be used as a string (like during System.out.println)
     */
    public String toString()
    {
        return "Vehicle: "+VIN+
                " Progress: "+RaceProgress+ " " +super.toString();
    }

    public boolean equals(Object other)
    {
        return this.VIN == ((Vehicle)other).VIN;
    }

    /**
     * This is the original speed modifier (it may need to be redefined)
     * @return int random between half the speed and the whole speed
     */
    public int SpeedModifier()
    {
        //returns speed/2 to maxSpeed
        return (int)(Math.random()*speed/2)+speed/2;
    }

}

/**
 *
 * SubClass of Vehicle
 *
 */


class Car extends Vehicle //car "is" a vehicle
{
    private int passengers;
    /**
     * Car Constructor (no return specified)
     * @param i = (0,100)
     * @param passengers
     * @param speed
     */
    public final static int maxOccupancy = 5;

    Car(int i, int passengers, int speed) //Working constructor
    {
        //super or this
        //super(); implied
        super(i);//calling the constructor in Vehicle
        this.speed = speed;

        if(passengers > maxOccupancy)
        {
            System.out.println("The max occupancy for this vehicle is exceeded, then it was created with the max occupancy: "+maxOccupancy);
            this.passengers = maxOccupancy;
        }
        else
            this.passengers = passengers;

    }
    public String toString()
    {
        return "Car::"+super.toString();
    }
    //This is overwriting the super/base classes method
    //car satisfies the vehicles Go requirement
    public void Go()
    {
        RaceProgress += SpeedModifier() - 10 * (passengers-1);

    }
}
//Another subclass of Vehicle (this is considered a concrete class because it is not abstract)
class Truck extends Vehicle
{
    private int passengers;
    //This is data that exists only in trucks
    int towWeight;//special note that vehicle cannot access this. In order for it to do so a
    //cast operation must be applied to a valid truck.
//	private int passengers;
    public final static int maxOccupancy = 3;

    Truck(int i, int passengers, int speed, int towWeight)
    {
        super(i);
        this.speed = speed;

        if(passengers > maxOccupancy)
        {
            System.out.println("The max occupancy for this vehicle is exceeded, then it was created with the max occupancy: "+maxOccupancy);
            this.passengers = maxOccupancy;
        }
        else
            this.passengers = passengers;

        this.towWeight = towWeight;
    }

    //truck satisfies the vehicles Go requirement
    public void Go()
    {
        RaceProgress += SpeedModifier() - (0.1f * towWeight);
    }

}

class Motorcycle extends Vehicle //Motorcycle is a vehicle
{
    private int passengers;
    Motorcycle(int i, int passengers, int speed)
    {
        super(i);//calling the constructor in Vehicle
        this.speed = speed;

        if(passengers > maxOccupancy)
        {
            System.out.println("The max occupancy for this vehicle is exceeded, then it was created with the max occupancy: "+maxOccupancy);
            this.passengers = maxOccupancy;
        }
        else
            this.passengers = passengers;
    }

    public final static int maxOccupancy = 1;

    public int modifySpeed()
    {
        return (int)Math.random() * 95;
    }



    //Motorcycle satisfies the vehicles Go requirement
    public void Go()
    {
        double probCrash = Math.random()*100;
        if (probCrash <= 2)
            RaceProgress = 0;
        else
            RaceProgress += modifySpeed() - 10 * (passengers-1);

    }


}




//setting passengers to private in the abstract class causes issues.
//motorcycle is stuck on zero speed
//tie breaker

