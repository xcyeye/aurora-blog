<template>
	<div>
		<draggable
			class="dragArea"
			tag="ul"
			@change="handleChangeDraggableAction"
			:list="navbarInfo"
			:animation="400"
			:group="{pull: true,name: 'navbar', put: true}"
			item-key="name"
		>
			<template #item="{ element }">
				<li class="list-group-item">
					<p>{{ element.name }}</p>
					<aurora-draggable :navbar-info="element.children"/>
				</li>
			</template>
		</draggable>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";
import draggable from 'vuedraggable'

defineComponent({name: 'AuroraDraggable'});

interface Prop {
	navbarInfo: Array<NavbarInfo>
}

const props = withDefaults(defineProps<Prop>(), {})
const emit = defineEmits(['handleFinallyNavbarData'])

const handleChangeDraggableAction = () => {
	emit('handleFinallyNavbarData', props.navbarInfo)
}
</script>

<style scoped lang="css">

</style>
