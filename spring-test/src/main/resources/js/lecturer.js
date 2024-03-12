let banner = function (selector, imgList, cb_set_speed) {
    let currentIndex = 1;
    let $banner = document.querySelector(selector);
    let speed = cb_set_speed();
    let timerId = null;

    timerId = setInterval(() => {
        let imgName = imgList[currentIndex++];
        $banner.setAttribute("src", "../../../images2/" + imgName);

        if (currentIdex >= imgList.length) {
            currentIdex = 0;
        }
    }, speed);

    return timerId;
};

document.addEventListener("DOMContentLoaded", () => {
    let timerId = null;
    let speed = 1000;
    let imageList = [
        "logo_01.jpg",
        "logo_02.jpg",
        "logo_03.jpg",
        "logo_04.jpg",
        "logo_05.jpg",
        "logo_06.jpg",
        "logo_07.jpg",
        "logo_08.jpg",
    ];

    timerId = banner("#banner1", imageList, () => speed);

    document.addEventListener("keydown", function (e) {
        const UP_KEY = 38,
            DOWN_KEY = 40;
        const CHANGE_SPEED = 100;
        const MIN_SPEED = 100,
            MAX_SPEED = 2000;

        switch (e.keyCode) {
            case UP_KEY:
                speed -= CHANGE_SPEED;
                speed = speed < MIN_SPEED ? MinSpeed : speed;
                break;
            case DOWN_KEY:
                speed += CHANGE_SPEED;
                speed = speed > MAX_SPEED ? MAX_SPEED : speed;
                break;
        }
    });
});