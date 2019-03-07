package sapienza.di.reti.beans;


import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * drone:{
 - nome
 - idUnivoco
 - X
 - Y
 - direzione
 - velocità
 - lastUpdateTimestamp
 - status: alive, crashed, killed; (dopo 10 secondi lo elimina)
 }
 */



public class Drone implements MovableObject{

    public static void main(String args[]){
        for(int i = 0; i < 100; i++)System.out.print("-");
    }

    private static String[] allowedDirections ={"N","NE","E","SE","S","SO","O","NO"};
    private static String[] allowedStatus ={"Alive","Crashed","Killed","Out"};

    private String name;
    private String uniqueId;
    private Integer xCoord;
    private Integer yCoord;
    private Integer speed = 1;


    private String direction = "NE";
    private Long lastUpdateTimestamp;
    private String status = "Alive";


    private Integer score = 0;

    public Drone(String name, Integer xCoord, Integer yCoord){
        this.name=name;
        this.uniqueId = UUID.randomUUID().toString();
        this.xCoord=xCoord;
        this.yCoord=yCoord;
        this.lastUpdateTimestamp = System.currentTimeMillis();
        direction = allowedDirections[new Random().nextInt(allowedDirections.length)];
    }

    public String getName() {
        return name;
    }

    public Integer getxCoord() {
        return xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }

    public String getDirection() {
        return direction;
    }

    public Long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public Integer getScore() {
        return score;
    }

    public void increaseScore(Integer score) {
        this.score += score;
    }



    public String getUniqueId() {
        return uniqueId;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void updateCoords(int orizz, int vert) {
        xCoord+=orizz;
        yCoord+=vert;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDirection(String direction) throws Exception {
        if(!Arrays.asList(allowedDirections).contains(direction)){
            throw  new Exception("Direction not allowed");
        }
        this.direction = direction;
    }

}