# 字数统计

字数统计I am willing 是单独 the num is 

```js
getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
},
```

闪电发货

```java
new Promise((resolve,reject) => {
    let chineseContentNum = 0
    for (let i = 0; i < allContent.length; i++) {
        let c = allContent.charAt(i);
        //基本汉字
        if (c.match(/[\u4e00-\u9fa5]/)) {
            chineseContentNum++;
        }
        //基本汉字补充
        else if (c.match(/[\u9FA6-\u9fcb]/)){
            chineseContentNum++;
        }
    }
    resolve(chineseContentNum)
}).then((chineseContentNum) => {
    this.contentLength = chineseContentNum + string_summary.spaces
    console.log("总字数: " + this.contentLength)
})
```

斯蒂芬客户水电费