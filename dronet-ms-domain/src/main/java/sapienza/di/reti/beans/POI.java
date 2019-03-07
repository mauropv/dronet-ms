package sapienza.di.reti.beans;

/**
 * Created by mauropiva on 06/03/19.
 */
public class POI {

    /***
     * POI:{
     -X
     -Y
     -connection version IPV4 IPV6
     -connection type: UDP - TCP
     -IP
     -Port
     }
     */

    private Integer x;
    private Integer y;
    private String version;
    private String type;
    private String ip;
    private Integer port;

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public POI(Integer x, Integer y, String version, String type, String ip, Integer port) {

        this.x = x;
        this.y = y;
        this.version = version;
        this.type = type;
        this.ip = ip;
        this.port = port;
    }
}
