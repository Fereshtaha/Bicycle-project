const elementToBeFilled = document.getElementById("productsToBeFilledFromDB");

const asyncRequest = new XMLHttpRequest();

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    const queryString = window.location.pathname;
    const lastSegment = queryString.split("/").pop();
    console.log(lastSegment);
    asyncRequest.open("GET", "/api/products/" + lastSegment);
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;



    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);

        let productName = responseJson.productName;
        let imageUrl = responseJson.imageUrl;
        let description = responseJson.description;
        let price = responseJson.price;

        elementToBeFilled.innerHTML =
            "<div id='image-container'>" +
            "<img class='productImage' src='/Images/" + imageUrl + "' alt='" + imageUrl+ "'>" +
            "</div>"+
            "<div id='description-container'>" +
            "<p class='productName'>" + productName + "</p>" +
            "<p class='productPrice'>" + price + " NOK" +  "</p>" +
            "<p class='productDescription'>" + description + "</p>" +
            "<button id='add-to-cart-button'>Add to chart</button>" +
            "</div>"



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
getInfoFromDB();
