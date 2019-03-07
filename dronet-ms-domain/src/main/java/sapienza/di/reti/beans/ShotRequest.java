package sapienza.di.reti.beans;

/**
 * Created by mauropiva on 07/03/19.
 */
public class ShotRequest {

    //(idUnivoco, SecretKey, direzione, velocita)

    private String idunivoco;

    public ShotRequest(){

    }



    private String secretkey;


    public String getIdunivoco() {
        return idunivoco;
    }

    public void setIdunivoco(String idunivoco) {
        this.idunivoco = idunivoco;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }


    public ShotRequest(String idunivoco, String secretkey, String direzione) {

        this.idunivoco = idunivoco;
        this.secretkey = secretkey;
    }

}
