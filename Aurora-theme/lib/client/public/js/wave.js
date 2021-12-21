function wave1() {
    let waveWidth = 3300,
        offset = 0,
        waveHeight = 15,  //波浪高度
        waveCount = 6,  //波浪个数
        startX = -1000,
        startY = 120,   //canvas 高度
        progress = 75,  //波浪位置的高度
        d2 = waveWidth / waveCount,
        d = d2 / 2,
        hd = d / 2,
        c = document.getElementById("home-wave-canvas1"),
        ctx = c.getContext("2d");
    function tick() {
        offset -= 5;
        if (-1 * offset === d2) offset = 0;
        ctx.clearRect(0, 0, c.width, c.height);
        ctx.fillStyle = 'rgba(255,255,255,.524)';
        ctx.beginPath();
        let offsetY = startY - progress;
        ctx.moveTo(startX - offset, offsetY);
        for (let i = 0; i < waveCount; i++) {
            let dx = i * d2;
            let offsetX = dx + startX - offset;
            ctx.quadraticCurveTo(offsetX + hd, offsetY + waveHeight, offsetX + d, offsetY);
            ctx.quadraticCurveTo(offsetX + hd + d, offsetY - waveHeight, offsetX + d2, offsetY);
        }
        ctx.lineTo(startX + waveWidth, 3000);
        ctx.lineTo(startX, 3000);
        ctx.fill();

        setTimeout(tick, 1500 / 60);    //速度
        //requestAnimationFrame(tick);
    }
    tick();
}
/* 第二条 */
function wave2() {
    let waveWidthB = 3300,
        offsetB = 0,
        waveHeightB = 30,  //波浪高度
        waveCountB = 4,  //波浪个数
        startXB = -1000,
        startYB = 155,   //canvas 高度
        progressB = 50,  //波浪位置的高度
        d2B = waveWidthB / waveCountB,
        dB = d2B / 2,
        hdB = dB / 2,
        cB = document.getElementById("home-wave-canvas2"),
        ctxB = cB.getContext("2d");
    function tickB() {
        offsetB -= 5;
        if (-1 * offsetB === d2B) offsetB = 0;
        ctxB.clearRect(0, 0, cB.width, cB.height);
        ctxB.fillStyle = 'rgba(255,255,255,.745)';
        ctxB.beginPath();
        let offsetYB = startYB - progressB;
        ctxB.moveTo(startXB - offsetB, offsetYB);
        for (let i = 0; i < waveCountB; i++) {
            let dxB = i * d2B;
            let offsetXB = dxB + startXB - offsetB;
            ctxB.quadraticCurveTo(offsetXB + hdB, offsetYB + waveHeightB, offsetXB + dB, offsetYB);
            ctxB.quadraticCurveTo(offsetXB + hdB + dB, offsetYB - waveHeightB, offsetXB + d2B, offsetYB);
        }
        ctxB.lineTo(startXB + waveWidthB, 3000);
        ctxB.lineTo(startXB, 3000);
        ctxB.fill();

        setTimeout(tickB, 5000 / 60); //速度
        //requestAnimationFrame(tick);
    }
    tickB();
}
export function wave () {
    let canvas1 = document.getElementById('home-wave-canvas1');
    canvas1.width = window.innerWidth;
    let canvas2 = document.getElementById('home-wave-canvas2');
    canvas2.width = window.innerWidth;
    wave1()
    wave2()
}

