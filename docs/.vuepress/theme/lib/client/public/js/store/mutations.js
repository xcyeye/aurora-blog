module.exports = {
    setAnimeImg(state,imgUrl) {
        this.state.animeImg = imgUrl.imgUrl
    },
    setHeroImg(state,heroImg) {
        this.state.heroImage = heroImg.heroImg
    },
    setTagArr(state,tag) {
        this.state.tagArr = tag.tagArr
    },
    setAllPageMap(state,allPageMap) {
        let arr = Array.from(allPageMap.allPageMap)
        this.state.allPageMap = arr
    },
    setPageNum(state,page) {
        this.state.pageNum = page.page
    },
    setTagStatus(state,isSuccess) {
        this.state.setTatStatus = isSuccess.isSuccess
    },
    setFontColorStyle(state,fontColorStyle) {
        this.state.fontColorStyle = "--fontColor: "+ fontColorStyle.color + ";"

    },
    setFontFamilyStyle(state,fontFamilyStyle) {

        this.state.fontFamilyStyle = "--fontFamily: " + fontFamilyStyle.fontFamily + ";"
    },
    setIsFitter(state,isFitter) {
        this.state.isFitter = isFitter.isFitter
    },
    setVarFilterBlur(state,filterBlur) {
        this.state.varFilterBlur = filterBlur.varFilterBlur
        this.state.filterBlurStyle = "--fitter-blue: " + filterBlur.varFilterBlur + "px;"
    },
    setVarBorderRadius(state,varBorderRadius) {
        this.state.varBorderRadius = varBorderRadius.varBorderRadius
        this.state.borderRadiusStyle = "--borderRadius: " + varBorderRadius.varBorderRadius + "px;"
    },
    setVarOpacity(state,varOpacity) {
        this.state.varOpacity = varOpacity.varOpacity
        this.state.opacityStyle = "--opacity: " + varOpacity.varOpacity +";"
    },
    setWelcomeOpenTime(state,time) {
        this.state.welcomeOpenTime = time.time
    },
    setWelcomeOpenStatus(state,status) {
        this.state.welcomeOpenStatus = status.status
    },
    setCategories(state,categories) {
        this.state.categories = categories.categories
    },
    setReadCount(state,readCount) {
        this.state.readCount = readCount.readCount
    },
    setCommentCount(state,commentCount) {
        this.state.commentCount = commentCount.commentCount
    },
    setShowPosterShadow(state,showPosterShadow) {
        this.state.showPosterShadow = showPosterShadow.showPosterShadow
    },
    setShowPostImg(state,showPostImg) {
        this.state.showPostImg = showPostImg.showPostImg
    },
    setPostImgHref(state,postImgHref) {
        this.state.postImgHref = postImgHref.postImgHref
    },
    setShowShadeLoad(state,showShadeLoad) {
        this.state.showShadeLoad = showShadeLoad.showShadeLoad
    },
    setQrImgHref(state,qrImgHref) {
        this.state.qrImgHref = qrImgHref.qrImgHref
    },
    setPicture(state,picture) {
        this.state.picture = picture.picture
    },
    setPhotos(state,photo) {
        this.commit("setLoadingFinish",{
            loadingFinish: true
        })
        if (this.state.photos.length === 0) {
            this.state.photos = photo.photos
        }
    },
    setLoadingFinish(state,loadingFinish) {
        this.state.loadingFinish = loadingFinish.loadingFinish
    },
    setVerifyStatus(state,verifyStatus) {
        this.state.verifyStatus = verifyStatus.verifyStatus
    },
    setEditMoods(state,editMoods) {
        this.state.editMoods = editMoods.editMoods
    }
}