const elementToBeFilled = document.getElementById("productsToBeFilledFromDB");

const asyncRequest = new XMLHttpRequest();

let loggedIn;

function createHtml(productId, productName, imageUrl, price) {
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
    return tag;
}

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

                let tag = createHtml(productId,productName,imageUrl,price);


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
                loggedIn = true;
            } else{
                loggedIn = false;
                alert("Not logged in, products received from localstorage");


                let cs = localStorage.getItem("products");
                let jsonArray = JSON.parse(cs);


                for (let i = 0; i<jsonArray.length;i++){
                    elementToBeFilled.appendChild(createHtml(jsonArray[i]._id, jsonArray[i]._name,jsonArray[i]._imageUrl,jsonArray[i]._price));
                }
            }
        }
    }
}

getInfoFromDB();

function removeProductFromCart(productID){
    alert("slettet vare med id " + productID)
    if (loggedIn){
        //todo: send DELETE request til server
    }else{
        let jsonArray = JSON.parse(localStorage.getItem("products"));

        for (let i = 0; i<jsonArray.length;i++){
            if (parseInt(jsonArray[i]._id) === parseInt(productID)){
                console.log(jsonArray);
                jsonArray.splice(jsonArray.indexOf(jsonArray[i],0),1);
                console.log(jsonArray);
                localStorage.setItem('products', JSON.stringify(jsonArray));
            }
        }
    }
}
