<template>
	<div class="h-full">

		<n-card v-for="(item, index) in props.showCommentInfo.commentList" :key="index" class="h-full shadow-sm rounded-16px">
			<template #header>
				<n-space justify="start">
					<n-avatar
						round
						:size="55"
						:src="item.avatar"
					/>
					<n-space vertical>
						<n-space justify="start">
							<a :href="item.site" target="_blank">
								<n-text>{{item.username}}</n-text>
							</a>
						</n-space>
						<n-space vertical>
							<n-space justify="start">
								<n-text>{{item.content}}</n-text>
							</n-space>
							<n-space justify="start">
								<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									{{item.createTime}}
								</n-gradient-text>
								<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									回复
								</n-gradient-text>
							</n-space>
						</n-space>
					</n-space>
				</n-space>
			</template>
			<!--下面的都是子评论-->
			<div class="aurora-comment-son-box">
				<n-space vertical>
					<!--从这里开始是子评论开始-->
<!--					<n-space v-for="(sonItem, sonIndex) in item.sonCommentList" :key="sonIndex" vertical>
						&lt;!&ndash;头像 评论内容&ndash;&gt;
						<n-space justify="start">
							<a :href="sonItem.site" target="_blank">
								<n-avatar
									round
									:size="35"
									:src="sonItem.avatar"
								/>
							</a>
							<n-space vertical>
								&lt;!&ndash;这是用户名和评论信息，或者回复某某&ndash;&gt;
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										{{sonItem.username}}
									</n-gradient-text>
									<n-text style="color: #18191C">{{sonItem.content}}</n-text>
								</n-space>

								&lt;!&ndash;评论发布的日期 回复操作&ndash;&gt;
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										{{sonItem.createTime}}
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>-->
					<!--到这里结束是一个子评论结束-->

					<p></p>
					<p></p>
					<p></p>
					<!--另一个子评论-->
					<n-space v-for="(sonItem, sonIndex) in item.sonCommentList" :key="sonIndex" vertical>
						<!--头像 评论内容-->
						<n-space justify="start">
							<a :href="sonItem.site" target="_blank">
								<n-avatar
									round
									:size="35"
									:src="sonItem.avatar"
								/>
							</a>
							<n-space vertical>
								<!--这是用户名和评论信息，或者回复某某-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										{{sonItem.username}}
									</n-gradient-text>
									<n-text v-if="sonItem.replyCommentUid" style="color: #18191C">回复</n-text>
									<a v-if="sonItem.replyCommentUid" href="https://baidu.com" target="_blank">
										<n-text style="color: #008AC5">@{{item.username}}</n-text>
									</a>
									<n-text style="color: #18191C">: {{sonItem.content}}</n-text>
								</n-space>

								<!--评论发布的日期 回复操作-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										{{sonItem.createTime}}
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>









					<!--在所有子评论后面显示该父评论下共有多少条子评论-->
					<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
						共{{item.sonCommentList.length}}条回复，
						<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
							点击查看
						</n-gradient-text>
					</n-gradient-text>

					<!--如果点击回复之后，在多少条回复后面展示回复框-->
					<p></p>
					<p></p>
					<n-grid layout-shift-disabled :x-gap="12" :y-gap="12" :cols="10">
						<n-gi>
							<n-avatar
								class="aurora-comment-son-flex-avatar"
								round
								:size="45"
								src="http://127.0.0.1/aurora-upload/jpg/2022/12/wallhaven-wq3yop.jpg"
							/>
						</n-gi>
						<n-gi :span="8">
							<n-space vertical>
								<n-input rounded-16px type="textarea" :autosize="{minRows: 3}"
												 placeholder="回复 @aurora" class="aurora-comment-son-flex-textarea h-full"/>
								<n-space justify="start">
									<n-tag>asdkjf</n-tag>
									<n-tag>asdkjf</n-tag>
								</n-space>
							</n-space>
						</n-gi>
						<n-gi>
							<n-space justify="end">
								<n-button class="aurora-comment-son-flex-button" type="info">发布</n-button>
							</n-space>
						</n-gi>
					</n-grid>
				</n-space>
			</div>
		</n-card>








		<n-card class="h-full shadow-sm rounded-16px">
			<template #header>
				<n-space justify="start">
					<n-avatar
						round
						:size="55"
						src="http://127.0.0.1/aurora-upload/jpg/2022/12/wallhaven-wq3yop.jpg"
					/>
					<n-space vertical>
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-text>xcyeye</n-text>
							</a>
							<n-text>xcyeye</n-text>
							<n-text>xcyeye</n-text>
						</n-space>
						<n-space vertical>
							<n-space justify="start">
								<n-text>xcyeye</n-text>
							</n-space>
							<n-space justify="start">
								<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									2022-12-03 20:06:34
								</n-gradient-text>
								<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									回复
								</n-gradient-text>
							</n-space>
						</n-space>
					</n-space>
				</n-space>
			</template>
			<!--下面的都是子评论-->
			<div class="aurora-comment-son-box">
				<n-space vertical>

					<!--从这里开始是子评论开始-->
					<n-space vertical>
						<!--头像 评论内容-->
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-avatar
									round
									:size="35"
									src="http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-1kl36v1672147667306.png"
								/>
							</a>
							<n-space vertical>
								<!--这是用户名和评论信息，或者回复某某-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										aurora
									</n-gradient-text>
									<n-text style="color: #18191C">和我认识的一个朋友好像</n-text>
								</n-space>

								<!--评论发布的日期 回复操作-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										2022-12-03 20:06:34
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>
				<!--到这里结束是一个子评论结束-->

					<p></p>
					<p></p>
					<p></p>
				<!--另一个子评论-->
					<n-space vertical>
						<!--头像 评论内容-->
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-avatar
									round
									:size="35"
									src="http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-6oe337.png"
								/>
							</a>
							<n-space vertical>
								<!--这是用户名和评论信息，或者回复某某-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										程钦义
									</n-gradient-text>
									<n-text style="color: #18191C">回复</n-text>
									<a href="https://baidu.com" target="_blank">
										<n-text style="color: #008AC5">@哔哩哔哩官方</n-text>
									</a>
									<n-text style="color: #18191C">: 和我认识的一个朋友好像</n-text>
								</n-space>

								<!--评论发布的日期 回复操作-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										2022-12-03 20:06:34
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>









				<!--在所有子评论后面显示该父评论下共有多少条子评论-->
					<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
						共44条回复，
						<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
							点击查看
						</n-gradient-text>
					</n-gradient-text>

				<!--如果点击回复之后，在多少条回复后面展示回复框-->
					<p></p>
					<p></p>
					<n-grid layout-shift-disabled :x-gap="12" :y-gap="12" :cols="10">
						<n-gi>
							<n-avatar
								class="aurora-comment-son-flex-avatar"
								round
								:size="45"
								src="http://127.0.0.1/aurora-upload/jpg/2022/12/wallhaven-wq3yop.jpg"
							/>
						</n-gi>
						<n-gi :span="8">
							<n-space vertical>
								<n-input rounded-16px type="textarea" :autosize="{minRows: 3}"
												 placeholder="回复 @aurora" class="aurora-comment-son-flex-textarea h-full"/>
								<n-space justify="start">
									<n-tag>asdkjf</n-tag>
									<n-tag>asdkjf</n-tag>
								</n-space>
							</n-space>
						</n-gi>
						<n-gi>
							<n-space justify="end">
								<n-button class="aurora-comment-son-flex-button" type="info">发布</n-button>
							</n-space>
						</n-gi>
					</n-grid>
				</n-space>
			</div>
		</n-card>


	<!--另一个父评论开始，使用分割线隔开-->
	<!--	<n-divider />-->
		<n-card>
			<template #header>
				<n-space justify="start">
					<n-avatar
						round
						:size="55"
						src="http://127.0.0.1/aurora-upload/jpg/2022/12/wallhaven-wq3yop.jpg"
					/>
					<n-space vertical>
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-text>xcyeye</n-text>
							</a>
							<n-text>xcyeye</n-text>
							<n-text>xcyeye</n-text>
						</n-space>
						<n-space vertical>
							<n-space justify="start">
								<n-text>xcyeye</n-text>
							</n-space>
							<n-space justify="start">
								<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									2022-12-03 20:06:34
								</n-gradient-text>
								<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
									回复
								</n-gradient-text>
							</n-space>
						</n-space>
					</n-space>
				</n-space>
			</template>
			<!--下面的都是子评论-->
			<div class="aurora-comment-son-box">
				<n-space vertical>

					<!--从这里开始是子评论开始-->
					<n-space vertical>
						<!--头像 评论内容-->
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-avatar
									round
									:size="35"
									src="http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-1kl36v1672147667306.png"
								/>
							</a>
							<n-space vertical>
								<!--这是用户名和评论信息，或者回复某某-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										aurora
									</n-gradient-text>
									<n-text style="color: #18191C">和我认识的一个朋友好像</n-text>
								</n-space>

								<!--评论发布的日期 回复操作-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										2022-12-03 20:06:34
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>
					<!--到这里结束是一个子评论结束-->

					<p></p>
					<p></p>
					<p></p>
					<!--另一个子评论-->
					<n-space vertical>
						<!--头像 评论内容-->
						<n-space justify="start">
							<a href="https://aurora.xcye.xyz" target="_blank">
								<n-avatar
									round
									:size="35"
									src="http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-6oe337.png"
								/>
							</a>
							<n-space vertical>
								<!--这是用户名和评论信息，或者回复某某-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#61666D',to: '#61666D'}" :size="13" type="success">
										程钦义
									</n-gradient-text>
									<n-text style="color: #18191C">回复</n-text>
									<a href="https://baidu.com" target="_blank">
										<n-text style="color: #008AC5">@哔哩哔哩官方</n-text>
									</a>
									<n-text style="color: #18191C">: 和我认识的一个朋友好像</n-text>
								</n-space>

								<!--评论发布的日期 回复操作-->
								<n-space justify="start">
									<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										2022-12-03 20:06:34
									</n-gradient-text>
									<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
										回复
									</n-gradient-text>
								</n-space>
							</n-space>

						</n-space>
					</n-space>









					<!--在所有子评论后面显示该父评论下共有多少条子评论-->
					<n-gradient-text :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
						共44条回复，
						<n-gradient-text class="aurora-comment-mouse" :gradient="{from: '#9499A0',to: '#9499A0'}" :size="13" type="success">
							点击查看
						</n-gradient-text>
					</n-gradient-text>
				</n-space>
			</div>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, ref, watch} from "vue";

defineComponent({name: 'BlogComment'});

interface ShowCommentVo {
	/** 此评论对应的文章的uid，或者是友情链接等的uid **/
	articleUid?: string | null;

	/** 此评论对应的页面的类型 **/
	pageType?: number | null;

	/** 此页面的所有父评论的个数，也就是一级评论数 **/
	parentNodeNum?: number | null;

	/** 展示所有的评论 **/
	commentList?: Array<CommentDto> | null;
}

interface Props {
	showCommentInfo: ShowCommentVo
}

const props = defineProps<Props>()

const allShowCommentInfoArr = ref<ShowCommentVo>({})

const packageShowCommentInfoArr = () => {
  if (!props.showCommentInfo) return
	if (!props.showCommentInfo.parentNodeNum) return;
	// 先解析父级评论
	const parentCommentArr: Array<CommentDto> = []
	for (let i = 0; i < props.showCommentInfo.parentNodeNum; i++) {

	}
}

watch(() =>props.showCommentInfo, () => {

})
</script>

<style scoped lang="css">
.aurora-comment-mouse {
	cursor: pointer;
}

.aurora-comment-son-box {
	/*border: 1px solid red;*/
	height: 100%;
	width: auto;
	/*background-color: aquamarine;*/
	margin-left: 4.3rem;
}

.aurora-comment-son-flex {

}

.aurora-comment-son-flex-avatar {

}

.aurora-comment-son-flex-input {
	width: 35rem;
	background-color: #F1F2F3;
	border-radius: 10px;
}

.aurora-comment-son-flex-textarea {
	width: 100%;
}

.aurora-comment-son-flex-input:focus {
	background-color: rgba(1, 2, 4, 0);
	outline: none;
}

.aurora-comment-son-flex-button {

}
</style>
