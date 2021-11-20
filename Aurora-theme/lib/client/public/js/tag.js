const myData = require('@temp/my-data')
let themeProperty = null

//需要排除的页面url
let excludes = ['/','/about/','/mood/','/link/','/tag/','/archive/','/aurora-archive/','/aurora-music/','/404.html']

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
                let excludeMapArr = myData.default.filter(pageData => {
                    return !excludes.includes(pageData.path);
                })
                resolve(excludeMapArr)
            }).then((excludeMapArr) => {
                new Promise((resolve,reject) => {
                    for (let i = 0; i < excludeMapArr.length; i++) {
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
                            date: 0,
                            //自定义时间戳
                            customTime: 0,
                            //使用git提交的时间戳
                            gitTime: 0,
                            pageCreateTime: 0
                        }

                        articleMap.data = excludeMapArr[i].data
                        let createPageDate = excludeMapArr[i].data.frontmatter.date
                        let commitPageDate = excludeMapArr[i].data.git.updatedTime

                        if (createPageDate !== undefined) {
                            articleMap.pageCreateTime = new Date(createPageDate).getTime();
                            articleMap.customTime = new Date(createPageDate).getTime();
                        }else {
                            if (commitPageDate !== undefined) {
                                articleMap.pageCreateTime = commitPageDate
                            }
                        }

                        articleMap.gitTime = commitPageDate === undefined ? 0 : commitPageDate

                        //获取path
                        let path = excludeMapArr[i].path
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

                                articleMap.frontmatter = excludeMapArr[i].frontmatter
                                articleMap.contentRendered = excludeMapArr[i].contentRendered
                                articleMap.content = excludeMapArr[i].content
                                articleMap.categories = excludeMapArr[i].frontmatter.categories === undefined ? [] : excludeMapArr[i].frontmatter.categories

                                let categories = excludeMapArr[i].frontmatter.categories
                                categories = categories === undefined ? [] : categories
                                for (let j = 0; j < categories.length; j++) {
                                    categoriesSet.add(categories[j])
                                }
                                //获取标题
                                let title = excludeMapArr[i].title
                                articleMap.title = title === undefined ? "" : title

                                mapSet.add(articleMap)
                            })
                        }else {
                            //获取标签
                            articleMap.tag = []

                            //获取文章连接
                            articleMap.articleUrl = path

                            articleMap.frontmatter = excludeMapArr[i].frontmatter
                            articleMap.contentRendered = excludeMapArr[i].contentRendered
                            articleMap.content = excludeMapArr[i].content
                            articleMap.categories = excludeMapArr[i].frontmatter.categories === undefined ? [] : excludeMapArr[i].frontmatter.categories

                            let categories = excludeMapArr[i].frontmatter.categories
                            categories = categories === undefined ? [] : categories
                            for (let j = 0; j < categories.length; j++) {
                                categoriesSet.add(categories[j])
                            }
                            //获取标题
                            let title = excludeMapArr[i].title
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

                    console.log(mapSet)
                })
            })
        })
    }
}