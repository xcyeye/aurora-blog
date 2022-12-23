const component: AuthRoute.Route = {
  name: 'component',
  path: '/component',
  component: 'basic',
  children: [
    {
      name: 'component_table',
      path: '/component/table',
      component: 'self',
      meta: {
        title: '表格',
        requiresAuth: true,
        icon: 'mdi:table-large'
      }
    }
  ],
  meta: {
    title: '组件示例',
    icon: 'cib:app-store',
    order: 3
  }
};

export default component;
