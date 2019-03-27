package sapienza.di.reti.controllers;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sapienza.di.reti.beans.Drone;
import sapienza.di.reti.beans.Shot;
import sapienza.di.reti.beans.ShotRequest;
import sapienza.di.reti.beans.UpdateRequest;
import sapienza.di.reti.services.GameMap;
import sapienza.di.reti.services.HTMLPage;

import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


/***
 * server calls
 GET /mapStatus()
 ritorna JSON contenente:
 - droni presenti su mappa
 - POI
 -GameMap Details: {Xsize, Ysize, BaseStationX, BaseStationY}
 -shots
 shot:{
 -X
 -Y
 -Direction
 -Speed
 }

 drone:{
 - nome
 - idUnivoco
 - X
 - Y
 - direzione
 - velocità
 - lastUpdateTimestamp
 - status: alive, crashed, killed; (dopo 10 secondi lo elimina)
 }

 POI:{
 -X
 -Y
 -connection version IPV4 IPV6
 -connection type: UDP - TCP
 -IP
 -Port
 }

 PUT /CreateDrone (String Name)
 return a idUnivoco and a secretKey

 POST /updateDroneStatus (idUnivoco, SecretKey, direzione, velocita)
 return a drone

 POST /fire (idUnivoco, secretKey)


 -> Download from a POI
 -> Upload to BaseStation

 Colonna: token con idunivoco

 */


@RestController
@RequestMapping(path="/dronet-ms-core")
public class DroneController {

    public static final String BASE_VERSION = "v1";

    private final Timer mapStatusTimer;
    private final Timer createDroneTimer;

    @Autowired
    GameMap gameMap;

    public DroneController(MetricRegistry metricRegistry) {
        this.mapStatusTimer = metricRegistry.timer("sapienza.di.reti.metrics.mapStatus");
        this.createDroneTimer = metricRegistry.timer("sapienza.di.reti.metrics.createDrone");

    }

    /**
     *
     *
     * @param
     * @return SOMETHING
     */


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/" + BASE_VERSION + "/mapStatus", method = GET)
    public HashMap<String,Object> mapStatus() {
        Timer.Context timer = mapStatusTimer.time();
        HashMap<String,Object> mapStat = new HashMap<>();
        try {
            //GameMap Details
            HashMap<String,Object> details = new HashMap<>();
            details.put("Xsize",gameMap.getSizeX());
            details.put("Ysize",gameMap.getSizeY());
            details.put("BaseStationX",gameMap.getBaseStationX());
            details.put("BaseStationY",gameMap.getBaseStationY());
            mapStat.put("mapDetails",details);
            mapStat.put("deadboard",gameMap.getHallOfFame());

            //Drones
            mapStat.put("drones",gameMap.getDrones().values());

            //Shots
            mapStat.put("shots",gameMap.getShots());

            //POI
            mapStat.put("POI",gameMap.getPOI());

        } finally {
            timer.stop();
        }
        return mapStat;
    }

    /**
     *
     * @param userAgent text1
     * @param droneName text2
     * @return ret1
     * @throws Exception ex1
     */

    @RequestMapping(value =  "/" + BASE_VERSION + "/createDrone", method = PUT, consumes = "text/plain")
    public HashMap<String, String> createDrone(@RequestHeader(value="user-agent") String userAgent, @RequestBody String droneName) throws Exception {
        Timer.Context timer = createDroneTimer.time();
        HashMap<String, String> response = new HashMap<String, String>();
        try {
            if (!userAgent.equals("drone-client"))
                throw new Exception("Solo i droni giusti possono accedere alla chat :(");
            if (droneName.length()==0 || droneName.length()>100)
                throw new Exception("Questo nome del drone non e' consentito");

            //Creiamo il drone
            Drone newDrone = new Drone(droneName, new Random().nextInt(gameMap.getSizeX()),new Random().nextInt(gameMap.getSizeY()));
            String secret = UUID.randomUUID().toString();

            if(newDrone.getName().contains("DTEST")){
                newDrone.setUniqueId(newDrone.getName());
                secret = newDrone.getName();
            }
            //Salviamo il drone
            gameMap.addDrone(newDrone,secret);

            response.put("uniqueId",newDrone.getUniqueId());
            response.put("secret",secret);

        }
        finally {
            timer.stop();
        }
        return response;
    }

 //   POST /updateDroneStatus (idUnivoco, SecretKey, direzione, velocita)
 //   return a drone

    @RequestMapping(value =  "/" + BASE_VERSION + "/updateDroneStatus", method = POST, consumes = "application/json")
    public Drone updateDroneStatus(@RequestHeader(value="user-agent") String userAgent, @RequestBody UpdateRequest updateRequest) throws Exception {
        Timer.Context timer = createDroneTimer.time();
        Drone response = null;
        try {
            if (!userAgent.equals("drone-client"))
                throw new Exception("Solo i droni giusti possono accedere alla chat :(");

            //System.out.println("UpldateRequest: " +updateRequest.getIdunivoco() + updateRequest.getSecretkey());
            //Check drone existance
            if (!gameMap.getDroneSecret(updateRequest.getIdunivoco()).equals(updateRequest.getSecretkey()))
                throw new Exception("Questo nome del drone non e' consentito");

            gameMap.getDrone(updateRequest.getIdunivoco()).setDirection(updateRequest.getDirezione());
            response = gameMap.getDrone(updateRequest.getIdunivoco());
        }
        finally {
            timer.stop();
        }
        return response;
    }

    //POST /fire (idUnivoco, secretKey)

    @RequestMapping(value =  "/" + BASE_VERSION + "/createShot", method = PUT, consumes = "application/json")
    public Shot createShot(@RequestHeader(value="user-agent") String userAgent, @RequestBody ShotRequest shotRequest) throws Exception {
        Timer.Context timer = createDroneTimer.time();
        Shot response = null;
        try {
            if (!userAgent.equals("drone-client"))
                throw new Exception("Solo i droni giusti possono accedere alla chat :(");
            if (!gameMap.getDroneSecret(shotRequest.getIdunivoco()).equals(shotRequest.getSecretkey()))
                throw new Exception("Questo nome del drone non e' consentito");

            //Controlliamo che il drone sia vivo!
            Drone shotingDrone = gameMap.getDrone(shotRequest.getIdunivoco());
            if(!shotingDrone.getStatus().equals("Alive")){
                throw new Exception("Questo drone non puo' sparare :(");
            }
            //Creiamo lo shot
            Shot shot = new Shot(shotingDrone.getxCoord(),shotingDrone.getyCoord(),shotingDrone.getDirection(),shotingDrone.getUniqueId());
            gameMap.updateShotPosition(shot);
            gameMap.updateShotPosition(shot);

            gameMap.addShot(shot);
            response = shot;

        }
        finally {
            timer.stop();
        }
        return response;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/" + BASE_VERSION + "/getmap", method = GET)
    public String map() {
        Timer.Context timer = mapStatusTimer.time();
       String response = "";
        try {

            response = HTMLPage.htmlPage;

        } finally {
            timer.stop();
        }
        return response;
    }



}