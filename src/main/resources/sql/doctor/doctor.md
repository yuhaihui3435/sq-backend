sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=doctor.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=doctor.UP_BY) as upByName from DOCTOR_T doctor where  #use("condition")#
	@orm.many({"id":"doctorId"},"doctor.doctorTag.sample","DoctorTag");
	@orm.many({"id":"doctorId"},"doctor.doctorPic.sample","DoctorPic");
	

sample$count
===
    select count(1) from DOCTOR_T doctor where #use("condition")#

cols
===
	doctor.SITE,doctor.CR_AT,doctor.INTRODUCTION,doctor.NOTICE,doctor.LOGIN_ID,doctor.FOR_VISITORS,doctor.CITY,doctor.AREA,doctor.LEVEL,doctor.PROVINCE,doctor.ID,doctor.DE_BY,doctor.AVATAR,doctor.UP_AT,doctor.DE_AT,doctor.CR_BY,doctor.NAME,doctor.DURATION,doctor.UP_BY,doctor.PRICE

updateSample
===

	doctor.SITE=#site#,doctor.CR_AT=#crAt#,doctor.INTRODUCTION=#introduction#,doctor.NOTICE=#notice#,doctor.LOGIN_ID=#loginId#,doctor.FOR_VISITORS=#forVisitors#,doctor.CITY=#city#,doctor.AREA=#area#,doctor.LEVEL=#level#,doctor.PROVINCE=#province#,doctor.ID=#id#,doctor.DE_BY=#deBy#,doctor.AVATAR=#avatar#,doctor.UP_AT=#upAt#,doctor.DE_AT=#deAt#,doctor.CR_BY=#crBy#,doctor.NAME=#name#,doctor.DURATION=#duration#,doctor.UP_BY=#upBy#,doctor.PRICE=#price#

condition
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



