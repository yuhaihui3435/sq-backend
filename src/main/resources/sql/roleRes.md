sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=roleRes.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=roleRes.UP_BY) as upByName from ROLE_RES_T roleRes where  #use("condition")#

sample$count
===
    select count(1) from ROLE_RES_T roleRes where #use("condition")#

cols
===
	roleRes.CR_BY,roleRes.SYS_ROLE_ID,roleRes.DE_AT,roleRes.ID,roleRes.SYS_RES_ID,roleRes.CR_AT,roleRes.UP_AT,roleRes.UP_BY

updateSample
===

	roleRes.CR_BY=#crBy#,roleRes.SYS_ROLE_ID=#sysRoleId#,roleRes.DE_AT=#deAt#,roleRes.ID=#id#,roleRes.SYS_RES_ID=#sysResId#,roleRes.CR_AT=#crAt#,roleRes.UP_AT=#upAt#,roleRes.UP_BY=#upBy#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(crBy)){
	 and roleRes.CR_BY=#crBy#
	@}
	@if(!isEmpty(sysRoleId)){
	 and roleRes.SYS_ROLE_ID=#sysRoleId#
	@}
	@if(!isEmpty(deAt)){
	 and roleRes.DE_AT=#deAt#
	@}
	@if(!isEmpty(id)){
	 and roleRes.ID=#id#
	@}
	@if(!isEmpty(sysResId)){
	 and roleRes.SYS_RES_ID=#sysResId#
	@}
	@if(!isEmpty(crAt)){
	 and roleRes.CR_AT=#crAt#
	@}
	@if(!isEmpty(upAt)){
	 and roleRes.UP_AT=#upAt#
	@}
	@if(!isEmpty(upBy)){
	 and roleRes.UP_BY=#upBy#
	@}



