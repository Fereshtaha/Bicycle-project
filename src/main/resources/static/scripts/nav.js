
function addAndRemoveClassWhenScrolledOnTopOfPage(){
    let navHeader = document.getElementById("header");
    window.onscroll = function() {


        // @let int scrollPoint
        let scrollPoint = window.scrollY;

        // check if we are under the top of the page
        if (scrollPoint > 0) {
            navHeader.classList.add("isScrolled");
        } else {
            navHeader.classList.remove("isScrolled");
        }
    }
}

function addWhiteBackgroundToNavWhenHamburgerIsHovered(){
    let navHeader = document.getElementById("header");
    let dropdownContent = document.getElementById("dropDownOnHover");

    dropdownContent.addEventListener("mouseover", function (){
        navHeader.classList.add("isScrolledEmmidietly");
    })

    dropdownContent.addEventListener("mouseleave", function (){
        navHeader.classList.remove("isScrolledEmmidietly");
    })
}



window.onload = function (){
    addAndRemoveClassWhenScrolledOnTopOfPage();
    addWhiteBackgroundToNavWhenHamburgerIsHovered();
}