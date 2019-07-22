sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=lesson.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=lesson.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=lesson.UP_BY) as upByName from LESSON_T lesson where  #use("condition")#

sample$count
===
    select count(1) from LESSON_T lesson where #use("condition")#

cols
===
	lesson.TEACHER_NAME,lesson.SUMMARY,lesson.PUBLICIZE_TYPE,lesson.INDEX_SHOW,lesson.INDEX_SHOW_SEQ,lesson.LESSON_END_AT,lesson.LESSON_DAYS,lesson.CR_AT,lesson.NAME,lesson.STATUS,lesson.PROVICE,lesson.CITY,lesson.AREA,lesson.PRICE,lesson.ID,lesson.DE_BY,lesson.SITE,lesson.UP_AT,lesson.DESCRIBLE,lesson.DE_AT,lesson.CR_BY,lesson.PUBLICIZE,lesson.UP_BY,lesson.LESSON_AT,lesson.LESSON_STATUS,lesson.THE_WAY,lesson.DOCTOR_ID

updateSample
===

	lesson.TEACHER_NAME=#teacherName#,lesson.SUMMARY=#summary#,lesson.PUBLICIZE_TYPE=#publicizeType#,lesson.INDEX_SHOW=#indexShow#,lesson.INDEX_SHOW_SEQ=#indexShowSeq#,lesson.LESSON_DAYS=#lessonDays#,lesson.LESSON_END_AT=#lessonEndAt#,lesson.CR_AT=#crAt#,lesson.NAME=#name#,lesson.STATUS=#status#,lesson.PROVICE=#provice#,lesson.CITY=#city#,lesson.AREA=#area#,lesson.PRICE=#price#,lesson.ID=#id#,lesson.DE_BY=#deBy#,lesson.SITE=#site#,lesson.UP_AT=#upAt#,lesson.DESCRIBLE=#describle#,lesson.DE_AT=#deAt#,lesson.CR_BY=#crBy#,lesson.PUBLICIZE=#publicize#,lesson.UP_BY=#upBy#,lesson.LESSON_AT=#lessonAt#,lesson.LESSON_STATUS=#lessonStatus#,lesson.THE_WAY=#theWay#,lesson.DOCTOR_ID=#doctorId#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(lessonStatuses)){
	and lesson.LESSON_STATUS in (
         @for(id in lessonStatuses){
         #id#  #text(idLP.last?"":"," )#
         @}
         )
         @}	
    @if(!isEmpty(lessonEndAt)){
     and lesson.LESSON_END_AT=#lessonEndAt#
    @}	
	@if(!isEmpty(lessonDays)){
     and lesson.LESSON_DAYS=#lessonDays#
    @}
	@if(!isEmpty(crAt)){
	 and lesson.CR_AT=#crAt#
	@}
	@if(!isEmpty(name)){
	 and lesson.NAME like #'%'+name+'%'# 
	@}
	@if(!isEmpty(status)){
	 and lesson.STATUS=#status#
	@}
	@if(!isEmpty(provice)){
	 and lesson.PROVICE=#provice#
	@}
	@if(!isEmpty(city)){
	 and lesson.CITY=#city#
	@}
	@if(!isEmpty(area)){
	 and lesson.AREA=#area#
	@}
	@if(!isEmpty(price)){
	 and lesson.PRICE=#price#
	@}
	@if(!isEmpty(id)){
	 and lesson.ID=#id#
	@}
	@if(!isEmpty(deBy)){
	 and lesson.DE_BY=#deBy#
	@}
	@if(!isEmpty(site)){
	 and lesson.SITE=#site#
	@}
	@if(!isEmpty(upAt)){
	 and lesson.UP_AT=#upAt#
	@}
	@if(!isEmpty(describle)){
	 and lesson.DESCRIBLE=#describle#
	@}
	@if(!isEmpty(deAt)){
	 and lesson.DE_AT=#deAt#
	@}
	@if(!isEmpty(crBy)){
	 and lesson.CR_BY=#crBy#
	@}
	@if(!isEmpty(publicize)){
	 and lesson.PUBLICIZE=#publicize#
	@}
	@if(!isEmpty(upBy)){
	 and lesson.UP_BY=#upBy#
	@}
	@if(!isEmpty(lessonAt)){
	 and lesson.LESSON_AT=#lessonAt#
	@}
	@if(!isEmpty(lessonStatus)){
	 and lesson.LESSON_STATUS=#lessonStatus#
	@}
	@if(!isEmpty(theWay)){
	 and lesson.THE_WAY=#theWay#
	@}
	@if(!isEmpty(doctorId)){
	 and lesson.DOCTOR_ID=#doctorId#
	@}
	
	
	@if(!isEmpty(publicizeType)){
    	 and lesson.PUBLICIZE_TYPE=#publicizeType#
    	@}
    	@if(!isEmpty(indexShow)){
        	 and lesson.INDEX_SHOW=#indexShow#
        	@}
        	@if(!isEmpty(indexShowSeq)){
            	 and lesson.INDEX_SHOW_SEQ=#indexShowSeq#
            	@}
            	@if(!isEmpty(teacherName)){
                            	 and lesson.TEACHER_NAME=#teacherName#
                            	@}



