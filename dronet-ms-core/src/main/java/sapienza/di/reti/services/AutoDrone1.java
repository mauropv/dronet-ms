package sapienza.di.reti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sapienza.di.reti.beans.Drone;
import sapienza.di.reti.beans.POI;
import sapienza.di.reti.beans.Shot;
import sapienza.di.reti.controllers.DroneController;

import javax.xml.ws.ServiceMode;
import java.util.Random;

/**
 * Created by mauropiva on 10/03/19.
 */
@Service
public class AutoDrone1 {

    @Autowired
    GameMap gameMap;

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void manageDrones() throws Exception {

        for (Drone dr : gameMap.getDrones().values()){
            if(dr.getName().contains("AUTODRONEv-1-")&&dr.getStatus()=="Alive"){


                if(new Random().nextInt(10)>7){
                    //Creiamo lo shot
                    dr.setDirection(Drone.allowedDirections[new Random().nextInt(Drone.allowedDirections.length)]);
                }
            }

            if(dr.getName().contains("AUTODRONEv-2-")&&dr.getStatus()=="Alive"){
                if(new Random().nextInt(10)>7){
                    //Creiamo lo shot
                    Shot shot = new Shot(dr.getxCoord(),dr.getyCoord(),dr.getDirection(),dr.getUniqueId());
                    gameMap.updateShotPosition(shot);
                    gameMap.updateShotPosition(shot);
                    gameMap.addShot(shot);
                }

                if(new Random().nextInt(10)>7){
                    //Creiamo lo shot
                    dr.setDirection(Drone.allowedDirections[new Random().nextInt(Drone.allowedDirections.length)]);
                }
            }

            if(dr.getName().contains("AUTODRONEv-3-")&&dr.getStatus()=="Alive"){

                    POI poi1 = gameMap.getPOI().get(0);
                    Double distanceFromPoi = Math.sqrt(Math.pow(poi1.getX()-dr.getxCoord(),2)+
                            Math.pow(poi1.getY()-dr.getyCoord(),2));

                    String newDir = Drone.allowedDirections[new Random().nextInt(Drone.allowedDirections.length)];
                    Integer[] newDirs = gameMap.getCoordsUpdaters(newDir,1);

                    Double distanceFromPoiwithNewDir = Math.sqrt(Math.pow(poi1.getX()-(dr.getxCoord()+newDirs[0]),2)+
                            Math.pow(poi1.getY()-(dr.getyCoord()+newDirs[1]),2));

                    if(distanceFromPoi>distanceFromPoiwithNewDir) {
                        System.out.println(dr.getName()+ " " + dr.getDirection() + " " + newDir + " " +distanceFromPoi + " "+ distanceFromPoiwithNewDir+
                        " " + Math.pow(poi1.getX()-dr.getxCoord(),2) + " " + Math.pow(poi1.getX()-(dr.getxCoord()+newDirs[0]),2) +
                        " " + Math.pow(poi1.getY()-dr.getyCoord(),2) + " " + Math.pow(poi1.getY()-(dr.getyCoord()+newDirs[1]),2)

                        );
                        dr.setDirection(newDir);

                    }
            }
        }

    }

}
