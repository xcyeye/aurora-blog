import { searchIndex as searchIndexRaw } from '@internal/searchIndex';
import { ref } from 'vue';
export const searchIndex = ref(searchIndexRaw);
export const useSearchIndex = () => searchIndex;
if (import.meta.webpackHot || import.meta.hot) {
    __VUE_HMR_RUNTIME__.updateSearchIndex = (data) => {
        searchIndex.value = data;
    };
}
