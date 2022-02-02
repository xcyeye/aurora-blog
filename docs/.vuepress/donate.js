module.exports = {
    donate: {

        //赞赏页面，支付二维码，推荐放置两张图片链接
        donateImg: [
            "https://ooszy.cco.vin/img/blog-public/wxpay.png",
            "https://ooszy.cco.vin/img/blog-public/zfbpay.jpg",
        ],

        //是否在文章页面显示赞赏 默认显示
        articlePage: true,

        //是否在关于页面显示 默认显示
        aboutPage: true,

        //显示在赞赏页面的信息
        donateProduct: [
            {
                //名字
                name: "奶茶",

                //图片地址
                img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",

                //价格
                price: 18,

                //前缀
                prefix: "$",
            },
            {
                name: "全味奶茶",
                img: "https://ooszy.cco.vin/img/blog-note/image-20210911233612031.png?",
                price: 11,
                prefix: "￥",
            },
        ],

        //是否显示在线支付的订单信息，如果需要开启，请自己写支付接口，自己修改源码，默认关闭
        //onlineList: true, v1.11.2已被移除

        //用户赞赏列表数组
        donateList: [
            {
                name: "初尘",
                msg: "主题太棒了",
                img: "https://ooszy.cco.vin/img/blog-public/nc.jpeg",
                price: 7,
                prefix: "￥",
            }
        ],
    }
}