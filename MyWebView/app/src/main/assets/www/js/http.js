//default values
var lat=1.3667;
var lng=103.75;
var markers=[];
var pos=[];


navigator.geolocation.watchPosition(
    function(position){
        lat=position.coords.latitude;
        lng=position.coords.longitude;
        map.setCenter(new google.maps.LatLng(lat,lng));
    },
    function(error){
        alert("watchPosition error,error code:"+error.code);
    },
    {
        enableHighAccuracy:true,
        maximumAge:4*1000,
        timeout:30*1000
    }
);
var map=new google.maps.Map(
    document.getElementById("myMap"),{
        zoom:15,
        center:new google.maps.LatLng(lat,lng),
        mapTypeId:google.maps.MapTypeId.ROADMAP}
);
function setMarker(){
    var marker=new google.maps.Marker({
        position:new google.maps.LatLng(lat,lng),
        map:map
    });

    markers.push(marker);

    pos.push([lat,lng]);
}
document.getElementById("marking").addEventListener("click",setMarker,true);
document.getElementById("clearMarker").addEventListener("click",function(){
    for(var i=0;i<markers.length;i++){
        markers[i].setMap(null);
    }
    markers=[];
    pos=[];
},true);
document.getElementById("go").addEventListener("click",goNewLocation,true);
function goNewLocation(){
    var newLat=document.getElementById('inputLat').value;
    var newLng=document.getElementById('inputLng').value;

    lat=newLat;
    lng=newLng;

    map.setCenter(new google.maps.LatLng(lat,lng));
    map.setZoom(15);

    var marker=new google.maps.Marker({
        position:new google.maps.LatLng(lat,lng),
        map:map
    });
    markers.push(marker);
    pos.push([lat,lng]);
    var cityName=locName;
    var theUrl='http://api.worldweatheronline.com/free/v1/weather.ashx';
    var paramString='q=' + cityName + '&format=json&num_of_days=5&key=b4b555e71b24530b342bc2bac7df12d9401885fd';
    var xmlHttp=null;
    xmlHttp=new XMLHttpRequest();
    xmlHttp.onreadystatechange=function(){
        if(xmlHttp.readyState==4){
            if(xmlHttp.status==200 || xmlHttp.status==0){
                var JSONDataPost2=JSON.parse(xmlHttp.responseText);
                document.getElementById("JSONText").innerHTML=
                "Asynchronous GET <br> "+
                "Temperature(deg C) in" + locName + "=" +
                JSONDataPost2.data.current_condition[0].temp_C+"<br>"+
                "Temperature(deg F) in" + locName + "=" +
                JSONDataPost2.data.current_condition[0].temp_F;
            }
        }
    }
    getString=theUrl + "?" +paramString;
    xmlHttp.open("GET",getString,true);
    xmlHttp.send(null);
}

var locName;

//drop down list

document.getElementById("locationName").addEventListener("change",
function(){
    locName=document.getElementById("locationName").value;
    switch(locName){
        case "Singapore":
            lat=1.33;
            lng=103.83;
            break;
        case "Pittsburgh,USA":
            lat=40.4417;
            lng=-79.89;
        break;
        case "Rome,Italy":
            lat=41.9;
            lng=12.4833;
        break;
        case "Florence,Italy":
            lat=43.7667;
            lng=11.25;
        break;
        case "Pisa,Italy":
            lat=43.7167;
            lng=10.3833;
            break;
        case "Siena,Italy":
            lat=43.3333;
            lng=11.3333;
            break;
        case "Venice,Italy":
            lat=45.4383;
            lng=12.3267;
            break;
        case "Paris,France":
            lat=48.8667;
            lng=2.3333;
            break;
        default:
            lag=1.33;
            lng=103.83;
            break;
        }
        document.getElementById('inputLat').value=lat;
        document.getElementById('inputLng').value=lng;
},true);

var data=localStorage.getItem("posData");
if(data){
    data=data.split(",");
    for(var i=0;i<data.length;i+=2){
        lat=data[i];
        lng=data[i+1];
        setMarker();
    }
}

window.addEventListener("unload",function(){
    try{
        localStorage.setItem("posData",pos.toString());
    }catch(e){
        alert("Not enough storage space,cannot save the current data");
    }
},true);
