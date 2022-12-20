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
