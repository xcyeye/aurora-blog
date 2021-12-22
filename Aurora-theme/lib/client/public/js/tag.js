import myData from'@temp/my-data'

//需要排除的页面url
let excludes = ['/','/about/','/mood/','/link/','/tag/','/archive/','/photo/','/aurora-coze/','/aurora-register/','/aurora-archive/','/aurora-music/','/404.html']
let categoriesIncludeFolderName = true

let customTagName = "tag"
let customCategoriesName = "categories"

export function setTag(app,themeProperty) {
    if (themeProperty.customTagName !== undefined) {
        customTagName = themeProperty.customTagName
    }

    if (themeProperty.customCategoriesName !== undefined) {
        customCategoriesName = themeProperty.customCategoriesName
    }

    return new Promise((resolve,reject) => {

        if (themeProperty.categoriesIncludeFolderName !== undefined) {
            categoriesIncludeFolderName = themeProperty.categoriesIncludeFolderName
        }
        let excludePathSet = new Set()

        for (let i = 0; i < excludes.length; i++) {
            excludePathSet.add(excludes[i])
        }

        if (themeProperty.excludePath !== undefined) {
            for (let i = 0; i < themeProperty.excludePath.length; i++) {
                excludePathSet.add(themeProperty.excludePath[i])
            }
        }
        resolve(excludePathSet)
    }).then((excludePathSet) => {
        excludes = Array.from(excludePathSet)
        //存放所有categories
        let categoriesSetArr = new Set()

        //set存放所有分割的tag
        let tagSetArr = new Set()

        //mapSet存放所有文章的map集合
        let mapSet = new Set()

        new Promise((resolve,reject) => {
            let excludeMapArr = myData.filter(pageData => {
                if (pageData.data.frontmatter.show !== undefined && !pageData.data.frontmatter.show) {
                    return false
                }
                return !excludes.includes(pageData.path);
            })
            resolve(excludeMapArr)
        }).then((excludeMapArr) => {
            new Promise((resolve,reject) => {
                for (let i = 0; i < excludeMapArr.length; i++) {
                    const articleMap = {
                        articleUrl: '',
                        title: '',
                        //这个是标签，在v1.8.0以前，这个是类别
                        tag: '',
                        component: '',
                        //路由名字
                        name: '',
                        content: '',
                        contentRendered: '',
                        //这个是类别，在v1.8.0之前，这个是标签
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
                        if (commitPageDate !== undefined && commitPageDate !== null) {
                            articleMap.pageCreateTime = commitPageDate
                        }
                    }

                    articleMap.gitTime = commitPageDate === undefined ? 0 : commitPageDate

                    let pageTagArr = excludeMapArr[i].data.frontmatter[customTagName] === undefined ? [] : excludeMapArr[i].data.frontmatter[customTagName]
                    articleMap.tag = pageTagArr
                    for (let j = 0; j < pageTagArr.length; j++) {
                        tagSetArr.add(pageTagArr[j])
                    }

                    //获取path
                    let path = excludeMapArr[i].path
                    let strings = path.split("/");
                    if (strings.length >2 && categoriesIncludeFolderName) {
                        //从strings中的第二个开始遍历，到倒数第二个结束

                        let tempCategoriesSetArr = new Set()
                        let temTag = []
                        new Promise((resolve,reject) => {

                            //将frontmatter中的categories放入此集合里面
                            if (excludeMapArr[i].data.frontmatter[customCategoriesName] !== undefined) {
                                for (let j = 0; j < excludeMapArr[i].data.frontmatter[customCategoriesName].length; j++) {
                                    categoriesSetArr.add(excludeMapArr[i].data.frontmatter[customCategoriesName][j])
                                    tempCategoriesSetArr.add(excludeMapArr[i].data.frontmatter[customCategoriesName][j])
                                }
                            }

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
                                            categoriesSetArr.add(tag)
                                            tempCategoriesSetArr.add(tag)
                                            temTag.push(tag)
                                        }
                                    }
                                }else {
                                    categoriesSetArr.add(tag)
                                    tempCategoriesSetArr.add(tag)
                                    temTag.push(tag)
                                }
                            }
                            resolve(tempCategoriesSetArr)
                        }).then((tempCategoriesSetArr) => {
                            //获取标签
                            //获取文章连接
                            articleMap.articleUrl = path

                            articleMap.frontmatter = excludeMapArr[i].frontmatter
                            articleMap.contentRendered = excludeMapArr[i].contentRendered
                            articleMap.content = excludeMapArr[i].content

                            articleMap.categories = Array.from(tempCategoriesSetArr)

                            //获取标题
                            let title = excludeMapArr[i].title
                            articleMap.title = title === undefined ? "" : title

                            mapSet.add(articleMap)
                        })
                    }else {
                        //获取标签
                        //获取文章连接
                        articleMap.articleUrl = path

                        articleMap.frontmatter = excludeMapArr[i].frontmatter
                        articleMap.contentRendered = excludeMapArr[i].contentRendered
                        articleMap.content = excludeMapArr[i].content

                        let categories = excludeMapArr[i].frontmatter[customCategoriesName]
                        categories = categories === undefined ? [] : categories

                        articleMap.categories = categories

                        for (let j = 0; j < categories.length; j++) {
                            categoriesSetArr.add(categories[j])
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

                //这个是所有的标签
                let allTagArr = Array.from(tagSetArr)

                //这个是所有类别
                let allCategoriesArr = Array.from(categoriesSetArr)

                //将标签和文章数组对象放入store中
                app.$store.commit('setTagArr',{
                    tagArr: allTagArr
                })

                app.$store.commit('setCategories',{
                    categories: allCategoriesArr
                })

                app.$store.commit('setAllPageMap',{
                    allPageMap: mapSet
                })
            })
        })
    })
}