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
import java.util.HashSet;
import java.util.Random;

/**
 * Created by mauropiva on 06/03/19.
 */
@Service
public class GameMap {


    private Integer sizeX = 200;
    private Integer sizeY = 100;

    private Integer baseStationX = 180;
    private Integer baseStationY = 90;

    private HashMap<String,Drone> drones = new HashMap<>();
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<POI> POIList = new ArrayList<>();

    private HashMap<String, Integer> hallOfFame = new HashMap<>();

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

    public ArrayList<POI> getPOI() {
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

    @PostConstruct
    public void buildAutodrones() {
        System.out.println("Building POIs");
        for (int i = 0; i <3; i++){
           // createAutodrone(1,i);
           // createAutodrone(2,i);
            createAutodrone(3,i);
        }
    }

    private void createAutodrone(Integer version, Integer i) {

        Drone drone = new Drone("AUTODRONEv-"+version+"-"+i, new Random().nextInt(sizeX),new Random().nextInt(sizeY));
        drones.put(drone.getUniqueId(),drone);
    }

    public void addDrone(Drone newDrone, String secret) {
        drones.put(newDrone.getUniqueId(),newDrone);
        dronesSecrets.put(newDrone.getUniqueId(),secret);
        System.out.println("NewDrone: "+newDrone.getUniqueId()+" "+secret);
    }

    @Scheduled(fixedRate = 500)
    public void updateMap() {

        if(epoch%2==0) {
            try{
                updateDronesPositions();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        try{
            updateShots();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            checkDiedDrones();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            checkCollidingDrones();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            giveRewards();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            removeDiedDrone();
        }catch(Exception e){
            e.printStackTrace();
        }

        epoch+=1;
    }

    private void removeDiedDrone() {

        Integer len = drones.size();

        for(int i = 0; i < len; i++) {

            try {
                String droneUnique = (String) drones.keySet().toArray()[i];
                Drone dr = drones.get(droneUnique);
                if (dr.getStatus().equals("Removing")) {
                    drones.remove(dr.getUniqueId());
                    if (!hallOfFame.containsKey(dr.getName()) || hallOfFame.get(dr.getName()) < dr.getScore())
                        hallOfFame.put(dr.getName(), dr.getScore());

                    if (dr.getName().contains("AUTODRONEv")) {
                        createAutodrone(Integer.parseInt(dr.getName().split("-")[1]),
                                Integer.parseInt(dr.getName().split("-")[2]));
                    }

                } else if (!dr.getStatus().equals("Alive")) dr.setStatus("Removing");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void giveRewards() {

        Integer len = drones.size();

        String[] dronesArray = (String[])drones.keySet().toArray();
        for(int i = 0; i < len; i++) {
                String droneUnique = dronesArray[i] ;
                try {
                    Drone drone = drones.get(droneUnique);
                    if (drone.getName().contains("DTEST")) {
                        System.out.println(drone.getxCoord() + " " + drone.getyCoord() + " " + POIList.get(0).getX() + " " + POIList.get(0).getY());
                    }
                    if (drone.getStatus().equals("Alive")) {
                        for (POI poi : POIList) {
                            if (drone.getxCoord() == poi.getX() && drone.getyCoord() == poi.getY()) {
                                drone.increaseScore(1);
                                poi.setX(new Random().nextInt(sizeX));
                                poi.setY(new Random().nextInt(sizeY));
                            }
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

        }

    }

    private void checkDiedDrones() {

        for(Shot shot:shots){

            Integer len = drones.size();

            for(int i = 0; i < len; i++) {
                String droneUnique = (String) drones.keySet().toArray()[i];
                Drone drone = drones.get(droneUnique);
                if(shot.getxCoord()==drone.getxCoord()&&shot.getyCoord()==drone.getyCoord()){
                    drone.setStatus("Died");
                }
            }
        }
    }

    private void checkCollidingDrones() {

        Integer len = drones.size();

        for(int i = 0; i < len; i++) {

            for(int j = 0; j < len; j++) {
                try{
                Drone droneX = drones.get((String) drones.keySet().toArray()[i]);
                Drone droneY = drones.get((String) drones.keySet().toArray()[j]);
                if((droneX.getStatus().equals("Alive"))&& (droneY.getStatus().equals("Alive")) &&(!droneX.getUniqueId().equals(droneY.getUniqueId()))&&droneX.getxCoord()==droneY.getxCoord()&&droneX.getyCoord()==droneY.getyCoord()){
                        droneX.setStatus("Crashed"); droneY.setStatus("Crashed");
                        System.out.println("Crash! " + droneX + " " + droneY);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateShots(){
        Integer lenght = shots.size();
        for(int i = 0; i <lenght; i++){
            try{
                updateShotPosition(shots.get(i));
            } catch(Exception e){
               // e.printStackTrace();
            }
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
