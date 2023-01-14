import {useClipboard} from "@vueuse/core";

const { copy, isSupported } = useClipboard();

function handleCopy(copyContent: string) {
  if (!isSupported) {
    window.$message?.error('您的浏览器不支持Clipboard API');
    return;
  }
  if (!copyContent) {
    window.$message?.error('请输入要复制的内容');
    return;
  }
  copy(copyContent);
  window.$message?.success(`复制成功：${copyContent}`);
}

export const copyContent = (copyContent: string | null | undefined) => {
  if (!copyContent) {
    handleCopy('无拷贝内容')
  }else {
    handleCopy(copyContent)
  }
}
