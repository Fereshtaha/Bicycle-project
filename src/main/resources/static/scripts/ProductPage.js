const elementToBeFilled = document.getElementById("productsToBeFilledFromDB");

const asyncRequest = new XMLHttpRequest();

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/api/products");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;



    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);
        if (responseJson.length !== 0){
            for (let i = 0; i<responseJson.length;i++){
                let productJSON = responseJson[i];
                let productName = productJSON.productName;
                let price = productJSON.price;

                let tag = document.createElement("div");
                tag.innerHTML = "<a href=\"https://google.com\"><p>" + productName + "</p>" +
                                "<p>" + price + ",-" +  "</p></a>"

                elementToBeFilled.appendChild(tag);
            }
        }else{
            elementToBeFilled.innerText = "No products in database";
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
getInfoFromDB();
