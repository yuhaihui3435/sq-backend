sample
===

	select #use("cols")# from USER_INFO_T userInfo where  #use("condition")#
	@orm.single({"loginId":"id"},"UserLogin");

sample$count
===
    select count(1) from USER_INFO_T userInfo where #use("condition")#

cols
===
	userInfo.REALNAME,userInfo.EDUCATION,userInfo.PROVINCE,userInfo.TYPE,userInfo.BIRTHDAY,userInfo.LOGIN_ID,userInfo.ID,userInfo.ADDRESS,userInfo.SEX,userInfo.AVATAR,userInfo.CAREER,userInfo.NICKNAME,userInfo.CITY,userInfo.AREA

updateSample
===

	userInfo.REALNAME=#realname#,userInfo.EDUCATION=#education#,userInfo.PROVINCE=#province#,userInfo.TYPE=#type#,userInfo.BIRTHDAY=#birthday#,userInfo.LOGIN_ID=#loginId#,userInfo.ID=#id#,userInfo.ADDRESS=#address#,userInfo.SEX=#sex#,userInfo.AVATAR=#avatar#,userInfo.CAREER=#career#,userInfo.NICKNAME=#nickname#,userInfo.CITY=#city#,userInfo.AREA=#area#

condition
===

	1 = 1 
	@if(!isEmpty(realname)){
	 and userInfo.REALNAME=#realname#
	@}
	@if(!isEmpty(education)){
	 and userInfo.EDUCATION=#education#
	@}
	@if(!isEmpty(province)){
	 and userInfo.PROVINCE=#province#
	@}
	@if(!isEmpty(type)){
	 and userInfo.TYPE=#type#
	@}
	@if(!isEmpty(birthday)){
	 and userInfo.BIRTHDAY=#birthday#
	@}
	@if(!isEmpty(loginId)){
	 and userInfo.LOGIN_ID=#loginId#
	@}
	@if(!isEmpty(id)){
	 and userInfo.ID=#id#
	@}
	@if(!isEmpty(address)){
	 and userInfo.ADDRESS=#address#
	@}
	@if(!isEmpty(sex)){
	 and userInfo.SEX=#sex#
	@}
	@if(!isEmpty(avatar)){
	 and userInfo.AVATAR=#avatar#
	@}
	@if(!isEmpty(career)){
	 and userInfo.CAREER=#career#
	@}
	@if(!isEmpty(nickname)){
	 and userInfo.NICKNAME=#nickname#
	@}
	@if(!isEmpty(city)){
	 and userInfo.CITY=#city#
	@}
	@if(!isEmpty(area)){
	 and userInfo.AREA=#area#
	@}



