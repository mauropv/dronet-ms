package sapienza.di.reti.services;

/**
 * Created by mauropiva on 08/03/19.
 */
public class HTMLPage {

    public static String htmlPage = "\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\n" +
            "    <title>Custom Icons Tutorial - Leaflet</title>\n" +
            "\n" +
            "    <meta charset=\"utf-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "\n" +
            "    <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"docs/images/favicon.ico\" />\n" +
            "\n" +
            "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.css\" integrity=\"sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==\" crossorigin=\"\"/>\n" +
            "    <script src=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.js\" integrity=\"sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg==\" crossorigin=\"\"></script>\n" +
            "\n" +
            "\n" +
            "    <style>\n" +
            "\t\thtml, body {\n" +
            "\t\t\theight: 100%;\n" +
            "\t\t\tmargin: 0;\n" +
            "\t\t}\n" +
            "\t\t#map {\n" +
            "\t\t\twidth: 1200px;\n" +
            "\t\t\theight: 800px;\n" +
            "\t\t}\n" +
            "\t</style>\n" +
            "\n" +
            "\n" +
            "</head>\n" +
            "<body onload=\"myDrone();\">\n" +
            "\n" +
            "<div id='map'></div>\n" +
            "\n" +
            "<script>\n" +
            "\tvar map = L.map('map').setView([51.505, -0.191], 16);\n" +
            "\n" +
            "\tL.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
            "\t\tattribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n" +
            "\t}).addTo(map);\n" +
            "\n" +
            "var firefoxIcon = L.icon({\n" +
            "        iconUrl: 'http://cdn.onlinewebfonts.com/svg/img_476254.png',\n" +
            "        iconSize: [38, 38], // size of the icon\n" +
            "        });\n" +
            "\n" +
            "        var mydrone = L.icon({\n" +
            "        iconUrl: 'https://image.flaticon.com/icons/png/512/215/215736.png',\n" +
            "        iconSize: [38, 38], // size of the icon\n" +
            "        });\n" +
            "\n" +
            "\n" +
            "\n" +
            "\tvar polygon = L.polygon([[51.5,-0.2],[51.5+(100/10000),-0.2],[51.5+(100/10000),-0.2+(200/10000)],[51.5,-0.2+(200/10000)]]).addTo(map);\n" +
            "\n" +
            "//\tL.marker([51.5, -0.09]).bindPopup(\"I am a green leaf.\").addTo(map);\n" +
            "//\tL.marker([51.495, -0.083]).bindPopup(\"I am a red leaf.\").addTo(map);\n" +
            "//\tL.marker([51.49, -0.1]).bindPopup(\"I am an orange leaf.\").addTo(map);\n" +
            "\n" +
            "    function findGetParameter(parameterName) {\n" +
            "    var result = null,\n" +
            "        tmp = [];\n" +
            "    location.search\n" +
            "        .substr(1)\n" +
            "        .split(\"&\")\n" +
            "        .forEach(function (item) {\n" +
            "          tmp = item.split(\"=\");\n" +
            "          if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);\n" +
            "        });\n" +
            "    return result;\n" +
            "}\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "<script>\n" +
            "\n" +
            "var markers = L.layerGroup().addTo(map);\n" +
            "\n" +
            "var mapList = {};\n" +
            "\n" +
            "var myDroneID = \"\";\n" +
            "\n" +
            "    function addMarker(unique,coordx,coordy){\n" +
            "\n" +
            "        if( unique in mapList){\n" +
            "            marker = mapList[unique];\n" +
            "            marker.setLatLng([51.5+(coordx/10000), -0.2+(coordy/10000)]);\n" +
            "        } else {\n" +
            "\n" +
            "        marker = new L.marker([51.5+(coordx/10000), -0.2+(coordy/10000)], {icon: firefoxIcon}).bindPopup(\"I am a LALA leaf.\");\n" +
            "        marker.addTo(markers);\n" +
            "        mapList[unique]=marker;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    function addMyMarker(unique,coordx,coordy){\n" +
            "\n" +
            "        if( unique in mapList){\n" +
            "            marker = mapList[unique];\n" +
            "            marker.setLatLng([51.5+(coordx/10000), -0.2+(coordy/10000)]);\n" +
            "        } else {\n" +
            "\n" +
            "        marker = new L.marker([51.5+(coordx/10000), -0.2+(coordy/10000)], {icon: mydrone}).bindPopup(\"I am a LALA leaf.\");\n" +
            "        marker.addTo(markers);\n" +
            "        mapList[unique]=marker;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "        function addShot(coordx,coordy){\n" +
            "        console.log(coordx);\n" +
            "        marker = new L.marker([51.5+(coordx/10000), -0.2+(coordy/10000)]).bindPopup(\"I am a LALA leaf.\");\n" +
            "        marker.addTo(markers);\n" +
            "    }\n" +
            "\n" +
            "    function deleteMarkers(){\n" +
            "    try {\n" +
            "      markers.clearLayers();\n" +
            "\n" +
            "}\n" +
            "catch(err) {\n" +
            "  console.log(err.message);\n" +
            "}\n" +
            "    }\n" +
            "\n" +
            "    var HttpClient = function() {\n" +
            "    this.get = function(aUrl, aCallback) {\n" +
            "        var anHttpRequest = new XMLHttpRequest();\n" +
            "        anHttpRequest.onreadystatechange = function() {\n" +
            "            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)\n" +
            "                aCallback(anHttpRequest.responseText);\n" +
            "        }\n" +
            "\n" +
            "        anHttpRequest.open( \"GET\", aUrl, true );\n" +
            "        anHttpRequest.send( null );\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "    window.setInterval(function(){\n" +
            "  /// call your function here\n" +
            "\n" +
            "\n" +
            "\n" +
            "  var client = new HttpClient();\n" +
            "client.get('http://52.178.41.64:80/dronet-ms-core/v1/mapStatus', function(response) {\n" +
            "    var json = JSON.parse(response);\n" +
            "    drones = json[\"drones\"];\n" +
            "    var arrayLength = drones.length;\n" +
            "    //deleteMarkers();\n" +
            "    myDroneID = findGetParameter(\"droneUnique\");\n" +
            "    console.log(\"MY\"+myDroneID);\n" +
            "    for (var i = 0; i < arrayLength; i++) {\n" +
            "        if(drones[i].unique_id==myDroneID)\n" +
            "            addMyMarker(drones[i].unique_id, drones[i].y_coord,drones[i].x_coord);\n" +
            "        else\n" +
            "            addMarker(drones[i].unique_id, drones[i].y_coord,drones[i].x_coord);\n" +
            "    }\n" +
            "\n" +
            "    shots = json[\"shots\"];\n" +
            "    var arrayLength = shots.length;\n" +
            "    for (var i = 0; i < arrayLength; i++) {\n" +
            "        addShot(shots[i].y_coord,shots[i].x_coord);\n" +
            "    }\n" +
            "\n" +
            "});\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "}, 500);\n" +
            "\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "function myDrone(){\n" +
            "\n" +
            "var myDroneID = findGetParameter(\"droneUnique\");\n" +
            "\n" +
            "}\n" +
            "\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>";

}
