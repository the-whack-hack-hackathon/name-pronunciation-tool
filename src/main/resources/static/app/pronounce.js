function pronounce() {
    const str = document.getElementById("name").value;
    if (str.length === 0 || !isValidString(str)) {
        const myModal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
        myModal.show();
        return;
    }
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open("GET", "/api/pronounceName/" + str);
    xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
    xmlHttpRequest.responseType = "blob";
    xmlHttpRequest.onload = function (event) {
        const blob = new Blob([xmlHttpRequest.response], { type: "audio/ogg" });
        const objectUrl = URL.createObjectURL(blob);
        const audio = new Audio();
        audio.src = objectUrl;
        audio.onload = function (audEvent) {
            URL.revokeObjectURL(objectUrl);
        };
        audio.play();
    };
    xmlHttpRequest.send();
}

function isValidString(inputStr){
    const regexForStr = /^[a-zA-Z]+$/
    return inputStr.match(regexForStr);
}