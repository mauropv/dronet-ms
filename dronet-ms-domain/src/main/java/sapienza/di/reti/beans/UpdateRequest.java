package sapienza.di.reti.beans;

/**
 * Created by mauropiva on 07/03/19.
 */
public class UpdateRequest {

    //(idUnivoco, SecretKey, direzione, velocita)

    private String idunivoco;

    public UpdateRequest(){

    }



    private String secretkey;
    private String direzione;


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

    public String getDirezione() {
        return direzione;
    }

    public void setDirezione(String direzione) {
        this.direzione = direzione;
    }

    public UpdateRequest(String idunivoco, String secretkey, String direzione) {

        this.idunivoco = idunivoco;
        this.secretkey = secretkey;
        this.direzione = direzione;
    }
}
