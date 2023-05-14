<template>
  <div class="h-full">
    <n-card title="用户角色管理" class="h-full shadow-sm rounded-16px">
      <n-tabs type="line" animated>
				<n-tab-pane name="list" tab="列表">
					<n-space vertical>
						<n-grid x-gap="12" :cols="3" v-for="(item, index) in userRolePermissionInfoArr" :key="index">
							<n-gi>
								<n-card hoverable class="rounded-16px shadow-sm">
									<n-space justify="center">
										<n-space vertical>
											<n-tag v-if="item.userInfoArr.length !== 0" v-for="(userItem, userIndex) in item.userInfoArr"
														 :key="userIndex" :bordered="false" :type="getPermissionRandomTagType(userIndex)">
												{{userItem.username}}
											</n-tag>
											<n-tag v-else :bordered="false" :type="getPermissionRandomTagType(1)">
												无用户
											</n-tag>
										</n-space>
									</n-space>
								</n-card>
							</n-gi>
							<n-gi>
								<n-card hoverable class="rounded-16px shadow-sm">
									<n-space justify="center">
										<n-tag type="success" :bordered="false">
											{{item.roleInfo.name}}
										</n-tag>
									</n-space>
								</n-card>
							</n-gi>
							<n-gi>
								<n-card hoverable class="rounded-16px shadow-sm">
									<n-space justify="center">
										<n-space vertical>
											<n-tag v-if="item.permissionArr.length !== 0" v-for="(permissionItem, permissionIndex) in item.permissionArr"
														 :key="permissionIndex" :bordered="false" :type="getPermissionRandomTagType(permissionIndex)">
												{{permissionItem.name}}
											</n-tag>
											<n-tag v-else :bordered="false" :type="getPermissionRandomTagType(2)">
												无权限
											</n-tag>
										</n-space>
									</n-space>
								</n-card>
							</n-gi>
						</n-grid>
					</n-space>
				</n-tab-pane>
        <n-tab-pane name="userRole" tab="用户角色">
          <n-space vertical>
            <n-card hoverable title="选择用户" class="rounded-16px shadow-sm" size="small">
              <n-select v-model:value="selectedUserUidArr" multiple :options="allUserOption" />
            </n-card>

            <n-card hoverable title="选择角色" class="rounded-16px shadow-sm" size="small">
              <n-select v-model:value="selectedRoleUidArr" multiple :options="allRoleOption" />
              <template #footer>
                <n-space justify="end">
                  <n-button strong secondary tertiary round type="success" @click="handleBindRoleForUserAction"
                    >添加</n-button
                  >
                </n-space>
              </template>
            </n-card>
          </n-space>
        </n-tab-pane>
        <n-tab-pane name="rolePermission" tab="角色权限">
          <n-space vertical>
            <n-card hoverable title="选择角色" class="rounded-16px shadow-sm" size="small">
              <n-select @update:value="handleSelectRoleAction" v-model:value="selectedRoleUidArr" multiple :options="allRoleOption" />
            </n-card>
            <n-card hoverable title="选择接口" class="rounded-16px shadow-sm" size="small">
							<n-transfer
								ref="transfer"
								v-model:value="transferInterfaceArr"
								:options="transferInterfaceOptions"
								:render-source-list="renderPermissionPathSourceList"
								source-filterable
							/>
              <template #footer>
                <n-space justify="end">
                  <n-button strong secondary tertiary round type="success" @click="handleBindPermissionForRoleAction"
                    >添加</n-button
                  >
                </n-space>
              </template>
            </n-card>
          </n-space>
        </n-tab-pane>
      </n-tabs>
    </n-card>
  </div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, h, onBeforeMount, ref} from 'vue';
import type {TransferRenderSourceList} from 'naive-ui';
import {NSpace, NTag, NTree} from 'naive-ui';
import type {SelectBaseOption} from 'naive-ui/es/select/src/interface';
import {permissionApi, roleApi, userApi} from '@/service';
import type {UserVo} from '@/bean/vo/admin/UserVo';
import {rolePermissionRelApi} from '@/service/api/admin/RolePermissionRelApi';
import type {PermissionVo} from '@/bean/vo/admin/PermissionVo';
import type {RoleVo} from '@/bean/vo/admin/RoleVo';
import {getRandomTagType, removeDuplicateElement} from "@/utils";
import {interfaceInfoApi} from "@/service/api/auth/interfaceInfoApi";

defineComponent({ name: 'Index' });

interface InterfaceTreeOption {
	label?: string,
	value?: string,
	children?: InterfaceTreeOption[],
	isInterfacePath?: boolean,
	requestMethod?: string,
	realLabel?: string,
	requestPath?: string,
	disabled?: boolean
}

interface ModuleServiceInterfaceProperties {
	moduleTagName?: string | null,
	interfaceArr?: {
		summary?: string | null,
		requestPath?: string | null,
		requestMethod?: string | null
	}[]
}

interface InterfaceProperties {
	serviceTagName: string | null,
	servicePathArr: Array<ModuleServiceInterfaceProperties> | []
}

interface PermissionRoleTreeOption {
  label?: string;
  value?: string;
  summary?: string;
}

interface UserRolePermissionInfo {
	roleInfo: RoleVo,
	userInfoArr: Array<UserVo>,
	permissionArr: Array<PermissionVo>
}

const allUserOption = ref<Array<SelectBaseOption>>([]);
const allRoleOption = ref<Array<SelectBaseOption>>([]);
const allPermissionOption = ref<Array<PermissionRoleTreeOption>>([]);
const selectedUserUidArr = ref<Array<string>>([]);
const selectedRoleUidArr = ref<Array<string>>([]);
const selectedPermissionUidArr = ref<Array<string>>([]);
const userInfoArr = ref<Array<UserVo>>([]);
const roleInfoArr = ref<Array<RoleVo>>([]);
const permissionInfoArr = ref<Array<PermissionVo>>([]);
const userRolePermissionInfoArr = ref<Array<UserRolePermissionInfo>>([])
const selectedRolePermissionPathArr = ref<Array<string>>([])

/** transferInterfaceOptions后端的所有接口选项 */
const transferInterfaceOptions = ref<Array<InterfaceTreeOption>>([])
/** 选中某个权限之后的值 */
const transferInterfaceArr = ref<Array<string>>([])
/** 渲染权限树的数组 */
const transferTreeOptions = ref<Array<InterfaceTreeOption>>([])

const getPermissionRandomTagType = computed(() => (index: number) => getRandomTagType())

async function scanAllInterfaceAction(allPermissionArr: Array<PermissionVo>): Promise<null> {
	transferInterfaceOptions.value = []
	transferInterfaceArr.value = []
	transferTreeOptions.value = []
	return new Promise((resolve, reject) => {
		interfaceInfoApi.queryListSwaggerConfig().then(result => {
			if (result.data) {
				result.data.urls?.forEach((v, index) => {
					const parentOneTreeOption: InterfaceTreeOption = {
						label: v.name!,
						value: v.name!,
						children: new Array<InterfaceTreeOption>(),
						isInterfacePath: false
					}
					interfaceInfoApi.querySingleSwaggerInterfaceInfo(v.name!).then(result1 => {
						if (!result1.tags) return
						const interfaceProperties :InterfaceProperties = {
							serviceTagName: v.name!,
							servicePathArr: []
						}
						result1.tags.forEach((tag, tagIndex) => {
							const moduleServiceInterfaceProperties :ModuleServiceInterfaceProperties = {
								moduleTagName: tag.name,
								interfaceArr: []
							}
							const parentTwoTreeOption: InterfaceTreeOption = {
								label: tag.name!,
								value: tag.name!,
								children: new Array<InterfaceTreeOption>(),
								isInterfacePath: false
							}
							moduleServiceInterfaceProperties.moduleTagName = tag.name
							result1.requestPaths?.forEach((pathInfo, pathInfoIndex) => {
								// @ts-ignore
								if (pathInfo.tags!.indexOf(tag.name) !== -1) {
									allPermissionArr.forEach((permission, permissionIndex) => {
										const pathTemp = pathInfo.requestMethod!.toUpperCase() + ":" + pathInfo.requestPath
										if (permission.path === pathTemp) {
											const parentThreeTreeOption: InterfaceTreeOption = {
												label: `${pathInfo.requestMethod!.toUpperCase()}-${pathInfo.summary}-${pathInfo.requestPath}`,
												value: permission.uid!,
												isInterfacePath: true,
												requestMethod: pathInfo.requestMethod!.toUpperCase(),
												realLabel: pathInfo.summary!,
												requestPath: pathInfo.requestPath!,
												disabled: false
											}
											transferInterfaceOptions.value.push(parentThreeTreeOption)
											parentTwoTreeOption.children?.push(parentThreeTreeOption)
											moduleServiceInterfaceProperties.interfaceArr?.push({
												summary: pathInfo.summary,
												requestMethod: pathInfo.requestMethod,
												requestPath: pathInfo.requestPath
											})
										}
										if ((index === result.data.urls!.length - 1) && (permissionIndex === allPermissionArr.length - 1)) {
											resolve(null)
										}
									})
								}else {

								}
							})
							// @ts-ignore
							interfaceProperties.servicePathArr.push(moduleServiceInterfaceProperties)
							parentOneTreeOption.children?.push(parentTwoTreeOption)
						})
						transferTreeOptions.value.push(parentOneTreeOption)
					})
				})
			}else {
				resolve(null)
			}
		})
	})
}

async function loadPermissionInfo(): Promise<null> {
	return new Promise((resolve, reject) => {
		// 查询某个角色所拥有的权限
		permissionApi.queryListDataByCondition({ pageSize: 10000 }).then(result => {
			if (result.data && result.data.result) {
				permissionInfoArr.value = result.data.result;
				result.data.result.forEach((v, index) => {
					allPermissionOption.value.push({
						label: v.path!,
						value: v.uid!,
						summary: v.name!
					});
					if (index === result.data.result!.length - 1) {
						resolve(null)
					}
				});
			}
		});
	})
}

async function packInterfaceAndPermissionPath() {
	await loadPermissionInfo()
	await scanAllInterfaceAction(permissionInfoArr.value)
}

async function handleSelectRoleAction(value: Array<string>) {
	await scanAllInterfaceAction(permissionInfoArr.value)
	rolePermissionRelApi.loadRolePermissionRelByRoleUid({roleUidArr: value}).then(result => {
		if (result.data) {
			transferTreeOptions.value.forEach(v => {
				if (v.children) {
					v.children.forEach(firstChildren => {
						if (firstChildren.children) {
							firstChildren.children.forEach(secondChildren => {
								result.data.forEach(permission => {
									const pathTemp = secondChildren.requestMethod?.toUpperCase() + ":" + secondChildren.requestPath
									if (permission.path === pathTemp) {
										secondChildren.disabled = true
										selectedRolePermissionPathArr.value.push(secondChildren.value!)
										transferInterfaceArr.value.push(secondChildren.value!)
									}
								})
							})
						}
					})
				}
			})
		}
	})
}

const loadUserRolePermissionInfo = () => {
  // 先查询role信息
	roleApi.queryListDataByCondition({pageSize: 10000}).then(result => {
		if (result.data && result.data.result) {
			// 查询此角色的用户
			result.data.result.forEach(v => {
				rolePermissionRelApi.loadPermissionByRoleName({roleNameArr: [v.name!]}).then(roleResult => {
					if (roleResult.data) {

						// 数据封装
						const userVoMaps = new Map<string, UserVo>();
						const permissionVoMaps = new Map<string, PermissionVo>();

						roleResult.data.map(userInfo => {
							const user: UserVo = {
								username: userInfo.username,
								uid: userInfo.userUid
							}
							return user;
						}).concat().forEach(v => {
							if (!userVoMaps.get(v.username!)) {
								userVoMaps.set(v.username!, v)
							}
						})
						const userVos = new Array<UserVo>()
						userVoMaps.forEach(v => {
							userVos.push(v)
						})

						const permissionSet = new Set<string>()
						roleResult.data.forEach(permissionInfo => {
							permissionSet.add(`${permissionInfo.permissionName!} - ${permissionInfo.path}`)
						})

						const permissionVos = new Array<PermissionVo>()
						permissionSet.forEach(v => {
							permissionVos.push({
								name: v
							})
						})

						userRolePermissionInfoArr.value.push({
							roleInfo: v,
							userInfoArr: userVos,
							permissionArr: permissionVos
						})
					}
				})
			})
		}
	})
}

const loadAllUserInfo = () => {
  userApi.queryListDataByCondition({ pageSize: 10000 }).then(result => {
    if (result.data && result.data.result) {
      userInfoArr.value = result.data.result;
      result.data.result.forEach(v => {
        allUserOption.value.push({
          label: v.username!,
          value: v.uid!
        });
      });
    }
  });

  roleApi.queryListDataByCondition({ pageSize: 10000 }).then(result => {
    if (result.data && result.data.result) {
      roleInfoArr.value = result.data.result;
      result.data.result.forEach(v => {
        allRoleOption.value.push({
          label: v.name!,
          value: v.uid!
        });
      });
    }
  });
};

function handleRenderLabel({ option }: { option: PermissionRoleTreeOption }) {
  const splits = option.label!.split(':');
  return h(
    NSpace,
    {
      justify: 'start'
    },
    {
      default: () =>
        Array.of(
          h(
            NTag,
            {
              type: 'success',
              bordered: false
            },
            {
              default: () => splits[0]
            }
          ),
          h(
            NTag,
            {
              type: 'info',
              bordered: false
            },
            {
              default: () => splits[1]
            }
          ),
          h(
            NTag,
            {
              type: 'error',
              bordered: false
            },
            {
              default: () => (option.summary ? option.summary : '')
            }
          )
        )
    }
  );
}

const renderPermissionPathSourceList: TransferRenderSourceList = function ({
																															 onCheck,
																															 pattern
																														 }) {
	return h(
		// @ts-ignore
		NTree,
		{
			checkboxPlacement: 'right',
			style: 'margin: 0 4px;',
			keyField: 'value',
			checkable: true,
			cascade: true,
			selectable: false,
			blockLine: true,
			checkOnClick: true,
			data: transferTreeOptions.value,
			pattern,
			checkedKeys: transferInterfaceArr.value,
			onUpdateCheckedKeys: (checkedKeys: Array<string | number>) => {
				onCheck(checkedKeys)
			}
		})
}

const renderSourceList: TransferRenderSourceList = function ({ onCheck, pattern }) {
  return h(
		// @ts-ignore
    NTree,
    {
      checkboxPlacement: 'right',
      style: 'margin: 0 4px;',
      keyField: 'value',
      checkable: true,
      cascade: true,
      selectable: false,
      blockLine: true,
      checkOnClick: true,
      renderLabel: handleRenderLabel,
      data: allPermissionOption.value,
      pattern,
      checkedKeys: selectedPermissionUidArr.value,
      onUpdateCheckedKeys: (checkedKeys: Array<string | number>) => {
        onCheck(checkedKeys);
      }
    }
  );
};

const handleBindRoleForUserAction = () => {
  if (selectedUserUidArr.value.length === 0 || selectedRoleUidArr.value.length === 0) {
    window.$message?.error('请至少选择一个用户或者角色');
    return;
  }

  window.$dialog?.success({
    title: 'Tip',
    content: `你确定要为 ${selectedUserUidArr.value.length} 个用户增加选中的角色?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick() {
      // 因为后端只有批量为多个用户添加某个权限，所以需要遍历
      selectedRoleUidArr.value.forEach(v => {
        rolePermissionRelApi
          .batchInsertUserRole({ userUidArr: selectedUserUidArr.value, roleUidArr: [v] })
          .then(result => {
            if (!result.error) {
              window.$message?.success('操作成功');
            }
          });
      });
    }
  });
};

const handleBindPermissionForRoleAction = () => {
	const transferInterfaceArrTemp: Array<string> = transferInterfaceArr.value
		.filter(v => /^[0-9]+$/.test(v))
		.filter(v => selectedRolePermissionPathArr.value.indexOf(v) === -1)

  if (transferInterfaceArrTemp.length === 0 || selectedRoleUidArr.value.length === 0) {
    window.$message?.error('请至少选择一个接口或者角色');
    return;
  }

  window.$dialog?.success({
    title: 'Tip',
    content: `你确定要为 ${selectedRoleUidArr.value.length} 个角色增加选中的接口?`,
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick() {
      // 因为后端只有批量为多个用户添加某个权限，所以需要遍历
			rolePermissionRelApi.batchInsertRolePermission({ roleUidArr: selectedRoleUidArr.value, permissionUidArr: transferInterfaceArrTemp }).then(result => {
				if (!result.error) {
					window.$message?.success('操作成功');
				}
			});
    }
  });
};

onBeforeMount(() => {
  loadAllUserInfo();
	loadUserRolePermissionInfo()
	packInterfaceAndPermissionPath()
});
</script>

<style scoped></style>
