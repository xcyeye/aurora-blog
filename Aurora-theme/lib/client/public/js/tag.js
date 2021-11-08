function confirmEnding(str, target) {
    // 请把你的代码写在这里
    var start = str.length-target.length;
    var arr = str.substr(start,target.length);
    if(arr == target){
        return true;
    }
    return false;
}

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
                    if (myData.default[i].path === "/404.html") {
                        continue
                    }

                    //判断是否为说说页面
                    if (myData.default[i].filePathRelative !== null) {
                        if (myData.default[i].filePathRelative.search("moods/") !== -1) {
                            continue
                        }

                        //判断是否为相册页面
                        if (myData.default[i].filePathRelative.search("photos/") !== -1) {
                            continue
                        }
                    }else {
                        continue
                    }

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
                        frontmatter: [],
                        data: null,
                        date: 0
                    }

                    articleMap.data = myData.default[i].data
                    let createPageDate = myData.default[i].data.frontmatter.date
                    let commitPageDate = myData.default[i].data.git.updatedTime

                    let time = 0
                    if (createPageDate !== undefined) {
                        time = new Date(createPageDate).getTime();
                    }else {
                        if (commitPageDate !== undefined) {
                            time = commitPageDate
                        }
                    }

                    articleMap.date = time

                    //获取path
                    let path = myData.default[i].path
                    let strings = path.split("/");
                    if (strings.length >2) {
                        //从strings中的第二个开始遍历，到倒数第二个结束
                        let temTag = []
                        new Promise((resolve,reject) => {
                            for (let j = 0; j < strings.length; j++) {
                                if (j +1 >= strings.length -1) {
                                    continue
                                }
                                let tag = strings[j+1]

                                let excludeTag = []
                                if (themeProperty.excludeTag !== undefined) {
                                    excludeTag = themeProperty.excludeTag
                                }

                                if (excludeTag.length !== 0) {
                                    for (let k = 0; k < excludeTag.length; k++) {
                                        if (!(tag === excludeTag[k])) {
                                            set.add(tag)
                                            temTag.push(tag)
                                        }
                                    }
                                }else {
                                    set.add(tag)
                                    temTag.push(tag)
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
                    }else {
                        //获取标签
                        articleMap.tag = []

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
                    }
                }
                resolve()
            }).then(()=> {

                app.$store.commit('setPageNum',{
                    page: mapSet.size
                })

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