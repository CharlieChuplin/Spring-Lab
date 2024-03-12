document.addEventListener("DOMContentLoaded", () => {
    let img = document.querySelector("#banner1");
    let imgSrcArray = [
        "../../../images2/logo_01.jpg",
        "../../../images2/logo_02.jpg",
        "../../../images2/logo_03.jpg",
        "../../../images2/logo_04.jpg",
        "../../../images2/logo_05.jpg",
        "../../../images2/logo_06.jpg",
        "../../../images2/logo_07.jpg",
        "../../../images2/logo_08.jpg",
    ];
    let count = 0;
    let speed = 1000;
    const time = 50;
    let timerId = null;

    timerId = setInterval(function () {
        img.src = imgSrcArray[count];
        count++;

        if (count == imgSrcArray.length) {
            count = 0;
        }
    }, speed);

    document.addEventListener("keydown", function (e) {
        switch (e.keyCode) {
            case 38:
                time = 700;
                break;
            case 40:
                time = 1500;
                break;
        }
    });
});