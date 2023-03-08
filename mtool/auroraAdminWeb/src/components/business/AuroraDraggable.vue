<template>
	<div>
		<draggable
			class="dragArea"
			tag="ul"
			@change="handleChangeDraggableAction"
			:list="navbarInfo"
			:animation="400"
			:group="{pull: true, name: 'navbar', put: true}"
			item-key="name"
		>
			<template #item="{ element }">
				<li class="list-group-item">
					<n-text>{{ element.name }} <n-tag :bordered="false" style="border-radius: 16px" type="warning" @click="handleDeleteNavbar(element)">移除</n-tag></n-text>
					<aurora-draggable :navbar-info="element.children"/>
				</li>
			</template>
		</draggable>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount} from "vue";
import draggable from 'vuedraggable'
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";

defineComponent({name: 'AuroraDraggable'});

interface Prop {
	navbarInfo: Array<NavbarInfo>
}

const props = withDefaults(defineProps<Prop>(), {})
const emit = defineEmits(['handleFinallyNavbarData', 'handleDeleteNavbar'])

const handleChangeDraggableAction = (evt) => {
	console.log(evt);
	emit('handleFinallyNavbarData', props.navbarInfo)
}

const handleDeleteNavbar = (element: NavbarInfo) => {
	emit('handleDeleteNavbar', element)
}
</script>

<style scoped lang="css">

</style>
