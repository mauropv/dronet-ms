package sapienza.di.reti.beans;


import java.util.Arrays;

/**
 * Created by mauropiva on 06/03/19.
 */
public class Shot {

    private Integer x;
    private Integer y;

    private static String[] allowedDirections ={"N","NE","E","SE","S","SO","O","NO"};
    private String direction;

    public Shot(Integer x, Integer y, String direction) throws Exception {
        if(!Arrays.asList(allowedDirections).contains(direction)){
            throw new Exception("Direction not allowed");
        }

        this.direction=direction;
        this.x = x;
        this.y = y;

    }

}
