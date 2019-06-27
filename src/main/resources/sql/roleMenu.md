sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=roleMenu.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=roleMenu.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=roleMenu.DE_BY) as deByName from ROLE_MENU_T roleMenu where  #use("condition")#

sample$count
===
    select count(1) from ROLE_MENU_T roleMenu where #use("condition")#

cols
===
	roleMenu.CR_AT,roleMenu.UP_AT,roleMenu.UP_BY,roleMenu.SYS_MENU_ID,roleMenu.STATUS,roleMenu.CR_BY,roleMenu.EXPIRED,roleMenu.DE_BY,roleMenu.EFFECT,roleMenu.SYS_ROLE_ID,roleMenu.DE_AT

updateSample
===

	roleMenu.CR_AT=#crAt#,roleMenu.UP_AT=#upAt#,roleMenu.UP_BY=#upBy#,roleMenu.SYS_MENU_ID=#sysMenuId#,roleMenu.STATUS=#status#,roleMenu.CR_BY=#crBy#,roleMenu.EXPIRED=#expired#,roleMenu.DE_BY=#deBy#,roleMenu.EFFECT=#effect#,roleMenu.SYS_ROLE_ID=#sysRoleId#,roleMenu.DE_AT=#deAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(crAt)){
	 and roleMenu.CR_AT=#crAt#
	@}
	@if(!isEmpty(upAt)){
	 and roleMenu.UP_AT=#upAt#
	@}
	@if(!isEmpty(upBy)){
	 and roleMenu.UP_BY=#upBy#
	@}
	@if(!isEmpty(sysMenuId)){
	 and roleMenu.SYS_MENU_ID=#sysMenuId#
	@}
	@if(!isEmpty(status)){
	 and roleMenu.STATUS=#status#
	@}
	@if(!isEmpty(crBy)){
	 and roleMenu.CR_BY=#crBy#
	@}
	@if(!isEmpty(expired)){
	 and roleMenu.EXPIRED=#expired#
	@}
	@if(!isEmpty(deBy)){
	 and roleMenu.DE_BY=#deBy#
	@}
	@if(!isEmpty(effect)){
	 and roleMenu.EFFECT=#effect#
	@}
	@if(!isEmpty(sysRoleId)){
	 and roleMenu.SYS_ROLE_ID=#sysRoleId#
	@}
	@if(!isEmpty(deAt)){
	 and roleMenu.DE_AT=#deAt#
	@}



