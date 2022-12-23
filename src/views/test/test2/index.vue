<template>
	<n-form ref="formRef" :model="model" :rules="rules">
		<n-form-item path="age" label="年龄">
			<n-input v-model:value="model.age" @keydown.enter.prevent />
		</n-form-item>
		<n-form-item path="password" label="密码">
			<n-input
				v-model:value="model.password"
				type="password"
				@input="handlePasswordInput"
				@keydown.enter.prevent
			/>
		</n-form-item>
		<n-form-item
			ref="rPasswordFormItemRef"
			first
			path="reenteredPassword"
			label="重复密码"
		>
			<n-input
				v-model:value="model.reenteredPassword"
				:disabled="!model.password"
				type="password"
				@keydown.enter.prevent
			/>
		</n-form-item>
		<n-button
			round
			type="primary"
			@click="handleValidateButtonClick"
		>
			验证
		</n-button>
	</n-form>

	<pre>{{ JSON.stringify(model, null, 2) }}
</pre>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import {
	FormInst,
	FormItemInst,
	FormItemRule,
	useMessage,
	FormRules
} from 'naive-ui'

interface ModelType {
	age: string | null
	password: string | null
	reenteredPassword: string | null
}

export default defineComponent({
	setup () {
		const formRef = ref<FormInst | null>(null)
		const rPasswordFormItemRef = ref<FormItemInst | null>(null)
		const message = useMessage()
		const modelRef = ref<ModelType>({
			age: null,
			password: null,
			reenteredPassword: null
		})
		function validatePasswordStartWith (
			rule: FormItemRule,
			value: string
		): boolean {
			return (
				!!modelRef.value.password &&
				modelRef.value.password.startsWith(value) &&
				modelRef.value.password.length >= value.length
			)
		}
		function validatePasswordSame (rule: FormItemRule, value: string): boolean {
			return value === modelRef.value.password
		}
		const rules: FormRules = {
			age: [
				{
					required: true,
					validator (rule: FormItemRule, value: string) {
						if (!value) {
							return new Error('需要年龄')
						} else if (!/^\d*$/.test(value)) {
							return new Error('年龄应该为整数')
						} else if (Number(value) < 18) {
							return new Error('年龄应该超过十八岁')
						}
						return true
					},
					trigger: ['input', 'blur']
				}
			],
			password: [
				{
					required: true,
					message: '请输入密码',
					trigger: 'blur'
				}
			],
			reenteredPassword: [
				{
					required: true,
					message: '请再次输入密码',
					trigger: ['input', 'blur']
				},
				{
					validator: validatePasswordStartWith,
					message: '两次密码输入不一致',
					trigger: 'input'
				},
				{
					validator: validatePasswordSame,
					message: '两次密码输入不一致',
					trigger: ['blur', 'password-input']
				}
			]
		}
		return {
			formRef,
			rPasswordFormItemRef,
			model: modelRef,
			rules,
			handlePasswordInput () {
				if (modelRef.value.reenteredPassword) {
					rPasswordFormItemRef.value?.validate({ trigger: 'password-input' })
				}
			},
			handleValidateButtonClick (e: MouseEvent) {
				console.log(/^\w{5,10}/.test('xcasdyeyeasdfa'));
				e.preventDefault()
				formRef.value?.validate((errors) => {
					if (!errors) {
						message.success('验证成功')
					} else {
						console.log(errors)
						message.error('验证失败')
					}
				})
			}
		}
	}
})
</script>
