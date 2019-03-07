package sapienza.di.reti.beans;


import java.util.Arrays;

/**
 * Created by mauropiva on 06/03/19.
 */
public class Shot {

    private Integer xCoord;
    private Integer yCoord;
    private Integer speed = 1;
    private String owner;

    private static String[] allowedDirections ={"N","NE","E","SE","S","SO","O","NO"};
    private String direction;

    public Shot(Integer x, Integer y, String direction, String owner) throws Exception {
        if(!Arrays.asList(allowedDirections).contains(direction)){
            throw new Exception("Direction not allowed");
        }

        this.direction=direction;
        this.xCoord = x;
        this.yCoord = y;
        this.owner = owner;
    }

    public String getDirection() {
        return direction;
    }

    public Integer getSpeed() {
        return speed;
    }
    public void updateCoords(int orizz, int vert) {
        xCoord+=orizz;
        yCoord+=vert;
    }

    public Integer getxCoord() {
        return xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }
}
