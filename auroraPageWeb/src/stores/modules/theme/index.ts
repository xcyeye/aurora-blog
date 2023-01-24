import {defineStore} from 'pinia';
import {darkTheme} from 'naive-ui';


const themeState: Theme.Setting = {
  borderRadius: 23,
  fontColor: '#2c3e50',
  fontSize: '1rem',
  mobileOpenStatus: false
}
export const useThemeStore = defineStore('bean-store', {
  state: () => {
    return {
      themeState
    }
  },
  getters: {
    /** naiveUI的主题配置 */
    naiveThemeOverrides(state) {
      return {};
    },
    /** naive-ui暗黑主题 */
    naiveTheme(state) {
      return state.themeState.darkMode ? darkTheme : undefined;
    },
    currentTheme(state) {
      return state.themeState
    }
  },
  actions: {
    /** 重置theme状态 */
    resetThemeStore() {
      this.$reset();
    },
    setCurrentThemeStore(themeSetting: Theme.Setting) {
      this.themeState = themeSetting
    }
  }
});
