sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=sysRole.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=sysRole.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=sysRole.DE_BY) as deByName from SYS_ROLE_T sysRole where  #use("condition")#

sample$count
===
    select count(1) from SYS_ROLE_T sysRole where #use("condition")#

cols
===
	sysRole.UP_BY,sysRole.ID,sysRole.CR_BY,sysRole.STATUS,sysRole.ROLE_CODE,sysRole.DE_BY,sysRole.DE_AT,sysRole.ROLE_NAME,sysRole.CR_AT,sysRole.UP_AT

updateSample
===

	sysRole.UP_BY=#upBy#,sysRole.ID=#id#,sysRole.CR_BY=#crBy#,sysRole.STATUS=#status#,sysRole.ROLE_CODE=#roleCode#,sysRole.DE_BY=#deBy#,sysRole.DE_AT=#deAt#,sysRole.ROLE_NAME=#roleName#,sysRole.CR_AT=#crAt#,sysRole.UP_AT=#upAt#

queryRoleByUserId
===

    select * from role_user_t ru left join sys_role_t sr on ru.sys_role_id=sr.id where ru.sys_user_id=#userId# and ru.de_at is null

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(upBy)){
	 and sysRole.UP_BY=#upBy#
	@}
	@if(!isEmpty(id)){
	 and sysRole.ID=#id#
	@}
	@if(!isEmpty(crBy)){
	 and sysRole.CR_BY=#crBy#
	@}
	@if(!isEmpty(status)){
	 and sysRole.STATUS=#status#
	@}
	@if(!isEmpty(roleCode)){
	 and sysRole.ROLE_CODE=#roleCode#
	@}
	@if(!isEmpty(deBy)){
	 and sysRole.DE_BY=#deBy#
	@}
	@if(!isEmpty(deAt)){
	 and sysRole.DE_AT=#deAt#
	@}
	@if(!isEmpty(roleName)){
	 and sysRole.ROLE_NAME=#roleName#
	@}
	@if(!isEmpty(crAt)){
	 and sysRole.CR_AT=#crAt#
	@}
	@if(!isEmpty(upAt)){
	 and sysRole.UP_AT=#upAt#
	@}



