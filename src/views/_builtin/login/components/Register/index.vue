<template>
  <n-form ref="formRef" :model="model" :rules="rules" size="large" :show-label="false">
    <!--<n-form-item path="phone">-->
    <!--  <n-input v-model:value="model.phone" placeholder="手机号码" />-->
    <!--</n-form-item>-->
    <n-form-item path="username">
			<n-input round v-model:value="model.username" show-password-on="click" placeholder="用户名" />
    </n-form-item>
    <n-form-item path="pwd">
      <n-input round v-model:value="model.pwd" type="password" show-password-on="click" placeholder="密码" />
    </n-form-item>
    <n-form-item path="confirmPwd">
      <n-input round v-model:value="model.confirmPwd" type="password" show-password-on="click" placeholder="确认密码" />
    </n-form-item>
    <n-space :vertical="true" :size="18">
      <!--<login-agreement v-model:value="agreement" />-->
      <n-button type="primary" size="large" :block="true" :round="true" @click="handleSubmit">确定</n-button>
      <n-button size="large" :block="true" :round="true" @click="toLoginModule('pwd-login')">返回</n-button>
    </n-space>
  </n-form>
</template>

<script lang="ts" setup>
import { reactive, ref, toRefs } from 'vue';
import type { FormInst, FormRules } from 'naive-ui';
import { useRouterPush } from '@/composables';
import { useSmsCode } from '@/hooks';
import { formRules, getConfirmPwdRule } from '@/utils';
import {userApi} from "@/service";
import {User} from "@/theme/pojo/admin/User";

const { toLoginModule } = useRouterPush();
const { label, isCounting, loading: smsLoading, start } = useSmsCode();

const formRef = ref<HTMLElement & FormInst>();

const model = reactive({
  username: '',
  pwd: '',
  confirmPwd: ''
});

const rules: FormRules = {
  username: formRules.username,
  pwd: formRules.pwd,
  confirmPwd: getConfirmPwdRule(toRefs(model).pwd)
};

const agreement = ref(false);

function handleSmsCode() {
  start();
}

async function handleSubmit() {
  await formRef.value?.validate();
  // 在插入成功用户之后，使用该用户名和密码作为clientId和clientSecret
	const registerUserInfo: User = {
		username: model.username,
		password: model.pwd
	}
	userApi.insertData(registerUserInfo).then(result => {
		if (!result.error) {
			window.$message?.success('注册成功 o(￣▽￣)ｄ')
		}
	})
}
</script>

<style scoped></style>
