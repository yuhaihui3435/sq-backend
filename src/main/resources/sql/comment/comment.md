sample
===

	select #use("cols")# from COMMENT_T comment where  #use("condition")#

sample$count
===
    select count(1) from COMMENT_T comment where #use("condition")#

cols
===
	comment.COMMENT_BY,comment.ID,comment.TARGET_ID,comment.CONTENT,comment.COMMENT_AT,comment.PARENT_ID,comment.ANONYMOUS,comment.TYPE

updateSample
===

	comment.COMMENT_BY=#commentBy#,comment.ID=#id#,comment.TARGET_ID=#targetId#,comment.CONTENT=#content#,comment.COMMENT_AT=#commentAt#,comment.PARENT_ID=#parentId#,comment.ANONYMOUS=#anonymous#,comment.TYPE=#type#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(commentBy)){
	 and comment.COMMENT_BY=#commentBy#
	@}
	@if(!isEmpty(id)){
	 and comment.ID=#id#
	@}
	@if(!isEmpty(targetId)){
	 and comment.TARGET_ID=#targetId#
	@}
	@if(!isEmpty(content)){
	 and comment.CONTENT=#content#
	@}
	@if(!isEmpty(commentAt)){
	 and comment.COMMENT_AT=#commentAt#
	@}
	@if(!isEmpty(parentId)){
	 and comment.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(anonymous)){
	 and comment.ANONYMOUS=#anonymous#
	@}
	@if(!isEmpty(type)){
	 and comment.TYPE=#type#
	@}



