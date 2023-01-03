export const mutations = {
    setAnimeImg(state,imgUrl) {
        state.animeImg = imgUrl.imgUrl
    },
    setHeroImg(state,heroImg) {
        state.heroImage = heroImg.heroImg
    },
    setTagArr(state,tag) {
        state.tagArr = tag.tagArr
    },
    setAllPageMap(state,allPageMap) {
        let arr = Array.from(allPageMap.allPageMap)
        state.allPageMap = arr
    },
    setPageNum(state,page) {
        state.pageNum = page.page
    },
    setTagStatus(state,isSuccess) {
        state.setTatStatus = isSuccess.isSuccess
    },
    setFontColorStyle(state,fontColorStyle) {
        state.fontColorStyle = "--fontColor: "+ fontColorStyle.color + ";"

    },
    setFontFamilyStyle(state,fontFamilyStyle) {

        state.fontFamilyStyle = "--fontFamily: " + fontFamilyStyle.fontFamily + ";"
    },
    setIsFitter(state,isFitter) {
        state.isFitter = isFitter.isFitter
    },
    setVarFilterBlur(state,filterBlur) {
        state.varFilterBlur = filterBlur.varFilterBlur
        state.filterBlurStyle = "--fitter-blue: " + filterBlur.varFilterBlur + "px;"
    },
    setVarBorderRadius(state,varBorderRadius) {
        state.varBorderRadius = varBorderRadius.varBorderRadius
        state.borderRadiusStyle = "--borderRadius: " + varBorderRadius.varBorderRadius + "px;"
    },
    setVarOpacity(state,varOpacity) {
        state.varOpacity = varOpacity.varOpacity
        state.opacityStyle = "--opacity: " + varOpacity.varOpacity +";"
    },
    setWelcomeOpenTime(state,time) {
        state.welcomeOpenTime = time.time
    },
    setWelcomeOpenStatus(state,status) {
        state.welcomeOpenStatus = status.status
    },
    setCategories(state,categories) {
        state.categories = categories.categories
    },
    setShowPosterShadow(state,showPosterShadow) {
        state.showPosterShadow = showPosterShadow.showPosterShadow
    },
    setShowPostImg(state,showPostImg) {
        state.showPostImg = showPostImg.showPostImg
    },
    setPostImgHref(state,postImgHref) {
        state.postImgHref = postImgHref.postImgHref
    },
    setShowShadeLoad(state,showShadeLoad) {
        state.showShadeLoad = showShadeLoad.showShadeLoad
    },
    setQrImgHref(state,qrImgHref) {
        state.qrImgHref = qrImgHref.qrImgHref
    },
    setPicture(state,picture) {
        state.picture = picture.picture
    },
    setPhotos(state,photo) {
        commit("setLoadingFinish",{
            loadingFinish: true
        })
        if (state.photos.length === 0) {
            state.photos = photo.photos
        }
    },
    setLoadingFinish(state,loadingFinish) {
        state.loadingFinish = loadingFinish.loadingFinish
    },
    setVerifyStatus(state,verifyStatus) {
        state.verifyStatus = verifyStatus.verifyStatus
    },
    setEditMoods(state,editMoods) {
        state.editMoods = editMoods.editMoods
    },
    setCurrentPageNum(state,currentPageNum) {
        state.currentPageNum = currentPageNum.currentPageNum
    },
    setOpenMobileSidebar(state,openMobileSidebar) {
        state.openMobileSidebar = openMobileSidebar.openMobileSidebar
    },
    setPosterData(state,posterData) {
        state.posterData = posterData.posterData
    },
    setHomeWps(state,homeWps) {
        state.homeWps = homeWps.homeWps
    },
    setCurrentCatalogObjectArr(state,currentCatalogObjectArr) {
        state.currentCatalogObjectArr = currentCatalogObjectArr.currentCatalogObjectArr
    },
    setCurrentTagNum(state,currentTagNum) {
        state.currentTagNum = currentTagNum.currentTagNum
    }
}