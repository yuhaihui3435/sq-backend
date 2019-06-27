sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=sysUser.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=sysUser.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=sysUser.CR_BY) as crByName,
	(select DEPT_NAME from DEPT_T where ID=sysUser.DEPT_ID and DE_AT is null) as ownDeptName
	from SYS_USER_T sysUser  where  #use("condition")#
    @orm.many({"id":"userId"},"sysUser.selectPostBySysUserId","Post");
sample$count
===
    select count(1) from SYS_USER_T sysUser  where #use("condition")#

cols
===
	sysUser.CR_AT,sysUser.DE_AT,sysUser.DE_BY,sysUser.ID,sysUser.EMAIL,sysUser.SU_NAME,sysUser.UP_BY,sysUser.CR_BY,sysUser.PWD,sysUser.UP_AT,sysUser.DEPT_ID,sysUser.TEL,sysUser.SALT,sysUser.AVATAR,sysUser.STATUS,sysUser.SU_CODE

updateSample
===

	sysUser.CR_AT=#crAt#,sysUser.DE_AT=#deAt#,sysUser.DE_BY=#deBy#,sysUser.ID=#id#,sysUser.EMAIL=#email#,sysUser.SU_NAME=#suName#,sysUser.UP_BY=#upBy#,sysUser.CR_BY=#crBy#,sysUser.PWD=#pwd#,sysUser.UP_AT=#upAt#,sysUser.DEPT_ID=#deptId#,sysUser.TEL=#tel#,sysUser.SALT=#salt#,sysUser.AVATAR=#avatar#,sysUser.STATUS=#status#,sysUser.SU_CODE=#suCode#

condition
===

	1 = 1 and sysUser.DE_AT is null 
	@if(!isEmpty(crAt)){
	 and sysUser.CR_AT=#crAt#
	@}
	@if(!isEmpty(deAt)){
	 and sysUser.DE_AT=#deAt#
	@}
	@if(!isEmpty(deBy)){
	 and sysUser.DE_BY=#deBy#
	@}
	@if(!isEmpty(id)){
	 and sysUser.ID=#id#
	@}
	@if(!isEmpty(email)){
	 and sysUser.EMAIL=#email#
	@}
	@if(!isEmpty(suName)){
	 and sysUser.SU_NAME=#suName#
	@}
	@if(!isEmpty(upBy)){
	 and sysUser.UP_BY=#upBy#
	@}
	@if(!isEmpty(crBy)){
	 and sysUser.CR_BY=#crBy#
	@}
	@if(!isEmpty(pwd)){
	 and sysUser.PWD=#pwd#
	@}
	@if(!isEmpty(upAt)){
	 and sysUser.UP_AT=#upAt#
	@}
	@if(!isEmpty(deptId)){
	 and sysUser.DEPT_ID=#deptId#
	@}
	@if(!isEmpty(tel)){
	 and sysUser.TEL=#tel#
	@}
	@if(!isEmpty(salt)){
	 and sysUser.SALT=#salt#
	@}
	@if(!isEmpty(avatar)){
	 and sysUser.AVATAR=#avatar#
	@}
	@if(!isEmpty(status)){
	 and sysUser.STATUS=#status#
	@}
	@if(!isEmpty(suCode)){
	 and sysUser.SU_CODE=#suCode#
	@}
	@if(!isEmpty(postId)){
     and sysUser.ID in (select SYS_USER_ID from USER_POST_T where POST_ID = #postId# and DE_AT IS NULL )
    @}

selectContainRelation
===
*查询用户所有关系数据，包括：角色，机构，职位，相关组件

    select #use("cols")# from SYS_USER_T sysUser where  #use("condition")#
    @orm.many({"id":"userId"},"sysUser.selectSysRoleBySysUserId","SysRole");
    @orm.many({"id":"userId"},"sysUser.selectDeptBySysUserId","Dept");
    @orm.many({"id":"userId"},"sysUser.selectPostBySysUserId","Post");
    @orm.many({"id":"userId"},"sysUser.selectWidgetBySysUserId","Widget");
    
selectDeptBySysUserId    
===
*查询用户关系机构

    @var now=date();
    select ud.*,d.*,
    (select SU_NAME from SYS_USER_T  where ID=ud.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=ud.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=ud.DE_BY) as deByName
    from USER_DEPT_T ud left join DEPT_T d on ud.DEPT_ID=d.ID where ud.SYS_USER_ID=#userId#  and d.STATUS='00'
    and to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')>=ud.EFFECT and (to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')<=ud.EXPIRED or ud.EXPIRED IS NULL) and d.DE_BY is null and ud.DE_AT is null
selectSysRoleBySysUserId    
===
*查询用户相关角色

    @var now=date();
    select ru.*,r.*,
    (select SU_NAME from SYS_USER_T  where ID=ru.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=ru.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=ru.DE_BY) as deByName
    from ROLE_USER_T ru left join SYS_ROLE_T r on ru.SYS_ROLE_ID=r.ID where ru.SYS_USER_ID=#userId#  and r.STATUS='00'
    and to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')>=ru.EFFECT and (to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')<=ru.EXPIRED or ru.EXPIRED IS NULL) and r.DE_BY is null and ru.DE_AT is null 
selectOwnDeptBySysUserId
===
*查询用户原始机构

    select * from DEPT_T where ID=#deptId# and DE_AT is null and STATUS='00'
    
selectPostBySysUserId
===
*查询用户职位

    select up.*,p.*,
    (select SU_NAME from SYS_USER_T  where ID=up.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=up.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=up.DE_BY) as deByName
    from USER_POST_T up left join POST_T p on up.POST_ID=P.ID where up.SYS_USER_ID=#userId#  and p.STATUS='00'
    and p.DE_BY is null and up.DE_AT is null 
selectWidgetBySysUserId
===
*查询用户小组件
    
    @var now=date();
    select uw.*,w.*,
    (select SU_NAME from SYS_USER_T  where ID=uw.CR_BY) as crByName,
    (select SU_NAME from SYS_USER_T  where ID=uw.UP_BY) as upByName,
    (select SU_NAME from SYS_USER_T  where ID=uw.DE_BY) as deByName
    from USER_WIDGET_T uw left join WIDGET_T w on uw.WIDGET_ID=w.ID where uw.SYS_USER_ID=#userId# and w.STATUS='00'
    and w.DE_AT is null and uw.DE_AT is null 
    and to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')>=uw.EFFECT and (to_date(#now,dateFormat='yyyy-MM-dd HH:mm:ss'#,'yyyy-MM-dd HH24:mi:ss')<=uw.EXPIRED or uw.EXPIRED IS NULL)

