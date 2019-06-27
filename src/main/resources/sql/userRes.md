sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=userRes.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=userRes.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=userRes.CR_BY) as crByName from USER_RES_T userRes where  #use("condition")#

sample$count
===
    select count(1) from USER_RES_T userRes where #use("condition")#

cols
===
	userRes.UP_AT,userRes.SYS_RES_ID,userRes.SYS_USER_ID,userRes.CR_AT,userRes.EFFECT,userRes.ID,userRes.DE_BY,userRes.UP_BY,userRes.DE_AT,userRes.CR_BY,userRes.EXPIRED

updateSample
===

	userRes.UP_AT=#upAt#,userRes.SYS_RES_ID=#sysResId#,userRes.SYS_USER_ID=#sysUserId#,userRes.CR_AT=#crAt#,userRes.EFFECT=#effect#,userRes.ID=#id#,userRes.DE_BY=#deBy#,userRes.UP_BY=#upBy#,userRes.DE_AT=#deAt#,userRes.CR_BY=#crBy#,userRes.EXPIRED=#expired#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(upAt)){
	 and userRes.UP_AT=#upAt#
	@}
	@if(!isEmpty(sysResId)){
	 and userRes.SYS_RES_ID=#sysResId#
	@}
	@if(!isEmpty(sysUserId)){
	 and userRes.SYS_USER_ID=#sysUserId#
	@}
	@if(!isEmpty(crAt)){
	 and userRes.CR_AT=#crAt#
	@}
	@if(!isEmpty(effect)){
	 and userRes.EFFECT=#effect#
	@}
	@if(!isEmpty(id)){
	 and userRes.ID=#id#
	@}
	@if(!isEmpty(deBy)){
	 and userRes.DE_BY=#deBy#
	@}
	@if(!isEmpty(upBy)){
	 and userRes.UP_BY=#upBy#
	@}
	@if(!isEmpty(deAt)){
	 and userRes.DE_AT=#deAt#
	@}
	@if(!isEmpty(crBy)){
	 and userRes.CR_BY=#crBy#
	@}
	@if(!isEmpty(expired)){
	 and userRes.EXPIRED=#expired#
	@}



