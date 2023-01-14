import { defineStore } from 'pinia';
import { darkTheme } from 'naive-ui';

type ThemeState = Theme.Setting;

// @ts-ignore
const themeState: ThemeState = {}
export const useThemeStore = defineStore('bean-store', {
  state: (): ThemeState => themeState,
  getters: {
    /** naiveUI的主题配置 */
    naiveThemeOverrides(state) {
      return {};
    },
    /** naive-ui暗黑主题 */
    naiveTheme(state) {
      return state.darkMode ? darkTheme : undefined;
    },
    /** 页面动画模式 */
    pageAnimateMode(state) {
      return state.page.animate ? state.page.animateMode : undefined;
    }
  },
  actions: {
    /** 重置theme状态 */
    resetThemeStore() {
      this.$reset();
    }
  }
});
