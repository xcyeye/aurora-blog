const network = require('../network')
const myData = require('@temp/my-data')
let themeProperty = null
module.exports = {
    /*getAnimeImg(context) {
        return new Promise((resolve,reject) => {
            new Promise((resolve,reject) => {
                for (let i = 0; i < myData.default.length; i++) {
                    if (myData.default[i].path === '/') {
                        themeProperty = myData.default[i].frontmatter
                    }
                }
                resolve()
            })
            network.req(themeProperty.animeOption).then(res => {
                const query = themeProperty.animeOption.query
                const url = res[query]
                context.commit('setAnimeImg', {
                    imgUrl: url
                })
                resolve()
            })
        })
    },*/
}