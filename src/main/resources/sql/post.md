sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=post.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=post.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=post.CR_BY) as crByName from POST_T post where  #use("condition")#

sample$count
===
    select count(1) from POST_T post where #use("condition")#

cols
===
	post.CR_AT,post.PARENT_ID,post.POST_CODE,post.DE_BY,post.DE_AT,post.UP_AT,post.POST_NAME,post.ID,post.STATUS,post.UP_BY,post.CR_BY

updateSample
===

	post.CR_AT=#crAt#,post.PARENT_ID=#parentId#,post.POST_CODE=#postCode#,post.DE_BY=#deBy#,post.DE_AT=#deAt#,post.UP_AT=#upAt#,post.POST_NAME=#postName#,post.ID=#id#,post.STATUS=#status#,post.UP_BY=#upBy#,post.CR_BY=#crBy#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(crAt)){
	 and post.CR_AT=#crAt#
	@}
	@if(!isEmpty(parentId)){
	 and post.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(postCode)){
	 and post.POST_CODE=#postCode#
	@}
	@if(!isEmpty(deBy)){
	 and post.DE_BY=#deBy#
	@}
	@if(!isEmpty(deAt)){
	 and post.DE_AT=#deAt#
	@}
	@if(!isEmpty(upAt)){
	 and post.UP_AT=#upAt#
	@}
	@if(!isEmpty(postName)){
	 and post.POST_NAME=#postName#
	@}
	@if(!isEmpty(id)){
	 and post.ID=#id#
	@}
	@if(!isEmpty(status)){
	 and post.STATUS=#status#
	@}
	@if(!isEmpty(upBy)){
	 and post.UP_BY=#upBy#
	@}
	@if(!isEmpty(crBy)){
	 and post.CR_BY=#crBy#
	@}



