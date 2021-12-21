<template>
  <div :class="showMusicBoxStatus ? 'show-aurora-music-box' : 'no-aurora-music-box'" class="aurora-music-box">
    <div @mouseleave="musicMouseLeave" class="music-box">
      <div class="music-more" :class="showMoreSongStatus ? 'show-more-song' : 'no-show-more-song'">
        <div class="aurora-music-more-single" v-for="(item,index) in musicMapArr" :key="index">
          <span @click="clickMusicPlay($event,index)" :class="{'aurora-music-song-active': index === currentMusicNum}" class="aurora-music-font aurora-music-music1 aurora-music-song-info aurora-music-cursor aurora-music-song">{{item.songName}}</span>
        </div>
      </div>
      <div class="music-player">

        <div class="aurora-music-pic-par">
          <div @mouseenter="musicMouseenter" :class="{'music-rotate': playMusicStatus}" class="aurora-music-pic" id="aurora-music-pic">
            <div class="aurora-music-pause">
              <span :class="playMusicStatus ? 'aurora-music-zanting2' : 'aurora-music-bofang4'" @click="playMusic" class="aurora-music-font aurora-music-control-pause aurora-music-cursor"></span>
            </div>
            <img :src="currentMusicObject.picSrc === undefined ? defaultCover : currentMusicObject.picSrc" alt="">
            <!--<img :src="currentMusicObject.picSrc" alt="">-->
          </div>
        </div>
        <div class="aurora-music-info" :class="{'show-aurora-music-info': showMusicBoxStatus}">
          <div class="aurora-music-info-left"></div>
          <div class="aurora-music-info-right">
            <div class="aurora-music-title aurora-music-info-common">
              <span class="aurora-music-font aurora-music-music1 aurora-music-song-info aurora-music-cursor aurora-music-song">{{currentMusicObject.songName}}</span>
            </div>
            <div class="aurora-music-bottom aurora-music-info-common">
              <div class="aurora-music-operate">
                <div>
                  <span @click="musicPre" class="aurora-music-font aurora-music-shangyishou1 aurora-music-cursor"></span>
                </div>
              </div>
              <div class="aurora-music-operate">
                <div>
                  <span @click="musicNext" class="aurora-music-font aurora-music-xiayishou2 aurora-music-cursor"></span>
                </div>
              </div>
              <div class="aurora-music-operate">
                <div>
                  <span @click="musicSetting" :class="isMusicRandomPlay ? 'aurora-music-geshunxubofang' : 'aurora-music-a-ziyuan156'" class="aurora-music-font aurora-music-cursor"></span>
                </div>
              </div>
              <div class="aurora-music-operate">
                <div>
                  <span @mouseenter="showMoreSong" class="aurora-music-font aurora-music-gengduotianchong aurora-music-cursor aurora-music-operate-more"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <audio class="aurora-music-player-control" ref="aurora-music-player" controls="controls">
      <source src="" type="audio/mp3" />
      Your browser does not support this audio format.
    </audio>
  </div>
</template>

<script>
import network from "../network";
let songIds = []
let playlist = ''
let showPlaylist = false
let disabledNetEaseMusic = false
let localSongs = {}
let serverUrl = ''
let disabledSpace = false
try {
  songIds = __SONG_IDS__
  playlist = __PLAYLIST__
  showPlaylist = __SHOW_PLAYLIST__
  disabledNetEaseMusic = __DISABLED_NET_EASE_MUSIC__
  localSongs = __LOCAL_SONGS__
  serverUrl =  __SERVER_URL__
  disabledSpace = __DISABLED_SPACE__
}catch (e) {
  console.warn(e)
}
export default {
  name: "AuroraMusic",
  props: {
    app: {}
  },
  data() {
    return {
      showMoreSongStatus: false,
      showMusicBoxStatus: false,
      playMusicStatus: false,
      //准备播放的所有歌曲数组
      currentMusicNum: 0,
      requestSuccessNum: 0,
      requestFailNum: 0,

      requestStatus: false,
      musicMapArr: [],

      currentMusicObject: {},

      musicRandomPlay: true,
      musicCyclePlay: false,
      musicOrderPlay: false,
      musicOperateClassArr: [],
      isMusicRandomPlay: true,
      baseURL: 'https://netease-cloud-music-api-teal-psi.vercel.app/',
      allSongIdArrLen: 0,
      isLoadingFinish: false,

      firstSongStatus: false,

      defaultCover: 'https://ooszy.cco.vin/img/blog-public/avatar.jpg',
      defaultSonger: '默认歌手',
      defaultSongName: '默认歌曲名',

      //判断当前是暂停还是播放，true表示暂停，false表示播放
      currentPauseStatus: true
    }
  },
  created() {
    if (serverUrl !== undefined && serverUrl !== "") {
      this.baseURL = serverUrl
    }

    if (disabledNetEaseMusic) {
      for (let i = 0; i < localSongs.songs.length; i++) {
        //使用本地的
        let musicMap = {
          picSrc: localSongs.songs[i].cover === undefined ? this.defaultCover : localSongs.songs[i].cover,
          songName: localSongs.songs[i].songName === undefined ? '暂时不知道歌名惹' : localSongs.songs[i].songName,
          singer: '',
          id: (i+1),
          songSrc: localSongs.songs[i].path
        }
        this.musicMapArr.push(musicMap)

        if (i === 0) {
          this.currentMusicObject = musicMap
        }

        if (i === localSongs.songs.length -1) {
          this.requestStatus = true
        }
      }
    }else {
      this.allSongIdArrLen = songIds.length
      if (showPlaylist) {
        //使用歌单的形式
        network.req({
          //baseURL: 'https://netease-cloud-music-api-teal-psi.vercel.app',
          baseURL: this.baseURL,
          url: '/playlist/detail',
          params: {
            id: playlist
          },
          songSrc: ''
        }).then((res) => {
          this.allSongIdArrLen = res.data.playlist.trackIds.length
          for (let i = 0; i < res.data.playlist.trackIds.length; i++) {
            this.requestSingleSong(res.data.playlist.trackIds[i].id)
          }
        })
      }else {
        for (let i = 0; i < songIds.length; i++) {
          this.requestSingleSong(songIds[i])
        }
      }
    }
  },
  mounted() {

    this.isLoadingFinish = true
    this.$refs["aurora-music-player"].addEventListener('ended', this.currentAudioFinish, false);

    setTimeout(() => {
      this.currentPauseStatus = this.$refs["aurora-music-player"].paused
      if (!this.currentPauseStatus) {
        //播放
        this.playMusicStatus = true
      }
    },3500)

    if (!disabledSpace) {
      window.addEventListener('keyup',this.keyListener)
    }
  },
  watch: {
    requestSuccessNum() {
      if ((this.requestSuccessNum + this.requestFailNum) === this.allSongIdArrLen) {
        this.requestStatus = true
      }
    },
    requestFailNum() {
      if ((this.requestSuccessNum + this.requestFailNum) === this.allSongIdArrLen) {
        this.requestStatus = true
      }
    },
    requestStatus() {
      //this.currentMusicObject = this.musicMapArr[0]
      this.currentMusicNum = 0
    },
    currentMusicObject() {
      console.warn(this.currentMusicObject)
      if (this.isLoadingFinish) {
        this.$refs["aurora-music-player"].src = this.musicMapArr[this.currentMusicNum].songSrc
      }
    },
    isLoadingFinish() {
      if (this.requestStatus) {
        this.$refs["aurora-music-player"].src = this.musicMapArr[this.currentMusicNum].songSrc
      }
    },
    currentMusicNum() {
      this.currentMusicObject = this.musicMapArr[this.currentMusicNum]
    }
  },
  methods: {
    requestSingleSong(songId) {
      network.req({
        baseURL: this.baseURL,
        url: '/song/url',
        params: {
          id: songId
        },
        songSrc: ''
      }).then((data) => {
        try {
          let src = data.data.data[0].url
          if (src === null) {
            this.requestFailNum++
            return
          }
        }catch (e) {
          this.requestFailNum++
          return
        }

        //这里的歌曲存在url
        network.req({
          baseURL: this.baseURL,
          url: '/song/detail',
          params: {
            ids: songId
          },
          songSrc: data.data.data[0].url
        }).then((data) => {
          this.requestSuccessNum++
          let picSrc = ''
          let songName = ''
          let singer = ''
          try {
            picSrc = data.data.songs[0].al.picUrl
            songName = data.data.songs[0].al.name
            singer = data.data.songs[0].ar[0].name
          }catch (e) {
            console.warn(e)
            picSrc = this.defaultCover
            songName = this.defaultSongName
            singer = this.defaultSonger
          }

          this.musicMapArr.push({
            picSrc: picSrc,
            songName,
            singer,
            id: data.data.songs[0].id,
            songSrc: data.src
          })

          if (this.requestSuccessNum === 1) {
            this.firstSongStatus = true
            this.currentMusicObject = {
              picSrc: picSrc,
              songName,
              singer,
              id: data.data.songs[0].id,
              songSrc: data.src
            }
          }
        }).catch((err) => {
          console.warn(err)
        })
      }).catch((err) => {
        console.warn(err)
      })
    },

    keyListener(e) {
      if (e.keyCode === 32) {
        this.playMusic()
      }
    },
    currentAudioFinish() {
      this.musicNext()
    },
    clickMusicPlay(e,index) {
      this.currentMusicNum = index
      this.autoPlay()
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    musicNext() {
      this.playMusicStatus = false
      //点击下一首
      if (this.isMusicRandomPlay) {
        //循环播放
        this.currentMusicNum = this.getRandomInt(0,this.musicMapArr.length)
        this.autoPlay()
        return
      }

      if ((this.currentMusicNum + 1) > this.musicMapArr.length -1) {
        this.currentMusicNum = 0
      }else {
        this.currentMusicNum++
      }
      this.autoPlay()
    },
    autoPlay() {
      setTimeout(() => {
        this.$refs["aurora-music-player"].play()
        this.playMusicStatus = true
      },400)
    },
    musicSetting() {
      this.isMusicRandomPlay = !this.isMusicRandomPlay
    },
    musicPre() {
      //点击下一首
      if (this.isMusicRandomPlay) {
        //循环播放
        this.currentMusicNum = this.getRandomInt(0,this.musicMapArr.length)
        this.autoPlay()
        return
      }

      if ((this.currentMusicNum - 1) < 0) {
        this.currentMusicNum = this.musicMapArr.length -1
      }else {
        this.currentMusicNum--
      }
      this.autoPlay()
    },
    playMusic() {
      if (!this.requestStatus) {
        if (!this.firstSongStatus) {
          return
        }
        this.playMusicStatus = false
      }


      if (this.playMusicStatus) {
        this.$refs["aurora-music-player"].pause()
        this.playMusicStatus = false
        //暂停
      }else {
        this.$refs["aurora-music-player"].play()
        this.playMusicStatus = true
      }
    },
    musicMouseenter() {
      this.showMusicBoxStatus = true
    },
    musicMouseLeave() {
      this.showMusicBoxStatus = false
      if (this.showMoreSongStatus) {
        this.showMoreSongStatus = !this.showMoreSongStatus
      }
    },
    showMoreSong() {
      this.showMoreSongStatus = !this.showMoreSongStatus
    }
  }
}
</script>
