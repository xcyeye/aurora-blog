import { onMounted, onUnmounted, ref, watch } from 'vue';
export const useDarkMode = () => {
    const isDarkMode = ref(false);
    const updateDarkModeClass = (value = isDarkMode.value) => {
        // set `class="dark"` on `<html>` element
        const htmlEl = window === null || window === void 0 ? void 0 : window.document.querySelector('html');
        htmlEl === null || htmlEl === void 0 ? void 0 : htmlEl.classList.toggle('dark', value);
    };
    const mediaQuery = ref(null);
    const onMediaQueryChange = (event) => {
        isDarkMode.value = event.matches;
    };
    onMounted(() => {
        // get `prefers-color-scheme` media query and set the initial mode
        mediaQuery.value = window.matchMedia('(prefers-color-scheme: dark)');
        isDarkMode.value = mediaQuery.value.matches;
        // watch changes
        mediaQuery.value.addEventListener('change', onMediaQueryChange);
        watch(isDarkMode, updateDarkModeClass, { immediate: true });
    });
    onUnmounted(() => {
        var _a;
        (_a = mediaQuery.value) === null || _a === void 0 ? void 0 : _a.removeEventListener('change', onMediaQueryChange);
        updateDarkModeClass(false);
    });
    return isDarkMode;
};
