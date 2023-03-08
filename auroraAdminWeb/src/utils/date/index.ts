export function formatTime(currentTime: number): string {
  return formatDate(currentTime);
}

function formatDate(currentTime: number): string {
  // 三目运算符
  const date = new Date(currentTime);

  // 年份
  const year: number = date.getFullYear();

  // 月份下标是0-11
  const month: any = date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;

  // 具体的天数
  const day: any = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate();

  // 小时
  const hour = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();

  // 分钟
  const minute = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();

  // 秒
  const second = date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds();

  // 返回数据格式
  return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
}
