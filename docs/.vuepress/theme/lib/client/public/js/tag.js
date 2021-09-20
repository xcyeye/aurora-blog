function confirmEnding(str, target) {
    // 请把你的代码写在这里
    var start = str.length-target.length;
    var arr = str.substr(start,target.length);
    if(arr == target){
        return true;
    }
    return false;
}

let tags = []
const myData = require('@temp/my-data')
let themeProperty = null

module.exports = {
    setTag: function (app) {
        return new Promise((resolve,reject) => {
            for (let i = 0; i < myData.default.length; i++) {
                if (myData.default[i].path === '/') {
                    themeProperty = myData.default[i].frontmatter
                }
            }
            resolve()
        }).then(() => {
            //set存放所有分割的tag
            let set = new Set()

            //存放所有categories
            let categoriesSet = new Set()

            //mapSet存放所有文章的map集合
            let mapSet = new Set()

            new Promise((resolve,reject) => {
                for (let i = 0; i < myData.default.length; i++) {
                    const articleMap = {
                        articleUrl: '',
                        title: '',
                        tag: '',
                        component: '',
                        //路由名字
                        name: '',
                        content: '',
                        contentRendered: '',
                        categories: [],
                        frontmatter: []
                    }

                    //获取path
                    let path = myData.default[i].path
                    var strings = path.split("/");
                    if (strings.length >2) {
                        //从strings中的第二个开始遍历，到倒数第二个结束
                        let temTag = []
                        new Promise((resolve,reject) => {
                            for (let j = 0; j < strings.length; j++) {
                                if (j +1 >= strings.length -1) {
                                    continue
                                }
                                let tag = strings[j+1]

                                for (let k = 0; k < themeProperty.excludeTag.length; k++) {
                                    if (!(tag === themeProperty.excludeTag[k])) {
                                        set.add(tag)
                                        temTag.push(tag)
                                    }
                                }
                            }
                            resolve()
                        }).then(() => {

                            //获取标签
                            articleMap.tag = temTag

                            //获取文章连接
                            articleMap.articleUrl = path

                            articleMap.frontmatter = myData.default[i].frontmatter
                            articleMap.contentRendered = myData.default[i].contentRendered
                            articleMap.content = myData.default[i].content
                            articleMap.categories = myData.default[i].frontmatter.categories === undefined ? [] : myData.default[i].frontmatter.categories

                            let categories = myData.default[i].frontmatter.categories
                            categories = categories === undefined ? [] : categories
                            for (let j = 0; j < categories.length; j++) {
                                categoriesSet.add(categories[j])
                            }
                            //获取标题
                            let title = myData.default[i].title
                            articleMap.title = title === undefined ? "" : title

                            mapSet.add(articleMap)
                        })
                    }
                }
                app.$store.commit('setPageNum',{
                    page: myData.default.length
                })
                resolve()
            }).then(()=> {
                let categoriesArr = Array.from(categoriesSet)

                let setArr = Array.from(set)

                //将标签和文章数组对象放入store中
                app.$store.commit('setTagArr',{
                    tagArr: setArr
                })

                app.$store.commit('setCategories',{
                    categories: categoriesArr
                })
                app.$store.commit('setAllPageMap',{
                    allPageMap: mapSet
                })
            })
        })
    }
}