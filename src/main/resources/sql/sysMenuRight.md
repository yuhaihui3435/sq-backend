sample
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.UP_BY) as upByName ,
    (select SM_NAME from SYS_MENU_RIGHT_T where id=sysMenuRight.parent_id) as parentName
	from SYS_MENU_RIGHT_T sysMenuRight where  #use("condition")#

special
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=sysMenuRight.UP_BY) as upByName,
	(select SM_NAME from SYS_MENU_RIGHT_T where id=sysMenuRight.parent_id) as parentName
	from SYS_MENU_RIGHT_T sysMenuRight where  #use("scondition")#


sample$count
===
    select count(1) from SYS_MENU_RIGHT_T sysMenuRight where #use("condition")#

special$count
===
    select count(1) from SYS_MENU_RIGHT_T sysMenuRight where #use("scondition")#


cols
===
	sysMenuRight.DE_AT,sysMenuRight.DISPLAY,sysMenuRight.UP_AT,sysMenuRight.DE_BY,sysMenuRight.CR_AT,sysMenuRight.SM_NAME,sysMenuRight.QN,sysMenuRight.STATUS,sysMenuRight.CR_BY,sysMenuRight.SM_URI,sysMenuRight.UP_BY,sysMenuRight.PARENT_ID,sysMenuRight.SM_ICON,sysMenuRight.SM_CODE,sysMenuRight.ID

updateSample
===

	sysMenuRight.DE_AT=#deAt#,sysMenuRight.DISPLAY=#display#,sysMenuRight.UP_AT=#upAt#,sysMenuRight.DE_BY=#deBy#,sysMenuRight.CR_AT=#crAt#,sysMenuRight.SM_NAME=#smName#,sysMenuRight.QN=#qn#,sysMenuRight.STATUS=#status#,sysMenuRight.CR_BY=#crBy#,sysMenuRight.SM_URI=#smUri#,sysMenuRight.UP_BY=#upBy#,sysMenuRight.PARENT_ID=#parentId#,sysMenuRight.SM_ICON=#smIcon#,sysMenuRight.SM_CODE=#smCode#

queryMenuByRole
=== 
    select distinct rm.*,m.* from role_menu_t rm left join sys_menu_right_t m on rm.sys_menu_id=m.id where rm.sys_role_id in (
     @for(id in ids){
     #id#  #text(idLP.last?"":"," )#
     @}
     )
      and m.id is not null
      and rm.de_at is null
     order by m.qn

queryMenuByUser
=== 
    select distinct rm.*,m.* from user_menu_t rm left join sys_menu_right_t m on rm.sys_menu_id=m.id 
    where rm.sys_user_id=#userId#
      and m.id is not null
      and rm.de_at is null
      and m.display='0'
     order by m.qn

condition
===

	1 = 1 and DE_AT is null
    @if(!isEmpty(id)){
     and sysMenuRight.ID=#id#
    @}
	@if(!isEmpty(deAt)){
	 and sysMenuRight.DE_AT=#deAt#
	@}
	@if(!isEmpty(display)){
	 and sysMenuRight.DISPLAY=#display#
	@}
	@if(!isEmpty(upAt)){
	 and sysMenuRight.UP_AT=#upAt#
	@}
	@if(!isEmpty(deBy)){
	 and sysMenuRight.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and sysMenuRight.CR_AT=#crAt#
	@}
	@if(!isEmpty(smName)){
	 and sysMenuRight.SM_NAME=#smName#
	@}
	@if(!isEmpty(qn)){
	 and sysMenuRight.QN=#qn#
	@}
	@if(!isEmpty(status)){
	 and sysMenuRight.STATUS=#status#
	@}
	@if(!isEmpty(crBy)){
	 and sysMenuRight.CR_BY=#crBy#
	@}
	@if(!isEmpty(smUri)){
	 and sysMenuRight.SM_URI=#smUri#
	@}
	@if(!isEmpty(upBy)){
	 and sysMenuRight.UP_BY=#upBy#
	@}
	@if(!isEmpty(parentId)){
	 and sysMenuRight.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(smIcon)){
	 and sysMenuRight.SM_ICON=#smIcon#
	@}
	@if(!isEmpty(smCode)){
	 and sysMenuRight.SM_CODE=#smCode#
	@}

scondition
===

	1 = 1 and DE_AT is null
    @if(!isEmpty(id)){
     and sysMenuRight.ID=#id#
    @}
	@if(!isEmpty(deAt)){
	 and sysMenuRight.DE_AT=#deAt#
	@}
	@if(!isEmpty(display)){
	 and sysMenuRight.DISPLAY=#display#
	@}
	@if(!isEmpty(upAt)){
	 and sysMenuRight.UP_AT=#upAt#
	@}
	@if(!isEmpty(deBy)){
	 and sysMenuRight.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and sysMenuRight.CR_AT=#crAt#
	@}
	@if(!isEmpty(smName)){
	 and (sysMenuRight.SM_NAME like #'%'+smName+'%'# or sysMenuRight.SM_CODE like #'%'+smName+'%'# or sysMenuRight.SM_URI like #'%'+smName+'%'#)
	@}
	@if(!isEmpty(qn)){
	 and sysMenuRight.QN=#qn#
	@}
	@if(!isEmpty(status)){
	 and sysMenuRight.STATUS=#status#
	@}
	@if(!isEmpty(crBy)){
	 and sysMenuRight.CR_BY=#crBy#
	@}
	@if(!isEmpty(smUri)){
	 and sysMenuRight.SM_URI=#smUri#
	@}
	@if(!isEmpty(upBy)){
	 and sysMenuRight.UP_BY=#upBy#
	@}
	@if(!isEmpty(parentId)){
	 and sysMenuRight.PARENT_ID=#parentId#
	@}else{
     and sysMenuRight.PARENT_ID IS NULL
    @}
	@if(!isEmpty(smIcon)){
	 and sysMenuRight.SM_ICON=#smIcon#
	@}
	@if(!isEmpty(smCode)){
	 and sysMenuRight.SM_CODE=#smCode#
	@}
    @if(!isEmpty(order!)){
     #order#
    @}else{
     order by CR_AT asc
    @}

