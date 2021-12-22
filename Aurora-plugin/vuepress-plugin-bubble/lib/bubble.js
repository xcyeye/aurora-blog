/* 这个气泡特效是收集于网络，作者为一为忆, http://www.iowen.cn */
let canvas, ctx, width, height, bubbles, animateHeader = true;
let bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color;
function initHeader() {
    canvas = document.getElementById('aurora-bubble-canvas');
    window_resize();
    ctx = canvas.getContext('2d');
    //建立泡泡
    bubbles = [];
    let num = width * bubbleNumber;//气泡数量
    for (let i = 0; i < num; i++) {
        let c = new Bubble();
        bubbles.push(c);
    }
    animate();
}

function animate() {
    if (animateHeader) {
        ctx.clearRect(0, 0, width, height);
        for (let i in bubbles) {
            bubbles[i].draw();
        }
    }
    requestAnimationFrame(animate);
}

function window_resize() {
    //canvas铺满窗口
    width = window.innerWidth;
    height = window.innerHeight;

    //如果需要铺满内容可以换下面这个
    let panel = document.getElementById('aurora-bubble-box');
    width=panel.offsetWidth;
    height=panel.offsetHeight;

    canvas.width = width;
    canvas.height = height;
}

function Bubble() {
    let _this = this;
    (function() {
        _this.pos = {};
        init();
    })();
    function init() {
        _this.pos.x = Math.random() * width;
        _this.pos.y = height + Math.random() * 100;
        _this.alpha = 0.1 + Math.random() * bubbleAlpha;//气泡透明度
        _this.alpha_change = 0.0002 + Math.random() * alphaChangeSpeed;//气泡透明度变化速度
        _this.scale = 0.2 + Math.random() * size;//气泡大小
        _this.scale_change = Math.random() * sizeChangeSpeed;//气泡大小变化速度
        _this.speed = 0.1 + Math.random() * riseSpeed;//气泡上升速度
    }
    //气泡
    this.draw = function() {
        if (_this.alpha <= 0) {
            init();
        }
        _this.pos.y -= _this.speed;
        _this.alpha -= _this.alpha_change;
        _this.scale += _this.scale_change;
        ctx.beginPath();
        ctx.arc(_this.pos.x, _this.pos.y, _this.scale * 10, 0, 2 * Math.PI, false);
        ctx.fillStyle = 'rgba(' + color + ',' + _this.alpha + ')';
        ctx.fill();
    };
}

export function bubble(aurora_bubbleNumber,aurora_bubbleAlpha,aurora_alphaChangeSpeed,aurora_size,aurora_sizeChangeSpeed,aurora_riseSpeed,aurora_color) {
    bubbleNumber = aurora_bubbleNumber
    bubbleAlpha = aurora_bubbleAlpha
    alphaChangeSpeed = aurora_alphaChangeSpeed
    size = aurora_size
    sizeChangeSpeed = aurora_sizeChangeSpeed
    riseSpeed = aurora_riseSpeed
    color = aurora_color
    initHeader();
}
