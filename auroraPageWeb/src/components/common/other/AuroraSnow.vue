<template>
	<canvas class="snow" id="snow"></canvas>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted} from "vue";

defineComponent({name: 'AuroraSnow'});

function ready(fn: Function) {
	if (document.readyState != 'loading'){
		fn();
	} else {
		document.addEventListener('DOMContentLoaded', fn);
	}
}

function makeSnow(el: HTMLCanvasElement) {
	let ctx = el.getContext('2d')!;
	let width = 0;
	let height = 0;
	let particles = [];
	
	let Particle = function() {
		this.x = this.y = this.dx = this.dy = 0;
		this.reset();
	}
	
	Particle.prototype.reset = function() {
		this.y = Math.random() * height;
		this.x = Math.random() * width;
		this.dx = (Math.random() * 1) - 0.5;
		this.dy = (Math.random() * 0.5) + 0.5;
	}
	
	function createParticles(count) {
		if (count != particles.length) {
			particles = [];
			for (let i = 0; i < count; i++) {
				particles.push(new Particle());
			}
		}
	}
	
	function onResize() {
		width = window.innerWidth;
		height = window.innerHeight;
		el.width = width;
		el.height = height;
		
		createParticles((width * height) / 10000);
	}
	
	function updateParticles() {
		ctx.clearRect(0, 0, width, height);
		ctx.fillStyle = '#f6f9fa';
		
		particles.forEach(function(particle) {
			particle.y += particle.dy;
			particle.x += particle.dx;
			
			if (particle.y > height) {
				particle.y = 0;
			}
			
			if (particle.x > width) {
				particle.reset();
				particle.y = 0;
			}
			
			ctx.beginPath();
			ctx.arc(particle.x, particle.y, 5, 0, Math.PI * 2, false);
			ctx.fill();
		});
		
		window.requestAnimationFrame(updateParticles);
	}
	
	onResize();
	updateParticles();
	
	window.addEventListener('resize', onResize);
}

onMounted(() => {
	ready(function() {
		let canvas = document.getElementById('snow') as HTMLCanvasElement;
		makeSnow(canvas);
	});
})
</script>

<style scoped>

</style>