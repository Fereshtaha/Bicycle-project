/**
 * calculates distance between two coordinates
 * @param lat1 latitude of location 1
 * @param lon1 longitude of location 1
 * @param lat2 latitude of location 2
 * @param lon2 longitude of location 2
 * @param unit "K" for Kilometers or "N" for Nautical miles
 * @returns {number}
 */
function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2, unit) {
    if ((lat1 === lat2) && (lon1 === lon2)) {
        return 0;
    }
    else {
        let radlat1 = Math.PI * lat1/180;
        let radlat2 = Math.PI * lat2/180;
        let theta = lon1-lon2;
        let radtheta = Math.PI * theta/180;
        let dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        if (dist > 1) {
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit==="K") { dist = dist * 1.609344 }
        if (unit==="N") { dist = dist * 0.8684 }

        return dist;
    }
}


const initialCoordinates = [62.46814742594781,6.34689191998423];


const DELIVERY_RADIUS = 500;


//custom icon for current location
let personIcon = L.icon({
    iconUrl: '/Images/person-icon.png',

    iconSize:     [50, 50], // size of the icon
    //shadowSize:   [50, 64], // size of the shadow
    iconAnchor:   [25, 40], // point of the icon which will correspond to marker's location
    //shadowAnchor: [4, 62],  // the same for the shadow
    popupAnchor:  [-3, -56] // point from which the popup should open relative to the iconAnchor
});

//custom icon for a bike
let bikeIcon = L.icon({
    iconUrl: '/Images/mike-map-icon.png',

    iconSize:     [70, 70], // size of the icon
    //shadowSize:   [50, 64], // size of the shadow
    iconAnchor:   [35, 50], // point of the icon which will correspond to marker's location
    //shadowAnchor: [4, 62],  // the same for the shadow
    popupAnchor:  [-3, -56] // point from which the popup should open relative to the iconAnchor
});

//map as a variable
let map = initMap();


//function for initiating the map;
function initMap() {
    let map = L.map('map').setView(initialCoordinates, 12);

    map.locate({setView: true, maxZoom: 14});

    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'pk.eyJ1Ijoic2ViYXN0aWFubmlsc2UiLCJhIjoiY2wzNmYxc3Q3MTBmZzNqcG83dDJncG4zbSJ9.QtNc1_46L0SG31C2F3C0nA'
    }).addTo(map);

    return map;
}



//variable the current location
let gpsMarker;
//variable for the accuracy of the location.
// if the error margin is 20 meters, the circle will have a radius of 10 meter around the location marker
let gpsCircleMarker;

//setting the location-marker til current location. updates every 15 seconds
/**
 *
 * @returns marker
 */
function interval(){
    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(onLocationFound);
        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }
    }
    function onLocationFound(position) {
        let radius = position.coords.accuracy / 2;


        if (gpsMarker == null) {
            gpsMarker = L.marker([position.coords.latitude,position.coords.longitude],{icon: personIcon}).addTo(map);
            gpsCircleMarker = L.circle([position.coords.latitude,position.coords.longitude], radius).addTo(map);
        }
        else {
            gpsMarker.setLatLng([position.coords.latitude,position.coords.longitude]);
            gpsCircleMarker.setLatLng([position.coords.latitude,position.coords.longitude]);
            gpsCircleMarker.setRadius(radius);
        }
    }
    getLocation();
    setTimeout(interval, 15000);

    return gpsMarker;
}
interval();
/**
 * function for creating a bike marker
 * @param position
 * @param bikeNo
 * @param color
 * @returns {Marker<any>}
 */
function createBikeMarker(position, bikeNo, color, pricePerMinute){
    const popup = '<p>Bike for rent!</p>';
    let marker = L.marker(position,{icon: bikeIcon}).on('click', showInfoOnClick).addTo(map);
    marker.bikeColor = color;
    marker.bikeId = bikeNo;
    marker.bikePricePerMinute = pricePerMinute;
    marker.bindPopup(popup);
    //marker.off('click', marker._openPopup);

    return marker;
}

/**
 * function for creating a circle on the map
 * @param position
 * @param radius
 * @param borderColor
 * @param fillColor
 * @returns {Circle<any>}
 */
function createCircle(position, radius, borderColor, fillColor){
    return L.circle(position, {
        color: borderColor,
        fillColor: fillColor,
        fillOpacity: 0.5,
        radius: radius
    }).addTo(map);
}

/**
 * markers for testing.
 * creates bike markers on location Aksla and Sjøbua.
 * @type {Marker<*>}
 */



/**
 * function for when a user clicks a bike icon.
 * fills the empty div with information about the bike and displays it.
 * or if div is alredy filled with info, then replaces the old info with new.
 * @param e the event of the marker
 */
function showInfoOnClick(e) {
    const infoDiv = document.getElementById("info-section");
    const mapContainer = document.getElementById("map-container");
    const mapDiv = document.getElementById("map");

    let lat = e.latlng.lat;
    let lng = e.latlng.lng;


    if (infoDiv.classList.length === 0) {
        mapContainer.classList.add("active");
        mapDiv.classList.add("map-flex");
        infoDiv.classList.add("info-flex");
        setTimeout(function () {
            map.setView(new L.LatLng(lat, lng), 16);
            map.invalidateSize(true);
        }, 400);
        createHTML(e.target.bikeColor);
    } else {
        map.setView(new L.LatLng(lat, lng), 16);
        map.invalidateSize(true);
        createHTML(e.target.bikeColor);
    }

    function createHTML() {
        return infoDiv.innerHTML = "" +
            "<button onclick='hideInfoDiv()'>X</button> " +
            "<br>" +
            "<h1>Bicycle rent</h1>" +
            "<ul>" +
            "<img id='bicycle-image' src='/images/bike-image" + e.target.bikeColor + ".jpg' alt='Image of bicyce product'>" +
            "<li>Classy bike from 80s</li>" +
            "<li>Color: " + e.target.bikeColor + "</li>" +
            "<li>Customizable seat height</li>" +
            "<li>Price: "+ e.target.bikePricePerMinute +" NOK/min</li>" +
            "<li>*100 NOK fee for leaving the bike at another location</li>" +
            "</ul>" +
            "<p>Bicycle id : " + e.target.bikeId + "</p>" +
            "<button id='rentButton' onclick='orderNowButton("+ e.target.bikeId + ",[" + e.latlng.lat + "," + e.latlng.lng  + "], " + e.target.bikePricePerMinute +")'>RENT NOW</button>";
    }
}

function orderNowButton(bikeId, location, pricePerMinute){
    const toJson = "{" + '"bikeId"' + ":" + '"' + bikeId + '"' + ","  +
        '"location"' + ":" + '"' + location + '"' + "," +
        '"pricePerMinute" : "' + pricePerMinute + '"}';

    //const jsonObject = JSON.parse(toJson.toString());

    console.log(toJson);

    const asyncRequest = new XMLHttpRequest();

    asyncRequest.open("POST", "/orders/rental/");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(toJson);

    asyncRequest.onreadystatechange = onResponseReceivedFromPOST;

    function onResponseReceivedFromPOST() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                alert("Added to cart");
                window.location = "/rental/confirmation/" + bikeId;
            } else if(this.status === 401){
               alert("Payment method not specified");
            }else{
                console.log(this.status);
                alert("Something went wrong");
            }
        }
    }
}

/**
 * function for hiding the bike info div.
 * is called when cross button is clicked in the div.
 */
function hideInfoDiv(){
    const infoDiv = document.getElementById("info-section");
    const mapContainer = document.getElementById("map-container");
    const mapDiv = document.getElementById("map");

    mapContainer.classList.remove("active");
    mapDiv.classList.remove("map-flex");
    infoDiv.classList.remove("info-flex");

    map.invalidateSize(true);
}

function getBicyclesFromServer(){
    const asyncRequest = new XMLHttpRequest();

    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/api/bicycle/available");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;

    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);
        if (responseJson.length !== 0){
            for (let i = 0; i<responseJson.length;i++){
                let productJSON = responseJson[i];
                let bicycleId = productJSON.id;
                let bicycleColor = productJSON.color;
                let locationArray = productJSON.location.split(",");
                let locationLatIng = L.latLng(locationArray[0], locationArray[1]);
                let bicycleLocation = L.latLng(locationLatIng);
                let bicyclePricePerMinute = productJSON.pricePerMinute;

                createBikeMarker(bicycleLocation, bicycleId, bicycleColor, bicyclePricePerMinute);
            }
        }
    }
    function onResponseReceivedFromGET() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                console.log("Successful GET request")
            } else{
                console.log("Request was unsuccessful");
                console.log("Status code is: " + this.status)
            }
        }
    }
}

getBicyclesFromServer();
