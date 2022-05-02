const elementToBeFilled = document.getElementById("productsToBeFilledFromDB");

const asyncRequest = new XMLHttpRequest();

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/api/products/shopping-cart");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;



    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);
        if (responseJson.length !== 0){
            let totalPriceCounter = 0;
            for (let i = 0; i<responseJson.length;i++){
                let productJSON = responseJson[i];
                let productId = productJSON.id;
                let productName = productJSON.productName;
                let imageUrl = productJSON.imageUrl;
                let price = productJSON.price;

                let tag = document.createElement("div");
                tag.classList.add("flex")
                tag.innerHTML =
                                `
                                <img src="Images/${imageUrl}">
                                <div>
                                    <p>${productName}</p>
                                    <p>${productId}</p>
                                </div>
                                <div>
                                    <a onclick="removeProductFromCart(${productId})" href="">X</a>
                                    <p>${price} NOK</p>
                                </div>
                                `
                totalPriceCounter += price;


                elementToBeFilled.appendChild(tag);
            }
            let totalPriceTag = document.createElement("b");
            totalPriceTag.innerText = "Total: " + totalPriceCounter.toString() + " NOK";
            elementToBeFilled.appendChild(totalPriceTag);
        }else{
            elementToBeFilled.innerText = "No products in database";
        }
    }
    function onResponseReceivedFromGET() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                console.log("Successful GET request")
            } else{
                alert("Need to be logged in");
            }
        }
    }
}
getInfoFromDB();

function removeProductFromCart(productID){
    alert("slettet vare med id " + productID)
}
