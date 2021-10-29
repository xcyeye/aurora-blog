import { defineClientAppEnhance } from '@vuepress/client'
import UseBlog from "./components/UseBlog";
export default defineClientAppEnhance(({ app, router, siteData }) => {
    app.component("UseBlog",UseBlog)

})