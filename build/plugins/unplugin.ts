import Components from 'unplugin-vue-components/vite';
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers';

export default function unplugin(viteEnv: ImportMetaEnv) {

  return [
    Components({
      dts: 'src/typings/components.d.ts',
      dirs: ['src/components'],
      extensions: ['vue'],
      resolvers: [
        NaiveUiResolver()
      ]
    })
  ];
}
