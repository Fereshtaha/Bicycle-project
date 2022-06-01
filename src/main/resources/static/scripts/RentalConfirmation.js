const initialCoordinates = [62.46814742594781,6.34689191998423];

//map as a variable
let map;

const queryString = window.location.pathname;
const lastSegment = queryString.split("/").pop();

let coordinates;


//function for initiating the map;
function initMap() {
    let map = L.map('map',  {zoomControl: false}).setView(coordinates, 14);

    map.touchZoom.disable();
    map.doubleClickZoom.disable();
    map.scrollWheelZoom.disable();
    map.boxZoom.disable();
    map.keyboard.disable();
    map.dragging.disable();

    //map.locate({setView: true, maxZoom: 14});

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


const asyncRequest = new XMLHttpRequest();

function getLocationFromServer(){


    //asyncRequest.addEventListener("load", setCoordinates);

    asyncRequest.open("GET", "/orders/confirmation/" + lastSegment);
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;

    function onResponseReceivedFromGET() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                let coordinatesArray = this.responseText.split(",");
                coordinates = L.latLng(coordinatesArray[0],coordinatesArray[1]) ;
                map = initMap();
                createCircle(coordinates, 500, "green", "green");
            } else{
                console.log("Request was unsuccessful");
                console.log("Status code is: " + this.status)
            }
        }
    }

}

const endRentalBtn = document.getElementById("endRentalBtn");

endRentalBtn.addEventListener("click", function (){
    let currentLocation = "";

    getLocation();

    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            currentLocation = "Geolocation is not supported by this browser.";
        }
    }

    function showPosition(position){
        currentLocation = position.coords.latitude + "," + position.coords.longitude;

        asyncRequest.open("POST", "/orders/addBicycleOrder");
        asyncRequest.setRequestHeader("Accept", "application/json");
        asyncRequest.setRequestHeader("Content-Type", "application/json");
        asyncRequest.send(lastSegment + "," + currentLocation);

        asyncRequest.onreadystatechange = function (){
            if (this.readyState === XMLHttpRequest.DONE) {
                if (this.status === 200) { // handle success
                    alert("Added to cart");
                } else if (this.status === 401) {
                    alert("Something went wrong");
                }
            }

        };
    }



})

getLocationFromServer();

