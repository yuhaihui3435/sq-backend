sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=userLogin.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=userLogin.DE_BY) as deByName from USER_LOGIN_T userLogin where  #use("condition")#

sample$count
===
    select count(1) from USER_LOGIN_T userLogin where #use("condition")#

cols
===
	userLogin.ID,userLogin.WX_OPEN_ID,userLogin.PWD,userLogin.UP_AT,userLogin.PHONE,userLogin.EMAIL,userLogin.QQ_OPEN_ID,userLogin.ACCOUNT,userLogin.STATUS,userLogin.UP_BY,userLogin.DE_AT,userLogin.CR_AT,userLogin.DE_BY,userLogin.TYPE

updateSample
===

	userLogin.ID=#id#,userLogin.WX_OPEN_ID=#wxOpenId#,userLogin.PWD=#pwd#,userLogin.UP_AT=#upAt#,userLogin.PHONE=#phone#,userLogin.EMAIL=#email#,userLogin.QQ_OPEN_ID=#qqOpenId#,userLogin.ACCOUNT=#account#,userLogin.STATUS=#status#,userLogin.UP_BY=#upBy#,userLogin.DE_AT=#deAt#,userLogin.CR_AT=#crAt#,userLogin.DE_BY=#deBy#,userLogin.TYPE=#type#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
	 and userLogin.ID=#id#
	@}
	@if(!isEmpty(wxOpenId)){
	 and userLogin.WX_OPEN_ID=#wxOpenId#
	@}
	@if(!isEmpty(pwd)){
	 and userLogin.PWD=#pwd#
	@}
	@if(!isEmpty(upAt)){
	 and userLogin.UP_AT=#upAt#
	@}
	@if(!isEmpty(phone)){
	 and userLogin.PHONE=#phone#
	@}
	@if(!isEmpty(email)){
	 and userLogin.EMAIL=#email#
	@}
	@if(!isEmpty(qqOpenId)){
	 and userLogin.QQ_OPEN_ID=#qqOpenId#
	@}
	@if(!isEmpty(account)){
	 and userLogin.ACCOUNT=#account#
	@}
	@if(!isEmpty(status)){
	 and userLogin.STATUS=#status#
	@}
	@if(!isEmpty(upBy)){
	 and userLogin.UP_BY=#upBy#
	@}
	@if(!isEmpty(deAt)){
	 and userLogin.DE_AT=#deAt#
	@}
	@if(!isEmpty(crAt)){
	 and userLogin.CR_AT=#crAt#
	@}
	@if(!isEmpty(deBy)){
	 and userLogin.DE_BY=#deBy#
	@}
	@if(!isEmpty(type)){
     and userLogin.TYPE=#type#
    @}



