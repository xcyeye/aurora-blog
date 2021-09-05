import { onMounted, onBeforeUnmount } from 'vue';
export const useHotKeys = ({ input, hotKeys, }) => {
    const onKeydown = (event) => {
        if (!input.value || hotKeys.value.length === 0)
            return;
        if (event.target === document.body && hotKeys.value.includes(event.key)) {
            input.value.focus();
            event.preventDefault();
        }
    };
    onMounted(() => {
        document.addEventListener('keydown', onKeydown);
    });
    onBeforeUnmount(() => {
        document.removeEventListener('keydown', onKeydown);
    });
};
