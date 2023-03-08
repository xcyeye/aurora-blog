<template>
  <n-form ref="formRef" :model="model" :rules="rules" size="large" :show-label="false">
    <n-form-item path="email">
			<n-input round v-model:value="model.email" type="text" show-password-on="click" placeholder="请输入邮箱" />
    </n-form-item>
    <n-space :vertical="true" size="large">
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

const { toLoginModule } = useRouterPush();
const { label, isCounting, loading: smsLoading, start } = useSmsCode();

const formRef = ref<HTMLElement & FormInst>();

const model = reactive({
  email: ''
});

const rules: FormRules = {
  phone: formRules.phone,
  code: formRules.code,
  pwd: formRules.pwd,
	email: formRules.email,
};

function handleSmsCode() {
  start();
}

async function handleSubmit() {
  await formRef.value?.validate();
  window.$message?.success('验证成功!');
}
</script>

<style scoped></style>
