export const routeModel: Record<Auth.RoleType, AuthRoute.Route[]> = {
  super: [
    {
      name: 'dashboard',
      path: '/dashboard',
      component: 'basic',
      children: [
        {
          name: 'dashboard_analysis',
          path: '/dashboard/analysis',
          component: 'self',
          meta: {
            title: '分析页',
            requiresAuth: true,
            icon: 'icon-park-outline:analysis'
          }
        },
        {
          name: 'dashboard_workbench',
          path: '/dashboard/workbench',
          component: 'self',
          meta: {
            title: '工作台',
            requiresAuth: true,
            icon: 'icon-park-outline:workbench'
          }
        }
      ],
      meta: {
        title: '仪表盘',
        icon: 'mdi:monitor-dashboard',
        order: 3
      }
    },
		{
			name: 'user',
			path: '/user',
			component: 'basic',
			children: [
				{
					name: 'user_profile',
					path: '/user/profile',
					component: 'self',
					meta: {
						title: '账户',
						requiresAuth: true,
						icon: 'dashicons:universal-access'
					}
				},
				{
					name: 'user_manage',
					path: '/user/manage',
					component: 'self',
					meta: {
						title: '用户管理',
						requiresAuth: true,
						icon: 'cil:3d'
					}
				}
			],
			meta: {
				affix: true,
				title: '用户',
				icon: 'system-uicons:user-circle',
				order: 2
			}
		},
		{
			name: 'comment',
			path: '/comment',
			component: 'self',
			meta: {
				title: '评论',
				requiresAuth: true,
				singleLayout: 'basic',
				icon: 'ic:baseline-comment',
				order: 8
			}
		},
		{
			name: 'system',
			path: '/system',
			component: 'self',
			meta: {
				title: '系统设置',
				requiresAuth: true,
				hide: false,
				singleLayout: 'basic',
				icon: 'ic:twotone-settings-system-daydream',
				order: 0
			}
		},
		{
			name: 'siteSetting',
			path: '/siteSetting',
			component: 'basic',
			children: [
				{
					name: 'siteSetting_list',
					path: '/siteSetting/list',
					component: 'self',
					meta: {
						title: '列表',
						requiresAuth: true,
						icon: 'ic:round-filter-vintage'
					}
				},
				{
					name: 'siteSetting_setting',
					path: '/siteSetting/setting',
					component: 'self',
					meta: {
						title: '站点设置',
						requiresAuth: true,
						icon: 'fa:flickr'
					}
				},
				{
					name: 'siteSetting_navbar',
					path: '/siteSetting/navbar',
					component: 'self',
					meta: {
						title: 'navbar',
						requiresAuth: true,
						icon: 'ic:baseline-mode-edit'
					}
				}
			],
			meta: {
				title: '站点设置',
				icon: 'ic:round-bookmarks',
				order: 3
			}
		},
		{
			name: 'article',
			path: '/article',
			component: 'basic',
			children: [
				{
					name: 'article_bulletin',
					path: '/article/bulletin',
					component: 'self',
					meta: {
						title: '公告',
						requiresAuth: true,
						icon: 'ic:baseline-panorama-wide-angle-select'
					}
				},
				{
					name: 'article_category',
					path: '/article/category',
					component: 'self',
					meta: {
						title: '分类',
						requiresAuth: true,
						icon: 'cil:bookmark'
					}
				},
				{
					name: 'article_tag',
					path: '/article/tag',
					component: 'self',
					meta: {
						title: '标签',
						requiresAuth: true,
						icon: 'ic:round-filter-vintage'
					}
				},
				{
					name: 'article_edit',
					path: '/article/edit',
					component: 'self',
					meta: {
						title: '发布',
						requiresAuth: true,
						icon: 'ic:baseline-mode-edit'
					}
				},
				{
					name: 'article_friend-link',
					path: '/article/friend-link',
					component: 'self',
					meta: {
						title: '友情链接',
						requiresAuth: true,
						icon: 'ic:baseline-link'
					}
				},
				{
					name: 'article_manage',
					path: '/article/manage',
					component: 'self',
					meta: {
						title: '文章管理',
						requiresAuth: true,
						icon: 'ic:outline-hourglass-bottom'
					}
				},
				{
					name: 'article_share',
					path: '/article/share',
					component: 'self',
					meta: {
						title: '自留地',
						requiresAuth: true,
						icon: 'cil:3d'
					}
				}
			],
			meta: {
				title: '文章',
				icon: 'ic:round-bookmarks',
				order: 3
			}
		},
		{
			name: 'auth-server',
			path: '/auth-server',
			component: 'basic',
			children: [
				{
					name: 'auth-server_login',
					path: '/auth-server/login',
					component: 'self',
					meta: {
						title: '登录日志',
						requiresAuth: true,
						icon: 'ic:outline-trip-origin'
					}
				},
				{
					name: 'auth-server_oauth',
					path: '/auth-server/oauth',
					component: 'self',
					meta: {
						title: '秘钥',
						requiresAuth: true,
						icon: 'ic:outline-auto-mode'
					}
				}
			],
			meta: {
				title: '安全',
				icon: 'ic:baseline-curtains-closed',
				order: 3
			}
		},
		{
			name: 'file-center',
			path: '/file-center',
			component: 'basic',
			children: [
				{
					name: 'file-center_picture',
					path: '/file-center/picture',
					component: 'self',
					meta: {
						title: 'Picture',
						requiresAuth: true,
						icon: 'ic:twotone-broken-image'
					}
				},
				{
					name: 'file-center_all-file',
					path: '/file-center/all-file',
					component: 'self',
					meta: {
						title: '所有文件',
						requiresAuth: true,
						icon: 'ic:baseline-filter-drama'
					}
				}
			],
			meta: {
				title: '文件',
				icon: 'ic:round-file-present',
				order: 3
			}
		},
		{
			name: 'message-center',
			path: '/message-center',
			component: 'basic',
			children: [
				{
					name: 'message-center_email-log',
					path: '/message-center/email-log',
					component: 'self',
					meta: {
						title: '邮件日志',
						requiresAuth: true,
						icon: 'ic:round-panorama-vertical'
					}
				},
				{
					name: 'message-center_email-manage',
					path: '/message-center/email-manage',
					component: 'self',
					meta: {
						title: '邮箱管理',
						requiresAuth: true,
						icon: 'ic:sharp-mail'
					}
				},
				{
					name: 'message-center_mq-message',
					path: '/message-center/mq-message',
					component: 'self',
					meta: {
						title: 'MQ消息',
						requiresAuth: true,
						icon: 'ic:round-nest-cam-wired-stand'
					}
				},
				{
					name: 'message-center_send-mail',
					path: '/message-center/send-mail',
					component: 'self',
					meta: {
						title: '发送邮件',
						requiresAuth: true,
						icon: 'ic:outline-attach-email'
					}
				}
			],
			meta: {
				title: '消息中心',
				icon: 'ic:outline-message',
				order: 3
			}
		},
		{
			name: 'permission',
			path: '/permission',
			component: 'basic',
			children: [
				{
					name: 'permission_interface',
					path: '/permission/interface',
					component: 'self',
					meta: {
						title: '接口',
						requiresAuth: true,
						icon: 'ic:baseline-rtt'
					}
				},
				{
					name: 'permission_role',
					path: '/permission/role',
					component: 'self',
					meta: {
						title: '角色',
						requiresAuth: true,
						icon: 'ic:outline-clear-all'
					}
				},
				{
					name: 'permission_admin-router',
					path: '/permission/admin-router',
					component: 'self',
					meta: {
						title: '路由',
						requiresAuth: true,
						icon: 'ic:round-waterfall-chart'
					}
				},
				{
					name: 'permission_white-url',
					path: '/permission/white-url',
					component: 'self',
					meta: {
						title: '白名单',
						requiresAuth: true,
						icon: 'ic:baseline-hourglass-bottom'
					}
				},
				{
					name: 'permission_permission-manage',
					path: '/permission/permission-manage',
					component: 'self',
					meta: {
						title: '权限管理',
						requiresAuth: true,
						icon: 'ic:baseline-show-chart'
					}
				}
			],
			meta: {
				title: '权限',
				icon: 'ic:baseline-hub',
				order: 3
			}
		},
		{
			name: 'monitor',
			path: '/monitor',
			component: 'basic',
			children: [
				{
					name: 'monitor_nacos',
					path: '/monitor/nacos',
					component: 'self',
					meta: {
						title: 'Nacos',
						requiresAuth: true,
						icon: 'material-symbols:monitoring'
					}
				},
				{
					name: 'monitor_rabbitmq',
					path: '/monitor/rabbitmq',
					component: 'self',
					meta: {
						title: 'RabbitMQ',
						requiresAuth: true,
						icon: 'logos:rabbitmq-icon'
					}
				},
				{
					name: 'monitor_sentinel',
					path: '/monitor/sentinel',
					component: 'self',
					meta: {
						title: 'Sentinel',
						requiresAuth: true,
						icon: 'uil:monitor-heart-rate'
					}
				}
			],
			meta: {
				title: '监控',
				icon: 'eos-icons:monitoring',
				order: 9
			}
		},
		{
			name: 'test',
			path: '/test',
			component: 'basic',
			children: [
				{
					name: 'test_test1',
					path: '/test/test1',
					component: 'self',
					meta: {
						title: 'test1',
						requiresAuth: true,
						icon: 'ic:baseline-security'
					}
				},
				{
					name: 'test_test2',
					path: '/test/test2',
					component: 'self',
					meta: {
						title: 'test2',
						requiresAuth: true,
						icon: 'carbon:user-role'
					}
				},
				{
					name: 'test_test3',
					path: '/test/test3',
					component: 'self',
					meta: {
						title: 'test3',
						requiresAuth: true,
						icon: 'carbon:user-role'
					}
				}
			],
			meta: {
				title: '测试',
				icon: 'carbon:cloud-service-management',
				order: 9
			}
		},
		{
			name: 'plugin',
			path: '/plugin',
			component: 'basic',
			children: [
				{
					name: 'plugin_charts',
					path: '/plugin/charts',
					component: 'multi',
					children: [
						{
							name: 'plugin_charts_echarts',
							path: '/plugin/charts/echarts',
							component: 'self',
							meta: {
								title: 'ECharts',
								requiresAuth: true,
								icon: 'simple-icons:apacheecharts'
							}
						},
						{
							name: 'plugin_charts_antv',
							path: '/plugin/charts/antv',
							component: 'self',
							meta: {
								title: 'AntV',
								requiresAuth: true,
								icon: 'simple-icons:antdesign'
							}
						}
					],
					meta: {
						title: '图表',
						icon: 'mdi:chart-areaspline'
					}
				},
				{
					name: 'plugin_map',
					path: '/plugin/map',
					component: 'self',
					meta: {
						title: '地图',
						requiresAuth: true,
						icon: 'mdi:map'
					}
				},
				{
					name: 'plugin_video',
					path: '/plugin/video',
					component: 'self',
					meta: {
						title: '视频',
						requiresAuth: true,
						icon: 'mdi:video'
					}
				},
				{
					name: 'plugin_editor',
					path: '/plugin/editor',
					component: 'multi',
					children: [
						{
							name: 'plugin_editor_quill',
							path: '/plugin/editor/quill',
							component: 'self',
							meta: {
								title: '富文本编辑器',
								requiresAuth: true,
								icon: 'mdi:file-document-edit-outline'
							}
						},
						{
							name: 'plugin_editor_markdown',
							path: '/plugin/editor/markdown',
							component: 'self',
							meta: {
								title: 'markdown编辑器',
								requiresAuth: true,
								icon: 'ri:markdown-line'
							}
						}
					],
					meta: {
						title: '编辑器',
						icon: 'icon-park-outline:editor'
					}
				},
				{
					name: 'plugin_swiper',
					path: '/plugin/swiper',
					component: 'self',
					meta: {
						title: 'Swiper插件',
						requiresAuth: true,
						icon: 'simple-icons:swiper'
					}
				},
				{
					name: 'plugin_copy',
					path: '/plugin/copy',
					component: 'self',
					meta: {
						title: '剪贴板',
						requiresAuth: true,
						icon: 'mdi:clipboard-outline'
					}
				},
				{
					name: 'plugin_icon',
					path: '/plugin/icon',
					component: 'self',
					meta: {
						title: '图标',
						requiresAuth: true,
						localIcon: 'custom-icon'
					}
				},
				{
					name: 'plugin_print',
					path: '/plugin/print',
					component: 'self',
					meta: {
						title: '打印',
						requiresAuth: true,
						icon: 'mdi:printer'
					}
				}
			],
			meta: {
				title: '插件示例',
				icon: 'clarity:plugin-line',
				order: 4
			}
		},
		{
			name: 'auth-demo',
			path: '/auth-demo',
			component: 'basic',
			children: [
				{
					name: 'auth-demo_permission',
					path: '/auth-demo/permission',
					component: 'self',
					meta: {
						title: '权限切换',
						requiresAuth: true,
						icon: 'ic:round-construction'
					}
				},
				{
					name: 'auth-demo_super',
					path: '/auth-demo/super',
					component: 'self',
					meta: {
						title: '超级管理员可见',
						requiresAuth: true,
						icon: 'ic:round-supervisor-profile'
					}
				}
			],
			meta: {
				title: '权限示例',
				icon: 'ic:baseline-security',
				order: 5
			}
		},
		{
			name: 'function',
			path: '/function',
			component: 'basic',
			children: [
				{
					name: 'function_tab',
					path: '/function/tab',
					component: 'self',
					meta: {
						title: 'Tab',
						requiresAuth: true,
						icon: 'ic:round-tab'
					}
				},
				{
					name: 'function_tab-detail',
					path: '/function/tab-detail',
					component: 'self',
					meta: {
						title: 'Tab Detail',
						requiresAuth: true,
						hide: true,
						activeMenu: 'function_tab',
						icon: 'ic:round-tab'
					}
				},
				{
					name: 'function_tab-multi-detail',
					path: '/function/tab-multi-detail',
					component: 'self',
					meta: {
						title: 'Tab Multi Detail',
						requiresAuth: true,
						hide: true,
						multiTab: true,
						activeMenu: 'function_tab',
						icon: 'ic:round-tab'
					}
				}
			],
			meta: {
				title: '功能',
				icon: 'icon-park-outline:all-application',
				order: 6
			}
		},
		{
			name: 'exception',
			path: '/exception',
			component: 'basic',
			children: [
				{
					name: 'exception_403',
					path: '/exception/403',
					component: 'self',
					meta: {
						title: '异常页403',
						requiresAuth: true,
						icon: 'ic:baseline-block'
					}
				},
				{
					name: 'exception_404',
					path: '/exception/404',
					component: 'self',
					meta: {
						title: '异常页404',
						requiresAuth: true,
						icon: 'ic:baseline-web-asset-off'
					}
				},
				{
					name: 'exception_500',
					path: '/exception/500',
					component: 'self',
					meta: {
						title: '异常页500',
						requiresAuth: true,
						icon: 'ic:baseline-wifi-off'
					}
				}
			],
			meta: {
				title: '异常页',
				icon: 'ant-design:exception-outlined',
				order: 7
			}
		},
		{
			name: 'multi-menu',
			path: '/multi-menu',
			component: 'basic',
			children: [
				{
					name: 'multi-menu_first',
					path: '/multi-menu/first',
					component: 'multi',
					children: [
						{
							name: 'multi-menu_first_second',
							path: '/multi-menu/first/second',
							component: 'self',
							meta: {
								title: '二级菜单',
								requiresAuth: true,
								icon: 'mdi:menu'
							}
						},
						{
							name: 'multi-menu_first_second-new',
							path: '/multi-menu/first/second-new',
							component: 'multi',
							children: [
								{
									name: 'multi-menu_first_second-new_third',
									path: '/multi-menu/first/second-new/third',
									component: 'self',
									meta: {
										title: '三级菜单',
										requiresAuth: true,
										icon: 'mdi:menu'
									}
								}
							],
							meta: {
								title: '二级菜单(有子菜单)',
								icon: 'mdi:menu'
							}
						}
					],
					meta: {
						title: '一级菜单',
						icon: 'mdi:menu'
					}
				}
			],
			meta: {
				title: '多级菜单',
				icon: 'carbon:menu',
				order: 8
			}
		},
  ],
  admin: [
    {
      name: 'dashboard',
      path: '/dashboard',
      component: 'basic',
      children: [
        {
          name: 'dashboard_analysis',
          path: '/dashboard/analysis',
          component: 'self',
          meta: {
            title: '分析页',
            requiresAuth: true,
            icon: 'icon-park-outline:analysis'
          }
        },
        {
          name: 'dashboard_workbench',
          path: '/dashboard/workbench',
          component: 'self',
          meta: {
            title: '工作台',
            requiresAuth: true,
            icon: 'icon-park-outline:workbench'
          }
        }
      ],
      meta: {
        title: '仪表盘',
        icon: 'mdi:monitor-dashboard',
        order: 1
      }
    },
    {
      name: 'plugin',
      path: '/plugin',
      component: 'basic',
      children: [
        {
          name: 'plugin_charts',
          path: '/plugin/charts',
          component: 'multi',
          children: [
            {
              name: 'plugin_charts_echarts',
              path: '/plugin/charts/echarts',
              component: 'self',
              meta: {
                title: 'ECharts',
                requiresAuth: true,
                icon: 'simple-icons:apacheecharts'
              }
            },
            {
              name: 'plugin_charts_antv',
              path: '/plugin/charts/antv',
              component: 'self',
              meta: {
                title: 'AntV',
                requiresAuth: true,
                icon: 'simple-icons:antdesign'
              }
            }
          ],
          meta: {
            title: '图表',
            icon: 'mdi:chart-areaspline'
          }
        },
        {
          name: 'plugin_map',
          path: '/plugin/map',
          component: 'self',
          meta: {
            title: '地图',
            requiresAuth: true,
            icon: 'mdi:map'
          }
        },
        {
          name: 'plugin_video',
          path: '/plugin/video',
          component: 'self',
          meta: {
            title: '视频',
            requiresAuth: true,
            icon: 'mdi:video'
          }
        },
        {
          name: 'plugin_editor',
          path: '/plugin/editor',
          component: 'multi',
          children: [
            {
              name: 'plugin_editor_quill',
              path: '/plugin/editor/quill',
              component: 'self',
              meta: {
                title: '富文本编辑器',
                requiresAuth: true,
                icon: 'mdi:file-document-edit-outline'
              }
            },
            {
              name: 'plugin_editor_markdown',
              path: '/plugin/editor/markdown',
              component: 'self',
              meta: {
                title: 'markdown编辑器',
                requiresAuth: true,
                icon: 'ri:markdown-line'
              }
            }
          ],
          meta: {
            title: '编辑器',
            icon: 'icon-park-outline:editor'
          }
        },
        {
          name: 'plugin_swiper',
          path: '/plugin/swiper',
          component: 'self',
          meta: {
            title: 'Swiper插件',
            requiresAuth: true,
            icon: 'simple-icons:swiper'
          }
        },
        {
          name: 'plugin_copy',
          path: '/plugin/copy',
          component: 'self',
          meta: {
            title: '剪贴板',
            requiresAuth: true,
            icon: 'mdi:clipboard-outline'
          }
        },
        {
          name: 'plugin_icon',
          path: '/plugin/icon',
          component: 'self',
          meta: {
            title: '图标',
            requiresAuth: true,
            localIcon: 'custom-icon'
          }
        },
        {
          name: 'plugin_print',
          path: '/plugin/print',
          component: 'self',
          meta: {
            title: '打印',
            requiresAuth: true,
            icon: 'mdi:printer'
          }
        }
      ],
      meta: {
        title: '插件示例',
        icon: 'clarity:plugin-line',
        order: 4
      }
    },
    {
      name: 'auth-demo',
      path: '/auth-demo',
      component: 'basic',
      children: [
        {
          name: 'auth-demo_permission',
          path: '/auth-demo/permission',
          component: 'self',
          meta: {
            title: '权限切换',
            requiresAuth: true,
            icon: 'ic:round-construction'
          }
        }
      ],
      meta: {
        title: '权限示例',
        icon: 'ic:baseline-security',
        order: 5
      }
    },
    {
      name: 'function',
      path: '/function',
      component: 'basic',
      children: [
        {
          name: 'function_tab',
          path: '/function/tab',
          component: 'self',
          meta: {
            title: 'Tab',
            requiresAuth: true,
            icon: 'ic:round-tab'
          }
        },
        {
          name: 'function_tab-detail',
          path: '/function/tab-detail',
          component: 'self',
          meta: {
            title: 'Tab Detail',
            requiresAuth: true,
            hide: true,
            activeMenu: 'function_tab',
            icon: 'ic:round-tab'
          }
        },
        {
          name: 'function_tab-multi-detail',
          path: '/function/tab-multi-detail',
          component: 'self',
          meta: {
            title: 'Tab Multi Detail',
            requiresAuth: true,
            hide: true,
            multiTab: true,
            activeMenu: 'function_tab',
            icon: 'ic:round-tab'
          }
        }
      ],
      meta: {
        title: '功能',
        icon: 'icon-park-outline:all-application',
        order: 6
      }
    },
    {
      name: 'exception',
      path: '/exception',
      component: 'basic',
      children: [
        {
          name: 'exception_403',
          path: '/exception/403',
          component: 'self',
          meta: {
            title: '异常页403',
            requiresAuth: true,
            icon: 'ic:baseline-block'
          }
        },
        {
          name: 'exception_404',
          path: '/exception/404',
          component: 'self',
          meta: {
            title: '异常页404',
            requiresAuth: true,
            icon: 'ic:baseline-web-asset-off'
          }
        },
        {
          name: 'exception_500',
          path: '/exception/500',
          component: 'self',
          meta: {
            title: '异常页500',
            requiresAuth: true,
            icon: 'ic:baseline-wifi-off'
          }
        }
      ],
      meta: {
        title: '异常页',
        icon: 'ant-design:exception-outlined',
        order: 7
      }
    },
    {
      name: 'multi-menu',
      path: '/multi-menu',
      component: 'basic',
      children: [
        {
          name: 'multi-menu_first',
          path: '/multi-menu/first',
          component: 'multi',
          children: [
            {
              name: 'multi-menu_first_second',
              path: '/multi-menu/first/second',
              component: 'self',
              meta: {
                title: '二级菜单',
                requiresAuth: true,
                icon: 'mdi:menu'
              }
            },
            {
              name: 'multi-menu_first_second-new',
              path: '/multi-menu/first/second-new',
              component: 'multi',
              children: [
                {
                  name: 'multi-menu_first_second-new_third',
                  path: '/multi-menu/first/second-new/third',
                  component: 'self',
                  meta: {
                    title: '三级菜单',
                    requiresAuth: true,
                    icon: 'mdi:menu'
                  }
                }
              ],
              meta: {
                title: '二级菜单(有子菜单)',
                icon: 'mdi:menu'
              }
            }
          ],
          meta: {
            title: '一级菜单',
            icon: 'mdi:menu'
          }
        }
      ],
      meta: {
        title: '多级菜单',
        icon: 'carbon:menu',
        order: 8
      }
    }
  ],
  user: [
		{
			name: 'dashboard',
			path: '/dashboard',
			component: 'basic',
			children: [
				{
					name: 'dashboard_analysis',
					path: '/dashboard/analysis',
					component: 'self',
					meta: {
						title: '分析页',
						requiresAuth: true,
						icon: 'icon-park-outline:analysis'
					}
				},
				{
					name: 'dashboard_workbench',
					path: '/dashboard/workbench',
					component: 'self',
					meta: {
						title: '工作台',
						requiresAuth: true,
						icon: 'icon-park-outline:workbench'
					}
				}
			],
			meta: {
				title: '仪表盘',
				icon: 'mdi:monitor-dashboard',
				order: 1
			}
		},
		{
			name: 'user',
			path: '/user',
			component: 'basic',
			children: [
				{
					name: 'user_profile',
					path: '/user/profile',
					component: 'self',
					meta: {
						title: '账户',
						requiresAuth: true,
						icon: 'dashicons:universal-access'
					}
				},
				{
					name: 'user_manage',
					path: '/user/manage',
					component: 'self',
					meta: {
						title: '用户管理',
						requiresAuth: true,
						icon: 'cil:3d'
					}
				}
			],
			meta: {
				title: '用户',
				icon: 'system-uicons:user-circle',
				order: 3
			}
		},
		{
			name: 'comment',
			path: '/comment',
			component: 'self',
			meta: {
				title: '评论',
				requiresAuth: true,
				singleLayout: 'basic',
				icon: 'ic:baseline-comment',
				order: 8
			}
		},
		{
			name: 'article',
			path: '/article',
			component: 'basic',
			children: [
				{
					name: 'article_bulletin',
					path: '/article/bulletin',
					component: 'self',
					meta: {
						title: '公告',
						requiresAuth: true,
						icon: 'ic:baseline-panorama-wide-angle-select'
					}
				},
				{
					name: 'article_category',
					path: '/article/category',
					component: 'self',
					meta: {
						title: '分类',
						requiresAuth: true,
						icon: 'cil:bookmark'
					}
				},
				{
					name: 'article_tag',
					path: '/article/tag',
					component: 'self',
					meta: {
						title: '标签',
						requiresAuth: true,
						icon: 'ic:round-filter-vintage'
					}
				},
				{
					name: 'article_edit',
					path: '/article/edit',
					component: 'self',
					meta: {
						title: '发布',
						requiresAuth: true,
						icon: 'ic:baseline-mode-edit'
					}
				},
				{
					name: 'article_friend-link',
					path: '/article/friend-link',
					component: 'self',
					meta: {
						title: '友情链接',
						requiresAuth: true,
						icon: 'ic:baseline-link'
					}
				},
				{
					name: 'article_manage',
					path: '/article/manage',
					component: 'self',
					meta: {
						title: '文章管理',
						requiresAuth: true,
						icon: 'ic:outline-hourglass-bottom'
					}
				},
				{
					name: 'article_share',
					path: '/article/share',
					component: 'self',
					meta: {
						title: '自留地',
						requiresAuth: true,
						icon: 'cil:3d'
					}
				}
			],
			meta: {
				title: '文章',
				icon: 'ic:round-bookmarks',
				order: 3
			}
		},
		{
			name: 'auth-server',
			path: '/auth-server',
			component: 'basic',
			children: [
				{
					name: 'auth-server_login',
					path: '/auth-server/login',
					component: 'self',
					meta: {
						title: '登录日志',
						requiresAuth: true,
						icon: 'ic:outline-trip-origin'
					}
				},
				{
					name: 'auth-server_oauth',
					path: '/auth-server/oauth',
					component: 'self',
					meta: {
						title: '秘钥',
						requiresAuth: true,
						icon: 'ic:outline-auto-mode'
					}
				}
			],
			meta: {
				title: '安全',
				icon: 'ic:baseline-curtains-closed',
				order: 3
			}
		},
		{
			name: 'file-center',
			path: '/file-center',
			component: 'basic',
			children: [
				{
					name: 'file-center_picture',
					path: '/file-center/picture',
					component: 'self',
					meta: {
						title: 'Picture',
						requiresAuth: true,
						icon: 'ic:twotone-broken-image'
					}
				},
				{
					name: 'file-center_all-file',
					path: '/file-center/all-file',
					component: 'self',
					meta: {
						title: '所有文件',
						requiresAuth: true,
						icon: 'ic:baseline-filter-drama'
					}
				}
			],
			meta: {
				title: '文件',
				icon: 'ic:round-file-present',
				order: 3
			}
		},
		{
			name: 'message-center',
			path: '/message-center',
			component: 'basic',
			children: [
				{
					name: 'message-center_email-log',
					path: '/message-center/email-log',
					component: 'self',
					meta: {
						title: '邮件日志',
						requiresAuth: true,
						icon: 'ic:round-panorama-vertical'
					}
				},
				{
					name: 'message-center_email-manage',
					path: '/message-center/email-manage',
					component: 'self',
					meta: {
						title: '邮箱管理',
						requiresAuth: true,
						icon: 'ic:sharp-mail'
					}
				},
				{
					name: 'message-center_mq-message',
					path: '/message-center/mq-message',
					component: 'self',
					meta: {
						title: 'MQ消息',
						requiresAuth: true,
						icon: 'ic:round-nest-cam-wired-stand'
					}
				},
				{
					name: 'message-center_send-mail',
					path: '/message-center/send-mail',
					component: 'self',
					meta: {
						title: '发送邮件',
						requiresAuth: true,
						icon: 'ic:outline-attach-email'
					}
				}
			],
			meta: {
				title: '消息中心',
				icon: 'ic:outline-message',
				order: 3
			}
		},
		{
			name: 'permission',
			path: '/permission',
			component: 'basic',
			children: [
				{
					name: 'permission_interface',
					path: '/permission/interface',
					component: 'self',
					meta: {
						title: '接口',
						requiresAuth: true,
						icon: 'ic:baseline-rtt'
					}
				},
				{
					name: 'permission_role',
					path: '/permission/role',
					component: 'self',
					meta: {
						title: '角色',
						requiresAuth: true,
						icon: 'ic:outline-clear-all'
					}
				},
				{
					name: 'permission_permission-manage',
					path: '/permission/permission-manage',
					component: 'self',
					meta: {
						title: '权限管理',
						requiresAuth: true,
						icon: 'ic:baseline-show-chart'
					}
				}
			],
			meta: {
				title: '权限',
				icon: 'ic:baseline-hub',
				order: 3
			}
		},
		{
			name: 'monitor',
			path: '/monitor',
			component: 'basic',
			children: [
				{
					name: 'monitor_nacos',
					path: '/monitor/nacos',
					component: 'self',
					meta: {
						title: 'Nacos',
						requiresAuth: true,
						icon: 'material-symbols:monitoring'
					}
				},
				{
					name: 'monitor_rabbitmq',
					path: '/monitor/rabbitmq',
					component: 'self',
					meta: {
						title: 'RabbitMQ',
						requiresAuth: true,
						icon: 'logos:rabbitmq-icon'
					}
				},
				{
					name: 'monitor_sentinel',
					path: '/monitor/sentinel',
					component: 'self',
					meta: {
						title: 'Sentinel',
						requiresAuth: true,
						icon: 'uil:monitor-heart-rate'
					}
				}
			],
			meta: {
				title: '监控',
				icon: 'eos-icons:monitoring',
				order: 9
			}
		},
		{
			name: 'test',
			path: '/test',
			component: 'basic',
			children: [
				{
					name: 'test_test1',
					path: '/test/test1',
					component: 'self',
					meta: {
						title: 'test1',
						requiresAuth: true,
						icon: 'ic:baseline-security'
					}
				},
				{
					name: 'test_test2',
					path: '/test/test2',
					component: 'self',
					meta: {
						title: 'test2',
						requiresAuth: true,
						icon: 'carbon:user-role'
					}
				},
				{
					name: 'test_test3',
					path: '/test/test3',
					component: 'self',
					meta: {
						title: 'test3',
						requiresAuth: true,
						icon: 'carbon:user-role'
					}
				}
			],
			meta: {
				title: '测试',
				icon: 'carbon:cloud-service-management',
				order: 9
			}
		},
		{
			name: 'plugin',
			path: '/plugin',
			component: 'basic',
			children: [
				{
					name: 'plugin_charts',
					path: '/plugin/charts',
					component: 'multi',
					children: [
						{
							name: 'plugin_charts_echarts',
							path: '/plugin/charts/echarts',
							component: 'self',
							meta: {
								title: 'ECharts',
								requiresAuth: true,
								icon: 'simple-icons:apacheecharts'
							}
						},
						{
							name: 'plugin_charts_antv',
							path: '/plugin/charts/antv',
							component: 'self',
							meta: {
								title: 'AntV',
								requiresAuth: true,
								icon: 'simple-icons:antdesign'
							}
						}
					],
					meta: {
						title: '图表',
						icon: 'mdi:chart-areaspline'
					}
				},
				{
					name: 'plugin_map',
					path: '/plugin/map',
					component: 'self',
					meta: {
						title: '地图',
						requiresAuth: true,
						icon: 'mdi:map'
					}
				},
				{
					name: 'plugin_video',
					path: '/plugin/video',
					component: 'self',
					meta: {
						title: '视频',
						requiresAuth: true,
						icon: 'mdi:video'
					}
				},
				{
					name: 'plugin_editor',
					path: '/plugin/editor',
					component: 'multi',
					children: [
						{
							name: 'plugin_editor_quill',
							path: '/plugin/editor/quill',
							component: 'self',
							meta: {
								title: '富文本编辑器',
								requiresAuth: true,
								icon: 'mdi:file-document-edit-outline'
							}
						},
						{
							name: 'plugin_editor_markdown',
							path: '/plugin/editor/markdown',
							component: 'self',
							meta: {
								title: 'markdown编辑器',
								requiresAuth: true,
								icon: 'ri:markdown-line'
							}
						}
					],
					meta: {
						title: '编辑器',
						icon: 'icon-park-outline:editor'
					}
				},
				{
					name: 'plugin_swiper',
					path: '/plugin/swiper',
					component: 'self',
					meta: {
						title: 'Swiper插件',
						requiresAuth: true,
						icon: 'simple-icons:swiper'
					}
				},
				{
					name: 'plugin_copy',
					path: '/plugin/copy',
					component: 'self',
					meta: {
						title: '剪贴板',
						requiresAuth: true,
						icon: 'mdi:clipboard-outline'
					}
				},
				{
					name: 'plugin_icon',
					path: '/plugin/icon',
					component: 'self',
					meta: {
						title: '图标',
						requiresAuth: true,
						localIcon: 'custom-icon'
					}
				},
				{
					name: 'plugin_print',
					path: '/plugin/print',
					component: 'self',
					meta: {
						title: '打印',
						requiresAuth: true,
						icon: 'mdi:printer'
					}
				}
			],
			meta: {
				title: '插件示例',
				icon: 'clarity:plugin-line',
				order: 4
			}
		},
		{
			name: 'auth-demo',
			path: '/auth-demo',
			component: 'basic',
			children: [
				{
					name: 'auth-demo_permission',
					path: '/auth-demo/permission',
					component: 'self',
					meta: {
						title: '权限切换',
						requiresAuth: true,
						icon: 'ic:round-construction'
					}
				},
				{
					name: 'auth-demo_super',
					path: '/auth-demo/super',
					component: 'self',
					meta: {
						title: '超级管理员可见',
						requiresAuth: true,
						icon: 'ic:round-supervisor-profile'
					}
				}
			],
			meta: {
				title: '权限示例',
				icon: 'ic:baseline-security',
				order: 5
			}
		},
		{
			name: 'function',
			path: '/function',
			component: 'basic',
			children: [
				{
					name: 'function_tab',
					path: '/function/tab',
					component: 'self',
					meta: {
						title: 'Tab',
						requiresAuth: true,
						icon: 'ic:round-tab'
					}
				},
				{
					name: 'function_tab-detail',
					path: '/function/tab-detail',
					component: 'self',
					meta: {
						title: 'Tab Detail',
						requiresAuth: true,
						hide: true,
						activeMenu: 'function_tab',
						icon: 'ic:round-tab'
					}
				},
				{
					name: 'function_tab-multi-detail',
					path: '/function/tab-multi-detail',
					component: 'self',
					meta: {
						title: 'Tab Multi Detail',
						requiresAuth: true,
						hide: true,
						multiTab: true,
						activeMenu: 'function_tab',
						icon: 'ic:round-tab'
					}
				}
			],
			meta: {
				title: '功能',
				icon: 'icon-park-outline:all-application',
				order: 6
			}
		},
		{
			name: 'exception',
			path: '/exception',
			component: 'basic',
			children: [
				{
					name: 'exception_403',
					path: '/exception/403',
					component: 'self',
					meta: {
						title: '异常页403',
						requiresAuth: true,
						icon: 'ic:baseline-block'
					}
				},
				{
					name: 'exception_404',
					path: '/exception/404',
					component: 'self',
					meta: {
						title: '异常页404',
						requiresAuth: true,
						icon: 'ic:baseline-web-asset-off'
					}
				},
				{
					name: 'exception_500',
					path: '/exception/500',
					component: 'self',
					meta: {
						title: '异常页500',
						requiresAuth: true,
						icon: 'ic:baseline-wifi-off'
					}
				}
			],
			meta: {
				title: '异常页',
				icon: 'ant-design:exception-outlined',
				order: 7
			}
		},
		{
			name: 'multi-menu',
			path: '/multi-menu',
			component: 'basic',
			children: [
				{
					name: 'multi-menu_first',
					path: '/multi-menu/first',
					component: 'multi',
					children: [
						{
							name: 'multi-menu_first_second',
							path: '/multi-menu/first/second',
							component: 'self',
							meta: {
								title: '二级菜单',
								requiresAuth: true,
								icon: 'mdi:menu'
							}
						},
						{
							name: 'multi-menu_first_second-new',
							path: '/multi-menu/first/second-new',
							component: 'multi',
							children: [
								{
									name: 'multi-menu_first_second-new_third',
									path: '/multi-menu/first/second-new/third',
									component: 'self',
									meta: {
										title: '三级菜单',
										requiresAuth: true,
										icon: 'mdi:menu'
									}
								}
							],
							meta: {
								title: '二级菜单(有子菜单)',
								icon: 'mdi:menu'
							}
						}
					],
					meta: {
						title: '一级菜单',
						icon: 'mdi:menu'
					}
				}
			],
			meta: {
				title: '多级菜单',
				icon: 'carbon:menu',
				order: 8
			}
		},
	]
};
