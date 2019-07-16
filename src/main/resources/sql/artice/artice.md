sample
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=artice.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=artice.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=artice.DE_BY) as deByName 
	from ARTICE_T artice LEFT JOIN artice_tag_t tag ON artice.ID=tag.ARTICE_ID where 
	
	@if(!isEmpty(tagId)){
        	 1=1 and tag.tag_id in (
        	    @for(id in tagId){
                   #id#  #text(idLP.last?"":"," )#
                @}
             )
             and
        @}
        
        #use("condition")#
        @orm.many({"id":"articeId"},"artice.articeTag.sample","ArticeTag");


sample$count
===
    select count(1) from ARTICE_T artice where #use("condition")#

cols
===
	artice.topShow,artice.ORIGIN,artice.ID,artice.READING_TIME,artice.EFFECT,artice.COLUMN_ID,artice.TPL_NAME,artice.TITLE,artice.AUTHOR,artice.EXPIRED,artice.UP_BY,artice.SUMMARY_EN,artice.DETAIL,artice.PUBLISH_STATUS,artice.DE_AT,artice.COVER_PIC,artice.REPORT_STATUS,artice.CR_BY,artice.CR_AT,artice.DE_BY,artice.UP_AT,artice.TOP,artice.LEAVE_MSG_STATUS,artice.TITLE_EN,artice.DETAIL_EN,artice.SUMMARY

updateSample
===

	artice.topShow=#topShow#,artice.ORIGIN=#origin#,artice.ID=#id#,artice.READING_TIME=#readingTime#,artice.EFFECT=#effect#,artice.COLUMN_ID=#columnId#,artice.TPL_NAME=#tplName#,artice.TITLE=#title#,artice.AUTHOR=#author#,artice.EXPIRED=#expired#,artice.UP_BY=#upBy#,artice.SUMMARY_EN=#summaryEn#,artice.DETAIL=#detail#,artice.PUBLISH_STATUS=#publishStatus#,artice.DE_AT=#deAt#,artice.COVER_PIC=#coverPic#,artice.REPORT_STATUS=#reportStatus#,artice.CR_BY=#crBy#,artice.CR_AT=#crAt#,artice.DE_BY=#deBy#,artice.UP_AT=#upAt#,artice.TOP=#top#,artice.LEAVE_MSG_STATUS=#leaveMsgStatus#,artice.TITLE_EN=#titleEn#,artice.DETAIL_EN=#detailEn#,artice.SUMMARY=#summary#

condition
===

	 1=1 and DE_AT is null
	
	
	@if(!isEmpty(origin)){
     and artice.ORIGIN=#origin#
    @}
	@if(!isEmpty(id)){
	 and artice.ID=#id#
	@}
	@if(!isEmpty(readingTime)){
	 and artice.READING_TIME=#readingTime#
	@}
	@if(!isEmpty(effect)){
	 and artice.EFFECT=#effect#
	@}
	@if(!isEmpty(columnId)&&columnId!=0){
	 and artice.COLUMN_ID=#columnId#
	@}
	@if(!isEmpty(tplName)){
	 and artice.TPL_NAME=#tplName#
	@}
	@if(!isEmpty(title)){
	 and (artice.TITLE like #'%'+title+'%'# )
	@}
	@if(!isEmpty(author)){
	 and artice.AUTHOR=#author#
	@}
	@if(!isEmpty(expired)){
	 and artice.EXPIRED=#expired#
	@}
	@if(!isEmpty(upBy)){
	 and artice.UP_BY=#upBy#
	@}
	@if(!isEmpty(summaryEn)){
	 and artice.SUMMARY_EN=#summaryEn#
	@}
	@if(!isEmpty(detail)){
	 and artice.DETAIL=#detail#
	@}
	@if(!isEmpty(publishStatus)){
	 and artice.PUBLISH_STATUS=#publishStatus#
	@}
	@if(!isEmpty(deAt)){
	 and artice.DE_AT=#deAt#
	@}
	@if(!isEmpty(coverPic)){
	 and artice.COVER_PIC=#coverPic#
	@}
	@if(!isEmpty(reportStatus)){
	 and artice.REPORT_STATUS=#reportStatus#
	@}
	@if(!isEmpty(crBy)){
	 and artice.CR_BY=#crBy#
	@}
	@if(!isEmpty(crAt)){
	 and artice.CR_AT=#crAt#
	@}
	@if(!isEmpty(deBy)){
	 and artice.DE_BY=#deBy#
	@}
	@if(!isEmpty(upAt)){
	 and artice.UP_AT=#upAt#
	@}
	@if(!isEmpty(top)){
	 and artice.TOP=#top#
	@}
	@if(!isEmpty(leaveMsgStatus)){
	 and artice.LEAVE_MSG_STATUS=#leaveMsgStatus#
	@}
	@if(!isEmpty(titleEn)){
	 and artice.TITLE_EN=#titleEn#
	@}
	@if(!isEmpty(detailEn)){
	 and artice.DETAIL_EN=#detailEn#
	@}
	@if(!isEmpty(summary)){
	 and artice.SUMMARY=#summary#
	@}
    @if(!isEmpty(topShow)){
	 and artice.TOP_SHOW=#topShow#
	@}
	order by artice.UP_AT desc



