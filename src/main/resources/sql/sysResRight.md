sample
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.DE_BY) as deByName,
	(select res_name from SYS_RES_RIGHT_T where id=sysResRight.parent_id) as parentName
	 from SYS_RES_RIGHT_T sysResRight where  #use("condition")#

special
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=sysResRight.DE_BY) as deByName,
    (select res_name from SYS_RES_RIGHT_T where id=sysResRight.parent_id) as parentName
	 from SYS_RES_RIGHT_T sysResRight where  #use("scondition")#

sample$count
===
    select count(1) from SYS_RES_RIGHT_T sysResRight where #use("condition")#

special$count
===
    select count(1) from SYS_RES_RIGHT_T sysResRight where #use("scondition")#

cols
===
	sysResRight.DE_AT,sysResRight.RES_NAME,sysResRight.UP_AT,sysResRight.CR_BY,sysResRight.UP_BY,sysResRight.STATUS,sysResRight.RES_URI,sysResRight.DE_BY,sysResRight.CR_AT,sysResRight.TYPE,sysResRight.PARENT_ID,sysResRight.CODE,sysResRight.ID

updateSample
===

	sysResRight.DE_AT=#deAt#,sysResRight.RES_NAME=#resName#,sysResRight.UP_AT=#upAt#,sysResRight.CR_BY=#crBy#,sysResRight.UP_BY=#upBy#,sysResRight.STATUS=#status#,sysResRight.RES_URI=#resUri#,sysResRight.DE_BY=#deBy#,sysResRight.CR_AT=#crAt#,sysResRight.TYPE=#type#,sysResRight.PARENT_ID=#parentId#,sysResRight.CODE=#code#

queryResByRole
=== 
    select distinct rm.*,m.* from role_res_t rm left join sys_res_right_t m on rm.sys_res_id=m.id where rm.sys_role_id in (
    @for(id in ids){
         #id#  #text(idLP.last?"":"," )#
    @}
    )
    and rm.de_at is null

queryResByUser
=== 
    select distinct rm.*,m.* from user_res_t rm left join sys_res_right_t m on rm.sys_res_id=m.id 
    where rm.sys_user_id=#userId#
    and rm.de_at is null
condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
     and sysResRight.ID=#id#
    @}
	@if(!isEmpty(deAt)){
	 and sysResRight.DE_AT=#deAt#
	@}
	@if(!isEmpty(resName)){
	 and sysResRight.RES_NAME=#resName#
	@}
	@if(!isEmpty(upAt)){
	 and sysResRight.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and sysResRight.CR_BY=#crBy#
	@}
	@if(!isEmpty(upBy)){
	 and sysResRight.UP_BY=#upBy#
	@}
	@if(!isEmpty(status)){
	 and sysResRight.STATUS=#status#
	@}
	@if(!isEmpty(resUri)){
	 and sysResRight.RES_URI=#resUri#
	@}
	@if(!isEmpty(deBy)){
	 and sysResRight.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and sysResRight.CR_AT=#crAt#
	@}
	@if(!isEmpty(type)){
	 and sysResRight.TYPE=#type#
	@}
	@if(!isEmpty(parentId)){
	 and sysResRight.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(code)){
	 and sysResRight.CODE=#code#
	@}


scondition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
     and sysResRight.ID=#id#
    @}
	@if(!isEmpty(deAt)){
	 and sysResRight.DE_AT=#deAt#
	@}
	@if(!isEmpty(resName)){
	 and (sysResRight.RES_NAME like #'%'+resName+'%'# or sysResRight.CODE like #'%'+resName+'%'# or sysResRight.RES_URI like #'%'+resName+'%'# or sysResRight.TYPE like #'%'+resName+'%'#)
	@}
	@if(!isEmpty(upAt)){
	 and sysResRight.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and sysResRight.CR_BY=#crBy#
	@}
	@if(!isEmpty(upBy)){
	 and sysResRight.UP_BY=#upBy#
	@}
	@if(!isEmpty(status)){
	 and sysResRight.STATUS=#status#
	@}
	@if(!isEmpty(resUri)){
	 and sysResRight.RES_URI=#resUri#
	@}
	@if(!isEmpty(deBy)){
	 and sysResRight.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and sysResRight.CR_AT=#crAt#
	@}
	@if(!isEmpty(type)){
	 and sysResRight.TYPE=#type#
	@}
	@if(!isEmpty(parentId)){
	 and sysResRight.PARENT_ID=#parentId#
	@}else{
     and sysResRight.PARENT_ID IS NULL
    @}
	@if(!isEmpty(code)){
	 and sysResRight.CODE=#code#
	@}
