# dronet-ms
Server per la prima esercitazione del corso di Reti di Elaboratori

#GET /dronet-ms-core/v1/mapStatus  
Return a  JSON containing information of the map  

#PUT /dronet-ms-core/v1/createDrone
Header: user-agent deve essere drone-client  
Il content type?  

Parametri: droneName = nome del drone da creare  
Restituisce un oggetto di tipo drone  

#POST /dronet-ms-core/v1/updateDroneStatus  
Header: user-agent deve essere drone-client  

Body:  
{  
  "direzione": "string", (Direzioni consentite: N;NE;E;SE;S;SO;O;NO)  
  "idunivoco": "string",  
  "secretkey": "string"  
}

#POST /dronet-ms-core/v1/createShot  
Header: user-agent deve essere drone-client  

Body:  
{  
  "idunivoco": "string",  
  "secretkey": "string"  
}  

