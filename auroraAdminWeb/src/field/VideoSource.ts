interface Track {
  src?: string,
  kink?: 'subtitles' | 'captions' | 'chapters' | 'descriptions' | 'metadata',
  srclang?: string,
  label?: string
}
interface VideoSource {
  src: string,
  type?: string,
  poster?: string,
  videoUrl?: string,
  videoPlatform?: string,
  tracks?: Array<Track>
}