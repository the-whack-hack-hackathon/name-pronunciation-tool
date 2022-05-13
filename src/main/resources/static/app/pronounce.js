function pronounce() {
    const name = document.getElementById("name").value;
    const gender = document.querySelector('input[name="gender"]:checked').value;;
    if (name.length === 0 || !isValidString(name)) {
        const myModal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
        myModal.show();
        return;
    }
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open("POST", "/api/pronounceName");

    xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
    xmlHttpRequest.responseType = "blob";
    xmlHttpRequest.onload = function (event) {
        const blob = new Blob([xmlHttpRequest.response], { type: "audio/ogg" });
        const objectUrl = URL.createObjectURL(blob);
        const audio = document.getElementById("myAudio");
        audio.src = objectUrl;
        audio.onload = function (audEvent) {
            URL.revokeObjectURL(objectUrl);
        };
        audio.play();
    };
    const request = "{\"name\":\""+name+"\",\"gender\":\""+gender+"\"}";
    xmlHttpRequest.send(request);
}

function isValidString(inputStr){
    const regexForStr = /^[a-zA-Z ]+$/
    return inputStr.match(regexForStr);
}