sample
===

	select #use("cols")# from USER_COLLECT_T userCollect where  #use("condition")#

sample$count
===
    select count(1) from USER_COLLECT_T userCollect where #use("condition")#

cols
===
	userCollect.USER_LOGIN_ID,userCollect.TARGET_ID,userCollect.ID,userCollect.CR_AT,userCollect.TYPE

updateSample
===

	userCollect.USER_LOGIN_ID=#userLoginId#,userCollect.TARGET_ID=#targetId#,userCollect.ID=#id#,userCollect.CR_AT=#crAt#,userCollect.TYPE=#type#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(userLoginId)){
	 and userCollect.USER_LOGIN_ID=#userLoginId#
	@}
	@if(!isEmpty(targetId)){
	 and userCollect.TARGET_ID=#targetId#
	@}
	@if(!isEmpty(id)){
	 and userCollect.ID=#id#
	@}
	@if(!isEmpty(crAt)){
	 and userCollect.CR_AT=#crAt#
	@}
	@if(!isEmpty(type)){
	 and userCollect.TYPE=#type#
	@}



