sample
===

	select #use("cols")# from INTERACTIVE_RECORD_T interactiveRecord where  #use("condition")#

sample$count
===
    select count(1) from INTERACTIVE_RECORD_T interactiveRecord where #use("condition")#

cols
===
	interactiveRecord.ID,interactiveRecord.TARGET_ID,interactiveRecord.TYPE,interactiveRecord.USER_LOGIN_ID,interactiveRecord.TARGET_TYPE,interactiveRecord.CR_AT

updateSample
===

	interactiveRecord.ID=#id#,interactiveRecord.TARGET_ID=#targetId#,interactiveRecord.TYPE=#type#,interactiveRecord.USER_LOGIN_ID=#userLoginId#,interactiveRecord.TARGET_TYPE=#targetType#,interactiveRecord.CR_AT=#crAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
	 and interactiveRecord.ID=#id#
	@}
	@if(!isEmpty(targetId)){
	 and interactiveRecord.TARGET_ID=#targetId#
	@}
	@if(!isEmpty(type)){
	 and interactiveRecord.TYPE=#type#
	@}
	@if(!isEmpty(userLoginId)){
	 and interactiveRecord.USER_LOGIN_ID=#userLoginId#
	@}
	@if(!isEmpty(targetType)){
	 and interactiveRecord.TARGET_TYPE=#targetType#
	@}
	@if(!isEmpty(crAt)){
	 and interactiveRecord.CR_AT=#crAt#
	@}



