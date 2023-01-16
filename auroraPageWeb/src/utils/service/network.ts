export const getHost = (): string => {
  if (!window) return ''
  return window.location.origin
}