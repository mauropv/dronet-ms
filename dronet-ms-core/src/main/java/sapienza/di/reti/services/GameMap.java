package sapienza.di.reti.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sapienza.di.reti.beans.Drone;
import sapienza.di.reti.beans.MovableObject;
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
        updateShots();
        checkDiedDrones();
        //giveRewards();

        epoch+=1;
    }

    private void checkDiedDrones() {

        for(Shot shot:shots){
            for(Drone drone:drones.values()){
                if(shot.getxCoord()==drone.getxCoord()&&shot.getyCoord()==drone.getyCoord()){
                    drone.setStatus("Died");
                }
            }
        }
    }


    private void updateShots(){
        for(Shot shot : shots){
            updateShotPosition(shot);
        }
    }

    public void updateDronesPositions(){


        for (String droneUnique:drones.keySet()) {
            Drone drone = drones.get(droneUnique);
            if(drone.getStatus().equals("Alive")) {
                Integer[] updater = getCoordsUpdaters(drone.getDirection(),drone.getSpeed());
                drone.updateCoords(updater[0],updater[1]);
                if (drone.getxCoord() < 0 || drone.getxCoord() >= sizeX || drone.getyCoord() < 0 || drone.getyCoord() >= sizeY) {
                    //DRONE FUORI DALLA MAPPA :(
                    drone.setStatus("Out");
                }
            }
        }
    }

    public void updateShotPosition(Shot shot){
        Integer[] updater = getCoordsUpdaters(shot.getDirection(),shot.getSpeed());
        shot.updateCoords(updater[0],updater[1]);
        if (shot.getxCoord() < 0 || shot.getxCoord() >= sizeX || shot.getyCoord() < 0 || shot.getyCoord() >= sizeY) {
            //DRONE FUORI DALLA MAPPA :(
            shots.remove(shot);
        }
    }

    public Drone getDrone(String idUnivoco) {
        return drones.get(idUnivoco);
    }

    public static Integer[] getCoordsUpdaters(String direction, Integer speed){
        int vert = 0;
        int orizz = 0;
        switch (direction) {
            case "N":
                vert = speed;
                break;
            case "NE":
                vert = speed;
                orizz = +speed;
                break;
            case "E":
                orizz = +speed;
                break;
            case "SE":
                vert = -speed;
                orizz = speed;
                break;
            case "S":
                vert = -speed;
                break;
            case "SO":
                vert = -speed;
                orizz = -speed;
                break;
            case "O":
                orizz = -speed;
                break;
            case "NO":
                vert = speed;
                orizz = -speed;
                break;
        }
        return new Integer[]{orizz,vert};
    }

    public void addShot(Shot shot) {
        shots.add(shot);
    }
}
