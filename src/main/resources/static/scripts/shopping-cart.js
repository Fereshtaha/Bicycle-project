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
                            <p>id : ${productId}</p>
                        </div>
                        <div style="text-align: right">
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

        while(elementToBeFilled.firstChild){
            elementToBeFilled.removeChild(elementToBeFilled.lastChild);
        }

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
            let totalPriceTag = document.createElement("div");
            totalPriceTag.innerHTML = "<b>Total: " + totalPriceCounter.toString() + " NOK</b>";
            totalPriceTag.id = "cartPriceSection";


            let purchaseBtn = document.createElement("button");
            purchaseBtn.innerText = "Order now";
            purchaseBtn.id = "purchaseBtn";

            purchaseBtn.addEventListener("click", sendOrder)

            totalPriceTag.appendChild(purchaseBtn);
            elementToBeFilled.appendChild(totalPriceTag);
        }else{
            elementToBeFilled.innerText = "No products in cart";
        }
    }
    function onResponseReceivedFromGET() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                console.log("Successful GET request")
                loggedIn = true;
            } else{
                //todo: specify http message for not logged in and general faults
                loggedIn = false;


                let cs = localStorage.getItem("products");
                let jsonArray = JSON.parse(cs);

                let totalPriceCounter = 0;

                for (let i = 0; i<jsonArray.length;i++){
                    elementToBeFilled.appendChild(createHtml(jsonArray[i]._id, jsonArray[i]._name,jsonArray[i]._imageUrl,jsonArray[i]._price));
                    totalPriceCounter += jsonArray[i]._price;
                }
                if (jsonArray.length !== 0){
                    let totalPriceTag = document.createElement("b");
                    totalPriceTag.innerText = "Total: " + totalPriceCounter.toString() + " NOK";
                    elementToBeFilled.appendChild(totalPriceTag);

                    let purchaseBtn = document.createElement("button");
                    purchaseBtn.innerText = "Order now";
                    purchaseBtn.id = "purchaseBtn";

                    purchaseBtn.addEventListener("click", sendOrder)

                    elementToBeFilled.appendChild(purchaseBtn);
                }else{
                    elementToBeFilled.innerText = "No products in cart";
                }
            }
        }
    }
}

getInfoFromDB();

function removeProductFromCart(productID){
    if (loggedIn){
        asyncRequest.open("DELETE", "/customers/deleteProductInCart");
        asyncRequest.setRequestHeader("Accept", "application/json");
        asyncRequest.setRequestHeader("Content-Type", "application/json");
        asyncRequest.send(productID);

        asyncRequest.onreadystatechange = function (){
            if (this.readyState === XMLHttpRequest.DONE) {
                if (this.status === 200) { // handle success
                    getInfoFromDB();
                }else{
                    //alert("Could not remove item from cart");
                    //console.log(this.status);
                }
            }
        };

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

function sendOrder(){
    if (loggedIn){
        alert("Backend not implemented yet");
    }else{
        alert("You need to log in to send an order");
    }
}