sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=doctor.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.UP_BY) as upByName from DOCTOR_T doctor
	 left join USER_LOGIN_T userLogin on doctor.LOGIN_ID=userLogin.id where #use("condition")#
	
	@orm.many({"id":"doctorId"},"doctor.doctor.selectDoctorPic","DoctorPic");
	@orm.many({"id":"doctorId"},"doctor.doctor.selectDoctorTag","DoctorTag");
	@orm.single({"loginId":"id"},"com.neuray.wp.entity.user.UserLogin");

sampleApi
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=doctor.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.UP_BY) as upByName from 
    (SELECT DISTINCT d.* FROM doctor_t AS d LEFT JOIN doctor_tag_t AS dt ON d.ID=dt.DOCTOR_ID
    @if(!isEmpty(tagCondition)){
        where dt.tag_id in (
            @for(id in tagCondition){
                #id#  #text(idLP.last?"":"," )#
            @}
        )
    @}
    )
	doctor where  #use("conditionApi")#
	@orm.many({"id":"doctorId"},"doctor.doctorTag.sample","DoctorTag");
	@orm.many({"id":"doctorId"},"doctor.doctorPic.sample","DoctorPic");
	@orm.single({"loginId":"id"},"com.neuray.wp.entity.user.UserLogin");


sample$count
===
    select count(1) from DOCTOR_T doctor left join USER_LOGIN_T userLogin on doctor.LOGIN_ID=userLogin.id where  #use("condition")#

sampleApi$count
===
    select count(1) from DOCTOR_T doctor where #use("conditionApi")#


cols
===
	doctor.INDEX_SHOW,doctor.INDEX_SHOW_SEQ,doctor.SITE,doctor.CR_AT,doctor.INTRODUCTION,doctor.NOTICE,doctor.LOGIN_ID,doctor.FOR_VISITORS,doctor.CITY,doctor.AREA,doctor.LEVEL,doctor.PROVINCE,doctor.ID,doctor.DE_BY,doctor.AVATAR,doctor.UP_AT,doctor.DE_AT,doctor.CR_BY,doctor.NAME,doctor.DURATION,doctor.UP_BY,doctor.PRICE

updateSample
===

	doctor.INDEX_SHOW,doctor.INDEX_SHOW_SEQ,doctor.SITE=#site#,doctor.CR_AT=#crAt#,doctor.INTRODUCTION=#introduction#,doctor.NOTICE=#notice#,doctor.LOGIN_ID=#loginId#,doctor.FOR_VISITORS=#forVisitors#,doctor.CITY=#city#,doctor.AREA=#area#,doctor.LEVEL=#level#,doctor.PROVINCE=#province#,doctor.ID=#id#,doctor.DE_BY=#deBy#,doctor.AVATAR=#avatar#,doctor.UP_AT=#upAt#,doctor.DE_AT=#deAt#,doctor.CR_BY=#crBy#,doctor.NAME=#name#,doctor.DURATION=#duration#,doctor.UP_BY=#upBy#,doctor.PRICE=#price#

condition
===

	1 = 1 and doctor.DE_AT is null
	@if(!isEmpty(site)){
	 and doctor.SITE=#site#
	@}
	@if(!isEmpty(crAt)){
	 and doctor.CR_AT=#crAt#
	@}
	@if(!isEmpty(introduction)){
	 and doctor.INTRODUCTION=#introduction#
	@}
	@if(!isEmpty(notice)){
	 and doctor.NOTICE=#notice#
	@}
	@if(!isEmpty(loginId)){
	 and doctor.LOGIN_ID=#loginId#
	@}
	@if(!isEmpty(forVisitors)){
	 and doctor.FOR_VISITORS=#forVisitors#
	@}
	@if(!isEmpty(city)){
	 and doctor.CITY=#city#
	@}
	@if(!isEmpty(area)){
	 and doctor.AREA=#area#
	@}
	@if(!isEmpty(level)){
	 and doctor.LEVEL=#level#
	@}
	@if(!isEmpty(province)){
	 and doctor.PROVINCE=#province#
	@}
	@if(!isEmpty(id)){
	 and doctor.ID=#id#
	@}
	@if(!isEmpty(deBy)){
	 and doctor.DE_BY=#deBy#
	@}
	@if(!isEmpty(avatar)){
	 and doctor.AVATAR=#avatar#
	@}
	@if(!isEmpty(upAt)){
	 and doctor.UP_AT=#upAt#
	@}
	@if(!isEmpty(deAt)){
	 and doctor.DE_AT=#deAt#
	@}
	@if(!isEmpty(crBy)){
	 and doctor.CR_BY=#crBy#
	@}
	@if(!isEmpty(name)){
	 and doctor.NAME=#name#
	@}
	@if(!isEmpty(duration)){
	 and doctor.DURATION=#duration#
	@}
	@if(!isEmpty(upBy)){
	 and doctor.UP_BY=#upBy#
	@}
	@if(!isEmpty(price)){
	 and doctor.PRICE=#price#
	@}
	@if(!isEmpty(indexShow)){
      and doctor.INDEX_SHOW=#indexShow#
    @}
    @if(!isEmpty(indexShowSeq)){
      and doctor.INDEX_SHOW_SEQ=#indexShowSeq#
    @}
    @if(!isEmpty(userLogin.status)){
      and userLogin.STATUS=#userLogin.status#
    @}
    @if(!isEmpty(userLogin.account)){
      and userLogin.ACCOUNT=#userLogin.account#
    @}
    @if(!isEmpty(userLogin.phone)){
      and userLogin.PHONE=#userLogin.phone#
    @}
    @if(!isEmpty(userLogin.email)){
      and userLogin.EMAIL=#userLogin.email#
    @}
    
conditionApi
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(site)){
	 and doctor.SITE=#site#
	@}
	@if(!isEmpty(crAt)){
	 and doctor.CR_AT=#crAt#
	@}
	@if(!isEmpty(introduction)){
	 and doctor.INTRODUCTION=#introduction#
	@}
	@if(!isEmpty(notice)){
	 and doctor.NOTICE=#notice#
	@}
	@if(!isEmpty(loginId)){
	 and doctor.LOGIN_ID=#loginId#
	@}
	@if(!isEmpty(forVisitors)){
	 and doctor.FOR_VISITORS=#forVisitors#
	@}
	@if(!isEmpty(city)){
	 and doctor.CITY=#city#
	@}
	@if(!isEmpty(area)){
	 and doctor.AREA=#area#
	@}
	@if(!isEmpty(level)){
	 and doctor.LEVEL=#level#
	@}
	@if(!isEmpty(province)){
	 and doctor.PROVINCE=#province#
	@}
	@if(!isEmpty(id)){
	 and doctor.ID=#id#
	@}
	@if(!isEmpty(deBy)){
	 and doctor.DE_BY=#deBy#
	@}
	@if(!isEmpty(avatar)){
	 and doctor.AVATAR=#avatar#
	@}
	@if(!isEmpty(upAt)){
	 and doctor.UP_AT=#upAt#
	@}
	@if(!isEmpty(deAt)){
	 and doctor.DE_AT=#deAt#
	@}
	@if(!isEmpty(crBy)){
	 and doctor.CR_BY=#crBy#
	@}
	@if(!isEmpty(name)){
	 and doctor.NAME like #'%'+name+'%'# 
	@}
	@if(!isEmpty(duration)){
	 and doctor.DURATION=#duration#
	@}
	@if(!isEmpty(upBy)){
	 and doctor.UP_BY=#upBy#
	@}
	@if(!isEmpty(price)){
	 and doctor.PRICE=#price#
	@}
	@if(!isEmpty(priceSmall)){
    	 and doctor.PRICE>=#priceSmall#
    	@}
    	@if(!isEmpty(priceLarge)){
        	 and doctor.PRICE<=#priceLarge#
        	@}
	@if(!isEmpty(indexShow)){
      and doctor.INDEX_SHOW=#indexShow#
    @}
    @if(!isEmpty(indexShowSeq)){
      and doctor.INDEX_SHOW_SEQ=#indexShowSeq#
    @}
    

selectDoctorPic
===

	select * from DOCTOR_PIC_T doctorPic where  doctorPic.doctor_id=#doctorId# 
	@orm.single({"picId":"id"},"com.neuray.wp.entity.FileMap");

selectDoctorTag
===

	select * from DOCTOR_TAG_T doctorTag where  doctorTag.doctor_id=#doctorId# 
	@orm.single({"tagId":"id"},"com.neuray.wp.entity.DictItem");
