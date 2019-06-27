sample
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=dept.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=dept.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=dept.DE_BY) as deByName,
	(select dept_name from dept_t where id=dept.parent_id) as parentName
	from DEPT_T dept where  #use("condition")#
	@orm.many({"id":"deptId"},"dept.selectSysRoleByDeptId","SysRole");

special
===

    select #use("cols")#,
    	(select SU_NAME from SYS_USER_T  where ID=dept.CR_BY) as crByName,
    	(select SU_NAME from SYS_USER_T  where ID=dept.UP_BY) as upByName,
    	(select SU_NAME from SYS_USER_T  where ID=dept.DE_BY) as deByName,
    	(select dept_name from dept_t where id=dept.parent_id) as parentName
    	from DEPT_T dept where  #use("scondition")#
    	

sample$count
===
    select count(1) from DEPT_T dept where #use("condition")# 
    


special$count
===
    select count(1) from DEPT_T dept where #use("scondition")#
    
cols
===
	dept.DE_AT,dept.DEPT_NAME,dept.QN,dept.UP_AT,dept.CR_BY,dept.UP_BY,dept.DE_BY,dept.CR_AT,dept.DEPT_CODE,dept.PARENT_ID,dept.STATUS,dept.ID,dept.RS_CHAIN

updateSample
===

	dept.DE_AT=#deAt#,dept.DEPT_NAME=#deptName#,dept.QN=#qn#,dept.UP_AT=#upAt#,dept.CR_BY=#crBy#,dept.UP_BY=#upBy#,dept.DE_BY=#deBy#,dept.CR_AT=#crAt#,dept.DEPT_CODE=#deptCode#,dept.PARENT_ID=#parentId#,dept.STATUS=#status#

scondition
===
    
    1 = 1 and DE_AT is null
    @if(!isEmpty(id)){
     and dept.ID=#id#
    @}
    @if(!isEmpty(deAt)){
     and dept.DE_AT=#deAt#
    @}
    @if(!isEmpty(deptName)){
     and (dept.DEPT_NAME like #'%'+deptName+'%'# or dept.DEPT_CODE like #'%'+deptName+'%'#)
    @}
    @if(!isEmpty(upAt)){
     and dept.UP_AT=#upAt#
    @}
    @if(!isEmpty(crBy)){
     and dept.CR_BY=#crBy#
    @}
    @if(!isEmpty(upBy)){
     and dept.UP_BY=#upBy#
    @}
    @if(!isEmpty(deBy)){
     and dept.DE_BY=#deBy#
    @}
    @if(!isEmpty(crAt)){
     and dept.CR_AT=#crAt#
    @}
    @if(!isEmpty(parentId)){
     and dept.PARENT_ID=#parentId#
    @}else{
            and dept.PARENT_ID IS NULL
            @}
    @if(!isEmpty(status)){
     and dept.STATUS=#status#
    @}
    and DE_AT IS NULL
    @if(!isEmpty(order!)){
     #order#
    @}else{
     order by CR_AT asc
    @}

condition
===

	1 = 1 and dept.DE_AT is null
	@if(!isEmpty(id)){
     and dept.ID=#id#
    @}
	@if(!isEmpty(deAt)){
	 and dept.DE_AT=#deAt#
	@}
	@if(!isEmpty(deptName)){
	 and dept.DEPT_NAME=#deptName#
	@}
	@if(!isEmpty(qn)){
	 and dept.QN=#qn#
	@}
	@if(!isEmpty(upAt)){
	 and dept.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and dept.CR_BY=#crBy#
	@}
	@if(!isEmpty(upBy)){
	 and dept.UP_BY=#upBy#
	@}
	@if(!isEmpty(deBy)){
	 and dept.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and dept.CR_AT=#crAt#
	@}
	@if(!isEmpty(deptCode)){
	 and dept.DEPT_CODE=#deptCode#
	@}
	@if(!isEmpty(parentId)){
	 and dept.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(status)){
	 and dept.STATUS=#status#
	@}
    @if(!isEmpty(order!)){
     #order#
    @}else{
     order by dept.UP_AT asc
    @}


rschain
===
    select a.id from (select t.id from dept_t t start with t.id=#id# connect by prior t.parent_id=t.id) a 
    where a.id!=#id#;
selectContainRelation
===
    select #use("cols")# from DEPT_T dept where  #use("condition")#
    @orm.many({"id":"deptId"},"dept.selectSysRoleByDeptId","SysRole");

selectSysRoleByDeptId    
===
    @var now=date();
    
    select dr.*,sr.*,
    (select SU_NAME from SYS_USER_T  where ID=dr.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=dr.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=dr.DE_BY) as deByName
    from DEPT_ROLE_T dr left join SYS_ROLE_T sr on dr.ROLE_ID=sr.ID where dr.DEPT_ID=#deptId#  and sr.STATUS='00'
    and to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')>=dr.EFFECT and (to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')<=dr.EXPIRED or dr.EXPIRED IS NULL) and dr.DE_BY is null and sr.DE_AT is null

selectBySysUserId
===
    @var now=date();
    select dept.*,
    (select SU_NAME from SYS_USER_T  where ID=dept.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=dept.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=dept.DE_BY) as deByName
    from DEPT_T dept left join USER_DEPT_T ud on dept.ID=ud.DEPT_ID where ud.SYS_USER_ID=#sysUserId# and dept.DE_AT is null 
    and dept.STATUS='00' and ud.DE_AT is null and to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')>=ud.EFFECT and (to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')<=ud.EXPIRED or ud.EXPIRED IS NULL)
    @orm.many({"id":"deptId"},"dept.selectSysRoleByDeptId","SysRole");
