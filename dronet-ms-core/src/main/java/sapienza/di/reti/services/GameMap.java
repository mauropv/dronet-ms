package sapienza.di.reti.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sapienza.di.reti.beans.Drone;
import sapienza.di.reti.beans.POI;
import sapienza.di.reti.beans.Shot;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mauropiva on 06/03/19.
 */
@Service
public class GameMap {


    private Integer sizeX = 100;
    private Integer sizeY = 100;

    private Integer baseStationX = 180;
    private Integer baseStationY = 90;

    private HashMap<String,Drone> drones = new HashMap<>();
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<POI> POIList = new ArrayList<>();

    private Long epoch = 0L;


    private HashMap<String,String> dronesSecrets = new HashMap<>();

    public Integer getSizeX() {
        return sizeX;
    }

    public Integer getSizeY() {
        return sizeY;
    }

    public Integer getBaseStationX() {
        return baseStationX;
    }

    public Integer getBaseStationY() {
        return baseStationY;
    }

    public HashMap<String, Drone> getDrones() {
        return drones;
    }

    public Object getShots() {
        return shots;
    }

    public Object getPOI() {
        return POIList;
    }

    public String getDroneSecret(String unique) {
        return dronesSecrets.get(unique);
    }


    @PostConstruct
    public void buildPOI() {
        System.out.println("Building POIs");
        POIList.add(new POI(10,10,"V4","TCP","localhost",80));
    }

    public void addDrone(Drone newDrone, String secret) {
        drones.put(newDrone.getUniqueId(),newDrone);
        dronesSecrets.put(newDrone.getUniqueId(),secret);
        System.out.println("NewDrone: "+newDrone.getUniqueId()+" "+secret);
    }

    @Scheduled(fixedRate = 500)
    public void updateMap() {

        if(epoch%4==0) {
            updateDronesPositions();
        }
        //updateShots();
        //giveRewards();

        epoch+=1;
    }

    private void updateDronesPositions(){


        for (String droneUnique:drones.keySet()) {
            Drone drone = drones.get(droneUnique);
            if(drone.getStatus().equals("Alive")) {
                int vert = 0;
                int orizz = 0;
                int speed = drone.getSpeed();
                switch (drone.getDirection()) {
                    case "N":
                        vert = -speed;
                        break;
                    case "NE":
                        vert = -speed;
                        orizz = +speed;
                        break;
                    case "E":
                        orizz = +speed;
                        break;
                    case "SE":
                        vert = speed;
                        orizz = speed;
                        break;
                    case "S":
                        vert = speed;
                        break;
                    case "SO":
                        vert = speed;
                        orizz = -speed;
                        break;
                    case "O":
                        orizz = -speed;
                        break;
                    case "NO":
                        vert = -speed;
                        orizz = -speed;
                        break;
                }
                drone.updateCoords(orizz, vert);
                if (drone.getxCoord() < 0 || drone.getxCoord() >= sizeX || drone.getyCoord() < 0 || drone.getyCoord() >= sizeY) {
                    //DRONE FUORI DALLA MAPPA :(
                    drone.setStatus("Out");
                }
            }
        }
    }

    public Drone getDrone(String idUnivoco) {
        return drones.get(idUnivoco);
    }
}
